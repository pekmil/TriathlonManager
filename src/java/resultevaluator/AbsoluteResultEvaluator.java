/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultevaluator;

import entity.Agegroup;
import entity.Entry;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import viewmodel.AbsoluteResults;

/**
 *
 * @author pekmil
 */
public class AbsoluteResultEvaluator extends ResultEvaluator {

    public AbsoluteResultEvaluator(Properties appProperties) {
        super(appProperties);
    }

    @Override
    public Object getResults(List<Entry> entries, Map<String, Agegroup> agegroups) {
        return new AbsoluteResults(entries);
    }
    
}
