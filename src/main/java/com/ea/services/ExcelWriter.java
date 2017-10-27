package main.java.com.ea.services;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelWriter
{
    private static final String FILE_NAME = "ExcelOutput.xlsx";

    public static void main(String[] args)
    {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes"},
                {"int", "Primitive", 2},
                {"double", "Primitive", 8},
                {"float", "Primitive", 4},
                {"char", "Primitive", 1},
                {"String", "Non primitive", "No fixed size"}

        };

        int rowNum = 0;
        System.out.println("Creating excel");

        for(Object[] datatype : datatypes)
        {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for(Object field : datatype)
            {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String)
                {
                    cell.setCellValue((String) field);
                }
                else if (field instanceof Integer)
                {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try
        {
            FileOutputStream outputStream = new FileOutputStream(new File(FILE_NAME));
            workbook.write(outputStream);
            workbook.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Done");
    }
}
