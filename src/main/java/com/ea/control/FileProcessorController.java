package main.java.com.ea.control;

import main.java.com.ea.domain.Item;
import main.java.com.ea.domain.Order;
import main.java.com.ea.services.SpreadSheetOpsProdImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Controller
public class FileProcessorController
{
    @Autowired
    private SpreadSheetOpsProdImpl spreadSheetOps;

    @Autowired
    ServletContext context;

    @RequestMapping(value = "/processExcel", method = RequestMethod.POST)
    public ModelAndView proccessExcel(@RequestParam("userFile")MultipartFile userFile, @RequestParam("azfile")MultipartFile azfile)
            throws IOException
    {
        //userFile.transferTo(new File("C:\\Temp\\CreatedFile.xlsx"));
        Set<Item> itemSet = spreadSheetOps.processFile(userFile, azfile);
        return new ModelAndView("/processOutcome.jsp", "itemSetResult", itemSet);
    }

    @RequestMapping(value = "/processOrders", method = RequestMethod.POST)
    public ModelAndView processOrderTrackingNumbers(@RequestParam("orderNumberFile") MultipartFile orderNumberFile) throws IOException
    {
        String path = context.getRealPath("");

        orderNumberFile.transferTo(new File(path +"/uploads/uploads.xlsx"));
        File orders = new File(path +"/uploads/uploads.xlsx");

        Set<Order> orderStatus = spreadSheetOps.processOrders(orders);

        return new ModelAndView("/trackingStatus.jsp", "orders", orderStatus);
    }

    @RequestMapping(value="/processTransactionsCC", method = RequestMethod.POST)
    public ModelAndView processTransactionsCC(@RequestParam("FileTransCC") MultipartFile transactionsCC) throws IOException, ParseException
    {
        String path = context.getRealPath("");

        transactionsCC.transferTo(new File(path +"/uploads/transactionsCC.xlsx"));
        File transactions = new File(path +"/uploads/transactionsCC.xlsx");

        spreadSheetOps.processTransactions(transactions);
        return new ModelAndView("/transCompleted.jsp");
    }
}
