import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class firstCalculation {
	read r;
	Map<String, Integer> information; 
	
	public firstCalculation(read r, Map<String, Integer> information){
		this.r = r;
		this.information = information;
	}
	
	public double onePointFourCalculation() throws IOException{
		double total = 0.;
		for(String o : this.information.keySet()){
			double singleProduct = r.findPrice(o) * this.information.get(o);
			total += singleProduct;
		}
		return total;
	}
	
	public double times(double assumptionOffer) throws IOException{
		if(assumptionOffer > 1000000) return 1 ;
		if(assumptionOffer > 500000) return 1.2 ;
		if(assumptionOffer > 300000) return 1.3 ;
	    if(assumptionOffer > 100000) return 1.35 ;
	    if(assumptionOffer > 10000) return 1.4 ;
	    return 1.45;
	}
	
}
