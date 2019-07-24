import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class DataCentre {

    private HashMap<String, Account> accounts = new HashMap<>();
    private LinkedList<Session> sessions = new LinkedList<>();
    private LinkedList<Confirmation> confirmations = new LinkedList<>();
    private LinkedList<Registration> registrations = new LinkedList<>();
    private LinkedList<Service> services = new LinkedList<>();

    /**Method to login client onto application
     * 
     *@param email The email used for the creation of the Account
     *@return The date state of the Account and if it's successful shows client QRcode, name, id and type
     */
    public String[] login(String email) {
        Account account = accounts.get(email);
        String code = account == null ? "1" : account.isSuspended() ? "2" : "0";
        if (!code.equals("1")) {
            String name = account.getName();
            String memberId = account.getId();
            String type = account.getType();
            return new String[]{code, name, memberId, type};
        } else {
            String[] output = new String[4];
            output[0] = code;
            return output;
        }
    }

    /**Method to validate an ID of client
     * 
     *@param id The ID of client
     *@return The validity of ID
     *@throws IOException if ID entered doesn't match specified format
     */
    public int validateId(String id) throws IOException {
        LinkedList accounts = getDataRecords("Account");
        for (Object account : accounts) {
            Account properAccount = (Account) account;
            if (properAccount.getId().equals(id)) {
                if (properAccount.isSuspended()) {
                    return 2;
                } else {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**Method to access to DataRecords
     * 
     *@param dataType The type of data in DataRecords
     *@return A linkedList of many of the same dataType
     *@throws IOException if dataType not in DataRecords in the good format
     */
    public LinkedList getDataRecords(String dataType) throws IOException {
        switch (dataType) {
            case "Account":
                return new LinkedList<Object>(accounts.values());
            case "Session":
                return sessions;
            case "Registration":
                return registrations;
            case "Confirmation":
                return confirmations;
            case "Service":
                return services;
        }
        return null;
    }

    /**Method to create a new data from types in DataRecords
     *
     *@param data Informations necessary to create dataType
     *@param dataType Type of data we are creating
     */
    public void createDataRecord(String[] data, String dataType) {
        switch (dataType) {
            case "Session":
                sessions.add(new Session(data));
                break;
            case "Registration":
                registrations.add(new Registration(data));
                String sessionId = data[5];
                for (Session session : sessions) {
<<<<<<< HEAD
                    if (sessionId.equals(session.getSessionId())) {
=======
                    if (sessionId == Integer.parseInt(session.getSessionId())) {
>>>>>>> 6cb92d3ad25f4ffdca7c4ef65bd60fd18ffb6812
                        System.out.println("Le montant à payer est de " + session.getPrice() + "$.");
                    }
                }
                break;
            case "Confirmation":
                confirmations.add(new Confirmation(data));
                break;
            case "Account":
                Account account = new Account(data);
                account.generateId();
                accounts.put(account.getEmail(), account);
                break;
            case "Service":
                services.add(new Service(data));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dataType);
        }
    }

    /**Method to access all existing sessions
     * 
     *@return A String of all the sessions
     *@throws IOException if data is not written properly
     */
    public String[] getSessions() throws IOException {
        LinkedList sessions = getDataRecords("Session");
        String[] output = new String[sessions.size()];
        int i = 0;
        for (Object session : sessions) {
            output[i] = ((Session) session).toString("Session");
            i++;
        }
        return output;
    }

    /**Method to see all registrations to a session
     * 
     *@param employeeId The ID of the professional
     *@return A list of registrations in String format
     *@throws IOException if data is not written properly
     */
    public String[] getRegistrations(int employeeId) throws IOException {
        LinkedList registrations = getDataRecords("Registration");
        String[] output = new String[registrations.size()];
        int outputIndex = 0;
        for (Object registration : registrations) {
            if (((Registration) registration).getEmployeeId() == employeeId) {
                output[outputIndex] = ((Registration) registration).toString("Registration");
                outputIndex++;
            }
        }
        return output;
    }

    /**Method to validate presence of member in a session
     * 
     *@param memberId Member ID
     *@param serviceId Service ID
     *@param sessionId Session ID
     *@return The truth value of de presence at session
     *@throws IOException if data not written properly
     */
    public boolean validatePresence(String memberId, String serviceId, String sessionId) throws IOException {
        LinkedList registrations = getDataRecords("Registration");
        for (Object registration : registrations) {
            if (Arrays.equals(((Registration) registration).getPresenceInformation(), new String[]{memberId, serviceId, sessionId})) {
                String[] data = new String[6];
                data[0] = "28-06-2019 19:45:30"; // TODO: Remove hard-coded
                data[1] = String.valueOf(((Registration) registration).getEmployeeId());
                data[2] = memberId;
                data[3] = serviceId;
                data[4] = sessionId;
                data[5] = "";
                createDataRecord(data, "Confirmation");
                return true;
            }
        }
        return false;
    }

    /**Method to generate pay weekly slips
     * 
     *@throws IOException if data doesn't match format
     */
    public void principalAccounting() throws IOException {
        String report = generateServiceReport();
        int eftCount = 0;
        for (String line : report.split("\n")) {
            if (Pattern.matches("[1-9]", line.substring(0, 1))) {
                String id = line.split("\t")[0];
                String toPay = line.split("\t")[2];
                String name = null;
                for (Object account : getDataRecords("Account")) {
                    if (((Account) account).getId().equals(id)) {
                        name = ((Account) account).getName();
                    }
                }
                String eft = "Nom du professionnel: " + name + '\n' +
                        "Numéro du professionnel: " + id + '\n' +
                        "Montant à transférer: " + toPay;
                FileWriter fileWriter = new FileWriter("eft_" + eftCount + ".txt");
                fileWriter.write(eft);
                fileWriter.close();
                eftCount++;
            }
        }
        // Envoyer le rapport au gerant
        System.out.println(report);
    }

    /**Method to update Accounts if needed 
     * 
     *@param accountId Number ID of client
     *@param suspended Status of the Account
     *@throws IOException if wrong input
     */
    public void updateAccounts(String accountId, boolean suspended) throws IOException {
        for (Object account : getDataRecords("Account")) {
            if (((Account) account).getId().equals(accountId)) {
                ((Account) account).setSuspended(suspended);
            }
        }
    }

    /**Method that creates a service report indicating which professionnal gets paid and how much
     * 
     *@return The report on the screen
     *@throws IOException if wrong input output
     */
    public String generateServiceReport() throws IOException {
        String[] sessions = getSessions();
        LinkedList<String> lines = new LinkedList<>();
        HashMap<String, double[]> employeeCounts = new HashMap<>();
        for (String session : sessions) {
            String[] split = session.split("\n");
            String employeeId = split[7].split(": ")[1];
            double fee = Double.parseDouble(split[10].split(": ")[1]);
            double[] current = employeeCounts.getOrDefault(employeeId, new double[]{0, 0});
            current[0]++;
            current[1] += fee;
            employeeCounts.put(employeeId, current);
        }
        double fees = 0;
        for (String employeeId : employeeCounts.keySet()) {
            double fee = employeeCounts.get(employeeId)[1];
            fees += fee;
            String line = employeeId + '\t' + employeeCounts.get(employeeId)[0] + '\t' + fee;
            lines.add(line);
        }
        return  "RAPPORT DE SYNTHESE POUR LE GERANT\n" +
                "Code du professionnel\tNombre de services\tFrais à payer\n" +
                String.join("\n", lines) + '\n' +
                "Nombre total de professionnels qui ont fourni des services: " + employeeCounts.keySet().size() + '\n' +
                "Nombre total de services: " + getSessions().length + '\n' +
                "Total des frais: " + fees + '\n';
    }

    /**Method to access a specific Account from an ID
     * 
     *@param id The ID query
     *@return The Account found or nothing
     *@throws IOException if wrong entries
     */
    private Account getAccount(String id) throws IOException {
        LinkedList accounts = getDataRecords("Account");
        Account foundAccount = null;
        for(Object account: accounts) {
            if(((Account)account).getId().equals(id)) {
                foundAccount = (Account) account;
            }
        }
        return foundAccount;
    }

    /**Method to get a specific service name from a service ID
     *
     *@param id The ID query of service
     *@return The name of the service found
     */
    public String getService(String id) {
        String name = "";
        for(Service service: services) {
            if(service.getId().equals(id)) {
                name += service.getName();
            }
        }
        return name;
    }

    /**Method to find a specific session from an ID
     *
     *@param id The ID query session
     *@return The found session
     */
    public Session getSession(String id) {
        Session foundSession = null;
        for(Session session: sessions) {
            if(session.getSessionId().equals(id)) {
                foundSession = session;
            }
        }
        return foundSession;
    }

<<<<<<< HEAD
    // TODO: send to Accounts & write to files
=======
    /**Method to write the reports for each member that benefited a service
     * and professional that offered the service
     * 
     *@param dateReport The date that the report is generated
     *@throws IOException if dateReport is not in a right format
     */
    // TODO: write to memory (AND file)
>>>>>>> 6cb92d3ad25f4ffdca7c4ef65bd60fd18ffb6812
    public void generateClientReport(String dateReport) throws IOException {
        LinkedList<Account> professionals = new LinkedList<>();
        LinkedList<Account> members = new LinkedList<>();
        LinkedList<Integer> consultations = new LinkedList<>();
        LinkedList<Double> pays = new LinkedList<>();
        HashMap<Account, String> memberReports = new HashMap<>();
        HashMap<Account, String> profReports = new HashMap<>();
        HashMap<Account, Double[]> profCountPay = new HashMap<>();
        for (Confirmation confirm: this.confirmations) {
            Account member = getAccount(confirm.getMemberId());
            Account professional = getAccount(confirm.getProfId());
            String date = confirm.getDate();
            String serviceId = confirm.getServiceId();
            Session session = getSession(confirm.getSessionId());

            memberReports.putIfAbsent(member, member.getInfosReport());
            profReports.putIfAbsent(professional, professional.getInfosReport());
            if (profCountPay.containsKey(professional)) {
                profCountPay.put(professional, new Double[]{++profCountPay.get(professional)[0], profCountPay.get(professional)[1] + session.getPrice()});
            } else {
                profCountPay.putIfAbsent(professional, new Double[]{1d, session.getPrice()});
            }

            String profReport = "\n" + date + "\n" +
                    confirm.getActualDate() + "\n" +
                    member.getName() + "\n" +
                    confirm.getProfReport() + "\n";
            String memberReport = "\n" + date + "\n" +
                    professional.getName() + "\n" +
                    getService(serviceId) + "\n";

            memberReports.put(member, memberReports.get(member) + memberReport);
            profReports.put(professional, profReports.get(professional) + profReport);

            pays.add(session.getPrice());
        }

        // add fees info
        for (Account prof : profReports.keySet()) {
            String extra = "\nLe nombre de consultations est: " + profCountPay.get(prof)[0] + "\n" +
                    "Le total des frais est: " + profCountPay.get(prof)[1] + "\n";
            profReports.put(prof, profReports.get(prof) + extra);
        }
        // Writing out reports & sending them to Accounts
        for (Account account : accounts.values()) {
            if (profReports.containsKey(account)) {
                account.addReport(profReports.get(account));
            } else {
                account.addReport(memberReports.get(account));
            }
            String filename = account.getId() + "_report.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(account.getReport());
            writer.close();
        }
    }

    public void testing() throws IOException {
        // create member account
        createDataRecord(new String[]{"mmrz33@gmail.com", "Rui Ze Ma", "8110 Naples", "Brossard", "QC", "J4Y2R6", "M"}, "Account");
        createDataRecord(new String[]{"mmrz33@live.com", "Other Member", "8110 Naples", "Brossard", "QC", "J4Y2R6", "M"}, "Account");
        // create professional account
        createDataRecord(new String[]{"abcde@gmail.com", "Bob Lol", "1234 Sherbrooke", "Montreal", "QC", "H4B2T6", "P"}, "Account");
        // create service
        createDataRecord(new String[]{"Yoga"}, "Service");
        // create session
        String serviceId = services.getFirst().getId();
        String profId = accounts.get("abcde@gmail.com").getId();
        createDataRecord(new String[]{"23-07-2019 19:54:23", "25-07-2019", "06-08-2019", "15:45", "L", "25", profId, serviceId, "6969420", "123.45", ""}, "Session");
        // register to session
        String userId = accounts.get("mmrz33@gmail.com").getId();
        String sessionId = sessions.getFirst().getSessionId();
        String otherUserId = accounts.get("mmrz33@live.com").getId();
        createDataRecord(new String[]{"23-07-2019 19:54:23", "25-07-2019", profId, userId, serviceId, sessionId, ""}, "Registration");
        createDataRecord(new String[]{"23-07-2019 19:54:23", "25-07-2019", profId, otherUserId, serviceId, sessionId, ""}, "Registration");
        // confirm presence
        if (validatePresence(userId, serviceId, sessionId) && validatePresence(otherUserId, serviceId, sessionId)) {
            System.out.println("Presence confirmed!");
        } else {
            System.out.println("PRESENCE AUTHENTIFICATION FAILED");
            return;
        }
        // procedure comptable (including both reports)
        principalAccounting();
        generateClientReport("29-07-2019");
    }

    public static void main(String[] args) throws IOException {
        (new DataCentre()).testing();
<<<<<<< HEAD
=======
    }

    /**Method read files created in report for services use
     * 
     *@param id Clients ID number
     *@param dateReport Date when the report is generated
     *@throws IOException if input is wrong
     */
    public void viewClientReport(String id, String dateReport) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(getAccount(id).getName() + "-" + dateReport + ".txt")); 
        String st; 
        while ((st = reader.readLine()) != null) {
        System.out.println(st); 
        }
        reader.close();
>>>>>>> 6cb92d3ad25f4ffdca7c4ef65bd60fd18ffb6812
    }
}
