import java.io.IOException;
import java.util.Scanner;

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
                    case "4":
                        viewSessions();
                        break;
                    case "5":
                        createDataRecord("Registration");
                        break;
                    case "6":
                        viewRegistrations();
                        break;
                    case "7":
                        confirmPresence();
                        break;
                    case "8":
                        generateServiceReport();
                        break;
                    case "q":
                        exit = true;
                        break;
                }
            } while (input.equals(""));
        } while (!exit);
    }

    private static void mainMenu() throws IOException {
        System.out.println("\nCentre Sportif #GYM\n" +
                "---- Choisissez une option ----\n" +
                "1. Vérification de l'accès\n" +
                "2. Création de compte\n" +
                "3. Création d'une séance\n" +
                "4. Consultation le Répertoire de Services\n" +
                "5. Inscription à une séance\n" +
                "6. Consultation des inscriptions\n" +
                "7. Confirmation de la présence\n" +
                "8. Rapport de comptable\n" +
                "q: QUITTER");
        dataCentre.principalAccounting();
    }

    private static void gymAccess() throws IOException {
        System.out.println("Entrez le numéro de membre:");
        int validationCode = dataCentre.validateId(scanner.nextLine());
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

        if (returnInt == -1) {
            System.out.println("Création réussie!");
        } else if (dataType.equals("Account")) {
            String returnIntToString = String.valueOf(returnInt);
            if (returnIntToString.length() < 9) {
                returnIntToString = "0".repeat(9 - returnIntToString.length()) + returnIntToString;
            }
            System.out.println("Le numéro du membre est " + returnIntToString);
        }
    }

    private static void viewSessions() throws IOException {
        String[] sessions = dataCentre.getSessions();
        for (String session : sessions) {
            System.out.println(session);
        }
    }

    private static void viewRegistrations() throws IOException {
        System.out.println("Entrez le numéro du professionnel:");
        String[] registrations = dataCentre.getRegistrations(scanner.nextInt());
        for (String registration : registrations) {
            System.out.println(registration);
        }
    }

    private static void confirmPresence() throws IOException {
        System.out.println("Entrez le numéro du membre");
        String memberId = scanner.nextLine();
        if (dataCentre.validateId(memberId) == 0) {
            viewSessions();
            System.out.println("Entrez le code du service (7 chiffres):");
            String serviceId = scanner.nextLine();
            System.out.println("Entrez le numéro de la séance");
            String sessionId = scanner.nextLine();
            if (dataCentre.validatePresence(memberId, serviceId, sessionId)) {
                System.out.println("Présence confirmée!");
            } else {
                System.out.println("Inscription invalide!");
            }
        } else {
            System.out.println("Numéro invalide!");
        }
    }

    private static void generateServiceReport() throws IOException {
        System.out.println(dataCentre.generateServiceReport());
    }
}
