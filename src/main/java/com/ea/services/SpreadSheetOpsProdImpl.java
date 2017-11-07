package main.java.com.ea.services;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import main.java.com.ea.domain.Item;
import main.java.com.ea.domain.Order;
import main.java.com.ea.domain.TransDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;

public class SpreadSheetOpsProdImpl implements SpreadSheetOps
{

    @Override
    public Set<Item> processFile(MultipartFile userInv, MultipartFile supInv) throws FileNotFoundException, IOException
    {
        Multimap<String, Item> userInvSet = processSellerFile(this.multipartToFile(userInv));
        Multimap<String, Item> supInvSet = processAZFile(this.multipartToFile(supInv));
        Set<Item> itemResult = new HashSet<>();

        for(String skuKey: userInvSet.keySet())
        {
            if(supInvSet.containsKey(skuKey))
            {
                Collection<Item> itemCol =  supInvSet.get(skuKey);
                Item SupplierItem = CollectionUtils.extractSingleton(itemCol);

                Collection<Item> selItem = userInvSet.get(skuKey);
                Item SellerItem = selItem.iterator().next();

                if(SupplierItem.getQuantity() < 1 && SellerItem.getQuantity() > 0)
                    itemResult.add(new Item(SupplierItem.getSku(), "Supplier is out of stock."));
                else if(SellerItem.getQuantity() < 1 && SupplierItem.getQuantity() > 0 )
                    itemResult.add(new Item(SupplierItem.getSku(),  "Item is now available. Update Amz."));
                if(SellerItem.getPrice() != SupplierItem.getPrice())
                {
                    String notes = SupplierItem.getPrice() > SellerItem.getPrice() ? "Price increased" : "Price decreased";
                    itemResult.add(new Item(SupplierItem.getSku(), "Attention: " + notes));
                }
            }
            else
            {
               itemResult.add(new Item(skuKey, "SKU is not on supplier list."));
            }
        }
        return itemResult;
    }

