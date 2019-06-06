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
    public void createSession(HashMap[] informationSession) {
        sessions[sessions.size()]= informationSession;
    }

    public void createRegistration(HashMap[] infoRegistration) {
        registrations[registrations.size()] = infoRegistration;
    }

    public void getSessions() {
        for (int key: sessions.keySet()) {
            System.out.println(key);
            System.out.println(sessions.get(key) + "/n");
        }
    }

    public void getRegistrations(int sessionId) {
    //Case of registration being #id member : #id session
        for (int key: registrations.ketSet()){
            if(registrations.get(key) == sessionId){
                System.out.println(key);
            }
        }
    //Case of registration being #id session: #id member
        for (int key: registrations.ketSet()){
            if(key == sessionId){
                System.out.println(registrations.get(key));
            }
        }
    }
}
