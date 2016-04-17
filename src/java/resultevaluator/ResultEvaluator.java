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

/**
 *
 * @author pekmil
 */
public abstract class ResultEvaluator {
    
    protected final Properties appProperties;

    public ResultEvaluator(Properties appProperties) {
        this.appProperties = appProperties;
    }
    
    public abstract Object getResults(List<Entry> entries, Map<String, Agegroup> agegroups);
    
}
