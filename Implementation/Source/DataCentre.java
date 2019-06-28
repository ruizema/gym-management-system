import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class DataCentre {

    private Main main;

    public int validateId(int id) {
        LinkedList<Integer> existingIds = DataRecord.getExistingIds();
        for (int existingId : existingIds) {
            if (id == existingId) {
                return 0;
            } else if (false) {} // Suspended member
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
            int lineNumber = lineCount % nbFields;
            if (lineNumber == 0) {
                if (data.length != 0) {
                    switch (dataType) {
                        case "Account":
                            dataRecords.add(new Account(data));
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
                break;
            case "Confirmation":
                dataRecord = new Confirmation(data);
                break;
            case "Account":
                dataRecord = new Account(data);
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
}
