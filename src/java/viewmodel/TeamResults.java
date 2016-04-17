package viewmodel;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pekmil
 */
public class TeamResults {        
    
    private Map<String, TeamAgegroupResult> teamResult = new HashMap<>();
    
    public TeamResults(){
        
    }

    public Map<String, TeamAgegroupResult> getTeamResult() {
        return teamResult;
    }

    public void setTeamResult(Map<String, TeamAgegroupResult> teamResult) {
        this.teamResult = teamResult;
    }
    
}
