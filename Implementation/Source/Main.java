import java.util.Arrays;
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
                        createAccount();
                        break;
                    case "3":
                        createSession();
                        break;
                    case "4":
                        viewSessions();
                        break;
                    case "5":
                        createRegistration();
                        break;
                    case "6":
                        viewRegistrations();
                        break;
                    case "7":
                        confirmPresence();
                        break;
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
                "2. Création de compte\n" +
                "3. Création d'une séance\n" +
                "4. Consultation le Répertoire de Services\n" +
                "5. Inscription à une séance\n" +
                "6. Consultation des inscriptions\n" +
                "7. Confirmation de la présence\n" +
                "q: QUITTER");
    }

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

    private static void createAccount() {
        System.out.println("Nom du membre");
        String name = scanner.nextLine();
        System.out.println("Le numéro du membre est " + server.createAccount(name));
    }

    private static void createSession() {
        System.out.println("Entrez ces données:\n" +
                "Date et heure actuelles\n" +
                "    Date de début du service\n" +
                "    Date de fin du service\n" +
                "    Heure du service\n" +
                "    Récurrence hebdomadaire du service (quels jours il est offert à la même heure)\n" +
                "    Capacité maximale\n" +
                "    Numéro du professionnel\n" +
                "    Code du service\n" +
                "    Commentaires (facultatif).");

        String[] sessionInfo = new String[9];
        for (int i = 0; i < sessionInfo.length; i++) {
            sessionInfo[i] = scanner.nextLine();
        }
        System.out.println("Le code de la séance est " + server.createSession(sessionInfo));
    }

    private static void createRegistration() {
        System.out.println("Entrez le code d'une séance");
        viewSessions();
        int sessionId = scanner.nextInt();
        System.out.println("Entrez le numéro du membre");
        int memberId = scanner.nextInt();
        server.createRegistration(memberId, sessionId);
    }

    private static void viewSessions() {
        String[] sessions = server.getSessions();
        for (int i = 0; i < sessions.length; i++) {
            System.out.println(sessions[i]);
        }
    }

    private static void viewRegistrations() {
        System.out.println("Entrez le code d'une séance");
        int[] registrations = server.getRegistrations(scanner.nextInt());
        for (int i = 0; i < registrations.length; i++) {
            System.out.println(registrations[i]);
        }
        System.out.println("Numéro: " + 7654321);
    }

    private static void confirmPresence() {
        System.out.println("Entrez le numéro du membre");
        int memberId = scanner.nextInt();

        if (server.validateId(memberId) == 0) {
            System.out.println("Entrez le numéro de la séance");
            viewSessions();
            int sessionId = scanner.nextInt();
            if (server.confirmPresence(memberId, sessionId)) {
                System.out.println("Présence confirmée!");
            }
        } else {
            System.out.println("Numéro invalide!");
        }
    }
}
