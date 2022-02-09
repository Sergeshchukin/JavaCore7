package serializers;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class WeatherResponse {
    private String localObservationDateTime = null;
    private String weatherText = null;
    private Temperature temperature;

    @JsonSetter("LocalObservationDateTime")
    public void setLocalObservationDateTime(String value) {
        this.localObservationDateTime = value;
    }


    @JsonGetter("LocalObservationDateTime")
    public String getLocalObservationDateTime() {
        String[] dateParts = this.localObservationDateTime.split("T");
        return dateParts[0];
    }

    @JsonGetter("WeatherText")
    public String getWeatherText() {
        return this.weatherText;
    }

    @JsonSetter("WeatherText")
    public void setWeatherText(String value) {
        this.weatherText = value;
    }

    @JsonGetter("Temperature")
    public Temperature getTemperature() {
        return this.temperature;
    }

    @JsonSetter("Temperature")
    public void setTemperature(Temperature value) {
        this.temperature = value;
    }

    public WeatherResponse() {}
}
