import java.util.HashMap;

public class CentralServer {

    private Main main;

    // TODO: Replace with actual files (after prototype)
    private HashMap[] sessions;
    private HashMap[] accounts;
    private HashMap[] registrations;
    private HashMap[] confirmations;

    public int validateId(int id) {return 0;}

    public void createAccount(HashMap[] personalInfo) {
        accounts[accounts.size()] = personalInfo;
        //retourne un num de membre

    }

    // Service != session!
    public void createService(HashMap[] informationSession) {
        sessions[sessions.size()]= informationSession;
    }

    public void createRegistration(HashMap[] infoRegistration) {
        registrations[registrations.size()] = infoRegistration;
    }

    public HashMap getSessions() {
        return new HashMap();
    }

    public HashMap getRegistrations(int sessionId, int staffId) {
        return new HashMap();
    }


}