package main.java.com.ea.client;

import main.java.com.ea.domain.Item;
import main.java.com.ea.services.SpreadSheetOps;
import main.java.com.ea.services.SpreadSheetOpsProdImpl;
import org.apache.poi.util.IOUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class ClientApplication
{
    private static final String FILE_NAME = "user.xlsx";
    private static final String FILE_NAME2 = "supplier.xlsx";

    public static void main(String[] args)
    {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("/application.xml");
        try
        {
            SpreadSheetOps spreadSheetOps = container.getBean(SpreadSheetOpsProdImpl.class);


            File fileSupplier = new File("C:\\Users\\alexander-florez\\Documents\\TrainProjects\\sample az.xlsx");
            File fileUser = new File("C:\\Users\\alexander-florez\\Documents\\TrainProjects\\sample seller.xlsx");
            FileInputStream input1 = new FileInputStream(fileSupplier);
            FileInputStream input2 = new FileInputStream(fileUser);
            MultipartFile multipartFileSup = new MockMultipartFile("fileSupplier", fileSupplier.getName(),
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input1));

            MultipartFile multipartFileUser = new MockMultipartFile("fileUser", fileUser.getName(),
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", IOUtils.toByteArray(input2));

            List<Item> itemSet = spreadSheetOps.processFile(multipartFileSup, multipartFileUser);

            for(Item item : itemSet )
            {
                System.out.println(item.getSku() + " " + item.getNotes());
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            container.close();
        }
    }
}
