import java.util.Scanner;

public class Main {

    private static CentralServer server = new CentralServer();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        do {
            mainMenu();
            String input;
            do {
                switch (input = scanner.nextLine()) {
                    case "1":
                        gymAccess();
                        break;
                    case "2":
                        break;
                    // (...)
                    case "q":
                        exit = true;
                        break;
                }
            } while (input.equals(""));
        } while (!exit);
    }

    public static void mainMenu() {
        System.out.println("\nCentre Sportif #GYM\n" +
                "---- Choisissez une option ----\n" +
                "1. Vérification de l'accès\n" +
                "2. (...)\n" +
                "q: QUITTER");
        // TODO: add all menu options
    }

    // These functions don't do anything except send and/or receive data from the CentralServer

    private static void gymAccess() {
        System.out.println("Entrez le numéro de membre:");
        int validation = server.validateId(scanner.nextInt());
        switch (validation) {
            case 0:
                System.out.println("Validé!");
                break;
            case 1:
                System.out.println("Numéro invalide");
                break;
            case 2:
                System.out.println("Membre suspendu");
                break;
        }
    }

    private static void createAccount() {}

    private static void createService() {}

    private static void createRegistration() {}

    private static void viewSessions() {}

    private static void viewRegistrations(int staffId) {}

    private static boolean confirmPresence(int memberId) {
        return false;
    }
}
