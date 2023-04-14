package org.example;

import java.sql.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "password";
        int n = 10; // количество вставок

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();

            // Цикл для вставки случайных данных в таблицу
            for (int i = 0; i < n; i++) {
                String sql = "INSERT INTO events (id, car_id, speed, time, type_of_car, type_of_event) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                Object[] data = generateRandomData();
                preparedStatement.setLong(1, (Long) data[0]);
                preparedStatement.setString(2, (String) data[1]);
                preparedStatement.setString(3, (String) data[2]);
                preparedStatement.setString(4, (String) data[3]);
                preparedStatement.setString(5, (String) data[4]);
                preparedStatement.setString(6, (String) data[5]);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // Функция для генерации случайных значений
    public static Object[] generateRandomData() {
        Random random = new Random();
        long id = random.nextLong() + 1;
        String time = "2023-04-10 " + String.format("%02d", random.nextInt(24)) + ":" + String.format("%02d", random.nextInt(60)) + ":" + String.format("%02d", random.nextInt(60));
        String carId = randomString(5);
        String typeOfCar = Integer.toString(random.nextInt(5));
        String typeOfEvent = Integer.toString(random.nextInt(3));
        String speed = Double.toString(Math.round(random.nextDouble() * 100 * 100) / 100.0);
        return new Object[] {id, carId, speed, time, typeOfCar, typeOfEvent};
    }

    // Функция для генерации случайной строки заданной длины
    public static String randomString(int length) {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 0; i < length; i++) {
            builder.append(characters.charAt(random.nextInt(characters.length())));
        }
        return builder.toString();
    }
}
