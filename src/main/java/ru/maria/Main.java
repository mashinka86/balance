package ru.maria;

import ru.maria.domain.Client;
import ru.maria.service.CalculateService;

import java.util.Map;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public class Main {

    private static final String CLIENT_FILE = "clients.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final String RESULT_FILE = "result.txt";

    /**
     * Запуск программы
     *
     * @param args : 0 - файл с клиентами, 1 - файл с заявками, 3 - файл с результатами (Если не указаны берутся по умолчанию)
     */
    public static void main(String[] args) {
        CalculateService calculateService = new CalculateService();
        Map<String, Client> clientList = calculateService.loadClients(args.length > 0 ? args[0] : CLIENT_FILE);
        calculateService.calculate(args.length > 1 ? args[1] : ORDERS_FILE, clientList);
        calculateService.writeResults(clientList.values(), args.length > 2 ? args[2] : RESULT_FILE);
    }
}
