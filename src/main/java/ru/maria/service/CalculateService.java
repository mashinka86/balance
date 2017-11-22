package ru.maria.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.maria.domain.Client;
import ru.maria.domain.Operation;

import java.util.List;
import java.util.Map;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public class CalculateService {
    private final static Logger logger = LogManager.getLogger(CalculateService.class);

    /**
     * Продажа
     * @param client клиент
     * @param operation операция
     */
    private void sold(Client client, Operation operation) {
        client.getPapers().put(operation.getPaper(), client.getPapers().get(operation.getPaper()).subtract(operation.getCount()));
        client.setDollars(client.getDollars().add(operation.getPrice().multiply(operation.getCount())));
    }

    /**
     * Покупка
     *
     * @param client    клиент
     * @param operation операция
     */
    private void buy(Client client, Operation operation) {
        client.getPapers().put(operation.getPaper(), client.getPapers().get(operation.getPaper()).add(operation.getCount()));
        client.setDollars(client.getDollars().subtract(operation.getPrice().multiply(operation.getCount())));

    }

    /**
     * Поиск и расчет продажи и покупки
     * @param operation Полученная операция
     * @param clients Список клиентов
     * @param type тип операции
     * @param sold список продаж
     * @param buy список покупок
     */
    void calculateOperation(Operation operation, Map<String, Client> clients, String type, List<Operation> sold, List<Operation> buy) {
        int index;
        switch (type) {
            //продажа
            case "s": {
                if ((index = buy.indexOf(operation)) > -1) {
                    sold(clients.get(operation.getClientName()), operation);
                    Operation buyOperation = buy.remove(index);
                    buy(clients.get(buyOperation.getClientName()), buyOperation);
                } else sold.add(operation);
                break;
            }
            //покупка
            case "b": {
                if ((index = sold.indexOf(operation)) > -1) {
                    buy(clients.get(operation.getClientName()), operation);
                    Operation soldOperation = sold.remove(index);
                    sold(clients.get(soldOperation.getClientName()), soldOperation);
                } else buy.add(operation);
                break;
            }
            default:
                break;
        }
     }


}
