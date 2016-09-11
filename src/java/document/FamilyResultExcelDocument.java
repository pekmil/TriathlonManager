/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Row;
import util.Utils;
import viewmodel.FamilyMemberResult;
import viewmodel.FamilyResult;

/**
 *
 * @author pekmil
 */
public class FamilyResultExcelDocument extends ResultExcelDocument {
    
    private final String[] columnNames = {"Név", "Helyezési", "Bónusz pont"};

    public FamilyResultExcelDocument(String serverSaveFolder) {
        super(serverSaveFolder);
    }

    @Override
    public ResultExcelDocument withCategoryName(String categoryName) {
        createHeader("Családi eredmények", columnNames);
        withFileName("eredmenylista_csalad_" + Utils.formatDate(new Date()) + ".xlsx");
        return this;
    }

    @Override
    public void withResults(Object resultData) {
        if(resultData instanceof List){
            List<FamilyResult> data = (List<FamilyResult>)resultData;
            int rowIdx = 3;
            int pos = 0;
            for(FamilyResult fr : data){
                applyGroupMedalistCellStyle(addCell(addRow(sheet, rowIdx), 0, 
                        fr.getFamilyName() + " - " + fr.getFamilyPoint() + " pont"), pos++);
                mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                rowIdx++;
                for(FamilyMemberResult fmr : fr.getMemberResults()){
                    addResultRow(addRow(sheet, rowIdx++), fmr);
                }
            }
            autoSizeColumns(sheet, columnNames.length);
        }
        else{
            throw new IllegalArgumentException("Arguemnt is not the correct type");
        }
    }
    
    private void addResultRow(Row row, FamilyMemberResult result){
        int cellIdx = 0;
        addCell(row, cellIdx++, result.getName()).setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, new Integer(result.getRankPoint()).doubleValue()).setCellStyle(styles.getBordered());
        addCell(row, cellIdx++, new Integer(result.getBonusPoint()).doubleValue()).setCellStyle(styles.getBordered());
    }
    
}
