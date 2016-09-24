/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import entity.Entry;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import util.Utils;

/**
 *
 * @author pekmil
 */
public class StartlistExcelDocument extends ExcelDocument {
    
    private static final String[] columnNames = {"Rajtszám", "Név", "Születési év",
                                          "Település", "Klub" };
    
    private final Sheet sheet;
    
    protected final ExcelDocumentStyles styles;
    
    private Map<String, List<Entry>> categoryEntriesByGender;
    
    public StartlistExcelDocument(String serverSaveFolder){
        super(serverSaveFolder);
        this.sheet = addSheet("Rajtlista");
        this.styles = new ExcelDocumentStyles(wb);
    }
    
    public StartlistExcelDocument withCategoryName(String categoryName){
        addCell(addRow(sheet, 0), 0, "Rajtlista - " + categoryName)
                .setCellStyle(styles.getHeaderLevel1());
        mergeCellsInRow(sheet, 0, 0, 5);
        Row headerRow = addRow(sheet, 1);
        int i = 0;
        for(String columnName : columnNames){
            addCell(headerRow, i++, columnName).setCellStyle(styles.getHeaderLevel2());
        } 
        withFileName("rajtlista_" + categoryName + "_" + Utils.formatDate(new Date()) + ".xlsx");
        return this;
    }
    
    public void withEntries(Map<String, List<Entry>> data){
        int rowIdx = 3;
        for(String gender : data.keySet()){
            addCell(addRow(sheet, rowIdx), 0, gender.equals("MALE") ? "Férfi": "Nő")
                    .setCellStyle(styles.getHeaderLevel2());
            mergeCellsInRow(sheet, rowIdx, 0, 5);
            rowIdx++;
            for(Entry entry : data.get(gender)){
                addEntryRow(addRow(sheet, rowIdx++), entry);
            }
            addSumRow(addRow(sheet, rowIdx++), data.get(gender).size());
            rowIdx++;
        }
        autoSizeColumns(sheet, columnNames.length);
    }
    
    private void addEntryRow(Row row, Entry entry){
        int cellIdx = 0;
        addCell(row, cellIdx++, entry.getKey().getRacenum())
                .setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, entry.getContestant().getName())
                .setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, new Short(entry.getContestant().getBirthyear()).doubleValue())
                .setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, entry.getContestant().getFromtown())
                .setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, entry.getContestant().getClub() == null ?
                                "-" : entry.getContestant().getClub().getName())
                .setCellStyle(styles.getBordered());
    }
    
    private void addSumRow(Row row, int count){
        addCell(row, columnNames.length - 1, "Összesen: " + count + " fő")
                .setCellStyle(styles.getBordered());
    }
    
}
