import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class DataCentre {

    private Main main;
    private int[] accounts;
    private int[] registrations = new int[1];
    private HashMap[] confirmations;

    public int validateId(int id) {
        if (id == 1234567) {
            return 0;
        } else {
            return 1;
        }
    }

    public int createAccount(String personalInfo) {
        return 1234567;
    }

    public void createSession(String[] sessionInfo) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("sessions.txt", true));
        writer.write((new Session(sessionInfo)).toString());
        writer.close();
    }

    public void createRegistration(int memberId, int sessionId) {
        registrations[0] = memberId;
    }

    public boolean confirmPresence(int memberId, int sessionId) {
        return true;
    }
}
