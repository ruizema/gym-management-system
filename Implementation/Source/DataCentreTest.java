import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

public class DataCentreTest {
    DataCentre dataCenter = new DataCentre();
    String[] dataA;
    String[] dataB;
    String[] dataC;
    String[] dataD;
    String[] dataE;

    Account a1;
    Account a2;
    Account a3;

    Service d;
    Session e;

    @Before
    public void setUp() throws IOException{
        //Accounts
        String[] dataA = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        String[] dataB = {"mmrz33@gmail.com", "Rui Ze Ma", "5555 Rome", "Brossard", "QC", "J4K3R7", "M"};
        String[] dataC = {"abcde@gmail.com", "Bob Lol", "1234 Sherbrooke", "Montreal", "QC", "H4B2T6", "P"};
        dataCenter.createDataRecord(dataA, "Account");
        dataCenter.createDataRecord(dataB, "Account");
        dataCenter.createDataRecord(dataC, "Account");
        LinkedList<Account> a = dataCenter.getDataRecords("Account");
        a1 = a.get(0);
        a2 = a.get(1);
        a3 = a.get(2);

        //Service
        String[] dataD = {"yoga"};
        dataCenter.createDataRecord(dataD, "Service");
        LinkedList<Service> service = dataCenter.getDataRecords("Service");
        d = service.get(0);

        //Session
        String[] dataE = {
                "20-07-2019 12:59:59",
                "20-07-2019",
                "20-08-2019",
                "19:00",
                "L, J, V",
                "10",
                a3.getId(),
                d.getId(),
                "6549872",
                "135.45",
                "Apporter vos tapis."};
        dataCenter.createDataRecord(dataE, "Session");
        LinkedList<Session> session = dataCenter.getDataRecords("Session");
        e = session.get(0);

        //Registrations
        String[] dataF = {"23-07-2019 19:54:23", "25-07-2019", a3.getId(), a1.getId(), d.getId(), e.getSessionId(), ""};
        String[] dataG = {"23-07-2019 19:54:23", "25-07-2019", a3.getId(), a2.getId(), d.getId(), e.getSessionId(), ""};
        dataCenter.createDataRecord(dataF, "Registration");
        dataCenter.createDataRecord(dataG, "Registration");
    }

    @Test
    public void testLogin() {
        String email = a1.getEmail();
        String[] login =  dataCenter.login(email);
        String result = login[0];

        assertEquals("0", result);
    }

    @Test
    public void testValidateId() throws IOException{
        String id = a1.getId();
        int result = dataCenter.validateId(id);

        assertEquals(0, result);
    }

    @Test
    public void testValidatePresence() throws IOException {
        String userId = a2.getId();
        String serviceId = d.getId();
        String sessionId = e.getSessionId();
        String otherUserId = a1.getId();
        assertTrue(dataCenter.validatePresence(userId, serviceId, sessionId)
                && dataCenter.validatePresence(otherUserId, serviceId, sessionId));
    }

    @Test
    public void testGetService() {
        assertEquals("yoga", dataCenter.getService(d.getId()));
    }
}
