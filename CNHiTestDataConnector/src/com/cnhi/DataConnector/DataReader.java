package com.cnhi.DataConnector;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.FilenameUtils;
import com.opencsv.CSVReader;

/**
 * Base class that creates and holds execution <code>filePath</code>,
 * <code>dataListArray</code> and <code>dataNameValuePairArray</code>
 * information for current execution cycle.
 * <p>
 * User can refer to these variables by creating object of type
 * <code>DataReader</code>
 * <p>
 * <b>Usage:</b><br>
 * <code>DataReader dataReader = new DataReader();</code>
 *
 * @author Vishal Kumar
 */
public class DataReader {

	private static Logger logger = LogManager.getLogger(DataReader.class.getName());

	private String filePath;
	private ArrayList<ArrayList<String>> dataListArray;
	private ArrayList<ArrayList<NameValuePair>> dataNameValuePairArray;

	Workbook workbook = null;
	XSSFSheet xsheet = null;
	HSSFSheet hsheet = null;
	CSVReader reader = null;
	FileInputStream fis;

	/**
	 * Returns the path of the test file chosen by the user to run the test
	 * cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getFilePath();</code>
	 * <p>
	 *
	 * @return filePath
	 **/
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Initializes and holds the path of the test file chosen for test case
	 * execution.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.setFilePath(filePath);</code>
	 * <p>
	 *
	 * @param filePath
	 *            Complete path of the test file
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * Returns the list of data elements from the test file chosen by the user
	 * to run the test cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getDataListArray();</code>
	 * <p>
	 *
	 * @return dataListArray
	 **/
	public ArrayList<ArrayList<String>> getDataListArray() {
		return dataListArray;
	}

	/**
	 * Initializes and holds the list of data elements from the test file chosen
	 * for test case execution.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.setDataListArray(filePath);</code>
	 * <p>
	 *
	 * @param dataListArray
	 *            list of data elements from the test file in execution.
	 */
	public void setDataListArray(ArrayList<ArrayList<String>> dataListArray) {
		this.dataListArray = dataListArray;
	}

	/**
	 * Returns the name value pair array of elements from the test file chosen
	 * by the user to run the test cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getDataNameValuePairArray();</code>
	 * <p>
	 *
	 * @return dataNameValuePairArray
	 **/
	public ArrayList<ArrayList<NameValuePair>> getDataNameValuePairArray() {
		return dataNameValuePairArray;
	}

	/**
	 * Initializes and holds the array of pairs of name and value of elements
	 * from the test file chosen for test case execution.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.setDataNameValuePairArray(dataNameValuePairArray);</code>
	 * <p>
	 *
	 * @param dataNameValuePairArray
	 *            Array of pairs of name and value
	 */
	public void setDataNameValuePairArray(ArrayList<ArrayList<NameValuePair>> dataNameValuePairArray) {
		this.dataNameValuePairArray = dataNameValuePairArray;
	}

	/**
	 * This method checks the file type chosen by user to run the test cases and
	 * call the required method accordingly. Types of files can be XLS, XLSX,
	 * CSV.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getFileContents();</code>
	 * </p>
	 * @return Returns list of test data in name value pair combination.
	 **/
	public ArrayList<ArrayList<NameValuePair>> getFileContents() {
		logger.debug("[DataReader:getFileContents()] To get the type of input file");

		final String CSV="csv";
		final String XLS="xls";
		final String XLSX="xlsx";
		final String DATABASE="db";


		String fileExtension=FilenameUtils.getExtension(getFilePath()); // returns file extension

		switch(fileExtension){

		case CSV:
			getCSVContent();
			break;

		case XLS:
			getXLSContent();
			break;

		case XLSX:
			getXLSXContent();
			break;

		case DATABASE:
			//To Do statements for data base connection
			//A dummy method for getting database content.
				getDatabaseContent();
			break;

		default:
			logger.error("[DataReader : getFileContents()] Failed to get the contents of the file: " + getFilePath());
		}

		return dataNameValuePairArray;
	}


