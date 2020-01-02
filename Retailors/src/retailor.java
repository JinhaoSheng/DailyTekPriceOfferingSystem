import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class retailor {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Map<String, Integer> input = new HashMap<String, Integer>();
		read r = new read("C:\\Users\\Yolanda\\Desktop\\DistributorOffer_lib\\DMPriceOffer.xlsx");
		
		JFrame frame = new JFrame("Distributor Price Offer");
		frame.setLayout(new FlowLayout());
		
		JTextField identifier = new JTextField(16);		
		JTextField quantity = new JTextField(16);
		
		JButton add = new JButton("ADD");		
		JButton close = new JButton("CLOSE");
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		identifier.setFont(font1);
		quantity.setFont(font1);
		close.setPreferredSize(new Dimension(146, 52));
		add.setPreferredSize(new Dimension(146, 52));
		close.setFont(font1);
		add.setFont(font1);
		
		add.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!identifier.getText().equals("") && !quantity.getText().equals("")) { 
		            input.put(identifier.getText(), Integer.parseInt(quantity.getText()));
		            //System.out.println(identifier.getText() + Integer.parseInt(quantity.getText()));
		            identifier.setText("");
		            quantity.setText("");
		        }
			}
			
		});
		
		close.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				firstCalculation fc = new firstCalculation(r, input);
				double times = 0.;
				try {
					times = fc.times(fc.onePointFourCalculation());
				} catch (IOException e1) {
					System.out.println("fc went wrong");
				}
				try {
					generateExcel(input, times);
				} catch (FileNotFoundException e1) {
				    System.out.println("Couldn't generate Excel");
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Couldn't generate Excel");
					e1.printStackTrace();
				}
				
			}
			
		});
		
		
		
		JLabel identifiers = new JLabel("Identifier");	
		JLabel quantities = new JLabel("Quantity");	
		identifiers.setFont (identifier.getFont ().deriveFont (30.0f));
		quantities.setFont (identifier.getFont ().deriveFont (30.0f));
		
		identifier.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					quantity.requestFocusInWindow();
				}
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					quantity.requestFocusInWindow();
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
					quantity.requestFocusInWindow();
				}
			}
			
		});
		
		quantity.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				int id = e.getKeyCode();
				if(id == KeyEvent.VK_ENTER){
					if (!identifier.getText().equals("") && !quantity.getText().equals("")) { 
			            input.put(identifier.getText(), Integer.parseInt(quantity.getText()));
			            identifier.setText("");
			            quantity.setText("");
			        }
					//identifier.requestFocusInWindow();
				}	
			}

			@Override
			public void keyReleased(KeyEvent e) {
//				int id = e.getKeyCode();
//				if(id == KeyEvent.VK_ENTER){
//					if (!identifier.getText().equals("") && !quantity.getText().equals("")) { 
//			            input.put(identifier.getText(), Integer.parseInt(quantity.getText()));
//			            identifier.setText("");
//			            quantity.setText("");
//			        }
//					identifier.requestFocusInWindow();
//				}	
			}

			@Override
			public void keyTyped(KeyEvent e) {
				int id = e.getKeyCode();
				if(id == KeyEvent.VK_ENTER){
					if (!identifier.getText().equals("") && !quantity.getText().equals("")) { 
			            input.put(identifier.getText(), Integer.parseInt(quantity.getText()));
			            identifier.setText("");
			            quantity.setText("");
			        }
					identifier.requestFocusInWindow();
				}	
			}
			
		});
		
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		p1.add(identifiers);
		p1.add(identifier);
		
		p2.add(quantities);
		p2.add(quantity);
		
		p3.add(add);
		p3.add(close);
		
		frame.add(p1);
		frame.add(p2);
		frame.add(p3);
		frame.setSize(1000, 200);
		frame.setLocationRelativeTo(null);  
		frame.show();
	}
	
	public static void generateExcel(Map<String, Integer> map, double times) throws FileNotFoundException, IOException{
		String description = " ";
		read r = new read("C:\\Users\\Yolanda\\Desktop\\DistributorOffer_lib\\DMPriceOffer.xlsx");
		Object[][] data = new Object[map.size()][7];
		Object[] title = {"Type", "Description", "Price Without Tax", "Tax(13%)", "Price With Tax", "Quantity", "Final Price"};
		
		int i = 0;
		for(String identifier : map.keySet()){
			Object[] temp = r.readSingleRow(identifier, description, times, map.get(identifier));
			data[i] = temp;
			i++;
		}
		
		write w = new write("sheet1",data);
		w.createTitle(title);
		w.writeData("price_offer(customer).xlsx");
	}


}
