package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import  org.apache.poi.hssf.usermodel.HSSFSheet;
import  org.apache.poi.hssf.usermodel.HSSFWorkbook;
import  org.apache.poi.hssf.usermodel.HSSFRow;
import  org.apache.poi.hssf.usermodel.HSSFCell;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private TextArea analyseArea;
	@FXML
	private TextField dateiNameFeld;
	@FXML
	private Text dateiNameText;
	@FXML
	private Text dateiGroesseText;
	@FXML
	private Text dateiZeilenText;
	@FXML
	private TextField xlsFileNameFeld;
	@FXML
	private TextField xlsSpeicherOrtFeld;
	@FXML
	private TextField zk1;
	@FXML
	private TextField zk2;
	@FXML
	private TextField zk3;
	@FXML
	private TextField zk4;
	@FXML
	private TextField zk5;
	@FXML
	private TextField zk6;
	

	static Logger log = Logger.getAnonymousLogger();
	FileChooser fC = new FileChooser();
	public File file;
	public Stage stage;
	String fileName;
	long fileSize;
	int fileLines;
	int nix = -1;
	String inhalt;
	String xlsFileName;
	String xlsSpeicherOrt;
	
	

	//Liest die Zeilenanzahl aus Datei aus
	public void getAmountOfLines(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;
		while (reader.readLine() != null)
			lines++;
		reader.close();
		fileLines = lines;
	}
	// Splittet die Datei in einzelne Zeilen und tut sie dann in Array, 
	public void splitFile(File file) throws IOException {
	
		Scanner sc = new Scanner(file);
		List<String> lines = new ArrayList<String>();
		while (sc.hasNextLine()) {
			lines.add(sc.nextLine());
		}
		//Splittet einzelne Lines nach jedem Komma
		String[] arr = lines.toArray(new String[0]);
		for (String line : lines) {
			String[] split = line.split(",");
			System.out.println(split[0].trim());
			
			log.info("Parsing [0] + [1] ");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");		
			
				
				
//			List<String> asList = Arrays.asList(split);
//			for (String field : asList) {
//				System.out.println(field);
//			}
		}

		
		// Gibt Datei inhalt zeile für zeile aus
		// while (nix != 2000) {
		// nix++;
		// System.out.println(arr[nix]);
		// }
	}

	public void readFile(File file) throws IOException {
		String text = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		inhalt = text;
	}

	
	
	@FXML
	public void durchsuchenButton(ActionEvent event) {
		try {
			log.info("Durchsuchen...");
			File selectedFile = fC.showOpenDialog(null);
			fC.setTitle("Datei auswählen");

			if (selectedFile != null) {
				fileName = selectedFile.getName();
				dateiNameFeld.setText(fileName);
				file = selectedFile;
				fileSize = file.length();
				dateiNameText.setText(fileName);
				String strLong = Long.toString(fileSize);
				dateiGroesseText.setText(strLong + " Bytes");
				getAmountOfLines(file);
				String fileLinesString = Integer.toString(fileLines);
				dateiZeilenText.setText(fileLinesString);
			
			} else {
				log.info("Keine oder unpassende Datei ausgewählt");
				dateiNameFeld.setText("Keine oder unpassende Datei ausgewählt");
				file = selectedFile;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startButton(ActionEvent event) throws IOException {
		
		readFile(file);

		splitFile(file);	
		
		analyseArea.setText(inhalt);
	}
	
	//Button zur Erstellung einer Excel Datei mit zuvor gefetchten Werten.
	
	
	
	public void createXLSButton(ActionEvent event) {
	log.info("XLS Datei wird erstellt...");
	xlsFileName = xlsFileNameFeld.getText();
	xlsSpeicherOrt = xlsSpeicherOrtFeld.getText();
	File xlsDatei = new File( xlsFileName + ".xls" );
	try {
		FileOutputStream fos = new FileOutputStream(xlsDatei);
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet  sheet = workbook.createSheet("FirstSheet");
		HSSFRow rowhead = sheet.createRow((short)0);
		//Excel Header Reihen
		rowhead.createCell(0).setCellValue(zk1.getText());
		rowhead.createCell(1).setCellValue(zk2.getText());
		rowhead.createCell(2).setCellValue(zk3.getText());
		rowhead.createCell(3).setCellValue(zk4.getText());
		rowhead.createCell(4).setCellValue(zk5.getText());
		rowhead.createCell(5).setCellValue(zk6.getText());
		
		//Excel Inhalt
		HSSFRow row = sheet.createRow((short)1);
		row.createCell(0).setCellValue("000");
		row.createCell(1).setCellValue("1111");
		row.createCell(2).setCellValue("2222");
		row.createCell(3).setCellValue("333333");
		row.createCell(4).setCellValue("44444444");
		row.createCell(5).setCellValue("555555");
		FileOutputStream fileOut = new FileOutputStream(xlsDatei);
		workbook.write(fileOut);
		fileOut.close();
		fos.close();
		workbook.close();
		log.info("XLS Datei erstellt");
		
	} catch (Exception ex) {
		System.out.println(ex);
	}

}
}