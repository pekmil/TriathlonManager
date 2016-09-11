/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author pekmil
 */
public abstract class ExcelDocument extends Document {
    
    protected final XSSFWorkbook wb;
    
    public ExcelDocument(String serverSaveFolder){
        super(serverSaveFolder);
        this.wb = new XSSFWorkbook();
    }

    @Override
    public boolean generate() {
        try{
            Path savePath = Paths.get(this.serverSaveFolder, this.fileName);
            try (FileOutputStream fileOut = new FileOutputStream(savePath.toFile())) {
                wb.write(fileOut);
            }
            return true;
        }
        catch(Exception ex){
            return false;
        }        
    }
    
    protected Sheet addSheet(String sheetName){
        return wb.createSheet(sheetName);
    }
    
    protected Row addRow(Sheet sheet, int rowIdx){
        return sheet.createRow(rowIdx);
    }
    
    protected Cell addCell(Row row, int cellIdx, String cellData){
        Cell cell = row.createCell(cellIdx);
        cell.setCellValue(cellData);
        return cell;
    }
    
    protected Cell addCell(Row row, int cellIdx, double cellData){
        Cell cell = row.createCell(cellIdx);
        cell.setCellValue(cellData);
        return cell;
    }
    
    protected Cell addCell(Row row, int cellIdx, Date cellData){
        Cell cell = row.createCell(cellIdx);
        cell.setCellValue(cellData);
        return cell;
    }
    
    protected void mergeCellsInRow(Sheet sheet, int rowIdx, int startCellIdx, int span){
        sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx,
                                                   startCellIdx, startCellIdx + span - 1));
    }
    
    protected void autoSizeColumns(Sheet sheet, int length){
        for(int i = 0; i < length; ++i){
            sheet.autoSizeColumn(i);
        }
    }
    
}
