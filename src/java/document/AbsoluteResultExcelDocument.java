/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.Date;
import org.apache.poi.ss.usermodel.Row;
import util.Utils;
import viewmodel.AbsoluteResults;
import viewmodel.AgegroupResult;
import viewmodel.Result;

/**
 *
 * @author pekmil
 */
public class AbsoluteResultExcelDocument extends ResultExcelDocument {
    
    private final String[] columnNames = {"Név", "Rajtszám", "Születési év",
                                          "Település", "Klub", "Licensz", "Idő",
                                          "Megjegyzés"};

    public AbsoluteResultExcelDocument(String serverSaveFolder) {
        super(serverSaveFolder);
    }

    @Override
    public ResultExcelDocument withCategoryName(String categoryName) {
        createHeader(categoryName + " - Abszolút", columnNames);
        withFileName("eredmenylista_abszolut_" + categoryName + "_" + Utils.formatDate(new Date()) + ".xlsx");
        return this;
    }

    @Override
    public void withResults(Object resultData) {
        if(resultData instanceof AbsoluteResults){
            AbsoluteResults data = (AbsoluteResults)resultData;
            int rowIdx = 3;
            AgegroupResult result = data.getAbsoluteResults();
            if(result.getFemaleResults() != null && !result.getFemaleResults().isEmpty()){
                addCell(addRow(sheet, rowIdx), 0, "Nő").setCellStyle(styles.getHeaderLevel3());
                mergeCellsInRow(sheet, rowIdx, 0, 8);
                rowIdx++;
                int pos = 0;
                for(Result r : result.getFemaleResults()){
                    addResultRow(addRow(sheet, rowIdx++), r, pos++);
                }
                rowIdx++;
            }            
            if(result.getMaleResults() != null && !result.getMaleResults().isEmpty()){
                addCell(addRow(sheet, rowIdx), 0, "Férfi").setCellStyle(styles.getHeaderLevel3());
                mergeCellsInRow(sheet, rowIdx, 0, 8);
                rowIdx++;
                int pos = 0;
                for(Result r : result.getMaleResults()){
                    addResultRow(addRow(sheet, rowIdx++), r, pos++);
                }
                rowIdx++;
            }
            autoSizeColumns(sheet, columnNames.length);
        }
        else{
            throw new IllegalArgumentException("Arguemnt is not the type of AbsoluteResults");
        }
    }
    
    private void addResultRow(Row row, Result result, int pos){
        int cellIdx = 0;
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getName()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, new Integer(result.getRacenum()).doubleValue()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getBirthYear()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getFromTown()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getClub()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getLicencenum()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getRacetime()), pos, false, result);
        addResultmodCell(result.getResultmodNames(), row, cellIdx++, pos, result);
    }
    
}
