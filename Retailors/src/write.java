import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class write {
	Object[][] data;
	XSSFWorkbook workbook;
	XSSFSheet sheet;
	
	public write(String sheetName, Object[][] data){
		this.workbook = new XSSFWorkbook();
		this.data = data;		
		this.sheet = this.workbook.createSheet(sheetName);
	}
	
	public void createTitle(Object[] title){
		Row row = sheet.createRow(0);
		int columnCount = 0;
		
		for(Object o : title){
			Cell cell = row.createCell(columnCount);
			cell.setCellValue((String) o);
			columnCount++;
		}
	}
	
	public void writeData(String fileName) throws FileNotFoundException, IOException{
		int rowCount = 1;
		
		for(Object[] item : this.data){
			Row row = sheet.createRow(rowCount);
			int columnCount = 0;
			
			for(Object num : item){
				Cell cell = row.createCell(columnCount);
				if(num instanceof String){
					cell.setCellValue((String) num);
				}else if(num instanceof Double){
					System.out.println(num);
					System.out.println((Double) num);
					cell.setCellValue((Double) num);
				}else{
				    System.out.println(num);
					cell.setCellValue((Integer) num);
				}
				columnCount++;
			}
			rowCount++;
		}
		
		try (FileOutputStream outputStream = new FileOutputStream(fileName)){
			this.workbook.write(outputStream);
		}
	}
	
}
