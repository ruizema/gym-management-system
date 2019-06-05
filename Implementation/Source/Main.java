import java.util.Scanner;

public class Main {

    private CentralServer server;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        do {
            mainMenu();
            switch (scanner.nextLine()) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                // (...)
                case "q":
                    exit = true;
            }
        } while (!exit);
    }

    private static void mainMenu() {
        System.out.println("Centre Sportif #GYM\n" +
                "---- Choisissez une option ----\n" +
                "1. Création d'un compte\n" +
                "2. Création d'une séance\n" +
                "3. (...)");
        // TODO: add all menu options
    }

    // These functions don't do anything except send and/or receive data from the CentralServer

    private static void createAccount() {}

    private static void createSession() {}

    private static void createRegistration() {}

    private static void viewSessions() {}

    private static void viewRegistrations(int staffId) {}

    private static boolean confirmPresence(int memberId) {
        return false;
    }
}
