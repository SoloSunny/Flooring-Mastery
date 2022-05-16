package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.stereotype.Component;

/**
 *
 * @author solomon
 */
@Component
public class ExportDaoFileImpl implements ExportDao {

    private final String BACKUP_DIRECTORY = "FileData/Backup/";
    private final String FILE_NAME = "DataExport.txt";
    private final String DELIMITER = ",";
    private final String HEADER = "OrderNumber,CustomerName,State,TaxRate,"
            + "ProductType,Area,CostPerSquareFoot,LabourCostPerSquareFoot,"
            + "MaterialCost,LaborCost,Tax,Total,OrderDate";
    private final Map<LocalDate, List<Order>> ordersByDate = new HashMap<>();

    @Override
    public void exportData() throws PersistenceException {
        loadDataFromFiles();
        try (PrintWriter out = new PrintWriter(
                new FileWriter(BACKUP_DIRECTORY + FILE_NAME))) {
            out.println(HEADER);
            out.flush();
            List<LocalDate> sortedDates = new ArrayList(ordersByDate.keySet());
            Collections.sort(sortedDates);

            sortedDates.forEach(date -> {
                ordersByDate.get(date).stream()
                        .map(order -> {
                            out.println(marshallOrderByDate(order, date));
                            return order;
                        })
                        .forEachOrdered(order -> {
                            out.flush();
                        });
            });
        } catch (IOException e) {
            throw new PersistenceException("Could not export data.", e);
        }
    }

    @Override
    public void setOrderNumberCounter() throws PersistenceException {
        loadDataFromFiles();
        int max = 0;
        for (List<Order> orders : ordersByDate.values()) {
            for (Order order : orders) {
                if (order.getOrderNumber() > max) {
                    max = order.getOrderNumber();
                }
            }
        }
        Order.setOrderNumberCounter(max + 1);
    }

    private void loadDataFromFiles() throws PersistenceException {
        File path = new File("FileData/Orders/");
        File[] files = path.listFiles();
        for (File file : files) {
            try (Scanner sc = new Scanner(
                    new BufferedReader(
                            new FileReader(file)))) {
                List<Order> ordersOnDate = new ArrayList<>();
                String currentLine = "";
                if (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                }
                Order currentOrder;
                while (sc.hasNextLine()) {
                    currentLine = sc.nextLine();
                    currentOrder = unmarshallOrder(currentLine);
                    ordersOnDate.add(currentOrder);
                }
                ordersByDate.put(fileNameToDate(file.getName()), ordersOnDate);
            } catch (IOException e) {
                throw new PersistenceException("Could not load the file " + file
                        + " memory.", e);
            }
        }
    }

    private Order unmarshallOrder(String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        int nameTokensLength = orderTokens.length - 11;
        String customerName = "";
        if (nameTokensLength == 1) {
            customerName = orderTokens[1];
        } else {
            for (int i = 1; i < nameTokensLength; i++) {
                customerName += orderTokens[i] + DELIMITER;
            }
            customerName += orderTokens[nameTokensLength];
        }
        return new Order(Integer.parseInt(orderTokens[0]), customerName,
                orderTokens[1 + nameTokensLength],
                new BigDecimal(orderTokens[2 + nameTokensLength]),
                orderTokens[3 + nameTokensLength],
                new BigDecimal(orderTokens[4 + nameTokensLength]),
                new BigDecimal(orderTokens[5 + nameTokensLength]),
                new BigDecimal(orderTokens[6 + nameTokensLength]));
    }

    private String marshallOrderByDate(Order order, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        return order.toString(DELIMITER) + "," + date.format(formatter);
    }

    private LocalDate fileNameToDate(String fileName) {
        String dateFromFileNameWithEx = fileName.split("_")[1];
        String dateFromFileName = dateFromFileNameWithEx.substring(0, dateFromFileNameWithEx.length() - 4);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        LocalDate ld = LocalDate.parse(dateFromFileName, formatter);
        return ld;
    }
}