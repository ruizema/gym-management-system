import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static DataCentre dataCentre = new DataCentre();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        boolean quit = false;
        while (!quit) {
            System.out.println("Quel utilisateur voulez-vous simuler?\n" +
                    "1. Utilisateur sur application mobile\n" +
                    "2. Administrateur");
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    loginMenu();
                    break;
                case "2":
                    adminMenu();
                    break;
                default:
                    System.out.println("Mauvais code...");
                    quit = true;
            }
        }
    }

    // TODO: display all accounts?
    private static void adminMenu() throws IOException {
        boolean exit = false;
        do {
            System.out.println("\nMenu de l'agent administratif\n" +
                    "---- Choisissez une option ----\n" +
                    "1. Création de compte\n" +
                    "2. Création d'une séance\n" +
                    "3. Consultation du Répertoire des Services\n" +
                    "4. Consultation des inscriptions\n" +
                    "5. Rapport de comptable\n" +
                    "6. Rapport des membres et professionnels\n" +
                    "7. Création d'un service\n" +
                    "q: RETOURNER");
            String input;
            do {
                switch (input = scanner.nextLine()) {
                    case "1":
                        createDataRecord("Account");
                        break;
                    case "2":
                        createDataRecord("Session");
                        break;
                    case "3":
                        viewSessions();
                        break;
                    case "4":
                        viewRegistrations();
                        break;
                    case "5":
                        generateServiceReport();
                        break;
                    case "6":
                        generateClientReport();
                        break;
                    case "7":
                        createDataRecord("Service");
                        break;
                    case "q":
                        exit = true;
                        break;
                }
            } while (input.equals(""));
        } while (!exit);
    }

    private static void loginMenu() throws IOException {
        System.out.println("\nIdentification de l'utilisateur\n" +
                "Entrez votre courriel:");
        String email = scanner.nextLine();
        String[] response = dataCentre.login(email);
        switch (Integer.parseInt(response[0])) {
            case 0:
                System.out.println("Validé");
                System.out.println(response[1]);
                System.out.println(response[2]);
                if (response[3].equals("P")) {
                    professionalMenu();
                } else {
                    memberMenu();
                }
                break;
            case 1:
                System.out.println("Numéro invalide");
                break;
            case 2:
                System.out.println("Membre suspendu");
                break;
        }
    }

    // TODO: view report
    private static void memberMenu() throws IOException {
        System.out.println("\nMenu du membre\n" +
                "---- Choisissez une option ----\n" +
                "1. Consultation du Répertoire des Services\n" +
                "2. Inscription à une séance\n" +
                "3. Consultation du rapport des services obtenus\n" +
                "q: RETOURNER");
        boolean exit = false;
        while (!exit) {
            switch (scanner.nextLine()) {
                case "1":
                    viewSessions();
                    break;
                case "2":
                    createDataRecord("Registration");
                    break;
                case "3":
                    viewSessionReport();
                    break;
                case "q":
                    exit = true;
            }
        }
    }

    // TODO: view report
    private static void professionalMenu() throws IOException {
        System.out.println("\nMenu du professionnel\n" +
                "---- Choisissez une option ----\n" +
                "1. Consultation du Répertoire des Services\n" +
                "2. Consultation des inscriptions\n" +
                "3. Confirmation des présences\n" +
                "4. Consulation des services fournis\n" +
                "q: RETOURNER");
        boolean exit = false;
        while (!exit) {
            switch (scanner.nextLine()) {
                case "1":
                    viewSessions();
                    break;
                case "2":
                    viewRegistrations();
                    break;
                case "3":
                    confirmPresence();
                    break;
                case "4":
                    viewSessionReport();
                    break;
                case "q":
                    exit = true;
            }
        }
    }

    private static void createDataRecord(String dataType) {
        int nbFields = DataRecord.getFieldNames(dataType).length;
        String[] data = new String[nbFields];
        System.out.println("Entrez ces données:");
        for (int i = 0; i < nbFields; i++) {
            System.out.println(DataRecord.getFieldNames(dataType)[i]);
            String field = scanner.nextLine();
            // validate data
            if (DataRecord.validation(dataType, field, i)) {
            data[i] = field;
            }
            else {
            	System.out.println("Wrong data type");
            }
            
        }
        dataCentre.createDataRecord(data, dataType);
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
        // TODO: Update
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

    private static void generateClientReport() throws IOException {
        System.out.println("Veuillez entrer la date (JJ-MM-AAAA)");
        String date = scanner.nextLine();
        dataCentre.generateClientReport(date);
    }

    private static void viewSessionReport() throws IOException{
        System.out.println("Entrez votre numéro de membre");
        String id = scanner.nextLine();
        System.out.println("Entrez la date (JJ-MM-AAAA)");
        String date = scanner.nextLine();
        dataCentre.viewClientReport(id, date);
    }
}
