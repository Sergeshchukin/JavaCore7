package utils;

public class Weather {
    public String city;
    public String date;
    public double temperature;
    public String weatherText;

    public Weather(String city, String date, double temperature, String weatherText) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.weatherText = weatherText;
    }
}