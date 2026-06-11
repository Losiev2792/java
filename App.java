import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Ваш вибір: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Помилка: введіть цифру від 0 до 3.\n");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    convertMetersToKilometers();
                    break;
                case 2:
                    convertKilogramsToGrams();
                    break;
                case 3:
                    convertCelsiusToFahrenheit();
                    break;
                case 0:
                    System.out.println("Вихід з програми. Бувай!");
                    running = false;
                    break;
                default:
                    System.out.println("Неправильний вибір. Спробуйте ще раз.\n");
            }
        }

        scanner.close();
    }

    private static void printMenu() {
        System.out.println("=== КОНВЕРТЕР ВЕЛИЧИН ===");
        System.out.println("1 - Метри в кілометри");
        System.out.println("2 - Кілограми в грами");
        System.out.println("3 - Цельсій у Фаренгейт");
        System.out.println("0 - Вихід");
    }

    private static void convertMetersToKilometers() {
        System.out.print("Введіть метри: ");
        double meters = readDoubleInput();
        double km = meters / 1000;
        System.out.println("Результат: " + km + " км\n");
    }

    private static void convertKilogramsToGrams() {
        System.out.print("Введіть кілограми: ");
        double kg = readDoubleInput();
        double grams = kg * 1000;
        System.out.println("Результат: " + grams + " г\n");
    }

    private static void convertCelsiusToFahrenheit() {
        System.out.print("Введіть температуру в C: ");
        double c = readDoubleInput();
        double f = (c * 9 / 5) + 32;
        System.out.println("Результат: " + f + " F\n");
    }

    private static double readDoubleInput() {
        while (!scanner.hasNextDouble()) {
            System.out.print("Помилка! Введіть коректне число: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}