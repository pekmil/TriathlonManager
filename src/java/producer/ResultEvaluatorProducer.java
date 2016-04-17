/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import resultevaluator.AbsoluteResultEvaluator;
import resultevaluator.FamilyResultEvaluator;
import resultevaluator.GroupedResultEvaluator;
import resultevaluator.TeamResultEvaluator;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class ResultEvaluatorProducer {
    
    @Inject
    Properties parameters;

    public ResultEvaluatorProducer() {
        
    }
    
    @Produces
    public GroupedResultEvaluator getGroupedResultEvaluator(){
        GroupedResultEvaluator groupedResultEvaluator = new GroupedResultEvaluator(parameters);
        return groupedResultEvaluator;
    }
    
    @Produces
    public AbsoluteResultEvaluator getAbsoluteResultEvaluator(){
        AbsoluteResultEvaluator absoluteResultEvaluator = new AbsoluteResultEvaluator(parameters);
        return absoluteResultEvaluator;
    }
    
    @Produces
    public TeamResultEvaluator getTeamResultEvaluator(){
        TeamResultEvaluator teamResultEvaluator = new TeamResultEvaluator(parameters);
        return teamResultEvaluator;
    }
    
    @Produces
    public FamilyResultEvaluator getFamilyResultEvaluator(){
        FamilyResultEvaluator familyResultEvaluator = new FamilyResultEvaluator(parameters);
        return familyResultEvaluator;
    }
    
}
