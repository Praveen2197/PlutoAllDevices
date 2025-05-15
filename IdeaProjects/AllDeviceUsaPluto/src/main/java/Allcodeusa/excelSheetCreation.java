package Allcodeusa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

public class excelSheetCreation extends webRecording{
	
	public static Sheet sheet;
	public static int rowIndex=0;
	public Workbook workbook;
	public File resultFile;
	
	public void ExcelSheetCreation() {
	    String callingMethodName = this.getClass().getSimpleName();
		String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		resultFile=new File("test_result_"+callingMethodName+"_"+timeStamp+".xlsx");
		workbook = new XSSFWorkbook();
		sheet=workbook.createSheet("Test results");
		Row headerRow=sheet.createRow(rowIndex++);
		headerRow.createCell(0).setCellValue("Test Name");
		headerRow.createCell(1).setCellValue("Duration (ms)");
		headerRow.createCell(2).setCellValue("Status");
		headerRow.createCell(3).setCellValue("Video Link");
		if(resultFile.exists()) {
			resultFile.delete();
		}
	}
	
	 public void writeToExcel(String testName, long duration, String status, String videoLink) {
		 	Row row = sheet.createRow(rowIndex++);
	        row.createCell(0).setCellValue(testName);
	        row.createCell(1).setCellValue(duration);
	        row.createCell(2).setCellValue(status);
	        row.createCell(3).setCellValue(videoLink);
	    }
	 
	 public static void writeToExcelForMobile(String testName, long duration, String status) {
		 	Row row = sheet.createRow(rowIndex++);
	        row.createCell(0).setCellValue(testName);
	        row.createCell(1).setCellValue(duration);
	        row.createCell(2).setCellValue(status);
	    }
	 
	 public void fileWrite() {
		 try (FileOutputStream fileOut = new FileOutputStream(resultFile)) {
	            workbook.write(fileOut);
	            workbook.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	 }
}
