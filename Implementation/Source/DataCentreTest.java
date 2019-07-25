import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class DataCentreTest {
    DataCentre dataCenter;
    String[] dataA;
    String[] dataB;
    String[] dataC;
    String[] dataD;
    String[] dataE;

    Account a1;
    Account a2;
    Account a3;

    Session b;
    DataCentre c;
    Service d;

    Registration r1;
    Registration r2;


    @Before
    public void setUp() {
        String[] dataA = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        a1 = new Account(dataA);
        a2 = new Account(new String[]{"mmrz33@gmail.com", "Rui Ze Ma", "5555 Rome", "Brossard", "QC", "J4K3R7", "M"});
        a3 = new Account(new String[]{"abcde@gmail.com", "Bob Lol", "1234 Sherbrooke", "Montreal", "QC", "H4B2T6", "P"});

        String[] dataD = {"yoga"};
        d = new Service(dataD);

        String[] dataB = {
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

        b = new Session(dataB);

        String userId = a2.getId();
        String serviceId = d.getId();
        String sessionId = b.getSessionId();
        String otherUserId = a3.getId();
        r1 = new Registration(new String[]{"23-07-2019 19:54:23", "25-07-2019", a3.getId(), userId, serviceId, sessionId, ""});
        r2 = new Registration(new String[]{"23-07-2019 19:54:23", "25-07-2019", a3.getId(), otherUserId, serviceId, sessionId, ""});
    }

/*********TODO*********/
    @Test
    public void testLogin() {
        String email = a1.getEmail();
        String[] login =  c.login(email);
        assertEquals("0", login);
    }
    
    @Test
    public void testGetType() {
        String type = a1.getType();
        assertEquals("M", type);
    }
    
    @Test
    public void testValidatePresence() throws IOException {
        String userId = a2.getId();
        String serviceId = d.getId();
        String sessionId = b.getSessionId();
        String otherUserId = a3.getId();
        assertTrue(dataCenter.validatePresence(userId, serviceId, sessionId) && dataCenter.validatePresence(otherUserId, serviceId, sessionId));
    }
/*
    @Test
    public void testValidateId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDataRecords() {
        fail("Not yet implemented");
    }



    @Test
    public void testGetService() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetSession() {
    	fail("Not yet implemented");
        
    }
    
    */
}
