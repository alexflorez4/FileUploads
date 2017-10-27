package main.java.com.ea.services;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import main.java.com.ea.domain.Item;
import main.java.com.ea.domain.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
        return new Order(order.getOrderNumber(),order.getTrackingId(), "success");
    }
}
