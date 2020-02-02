package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {
	
	Workbook workbook=null;
	
	
	

	public WriteExcel(String filePath, String sheetName) throws IOException {
		super();
		this.workbook = new XSSFWorkbook();
		addSheet(sheetName);
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
	}//eocst
	
	public WriteExcel(String filePath) throws IOException {
		super();
		this.workbook = new XSSFWorkbook();
		FileOutputStream fileOut = new FileOutputStream(filePath);
		workbook.write(fileOut);
	}//eocst

	
	
	
	
	public Workbook getWorkbook() {
		return workbook;
	}//eom

	public void setWorkbook() {
		this.workbook = new XSSFWorkbook();
	}//eom

	public Sheet addSheet(String sheetName) {
		Sheet sheet = workbook.createSheet(sheetName);
		return sheet;
	}//eom
	
	
	
	
	
	public Font createHeaderFont() {
		Font headerFont=workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 17);
		headerFont.setColor(IndexedColors.RED.getIndex());
		return headerFont;
	}//eom

	
	public CellStyle createHeaderCellStyle(Font headerFont) {
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		return headerCellStyle;
	}//eom
	
	public Row createHeaderRow(Sheet sheet) {
		Row headerRow = sheet.createRow(0);
		return headerRow;
	}
	
	
}//eoc
