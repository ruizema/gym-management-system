import java.util.Arrays;

public class Registration extends DataRecord {

    public Registration(String[] data) {
        super(data);
    }

    public int getEmployeeId() {
        return Integer.parseInt(data[2]);
    }

    public String[] getPresenceInformation() {
        return Arrays.copyOfRange(data, 3, 6);
    }
}
