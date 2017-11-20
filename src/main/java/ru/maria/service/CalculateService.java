package ru.maria.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.maria.domain.Client;
import ru.maria.domain.Paper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public class CalculateService {

    private final static Logger logger = LogManager.getLogger(CalculateService.class);

    private static final String TAB = "\t";

    /**
     * Получение клиентов со счетами из файла
     * @param fileName имя файла
     * @return Map ключ - имя клиента, значение - {@link ru.maria.domain.Client}
     */
    public Map<String, Client> loadClients(String fileName) {
        logger.info("load clients start from file {}",fileName);
        Map<String, Client> clients = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.lines().forEach(line -> {
                String[] data = line.split(TAB);
                Map<Paper, BigInteger> papers = new LinkedHashMap<>();
                Arrays.asList(Paper.values()).forEach(paper -> papers.put(paper, new BigInteger(data[paper.getPosition()])));
                String name = data[0];
                clients.put(name, new Client(name, new BigInteger(data[1]), papers));
            });
        } catch (Exception e) {
            logger.error("error in load clients",e);
        }
        logger.info("finished load clients");
        return clients;
    }

    /**
     * Расчет баланса клиентов
     * @param fileName имя файла с операциями
     * @param clients клиенты со счетами
     */
    public void calculate(String fileName, Map<String, Client> clients) {
        logger.info(" start calculate with operations from file {}", fileName);
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))  {
            br.lines().forEach(line -> {
                String[] data = line.split(TAB);
                Client client = clients.get(data[0]);
                Paper paper = Paper.valueOf(data[2]);
                BigInteger price = new BigInteger(data[3]);
                BigInteger count = new BigInteger(data[4]);
                switch (data[1]) {
                    //продажа
                    case "s":
                        client.getPapers().put(paper,client.getPapers().get(paper).subtract(count));
                        client.setDollars(client.getDollars().add(price.multiply(count)));
                        break;
                    //покупка
                    case "b":
                        client.getPapers().put(paper,client.getPapers().get(paper).add(count));
                        client.setDollars(client.getDollars().subtract(price.multiply(count)));
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception ex) {
            logger.error("error in calculate", ex);
        }
        logger.info("calculate finished");
    }

    /**
     * Запись результатов расчета в файл
     * @param clients клиенты со счетами
     * @param fileName файл с результатами
     */
    public void writeResults(Collection<Client> clients, String fileName){
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
        }
        logger.info("finished write results");
    }



}
