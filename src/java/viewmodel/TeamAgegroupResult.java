/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author pekmil
 */
public class TeamAgegroupResult {
    
    private final List<TeamResult> maleResults;
    private final List<TeamResult> femaleResults;

    public TeamAgegroupResult(List<TeamResult> maleResults, List<TeamResult> femaleResults) {
        this.maleResults = maleResults;
        this.femaleResults = femaleResults;
    }

    public List<TeamResult> getMaleResults() {
        if(maleResults != null) Collections.sort(maleResults, (tr1, tr2) -> tr1.getTeamDuration().compareTo(tr2.getTeamDuration()));
        return maleResults;
    }

    public List<TeamResult> getFemaleResults() {
        if(femaleResults != null) Collections.sort(femaleResults, (tr1, tr2) -> tr1.getTeamDuration().compareTo(tr2.getTeamDuration()));
        return femaleResults;
    }
    
    public boolean isEmpty(){
        return (maleResults == null || maleResults.isEmpty()) && (femaleResults == null || femaleResults.isEmpty());
    }
    
}
