import java.io.IOException;
import java.util.Scanner;

// TODO: Pay to createAccount (client)
// TODO: "Membre suspendu" (hasn't paid for a month)
// TODO: Show price when creating registration for session
// TODO: principalAccounting() in DataCentre - generates EFT files
// TODO: generateServiceReport() in DataCentre - called from principalAccounting
// TODO: updateAccounts() in DataCentre - allows RnB to update clients' payment information

public class Main {

    private static DataCentre dataCentre = new DataCentre();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
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
                        createDataRecord("Account");
                        break;
                    case "3":
                        createDataRecord("Session");
                        break;
                    /*case "4":
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
                        break;*/
                    case "q":
                        exit = true;
                        break;
                }
            } while (input.equals(""));
        } while (!exit);
    }

    private static void mainMenu() {
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
        int validationCode = dataCentre.validateId(scanner.nextInt());
        switch (validationCode) {
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

    private static void createDataRecord(String dataType) throws IOException {
        int nbFields = DataRecord.getFieldNames(dataType).length;
        String[] data = new String[nbFields];
        System.out.println("Entrez ces données:");
        for (int i = 0; i < nbFields; i++) {
            System.out.println(DataRecord.getFieldNames(dataType)[i]);
            String field = scanner.nextLine();
            data[i] = field;
        }
        int returnInt = dataCentre.createDataRecord(data, dataType);
        if (dataType.equals("Account")) {
            // TODO: change the id to a 9 digit string
            System.out.println("Le numéro du membre est " + returnInt);
        }
    }

    /*
    private static void createRegistration() {
        System.out.println("Entrez le code d'une séance");
        viewSessions();
        int sessionId = scanner.nextInt();
        System.out.println("Entrez le numéro du membre");
        int memberId = scanner.nextInt();
        dataCentre.createRegistration(memberId, sessionId);
    }

    /*private static void viewSessions() {
        String[] sessions = dataCentre.getSessions();
        for (int i = 0; i < sessions.length; i++) {
            System.out.println(sessions[i]);
        }
    }

    private static void viewRegistrations() {
        System.out.println("Entrez le code d'une séance");
        int[] registrations = dataCentre.getRegistrations(scanner.nextInt());
        for (int i = 0; i < registrations.length; i++) {
            System.out.println(registrations[i]);
        }
        System.out.println("Numéro: " + 7654321);
    }

    private static void confirmPresence() {
        System.out.println("Entrez le numéro du membre");
        int memberId = scanner.nextInt();

        if (dataCentre.validateId(memberId) == 0) {
            System.out.println("Entrez le numéro de la séance");
            viewSessions();
            int sessionId = scanner.nextInt();
            if (dataCentre.confirmPresence(memberId, sessionId)) {
                System.out.println("Présence confirmée!");
            }
        } else {
            System.out.println("Numéro invalide!");
        }
    }

     */

}
