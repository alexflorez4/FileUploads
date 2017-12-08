package main.java.com.ea.services;

import main.java.com.ea.domain.Item;
import main.java.com.ea.domain.Order;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Set;

public interface SpreadSheetOps
{
    public List<Item> processFile(MultipartFile supInv, MultipartFile userInv) throws FileNotFoundException, IOException;

    public Set<Order> processOrders(File orders) throws IOException;

    public void processTransactions(File transactions) throws IOException, ParseException;
}
