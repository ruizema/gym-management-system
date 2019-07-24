
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AccountTest {
    Account a;
    String id;

    @Before
    public void setUp() {
        String[] data = {"ab@gmail.com","ZY","999 moland","Tuni","MO","5G5G5G","M"};
        a = new Account(data);
        id = a.getId();
    }

    @Test
    public void testGetInfosReport() {
        String infos = a.getInfosReport();
        String expected = "ZY\n" + id + "\n999 moland\nTuni\nMO\n5G5G5G\n";

        assertEquals(expected, infos);
    }

    @Test
    public void testGenerateId() {
        a.generateId();
        String id = a.getId();

        assertEquals("Number of characters in ID", 9, id.length());
    }
}
