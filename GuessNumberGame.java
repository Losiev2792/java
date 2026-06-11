import java.util.Random;
import java.util.Scanner;

public class GuessNumberGame {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        int secretNumber = random.nextInt(100) + 1;
        int userNumber;
        int attempts = 0;

        System.out.println("вгадай число від 1 до 100");

        do {
            System.out.print("введіть ваше число: ");
            userNumber = scanner.nextInt();
            attempts++;

            if (userNumber < secretNumber) {
                System.out.println("загадане число більше.");
            } else if (userNumber > secretNumber) {
                System.out.println("загадане число менше.");
            } else {
                System.out.println("ви вгадали число.");
                System.out.println("кількість спроб: " + attempts);
            }

        } while (userNumber != secretNumber);

        scanner.close();
    }
}