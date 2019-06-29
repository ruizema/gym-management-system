import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class DataCentre {

    private Main main;

    public int validateId(int id) throws IOException {
        LinkedList<DataRecord> accounts = readDataRecords("Account");
        LinkedList<Integer> existingIds = new LinkedList<>();
        for (DataRecord account : accounts) {
            Account properAccount = (Account) account;
            if (properAccount.getId() == id) {
                if (properAccount.isSuspended()) {
                    return 2;
                } else {
                    return 0;
                }
            }
        }
        return 1;
    }

    public Account findAccount(int accountId) {
        return null;
    }

    public LinkedList<DataRecord> readDataRecords(String dataType) throws IOException {
        String filename = dataType.toLowerCase() + "s.txt";
        Scanner reader = new Scanner(new File(filename));
        int lineCount = 0;
        LinkedList<DataRecord> dataRecords = new LinkedList<>();
        String[] data = {};
        while (reader.hasNextLine()) {
            String line = reader.nextLine();
            int nbFields = DataRecord.getFieldNames(dataType).length;
            // Accounts have 2 extra fields beyond the original input
            if (dataType.equals("Account")) {
                nbFields += 2;
            }
            int lineNumber = lineCount % (nbFields + 1);
            if (lineNumber == 0 || !reader.hasNextLine()) {
                if (lineCount != 0) {
                    switch (dataType) {
                        case "Account":
                            Account account = new Account(data);
                            account.setId(Integer.parseInt(data[1]));
                            dataRecords.add(account);
                            break;
                        case "Session":
                            dataRecords.add(new Session(data));
                            break;
                        case "Registration":
                            dataRecords.add(new Registration(data));
                            break;
                        case "Confirmation":
                            dataRecords.add(new Confirmation(data));
                            break;
                    }
                }
                data = new String[nbFields];
            } else if (!line.equals("")) {
                data[lineNumber - 1] = line.split(": ")[1];
            }
            lineCount++;
        }
        reader.close();
        return dataRecords;
    }

    public int createDataRecord(String[] data, String dataType) throws IOException {
        FileWriter writer = new FileWriter(dataType.toLowerCase() + "s.txt", true);
        DataRecord dataRecord;
        switch (dataType) {
            case "Session":
                dataRecord = new Session(data);
                break;
            case "Registration":
                dataRecord = new Registration(data);
                int sessionId = Integer.parseInt(data[5]);
                for (DataRecord session : readDataRecords("Session")) {
                    Session properSession = (Session) session;
                    if (sessionId == properSession.getSessionId()) {
                        System.out.println("Le montant à payer est de " + properSession.getPrice() + "$.");
                    }
                }
                break;
            case "Confirmation":
                dataRecord = new Confirmation(data);
                break;
            case "Account":
                dataRecord = new Account(data);
                ((Account) dataRecord).generateId();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dataType);
        }
        writer.write(dataRecord.toString(dataType));
        writer.close();
        // return the id if an account, -1 otherwise, -2 if a field is wrong
        if (dataType.equals("Account")) {
            System.out.println("Veuillez payer les frais d'adhésion!");
            return ((Account) dataRecord).getId();
        } else {
            return -1;
        }
    }

    public String[] getSessions() throws IOException {
        LinkedList<DataRecord> sessions = readDataRecords("Session");
        String[] output = new String[sessions.size()];
        int i = 0;
        for (DataRecord session : sessions) {
            output[i] = session.toString("Session");
            i++;
        }
        return output;
    }

    public String[] getRegistrations(int employeeId) throws IOException {
        LinkedList<DataRecord> registrations = readDataRecords("Registration");
        String[] output = new String[registrations.size()];
        int outputIndex = 0;
        for (DataRecord registration : registrations) {
            if (((Registration) registration).getEmployeeId() == employeeId) {
                output[outputIndex] = registration.toString("Registration");
                outputIndex++;
            }
        }
        return output;
    }

    public boolean validatePresence(String memberId, String serviceId, String sessionId) throws IOException {
        LinkedList<DataRecord> registrations = readDataRecords("Registration");
        for (DataRecord registration : registrations) {
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

    public void principalAccounting() {

    }

    public void updateAccounts() {}

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
}
