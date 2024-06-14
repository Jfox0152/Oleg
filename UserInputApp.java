import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.regex.*;

public class UserInputApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол (пример: Иванов Иван Иванович 01.01.1990 89101234567 m)");

        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        try {
            if (parts.length != 6) {
                throw new IllegalArgumentException("Неверное количество данных. Ожидалось 6, получено: " + parts.length);
            }

            String lastName = parts[0];
            String firstName = parts[1];
            String middleName = parts[2];
            String birthDate = parts[3];
            String phoneNumber = parts[4];
            String gender = parts[5];

            validateDate(birthDate);
            validatePhoneNumber(phoneNumber);
            validateGender(gender);

            String fileName = lastName + ".txt";
            String data = String.join(" ", lastName, firstName, middleName, birthDate, phoneNumber, gender);

            writeFile(fileName, data);

            System.out.println("Данные успешно записаны в файл: " + fileName);
        } catch (IllegalArgumentException | IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void validateDate(String date) {
        if (!Pattern.matches("\\d{2}\\.\\d{2}\\.\\d{4}", date)) {
            throw new IllegalArgumentException("Неверный формат даты. Ожидался формат dd.mm.yyyy, получено: " + date);
        }
    }

    private static void validatePhoneNumber(String phoneNumber) {
        if (!Pattern.matches("\\d{11}", phoneNumber)) {
            throw new IllegalArgumentException("Неверный формат номера телефона. Ожидалось 11 цифр, получено: " + phoneNumber);
        }
    }

    private static void validateGender(String gender) {
        if (!gender.equals("m") && !gender.equals("f")) {
            throw new IllegalArgumentException("Неверный формат пола. Ожидалось 'm' или 'f', получено: " + gender);
        }
    }

    private static void writeFile(String fileName, String data) throws IOException {
        Path filePath = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(data);
            writer.newLine();
        }
    }
}
