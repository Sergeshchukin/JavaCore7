package utils;

public class TemperatureConverters {

    public static double FahrenheitCelsius(double value) {


        double result = (value - 32) / 1.8;


        result = Math.round(result * 10);
        return result / 10;
    }

}
