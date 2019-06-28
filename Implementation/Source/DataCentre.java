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
            int lineNumber = lineCount % nbFields;
            if (lineNumber == 0) {
                if (data.length != 0) {
                    if (dataType.equals("Account")) {
                        dataRecords.add(new Account(data));
                    } else {
                        // TODO: Create the appropriate data record
                    }
                }
                data = new String[nbFields];
            } else {
                data[lineNumber - 1] = line.split(": ")[1];
            }
            lineCount++;
        }
        reader.close();
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
}
