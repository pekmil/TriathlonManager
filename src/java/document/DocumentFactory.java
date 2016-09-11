/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import resultevaluator.ResultType;

/**
 *
 * @author pekmil
 */
public abstract class DocumentFactory {

    public abstract StartlistExcelDocument createStartlist();
    
    public abstract ResultExcelDocument createResultlist(ResultType type);
    
}
