package ru.maria;

import ru.maria.domain.Client;
import ru.maria.exception.BalanceException;
import ru.maria.service.FileService;

import java.util.Map;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public class Main {

    private static final String PAPERS_FILE = "papers.txt";
    private static final String CLIENT_FILE = "clients.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final String RESULT_FILE = "result.txt";

    /**
     * Запуск программы
     *
     * @param args : 0 - файл с типами бумаг, 1 - файл с клиентами, 2 - файл с заявками, 4 - файл с результатами (Если не указаны берутся по умолчанию)
     */
    public static void main(String[] args) {
        FileService fileService = new FileService();
        try {
            Map<String, Client> clientList = fileService.loadClients(args.length > 1 ? args[1] : CLIENT_FILE, fileService.loadPapers(args.length > 0 ? args[0] : PAPERS_FILE));
            fileService.loadOrdersWithCalc(args.length > 2 ? args[2] : ORDERS_FILE, clientList);
            fileService.writeResults(clientList.values(), args.length > 3 ? args[3] : RESULT_FILE);
        } catch (BalanceException ex) {
            System.exit(ex.getCode());
        }

    }
}
