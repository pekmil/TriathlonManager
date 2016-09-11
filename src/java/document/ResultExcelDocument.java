/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import viewmodel.Result;

/**
 *
 * @author pekmil
 */
public abstract class ResultExcelDocument extends ExcelDocument {
    
    protected final Sheet sheet;
    
    protected final ExcelDocumentStyles styles;

    public ResultExcelDocument(String serverSaveFolder) {
        super(serverSaveFolder);
        this.sheet = addSheet("Eredmények");
        this.styles = new ExcelDocumentStyles(wb);
    }
    
    public abstract ResultExcelDocument withCategoryName(String categoryName);
    
    public abstract void withResults(Object resultData);
    
    protected void createHeader(String title, String[] headers){
        addCell(addRow(sheet, 0), 0, title).setCellStyle(styles.getHeaderLevel1());
        mergeCellsInRow(sheet, 0, 0, headers.length);
        Row headerRow = addRow(sheet, 1);
        int i = 0;
        for(String columnName : headers){
            addCell(headerRow, i++, columnName).setCellStyle(styles.getHeaderLevel2());
        }        
    }
    
    protected void addResultmodCell(List<String> resultmods, Row row, int cellIdx, int pos, Result result){
        StringBuilder sb = new StringBuilder();
        if(resultmods != null){
            int size = resultmods.size();
            for(int i = 0; i < size; ++i){
                String mod = resultmods.get(i);
                sb.append(mod).append(i == size - 1 ? "" : "\n");
            }
        }
        Cell c = addCell(row, cellIdx, sb.toString());
        applyMedalistCellStyle(c, pos, false, result);
        c.getCellStyle().setWrapText(true);
    }
    
    protected void applyMedalistCellStyle(Cell c, int pos, boolean team, Result result){
        if(pos < 3 && "FINISHED".equals(result.getStatus())){
            if(team){
                c.setCellStyle(styles.getHeaderLevel4Medalist());
            }
            else{
                c.setCellStyle(styles.getBoldItalic());
            }
        }
        else{
            c.setCellStyle(styles.getBordered());
        }
    }
    
    protected void applyGroupMedalistCellStyle(Cell c, int pos){
        if(pos < 3){
            c.setCellStyle(styles.getHeaderLevel4Medalist());
        }
        else{
            c.setCellStyle(styles.getHeaderLevel4());
        }
    }
    
}
