/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import resultevaluator.AbsoluteResultEvaluator;
import resultevaluator.FamilyResultEvaluator;
import resultevaluator.GroupedResultEvaluator;
import resultevaluator.ResultEvaluator;
import resultevaluator.ResultType;
import resultevaluator.TeamResultEvaluator;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class DefaultResultEvaluatorFactory implements ResultEvaluatorFactory {
    
    @Inject
    private GroupedResultEvaluator grouped;
    @Inject
    private AbsoluteResultEvaluator absolute;
    @Inject
    private TeamResultEvaluator team;
    @Inject
    private FamilyResultEvaluator family;

    @Override
    public ResultEvaluator getResultEvaluator(ResultType type) {
        switch(type){
            case TEAM:
                return team;
            case ABSOLUTE:
                return absolute;
            case FAMILY:
                return family;
            default:
                return grouped;
        }
    }
    
}
