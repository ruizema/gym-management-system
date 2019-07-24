import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataCentreTest {
    DataCentre dataCenter;
    String[] dataA;
    String[] dataB;
    String[] dataC;
    String[] dataD;
    String[] dataE;

/*    Account a;
    Session b;
    Session c;
    Service d;
    Registration e;*/

    @Before
    public void setUp() {
        String[] dataA = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        a = new Account(dataA);

        String[] dataB = {
                "20-07-2019 12:59:59",
                "20-07-2019",
                "20-08-2019",
                "19:00",
                "L, J, V",
                "10",
                "123456789",
                d.getId(),
                "6549872",
                "135.45",
                "Apporter vos tapis."};

        String[] dataD = {"yoga"};
        d = new Service(dataD);
    }

/*********TODO*********/
    @Test
    public void testLogin() {
        fail("Not yet implemented");
    }

    @Test
    public void testValidateId() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetDataRecords() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRegistrations() {
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
}
