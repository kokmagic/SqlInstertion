package org.example;

import java.sql.*;
import java.time.LocalDateTime;
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
                String sql2 = "INSERT INTO device_events (device_id, event_id) VALUES (6, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                Object[] data = generateRandomData();
                preparedStatement.setLong(1, (Long) data[0]);
                preparedStatement2.setLong(1, (Long) data[0]);
                preparedStatement.setString(2, (String) data[1]);
                preparedStatement.setDouble(3, (Double) data[2]);
                preparedStatement.setTimestamp(4, Timestamp.valueOf((LocalDateTime) data[3]));
                preparedStatement.setString(5, (String) data[4]);
                preparedStatement.setString(6, (String) data[5]);
                preparedStatement.executeUpdate();
                preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    public static int generateRandomTypeEvent() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // генерируем число от 0 до 99
//        return random.nextInt(3);
        if (randomNumber < 80) {
            return 0; // 80% вероятность
        } else if (randomNumber < 95) {
            return 1; // 15% вероятность
        } else {
            return 2; // 5% вероятность
        }
    }
    public static int generateRandomTypeCar() {
        Random random = new Random();
        int randomNumber = random.nextInt(100); // генерируем число от 0 до 99
//        return random.nextInt(4);
        if (randomNumber < 70) {
            return 0; // 70% вероятность
        } else if (randomNumber < 85) {
            return 1; // 15% вероятность
        } else if (randomNumber < 90) {
            return 2; // 5% вероятность
        } else {
            return 3; // 10% вероятность
        }
    }
    // Функция для генерации случайных значений
    public static Object[] generateRandomData() {
        Random random = new Random();
        long id = random.nextInt(10000000) ;
        int rand = (random.nextInt(1));
        LocalDateTime time = LocalDateTime.parse("2023-04-10T" + String.format("%02d", random.nextInt(24)) + ":" + String.format("%02d", random.nextInt(60)) + ":" + String.format("%02d", random.nextInt(60)));
        String carId = randomCarId();
        String typeOfCar = Integer.toString(generateRandomTypeCar());
        String typeOfEvent = Integer.toString(generateRandomTypeEvent());
        double speed = Math.round(random.nextDouble() * 100 * 100) / 100.0;
        return new Object[] {id, carId, speed, time, typeOfCar, typeOfEvent};
    }

    // Функция для генерации случайной строки заданной длины
    public static String randomCarId() {
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        String characters = "АВЕКМНОРСТУХ";
        builder.append(characters.charAt(random.nextInt(characters.length())));
        for (int i = 0; i < 3; i++) {
            builder.append(random.nextInt(10));
        }
        builder.append(characters.charAt(random.nextInt(characters.length())));
        builder.append(characters.charAt(random.nextInt(characters.length())));
        builder.append(96);
        return builder.toString();
    }
}
