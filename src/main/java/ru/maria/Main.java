package ru.maria;

import ru.maria.domain.Client;
import ru.maria.service.CalculateService;

import java.util.Map;

/**
 * Created by mariafedotova on 19.11.2017.
 */
public class Main {


    public static void main(String[] args){
        CalculateService calculateService=new CalculateService();
        Map<String, Client> clientList = calculateService.loadClients("clients.txt");
        calculateService.calculate("orders.txt",clientList);
        calculateService.writeResults(clientList.values(),"results.txt");
    }
}
