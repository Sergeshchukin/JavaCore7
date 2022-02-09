package utils;

import org.sqlite.JDBC;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dbHandler {

    private final String PATH_TO_DB = "jdbc:sqlite:F:\\core_weather.db";

    private Connection connection;

    public dbHandler () throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(PATH_TO_DB);
    }


    public void add (String city, String date, double temperature, String weatherText){
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO WEATHERACC('city', 'date', 'temperature', 'weatherText') " +
                        "VALUES(?, ?, ?, ?)"
        )) {
            statement.setObject(1, city);
            statement.setObject(2, date);
            statement.setObject(3, temperature);
            statement.setObject(4, weatherText);
            statement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<Weather> getAllWeather(){
        List<Weather> weathers = new ArrayList<>();
        try(Statement statement = this.connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(
                    "SELECT city, date, temperature, weatherText from WEATHERACC");
            while (resultSet.next()){
                weathers.add(new Weather(
                        resultSet.getString("city"),
                        resultSet.getString("date"),
                        resultSet.getDouble("temperature"),
                        resultSet.getString("weatherText")
                ));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return weathers;
    }
}
