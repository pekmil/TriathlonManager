/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultevaluator;

import resultevaluator.ResultEvaluator;
import resultevaluator.ResultType;

/**
 *
 * @author pekmil
 */
public interface ResultEvaluatorFactory {
    
    public ResultEvaluator getResultEvaluator(ResultType type);
    
}
