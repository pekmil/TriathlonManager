/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import entity.Agegroup;
import java.util.Date;
import org.apache.poi.ss.usermodel.Row;
import util.Utils;
import viewmodel.AgegroupResult;
import viewmodel.GroupedResults;
import viewmodel.Result;

/**
 *
 * @author pekmil
 */
public class GroupedResultExcelDocument extends ResultExcelDocument {        
    
    private static final String[] columnNames = {"Név", "Rajtszám", "Születési év",
                                          "Település", "Klub", "Licensz", "Idő",
                                          "Megjegyzés"};

    public GroupedResultExcelDocument(String serverSaveFolder) {
        super(serverSaveFolder);           
    }
    
    @Override
    public ResultExcelDocument withCategoryName(String categoryName){
        createHeader(categoryName, columnNames);
        withFileName("eredmenylista_" + categoryName + "_" + Utils.formatDate(new Date()) + ".xlsx");
        return this;
    }
    
    @Override
    public void withResults(Object resultData){
        if(resultData instanceof GroupedResults){
            GroupedResults data = (GroupedResults)resultData;
            int rowIdx = 3;
            for(Agegroup agegroup : data.getGroupedResults().keySet()){
                AgegroupResult result = data.getGroupedResults().get(agegroup);
                if(result.getFemaleResults() != null && !result.getFemaleResults().isEmpty()){
                    addCell(addRow(sheet, rowIdx), 0, agegroup.getName() + " - Nő")
                            .setCellStyle(styles.getHeaderLevel3());
                    mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);                   
                    rowIdx++;
                    int pos = 0;
                    for(Result r : result.getFemaleResults()){
                        addResultRow(addRow(sheet, rowIdx++), r, pos++);
                    }
                    rowIdx++;
                }            
                if(result.getMaleResults() != null && !result.getMaleResults().isEmpty()){
                    addCell(addRow(sheet, rowIdx), 0, agegroup.getName() + " - Férfi")
                            .setCellStyle(styles.getHeaderLevel3());
                    mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                    rowIdx++;
                    int pos = 0;
                    for(Result r : result.getMaleResults()){
                        addResultRow(addRow(sheet, rowIdx++), r, pos++);
                    }
                    rowIdx++;
                }            
            }
            autoSizeColumns(sheet, columnNames.length);
        }
        else{
            throw new IllegalArgumentException("Arguemnt is not the type of GroupedResults");
        }
    }
    
    private void addResultRow(Row row, Result result, int pos){
        int cellIdx = 0;
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getName()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getRacenum()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getBirthYear()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getFromTown()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getClub()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getLicencenum()), pos, false, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, 
                result.getRacetime() != null ? result.getRacetime() : "-"), pos, false, result);
        if("FINISHED".equals(result.getStatus())){
            addResultmodCell(result.getResultmodNames(), row, cellIdx++, pos, result);
        }
        else{
            applyMedalistCellStyle(addCell(row, cellIdx++, result.getStatusString()), pos, false, result);
        }
    }        
    
}
