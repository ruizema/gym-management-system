import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataCentreTest {
    Account a;
    Session b;
    Session c;
    Service d;
    Registration e;

    @Before
    public void setUp() {
        String[] dataA = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        a = new Account(dataA);

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
