package com.cnhi.DataConnector;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelWriter {

	
	@SuppressWarnings("resource")
	public void createExcel(String fileName, Map<String, Object[]> data){
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(fileName);

		// Iterate over data and write to sheet
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			// this creates a new row in the sheet
			Row row = sheet.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				// this line creates a cell in the next column of that row
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String)obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer)obj);
			}
		}

		try {
			// this Writes the workbook fileName
			FileOutputStream out = new FileOutputStream(new File(fileName+".xlsx"));
			workbook.write(out);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
