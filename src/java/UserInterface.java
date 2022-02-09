package java;

import java.io.IOException;
import java.util.Scanner;

public class UserInterface {

    private final Controller controller = new Controller();

    public void runApplication() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Greetins! You can close this application. Just use this button or commands: q / exit ");

        while (true) {
            System.out.println("Write name City");
            String city = scanner.nextLine();


            checkIsExit(city);


            setGlobalCity(city);


            System.out.println(
                    "Choose: 1 - Weather today, " +
                            "2 - Weather on next 5 days, " +
                            "3 - History "
            );



            String result = scanner.nextLine();


            checkIsExit(result);


            try {
                validateUserInput(result);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }

            try {
                notifyController(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Обработка выхода...
    private void checkIsExit(String result) {
        if
            ( result.toLowerCase().equals("exit")
            || result.toLowerCase().equals("q")) {
            System.out.println("Завершаю работу");
            System.exit(0);
        }
    }


    private void setGlobalCity(String city) {
        ApplicationGlobalState.getInstance().setSelectedCity(city);
    }


    private void validateUserInput(String userInput) throws IOException {
        if (userInput == null || userInput.length() != 1) {
            throw new IOException("Incorrect user input: expected one digit as answer, but actually get " + userInput);
        }
        int answer = 0;
        try {
            answer = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new IOException("Incorrect user input: character is not numeric!");
        }
    }

    private void notifyController(String input) throws IOException {
        controller.onUserInput(input);
    }
}