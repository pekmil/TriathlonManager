/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import util.Utils;

/**
 *
 * @author pekmil
 */
public class TeamResult {
    
    private String clubName;
    private String teamTime;
    private Duration teamDuration;
    
    private List<Result> teamResults;

    public TeamResult() {
        this.teamDuration = Duration.ZERO;
    }

    public String getTeamTime() {
        LocalTime lt = LocalTime.MIDNIGHT.plus(teamDuration);
        teamTime = DateTimeFormatter.ISO_LOCAL_TIME.format(lt);
        return teamTime;
    }

    public void setTeamTime(String teamTime) {
        this.teamTime = teamTime;
    }

    public List<Result> getTeamResults() {
        return teamResults;
    }

    public void setTeamResults(List<Result> teamResults) {
        Collections.sort(teamResults, (r1, r2) -> r1.getRt().compareTo(r2.getRt()));
         for(int i = 0; i < 3; ++i){
            Duration d = Utils.dateToDuration(teamResults.get(i).getRt());
            teamDuration = teamDuration.plus(d);
        }
        this.teamResults = teamResults;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    @JsonIgnore
    public Duration getTeamDuration() {
        return teamDuration;
    }
    
}