	/**
	 * This method reads the CSV file chosen by user to run the test cases and
	 * set the data to a string array.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getCSVContent();</code>
	 **/
	public void getCSVContent() {
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		String[] nextLine;
		try {
			reader = new CSVReader(new FileReader(getFilePath()));
			while ((nextLine = reader.readNext()) != null) {
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < nextLine.length; i++) {
					list.add(nextLine[i]);
				}
				array.add(list);
			}
			setDataListArray(array);
			setDataNameValuePairArray(nameValuePairData());
			reader.close();
		} catch (Exception e) {
			logger.error("[DataReader : getCSVContent] Exception is: " + e.getMessage());
		}
	}

	/**
	 * This method reads the XLS file chosen by user to run the test cases and
	 * set the data to a string array.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getXLSContent();</code>
	 **/
	public void getXLSContent() {
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		try {
			fis = new FileInputStream(getFilePath());
			workbook = new HSSFWorkbook(fis);
			hsheet = (HSSFSheet) workbook.getSheetAt(0);
			// Iterating over Excel file in Java
			Iterator<Row> itr = hsheet.iterator();
			while (itr.hasNext()) {
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				ArrayList<String> list = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = cell.toString();
					list.add(cellValue);
				}
				array.add(list);
			}
			setDataListArray(array);
			setDataNameValuePairArray(nameValuePairData());
			cleanup(workbook, fis);

		} catch (IOException io) {
			logger.error("[DataReader : getXLSContent] Exception is: " + io.getMessage());
		}

	}

	/**
	 * This method reads the XLSX file chosen by user to run the test cases and
	 * set the data to a string array.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getXLSXContent();</code>
	 **/
	public void getXLSXContent() {
		ArrayList<ArrayList<String>> array = new ArrayList<ArrayList<String>>();
		try {
			fis = new FileInputStream(getFilePath());
			workbook = new XSSFWorkbook(fis);
			xsheet = (XSSFSheet) workbook.getSheetAt(0);
			// Iterating over Excel file in Java
			Iterator<Row> itr = xsheet.iterator();
			while (itr.hasNext()) {
				Row row = itr.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				ArrayList<String> list = new ArrayList<String>();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = cell.toString();
					list.add(cellValue);
				}
				array.add(list);
			}
			setDataListArray(array);
			setDataNameValuePairArray(nameValuePairData());
			cleanup(workbook, fis);
		} catch (IOException io) {
			logger.error("[DataReader : getXLSXContent] Exception is: " + io.getMessage());
		}
	}

	/**
	 * This method reads the database and set the data to a string array.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getDatabaseContent();</code>
	 **/
	public void getDatabaseContent(){
		//TO DO STATEMENTS

		/*	STEP1: Create a database connection method and call here or add the connection code here only.
		 *  STEP1: Fetch the entire table data in a String arraylist and save the data row wise.
		 *  STEP2: Create a function which will split these rows in name value pairs. Name value would be
		 *  	   the column header name and its respective value at row 1, 2, 3 etc. Similar way what currently
		 *  	   we are doing for csv, xls, xlsx in function nameValuePairData();
		 *
		 */
	}

	/**
	 * Returns the list of NameValuePair lists from the test file chosen by the
	 * user to run the test cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.nameValuePairData();</code>
	 * <p>
	 *
	 * @return nvpair
	 **/
	public ArrayList<ArrayList<NameValuePair>> nameValuePairData() {
		ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
		data = getDataListArray();

		ArrayList<ArrayList<NameValuePair>> nvpair = new ArrayList<ArrayList<NameValuePair>>();

		ArrayList<String> header = getDataListArray().get(0);
		data.remove(0);
		for (ArrayList<String> row : data) {
			ArrayList<NameValuePair> nvp = new ArrayList<NameValuePair>();
			for (int i = 0; i < header.size(); i++) {
				NameValuePair nameValuePair = new NameValuePair();
				nameValuePair.setElementName(header.get(i));
				nameValuePair.setElementValue(row.get(i));
				nvp.add(nameValuePair);
			}
			nvpair.add(nvp);
		}
		return nvpair;
	}

	/**
	 * This method returns the index of a particular column.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.getColumnIndex(columnName, row);</code>
	 * <p>
	 *
	 * @param columnName  Name of the column for which index is required
	 * @param row A row from NameValuePair ArrayList object
	 * @return Return colIndex of the corresponding column name
	 */
	public Integer getColumnIndex(String columnName, ArrayList<NameValuePair> row) {
		Integer colIndex = -1;
		for (Integer col = 0; col < row.size(); col++) {
			if (row.get(col).getElementName().trim().equals(columnName)) {
				colIndex = col;
				break;
			}
		}
		return colIndex;
	}

	/**
	 * For cleaning up of opened workbook and inputStream resources *
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * DataReader dataReader = new DataReader();<br>
	 * dataReader.cleanup(workbook, inputStream);</code>
	 * <p>
	 *
	 * @param workbook
	 *            Workbook object that needs to be closed
	 * @param inputStream
	 *            InputStream object that needs to be closed
	 */
	public void cleanup(Workbook workbook, FileInputStream inputStream) {
		logger.debug("[DataReader:cleanup()] To close the workbook and inputStream objects");
		try {
			workbook.close();
			inputStream.close();
		} catch (IOException io) {
			logger.error("Exception: " + io.getMessage());
		}
	}
}
