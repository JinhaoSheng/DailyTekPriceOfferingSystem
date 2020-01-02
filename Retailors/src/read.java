import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class read {
	String excelFilePath;
	FileInputStream inputStream;
	Workbook workbook;
	Sheet firstSheet;
	Iterator<Row> iterator;
	private static DecimalFormat df = new DecimalFormat("0.00");
	
	public read(String excelFilePath) throws FileNotFoundException, IOException{
		this.excelFilePath = excelFilePath;
		this.inputStream = new FileInputStream(new File(excelFilePath));
		this.workbook = new XSSFWorkbook(inputStream);
		this.firstSheet = workbook.getSheetAt(0);
		this.iterator = firstSheet.iterator();
	}
	
	public Object[] readSingleRow(String identifier, String description, double times, int quantity) throws IOException{
		Object[] output = new Object[7];
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		while(iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();			
			String id = cellIterator.next().getStringCellValue();
			if(id.equals(identifier)){
				Cell cost = cellIterator.next();
				double priceWithoutTax = cost.getNumericCellValue() * times;
				double tax = priceWithoutTax * 0.13;
				double priceWithTax = priceWithoutTax + Double.parseDouble(df.format(tax));
				double totalPrice = Double.parseDouble(df.format(priceWithTax)) * quantity;
				output[0] = id;
				output[1] = description;
				output[2] = df.format(priceWithoutTax);
				output[3] = df.format(tax);
				output[4] = df.format(priceWithTax);
				output[5] = quantity;
				output[6] = df.format(totalPrice);
				break;
			}
		}
		this.workbook.close();
		this.inputStream.close();
		return output;
	}
	
	public double findPrice(String identifier) throws IOException{
		Iterator<Row> temp = firstSheet.iterator();
		while(temp.hasNext()) {
			Row nextRow = temp.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			String id = cellIterator.next().getStringCellValue();
			//System.out.println(id);
			if(id.equals(identifier)){
				System.out.println(id);
				Cell cost = cellIterator.next();
				double onePointFour = cost.getNumericCellValue() * 1.4;
				return onePointFour;
			}
		}
		throw new IOException("Identifiers not found");
		
	}
}
