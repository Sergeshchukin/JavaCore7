package java;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import enums.Periods;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import serializers.DailyForecast;
import serializers.Temperature5Days;
import serializers.WeatherIn5DaysResponse;
import serializers.WeatherResponse;
import utils.Weather;
import utils.dbHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static utils.TemperatureConverters.FahrenheitCelsius;


public class AccuWeatherProvider implements WeatherProvider {

    private static final String BASE_HOST = "dataservice.accuweather.com";
    private static final String FORECAST_ENDPOINT = "forecasts";
    private static final String CURRENT_CONDITIONS_ENDPOINT = "currentconditions";
    private static final String API_VERSION = "v1";
    private static final String API_KEY = ApplicationGlobalState.getInstance().getApiKey();
    private  dbHandler handler;
    {
        try{ handler = new dbHandler();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void getWeather(Periods periods) throws IOException {
        String cityKey = detectCityKey();
        if (periods.equals(Periods.NOW)) {
            HttpUrl url = new HttpUrl.Builder()
                    .scheme("http")
                    .host(BASE_HOST)
                    .addPathSegment(CURRENT_CONDITIONS_ENDPOINT)
                    .addPathSegment(API_VERSION)
                    .addPathSegment(cityKey)
                    .addQueryParameter("apikey", API_KEY)
                    .addQueryParameter("language", "ru-ru")
                    .build();

            Request request = new Request.Builder()
                    .addHeader("accept", "application/json")
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
            String rawBody = response.body().string();


            List<WeatherResponse> weatherResponse = objectMapper.readValue(
                    rawBody,
                    new TypeReference<>() {});


            if (weatherResponse.isEmpty()) {
                System.out.println("Not found result");
                return;
            }


            WeatherResponse currentConditionResponse = weatherResponse.get(0);

            System.out.printf("?? ???????????? %s ???? ???????? %s ?????????????????? \"%s\", ?????????????????????? %s %s\n\n",
                    ApplicationGlobalState.getInstance().getSelectedCity(),
                    currentConditionResponse.getLocalObservationDateTime(),
                    currentConditionResponse.getWeatherText(),
                    currentConditionResponse.getTemperature().getMetric().getValue(),
                    currentConditionResponse.getTemperature().getMetric().getUnit());
            handler.add(ApplicationGlobalState.getInstance().getSelectedCity(), currentConditionResponse.getLocalObservationDateTime(), currentConditionResponse.getTemperature().getMetric().getValue(), currentConditionResponse.getWeatherText());
        }
    }

    @Override
    public void getWeatherIn5Days() throws IOException {
        String cityKey = detectCityKey();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment(FORECAST_ENDPOINT)
                .addPathSegment(API_VERSION)
                .addPathSegments("daily/5day")
                .addPathSegment(cityKey)
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("language", "ru-ru")
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        String rawBody = response.body().string();


        WeatherIn5DaysResponse weatherIn5DaysResponse = objectMapper.readValue(
                rawBody,
                WeatherIn5DaysResponse.class);


        List<DailyForecast> dailyForecasts = weatherIn5DaysResponse.getDailyForecasts();
        for(DailyForecast dailyForecast: dailyForecasts) {
            Temperature5Days temperature = dailyForecast.getTemperature();
            System.out.printf("?? ???????????? %s ???? ???????? %s ?????????????????? ???????? \"%s\", ?????????? \"%s\", ?????????????????????? - ???? %s ?? ???? %s ??\n",
                    ApplicationGlobalState.getInstance().getSelectedCity(),
                    dailyForecast.getDate(),
                    dailyForecast.getDay().getIconPhrase(),
                    dailyForecast.getNight().getIconPhrase(),
                    FahrenheitCelsius(temperature.getMinimum().getValue()),
                    FahrenheitCelsius(temperature.getMaximum().getValue()));
            handler.add(ApplicationGlobalState.getInstance().getSelectedCity(), dailyForecast.getDate(), FahrenheitCelsius(temperature.getMinimum().getValue()), dailyForecast.getDay().getIconPhrase());
        }


        System.out.println();
    }

    public String detectCityKey() throws IOException {
        String selectedCity = ApplicationGlobalState.getInstance().getSelectedCity();

        HttpUrl detectLocationURL = new HttpUrl.Builder()
                .scheme("http")
                .host(BASE_HOST)
                .addPathSegment("locations")
                .addPathSegment(API_VERSION)
                .addPathSegment("cities")
                .addPathSegment("autocomplete")
                .addQueryParameter("apikey", API_KEY)
                .addQueryParameter("q", selectedCity)
                .build();

        Request request = new Request.Builder()
                .addHeader("accept", "application/json")
                .url(detectLocationURL)
                .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()) {
            throw new IOException("???????????????????? ???????????????? ???????????????????? ?? ????????????. " +
                    "?????? ???????????? ?????????????? = " + response.code() + " ???????? ???????????? = " + response.body().string());
        }
        String jsonResponse = response.body().string();
        System.out.println("?????????????????? ?????????? ???????????? " + selectedCity);

        if (objectMapper.readTree(jsonResponse).size() > 0) {
            String cityName = objectMapper.readTree(jsonResponse).get(0).at("/LocalizedName").asText();
            String countryName = objectMapper.readTree(jsonResponse).get(0).at("/Country/LocalizedName").asText();
            System.out.println("???????????? ?????????? " + cityName + " ?? ???????????? " + countryName);
        } else throw new IOException("Server returns 0 cities");

        return objectMapper.readTree(jsonResponse).get(0).at("/Key").asText();
    }

    @Override
    public void getHistory(){
        List<Weather> listWeather = handler.getAllWeather();
        for (Weather weather:listWeather){
            System.out.println("?? ???????????? " + weather.city + " ???? ???????? " + weather.date + " ?????????????????????? " + weather.temperature + " ???????????? " + weather.weatherText);
        }
    }
}