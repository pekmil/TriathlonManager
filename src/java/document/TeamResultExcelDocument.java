/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.Date;
import org.apache.poi.ss.usermodel.Row;
import util.Utils;
import viewmodel.Result;
import viewmodel.TeamAgegroupResult;
import viewmodel.TeamResult;
import viewmodel.TeamResults;

/**
 *
 * @author pekmil
 */
public class TeamResultExcelDocument extends ResultExcelDocument {
    
    private final String[] columnNames = {"Név", "Rajtszám", "Klub", "Licensz", "Idő"};

    public TeamResultExcelDocument(String serverSaveFolder) {
        super(serverSaveFolder);
    }

    @Override
    public ResultExcelDocument withCategoryName(String categoryName) {
        createHeader(categoryName + " - Csapat", columnNames);
        withFileName("eredmenylista_csapat_" + categoryName + "_" + Utils.formatDate(new Date()) + ".xlsx");
        return this;
    }

    @Override
    public void withResults(Object resultData) {
        if(resultData instanceof TeamResults){
            TeamResults data = (TeamResults)resultData;
            int rowIdx = 3;
            for(String agegroup : data.getTeamResult().keySet()){
                TeamAgegroupResult result = data.getTeamResult().get(agegroup);
                if(result.getFemaleResults() != null && !result.getFemaleResults().isEmpty()){
                    addCell(addRow(sheet, rowIdx), 0, agegroup + " - Nő")
                            .setCellStyle(styles.getHeaderLevel3());
                    mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                    rowIdx++;
                    int teamPos = 0;
                    for(TeamResult tr : result.getFemaleResults()){
                        applyGroupMedalistCellStyle(addCell(addRow(sheet, rowIdx), 
                                0, tr.getClubName() + " - " + tr.getTeamTime()), teamPos++);
                        mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                        rowIdx++;
                        int pos = 0;
                        for(Result r : tr.getTeamResults()){
                            addResultRow(addRow(sheet, rowIdx++), r, pos++);
                        }
                    }
                    rowIdx++;
                }            
                if(result.getMaleResults() != null && !result.getMaleResults().isEmpty()){
                    addCell(addRow(sheet, rowIdx), 0, agegroup + " - Férfi")
                            .setCellStyle(styles.getHeaderLevel3());
                    mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                    rowIdx++;
                    int teamPos = 0;
                    for(TeamResult tr : result.getMaleResults()){
                        applyGroupMedalistCellStyle(addCell(addRow(sheet, rowIdx), 
                                0, tr.getClubName() + " - " + tr.getTeamTime()), teamPos++);
                        mergeCellsInRow(sheet, rowIdx, 0, columnNames.length);
                        rowIdx++;
                        int pos = 0;
                        for(Result r : tr.getTeamResults()){
                            addResultRow(addRow(sheet, rowIdx++), r, pos++);
                        }
                    }
                    rowIdx++;
                }            
            }
            autoSizeColumns(sheet, columnNames.length);
        }
        else{
            throw new IllegalArgumentException("Arguemnt is not the type of TeamResults");
        }
    }
    
    private void addResultRow(Row row, Result result, int pos){
        int cellIdx = 0;
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getName()), pos, true, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, new Integer(result.getRacenum()).doubleValue()), pos, true, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getClub()), pos, true, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getLicencenum()), pos, true, result);
        applyMedalistCellStyle(addCell(row, cellIdx++, result.getRacetime()), pos, true, result);
    }
    
}
