package resultevaluator;

import entity.Agegroup;
import entity.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import util.Utils;
import viewmodel.Result;
import viewmodel.TeamAgegroupResult;
import viewmodel.TeamResult;
import viewmodel.TeamResults;

/**
 *
 * @author pekmil
 */
public class TeamResultEvaluator extends ResultEvaluator {
    
    private final Map<String, String> teamAgegroupMapper = new HashMap<>();
    
    private final int teamPlaces;

    public TeamResultEvaluator(Properties appProperties) {
        super(appProperties);
        String teamAgegroupsString = appProperties.getProperty("teamAgegroups");
        String[] teamAgegroups = teamAgegroupsString.split("\\|");
        for(String teamAgegroup : teamAgegroups){
            String[] parts = teamAgegroup.split(";");
            String teamAgegroupName = parts[1];
            for(String originalAgegroupPrefix : parts[0].split(",")){
                this.teamAgegroupMapper.put(originalAgegroupPrefix, teamAgegroupName);
            }
        }
        this.teamPlaces = Integer.parseInt(appProperties.getProperty("teamPlaces", "3"));
    }

    @Override
    public Object getResults(List<Entry> entries, Map<String, Agegroup> agegroups) {
        TeamResults teamResults = new TeamResults();
        Map<String, List<Entry>> entriesByTeamAgegroup  = entries.stream().
                                collect(Collectors.groupingBy(e -> e.getTeamAgegroup(teamAgegroupMapper)));
        entriesByTeamAgegroup.keySet().stream().forEach(agegroup -> {
            Map<String, List<Entry>> entriesByGender = entriesByTeamAgegroup.get(agegroup).stream().
                    collect(Collectors.groupingBy(e -> e.getContestant().getGender()));
            Map<String, List<TeamResult>> resultsByGender = new HashMap<>();
            entriesByGender.keySet().stream().forEach(gender -> {
                Map<String, List<Entry>> entriesByTeam = entriesByGender.get(gender).stream().
                                collect(Collectors.groupingBy(e -> e.getContestant().getClub().getName()));
                entriesByTeam.keySet().stream().
                                       filter(team -> entriesByTeam.get(team).size() >= teamPlaces).
                                       forEach(team -> {                    
                    List<Result> results = entriesByTeam.get(team).stream().map(e -> {
                        Result r = new Result();
                        r.setName(e.getContestant().getName());
                        r.setRacenum(e.getKey().getRacenum());
                        r.setClub(e.getContestant().getClub() != null ? e.getContestant().getClub().getName() : "-");
                        r.setLicencenum(e.getLicencenum());
                        r.setRt(e.getRacetime());
                        r.setRacetime(Utils.simpleTimeFormat.format(e.getRacetime()));
                        r.setResultmodNames(e.getResultmodNames());
                        return r;
                    }).collect(Collectors.toList());
                    TeamResult tr = new TeamResult();
                    tr.setClubName(team);
                    tr.setTeamResults(results, teamPlaces);
                    if(!resultsByGender.containsKey(gender)) resultsByGender.put(gender, new ArrayList<>());
                    resultsByGender.get(gender).add(tr);
                });
            });
            TeamAgegroupResult teamAgegroupResult = new TeamAgegroupResult(resultsByGender.get("MALE"), resultsByGender.get("FEMALE"));
            if(!teamAgegroupResult.isEmpty()){
                teamResults.getTeamResult().put(agegroup, teamAgegroupResult);
            }
        });
        return teamResults;
    }
    
}
