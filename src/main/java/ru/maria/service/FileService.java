package ru.maria.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.maria.domain.Client;
import ru.maria.domain.Operation;
import ru.maria.exception.BalanceException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by mariafedotova on 22.11.2017.
 */
public class FileService {

    private final static Logger logger = LogManager.getLogger(FileService.class);

    private static final String TAB = "\t";

    private CalculateService calculateService = new CalculateService();

    /**
     * Загрузка файла с типами бумаг
     *
     * @param fileName имя файла
     * @return список типов бумаг
     * @throws BalanceException exception с кодом ошибки
     */
    public List<String> loadPapers(String fileName) throws BalanceException {
        logger.info("load papers start from file {}", fileName);
        List<String> papers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(papers::add);
        } catch (Exception e) {
            logger.error("error in load papers", e);
            throw new BalanceException(BalanceException.LOAD_PAPERS_ERROR, e.getMessage());
        }
        logger.info("finished load papers");
        return papers;
    }


    /**
     * Получение клиентов со счетами из файла
     *
     * @param fileName имя файла
     * @return Map ключ - имя клиента, значение - {@link ru.maria.domain.Client}
     * @throws BalanceException exception с кодом ошибки
     */
    public Map<String, Client> loadClients(String fileName, List<String> paperNames) throws BalanceException {
        logger.info("load clients start from file {}", fileName);
        Map<String, Client> clients = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                String[] data = line.split(TAB);
                Map<String, BigInteger> papers = new LinkedHashMap<>();
                IntStream.range(0, paperNames.size()).forEach(index -> papers.put(paperNames.get(index), new BigInteger(data[index + 2])));
                String name = data[0];
                clients.put(name, new Client(name, new BigInteger(data[1]), papers));
            });
        } catch (Exception e) {
            logger.error("error in load clients", e);
            throw new BalanceException(BalanceException.LOAD_CLIENT_ERROR, e.getMessage());
        }
        logger.info("finished load clients");
        return clients;
    }

    /**
     * Расчет баланса клиентов
     *
     * @param fileName имя файла с операциями
     * @param clients  клиенты со счетами
     * @throws BalanceException exception с кодом ошибки
     */
    public void loadOrdersWithCalc(String fileName, Map<String, Client> clients) throws BalanceException {
        logger.info(" start calculate with operations from file {}", fileName);
        List<Operation> sold = new ArrayList<>();
        List<Operation> buy = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                String[] data = line.split(TAB);
                String clientName = data[0];
                String paper = data[2];
                BigInteger price = new BigInteger(data[3]);
                BigInteger count = new BigInteger(data[4]);
                Operation operation = new Operation(paper, price, count, clientName);
                calculateService.calculateOperation(operation, clients, data[1], sold, buy);
            });
        } catch (Exception ex) {
            logger.error("error in calculate", ex);
            throw new BalanceException(BalanceException.CALCULATE_ERROR, ex.getMessage());
        }
        logger.info("calculate finished");
    }


    /**
     * Запись результатов расчета в файл
     *
     * @param clients  клиенты со счетами
     * @param fileName файл с результатами
     * @throws BalanceException exception с кодом ошибки
     */
    public void writeResults(Collection<Client> clients, String fileName) throws BalanceException {
        logger.info("starting write results from file {}", fileName);
        try (PrintWriter out = new PrintWriter(new File(fileName))) {
            clients.forEach(client -> {
                List<String> data = new ArrayList<>();
                data.add(client.getName());
                data.add(client.getDollars().toString());
                client.getPapers().values().forEach(paper -> data.add(paper.toString()));
                out.println(data.stream().collect(Collectors.joining(TAB)));
            });
        } catch (Exception ex) {
            logger.error("error in write clients", ex);
            throw new BalanceException(BalanceException.SAVE_RESULTS_ERROR, ex.getMessage());
        }
        logger.info("finished write results");
    }
}
