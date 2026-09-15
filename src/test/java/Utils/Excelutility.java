package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelutility {
	public static Object[][] getTestdata(String Filepath,String SheetName)
	{
		Object[][]data=null;
		try (FileInputStream file=new FileInputStream(Filepath);Workbook workbook= new XSSFWorkbook(file))
		{
			Sheet sheet=workbook.getSheet(SheetName);
			if(sheet==null)
			{
				throw new RuntimeException("Sheet named '" + SheetName + "' not found in the Excel file!");
			}
			int totalRows = sheet.getLastRowNum() + 1;
			if (totalRows <= 1)
			{
                return new Object[0][0];
            }
	        int colcount = sheet.getRow(0).getLastCellNum();
			DataFormatter formatter=new DataFormatter();
			List<Object[]> dataList = new ArrayList<>();
			for (int i = 1; i < totalRows; i++)
			{
				Row row=sheet.getRow(i);
			
				if (row==null)
					continue;
				Object[] rowData = new Object[colcount];
                boolean isEmptyRow = true;
                for (int j = 0; j < colcount; j++) {
                    Cell cell = row.getCell(j);
                    String cellValue = (cell == null) ? "" : formatter.formatCellValue(cell).trim();
                    rowData[j] = cellValue;
                    if (!cellValue.isEmpty())
                    {
                        isEmptyRow = false;
                    }
                }
                if (!isEmptyRow) {
                    dataList.add(rowData);
                }
            }
			data = new Object[dataList.size()][colcount];
            for (int i = 0; i < dataList.size(); i++) {
                data[i] = dataList.get(i);
            }
		} 
	catch(IOException e)
	{
		e.printStackTrace();
	}
return data;

}
	// Overloaded method to filter data by status column 
	//(e.g., 'Valid' or 'Invalid') from the 'Credentials' sheet
	public static Object[][] getTestDataByStatus(String filePath, String sheetName, String statusFilter) 
	{
        Object[][] allData = getTestdata(filePath, sheetName);
        List<Object[]> filteredList = new ArrayList<>();

        for (Object[] row : allData) {
            // Assuming status is in the 3rd column (index 2)
            if (row.length > 2 && row[2].toString().equalsIgnoreCase(statusFilter)) {
                // Return only email (index 0) and password (index 1)
                filteredList.add(new Object[] { row[0], row[1] });
            }
        }
        return filteredList.toArray(new Object[filteredList.size()][]);
    }
}