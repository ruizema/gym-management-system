import java.util.HashMap;

public class CentralServer {

    private Main main;

    // TODO: Replace with actual files (after prototype)
    private String[] sessions;
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

    public int createSession(String[] sessionInfo) {
        sessions = sessionInfo;
        return 7654321;
    }

    public void createRegistration(int memberId, int sessionId) {
        registrations[0] = memberId;
    }

    public String[] getSessions() {
        return sessions;
    }

    public int[] getRegistrations(int sessionId) {
        return registrations;
    }

    public boolean confirmPresence(int memberId, int sessionId) {
        return true;
    }
}