    //This multimap is used to store <sku, item>.
    private Multimap<String, Item> processSellerFile(File fileInProcess) throws FileNotFoundException, IOException
    {
        Multimap<String, Item> objectsMultiMap = HashMultimap.create();
        Multiset<Item> objects = HashMultiset.create();

        String sku = "";
        double price = 0;
        int quantity = 0;

        FileInputStream excelFile = new FileInputStream(fileInProcess);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet dataTypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = dataTypeSheet.iterator();
        iterator.next(); //skipping head row.

        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Item item = new Item(sku, quantity, price);

            while (cellIterator.hasNext())
            {
                Cell currentCell = cellIterator.next();
                int cell = currentCell.getColumnIndex();

                switch (cell)
                {
                    case 0:
                        item.setSku(StringUtils.isEmpty(currentCell.getStringCellValue()) ? "" : currentCell.getStringCellValue());
                        break;
                    case 1:
                        item.setQuantity((int) currentCell.getNumericCellValue());
                        break;
                    case 2:
                        item.setPrice(currentCell.getNumericCellValue());
                    default:
                        break;
                }
            }
            objectsMultiMap.put(item.getSku(), item);
        }
        return objectsMultiMap;
    }

    private Multimap<String, Item> processAZFile(File fileInProcess) throws FileNotFoundException, IOException
    {
        Multimap<String, Item> objectsMultiMap = HashMultimap.create();
        Multiset<Item> objects = HashMultiset.create();

        String sku = "";
        double price = 0;
        int quantity = 0;

        FileInputStream excelFile = new FileInputStream(fileInProcess);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet dataTypeSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = dataTypeSheet.iterator();
        iterator.next(); //skipping head row.

        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Item item = new Item(sku, quantity, price);

            while (cellIterator.hasNext())
            {
                Cell currentCell = cellIterator.next();
                int cell = currentCell.getColumnIndex();

                switch (cell)
                {
                    case 0:
                        item.setSku(StringUtils.isEmpty(currentCell.getStringCellValue()) ? "" : currentCell.getStringCellValue());
                        break;
                    case 1:
                        item.setPrice(currentCell.getNumericCellValue());
                        break;
                    case 2:
                        item.setQuantity((int) currentCell.getNumericCellValue());
                    default:
                        break;
                }
            }
            objectsMultiMap.put(item.getSku(), item);
        }
        return objectsMultiMap;
    }

    public File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException
    {
        File convFile = new File(multipart.getOriginalFilename());
        multipart.transferTo(convFile);
        return convFile;
    }

    @Override
    public Set<Order> processOrders(File orders) throws IOException
    {
        Set<Order> orderResult = new HashSet<Order>();

        int orderNumber = 0;
        String tracking = "";

        FileInputStream fileInputStream = new FileInputStream(orders);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        iterator.next(); //head row

        while (iterator.hasNext())
        {
            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            Order order = new Order(orderNumber, tracking);

            while (cellIterator.hasNext())
            {
                Cell currentCell = cellIterator.next();
                int cell = currentCell.getColumnIndex();

                switch (cell)
                {
                    case 0:
                        order.setOrderNumber((int) currentCell.getNumericCellValue());
                        break;
                    case 1:
                        order.setTrackingId(currentCell.getStringCellValue());
                        break;
                    default:
                        break;
                }
            }

            //make rest call here
            Order orderStatus = restCallToEAPlat(order);
            orderResult.add(orderStatus);
        }
        return orderResult;
    }

    private Order restCallToEAPlat(Order order) {
        Client client = Client.create();
        String url = "http://www.eagroupvac.com/inventory/users/orderTrackingCall.php?orderid=" + order.getOrderNumber()
                + "&trackingid=" + order.getTrackingId();

        WebResource webResource = client.resource(url);

        ClientResponse response = webResource
                .header("Content-Type", "text/plain")
                .accept("text/plain")
                .get(ClientResponse.class);

        String out = response.getEntity(String.class);

        String status = out.contains("success") ? "Success" : "Fail";

        return new Order(order.getOrderNumber(),order.getTrackingId(), status);
    }

    public void processTransactions(File transactions) throws IOException, ParseException {
        int llc = 0;
        Date transDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String supplier = "";
        int entity = 0;
        int cc = 0000;
        double total = 0.0;

        FileInputStream fileInputStream = new FileInputStream(transactions);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();
        iterator.next(); //head row

        while (iterator.hasNext())
        {

            Row currentRow = iterator.next();
            Iterator<Cell> cellIterator = currentRow.iterator();
            TransDetail transDetail = new TransDetail(llc, transDate, supplier, entity, cc, total, "");

            while (cellIterator.hasNext())
            {
                Cell currentCell = cellIterator.next();
                int cell = currentCell.getColumnIndex();

                switch (cell)
                {
                    case 0: //posting date
                        break;
                    case 1: //transaction date
                        transDetail.setTransDate(currentCell.getDateCellValue());
                        break;
                    case 2: //Reference ID
                        break;
                    case 3: //Description
                        transDetail.setSupplier(currentCell.getStringCellValue());
                        break;
                    case 4: //Amount
                        transDetail.setTotal(currentCell.getNumericCellValue());
                        break;
                    case 5: //Balance
                        break;
                    case 6: //llc
                        transDetail.setLlc((int)currentCell.getNumericCellValue());
                        break;
                    case 7: //cc
                        transDetail.setCc((int)currentCell.getNumericCellValue());
                        break;
                    case 8: //ea_ent_id
                        transDetail.setEntity((int)currentCell.getNumericCellValue());
                        break;
                    case 9: //notes
                        String extraNotes = org.apache.commons.lang.StringUtils.isEmpty(currentCell.getStringCellValue()) ? "" : currentCell.getStringCellValue();
                        transDetail.setNotes(extraNotes);
                        break;
                    default:
                        break;
                }
            }
            //make rest call here
            String url = "http://www.eagroupvac.com/inventory/users/transactionsCC.php?ea_llc=" + transDetail.getLlc() +
                    "&ea_purchase_date=" + transDetail.getTransDate() +
                    "&ea_supplier_name=" + transDetail.getSupplier() +
                    "&ea_cc_number=" +transDetail.getCc() +
                    "&ea_total=" + transDetail.getTotal() +
                    "&ea_ent_id=" + transDetail.getEntity() +
                    "&ea_notes=" + transDetail.getNotes();

            System.out.println(url);

        }
    }
}
