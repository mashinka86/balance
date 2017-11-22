import org.junit.Before;
import org.junit.Test;
import ru.maria.domain.Client;
import ru.maria.service.FileService;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by mariafedotova on 20.11.2017.
 */
public class CalculateTest {

    private FileService fileService = new FileService();
    private String clientFile;
    private String orderFile;

    private static final String CLIENT = "C1";
    private static final String CLIENT_2 = "C2";

    @Before
    public void before() {
        clientFile = getClass().getClassLoader().getResource("clients.txt").getPath();
        orderFile = getClass().getClassLoader().getResource("orders.txt").getPath();
    }

    @Test
    public void test() throws Exception {
        Map<String, Client> clients = fileService.loadClients(clientFile, Arrays.asList("A", "B", "C", "D"));
        assertThat(clients.keySet(), hasItem(CLIENT));
        fileService.loadOrdersWithCalc(orderFile, clients);
        Client client = clients.get(CLIENT);
        Client client2 = clients.get(CLIENT_2);
        assertEquals(new BigInteger("6"), client.getDollars());
        assertEquals(new BigInteger("4"), client.getPapers().get("D"));
        assertEquals(new BigInteger("1"), client2.getPapers().get("D"));
        assertEquals(new BigInteger("24"), client2.getDollars());
    }
}
