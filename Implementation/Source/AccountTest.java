
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
    Account a;

    @Before
    public void setUp() {
        String[] data = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        a = new Account(data);
    }

/*********TODO*********/
    @Test
    public void testToStringString() {
        fail("Not yet implemented");
    }

    @Test
    public void testGenerateId() {
        a.generateId();
        String id = a.getId();

        assertEquals("number of characters in ID", 7, id.length());
    }
}
