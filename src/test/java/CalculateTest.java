import org.junit.Before;
import org.junit.Test;
import ru.maria.domain.Client;
import ru.maria.domain.Paper;
import ru.maria.service.CalculateService;

import java.math.BigInteger;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Created by mariafedotova on 20.11.2017.
 */
public class CalculateTest {

    private CalculateService calculateService = new CalculateService();
    private String clientFile;
    private String orderFile;

    private static final String CLIENT = "C1";

    @Before
    public void before() {
        clientFile = getClass().getClassLoader().getResource("clients.txt").getPath();
        orderFile = getClass().getClassLoader().getResource("orders.txt").getPath();
    }

    @Test
    public void test() {
        Map<String, Client> clients = calculateService.loadClients(clientFile);
        assertThat(clients.keySet(), hasItem(CLIENT));
        calculateService.calculate(orderFile, clients);
        Client client = clients.get(CLIENT);
        assertEquals(new BigInteger("12"), client.getDollars());
        assertEquals(new BigInteger("6"), client.getPapers().get(Paper.D));
        assertEquals(new BigInteger("1"), client.getPapers().get(Paper.B));
    }
}
