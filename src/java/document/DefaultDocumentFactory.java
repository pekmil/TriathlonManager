/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import resultevaluator.ResultType;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class DefaultDocumentFactory extends DocumentFactory {
    
    @Inject
    Properties appParameters;

    @Override
    public StartlistExcelDocument createStartlist() {
        return new StartlistExcelDocument(appParameters.getProperty("documentFolder"));
    }

    @Override
    public ResultExcelDocument createResultlist(ResultType type) {
        switch(type){
            case ABSOLUTE:
                return new AbsoluteResultExcelDocument(appParameters.getProperty("documentFolder"));
            case TEAM:
                return new TeamResultExcelDocument(appParameters.getProperty("documentFolder"));
            case FAMILY:
                return new FamilyResultExcelDocument(appParameters.getProperty("documentFolder"));
            case GROUPED:
            default:
                return new GroupedResultExcelDocument(appParameters.getProperty("documentFolder"));
        }        
    }        
    
    
}
