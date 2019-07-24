import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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

    public LinkedList getDataRecords(String dataType) throws IOException {
        switch (dataType) {
            case "Account":
                return (LinkedList) accounts.values();
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

    public void createDataRecord(String[] data, String dataType) {
        switch (dataType) {
            case "Session":
                sessions.add(new Session(data));
                break;
            case "Registration":
                registrations.add(new Registration(data));
                int sessionId = Integer.parseInt(data[5]);
                for (Session session : sessions) {
                    if (sessionId == session.getSessionId()) {
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

    public boolean validatePresence(String memberId, String serviceId, String sessionId) throws IOException {
        LinkedList registrations = getDataRecords("Registration");
        for (Object registration : registrations) {
            if (Arrays.equals(((Registration) registration).getPresenceInformation(), new String[]{memberId, serviceId, sessionId})) {
                String[] data = new String[6];
                data[0] = "28-06-2019 19:45:30";
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

    public void updateAccounts(String accountId, boolean suspended) throws IOException {
        for (Object account : getDataRecords("Account")) {
            if (((Account) account).getId().equals(accountId)) {
                ((Account) account).setSuspended(suspended);
            }
        }
    }

    public String generateServiceReport() throws IOException {
        String[] sessions = getSessions();
        LinkedList<String> lines = new LinkedList<>();
        HashMap<String, int[]> employeeCounts = new HashMap<>();
        for (String session : sessions) {
            String[] split = session.split("\n");
            String employeeId = split[7].split(": ")[1];
            int fee = Integer.parseInt(split[10].split(": ")[1]);
            int[] current = employeeCounts.getOrDefault(employeeId, new int[]{0, 0});
            current[0]++;
            current[1] += fee;
            employeeCounts.put(employeeId, current);
        }
        int fees = 0;
        for (String employeeId : employeeCounts.keySet()) {
            int fee = employeeCounts.get(employeeId)[1];
            fees += fee;
            String line = employeeId + '\t' + employeeCounts.get(employeeId)[0] + '\t' + fee;
            lines.add(line);
        }
        return "Code du professionnel\tNombre de services\tFrais à payer\n" +
                String.join("\n", lines) + '\n' +
                "Nombre total de professionnels qui ont fourni des services: " + employeeCounts.keySet().size() + '\n' +
                "Nombre total de services: " + getSessions().length + '\n' +
                "Total des frais: " + fees + '\n';
    }

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

    public String getService(String id) {
        String name = "";
        for(Service service: services) {
            if(service.getId().equals(id)) {
                name += service.getName();
            }
        }
        return name;
    }

    public Session getSession(String id) {
        Session foundSession = null;
        for(Session session: sessions) {
            if(session.getSessionId() == (Integer.parseInt(id))) {
                foundSession = session;
            }
        }
        return foundSession;
    }

    // TODO: write to memory (AND file)
    public void generateClientReport(String dateReport) throws IOException {
        LinkedList<Account> professionals = new LinkedList<>();
        LinkedList<Account> members = new LinkedList<>();
        LinkedList<Integer> consultations = new LinkedList<>();
        LinkedList<Integer> pays = new LinkedList<>();
        String file = "";
        for (Confirmation confirm: this.confirmations) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            String date = confirm.getDate();
            String serviceId = confirm.getServiceId();
            Session session = getSession(confirm.getSessionId());
            Account professional = getAccount(confirm.getProfId());
            Account member = getAccount(confirm.getMemberId());

            String profReport = date + "\n" +
                                confirm.getActualDate() + "\n" +
                                member.getName() + "\n" +
                                confirm.getProfReport() + "\n";
            String memberReport = date + "\n" +
                                  professional.getName() + "\n" +
                                  getService(serviceId) + "\n";

            file = professional.getName() + "-" + dateReport + ".txt";
            if(professionals.contains(professional)) {
                consultations.add(professionals.indexOf(professional), +1);
                pays.add(professionals.indexOf(professional), +session.getPrice());
                writer.write(profReport);
            }else {
                professionals.add(professional);
                consultations.add(1);
                pays.add(session.getPrice());
                writer.write(professional.getInfosReport() + "\n" + profReport);
            }

            file = member.getName() + "-" + dateReport + ".txt";
            if(members.contains(member)) {
                writer.write(memberReport);
            }else {
                members.add(member);
                writer.write(member.getInfosReport() + "\n" + memberReport);
            }

            if(confirm.equals(confirmations.getLast())) {
                for(Account prof: professionals) {
                    file = prof.getName() + "-" + dateReport + ".txt";
                    writer.write("\nLe nombre de consultations est: " + consultations.get(professionals.indexOf(prof)) + "\n");
                    writer.write("Le total des frais est: " + pays.get(professionals.indexOf(prof)) + "\n");
                }
            }
            writer.close();
        }
    }

    public void testing() throws IOException {
        // create member account
        createDataRecord(new String[]{"mmrz33@gmail.com", "Rui Ze Ma", "8110 Naples", "Brossard", "QC", "J4Y2R6", "M"}, "Account");
        // create professional account
        createDataRecord(new String[]{"abcde@gmail.com", "Bob Lol", "1234 Sherbrooke", "Montreal", "QC", "H4B2T6", "P"}, "Account");
        // create service
        createDataRecord(new String[]{"Yoga"}, "Service");
        // create session
        String serviceId = services.getFirst().getId();
        String profId = accounts.get("abcde@gmail.com").getId();
        createDataRecord(new String[]{"23-07-2019 19:54:23", "25-07-2019", "06-08-2019", "15:45", "L", profId, serviceId, "", ""}, "Session");
        // register to session
        String userId = accounts.get("mmrz33@gmail.com").getId();
        String sessionId = sessions.getFirst().getSessionId();
        createDataRecord(new String[]{"23-07-2019 19:54:23", "25-07-2019", profId, userId, serviceId, sessionId, ""}, "Registration");
        // confirm presence
        if (validatePresence(userId, serviceId, sessionId)) {
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
	}
	
    public void viewClientReport(String id, String dateReport) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(getAccount(id).getName() + "-" + dateReport + ".txt")); 
        String st; 
        while ((st = reader.readLine()) != null) {
        System.out.println(st); 
        }
        reader.close();
    }
}
