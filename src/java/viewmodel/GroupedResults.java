/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import entity.Agegroup;
import entity.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import util.Utils;

/**
 *
 * @author pekmil
 */
public class GroupedResults {        
    
    private Map<Agegroup, AgegroupResult> groupedResults;

    public GroupedResults(List<Entry> entries, Map<String, Agegroup> agegroups) {
        this.groupedResults = new TreeMap<>((ag1, ag2) -> ag1.getDisplayorder().compareTo(ag2.getDisplayorder()));
        Map<String, List<Entry>> entriesByAgegroup = entries.stream().
                sorted((e1, e2) -> e1.getRacetime().compareTo(e2.getRacetime())).
                collect(Collectors.groupingBy(Entry::getAgegroupName));
        entriesByAgegroup.keySet().stream().forEach((agegroup) -> {
            Map<String, List<Entry>> entriesByGender = entriesByAgegroup.get(agegroup).stream().
                    collect(Collectors.groupingBy(e -> e.getContestant().getGender()));
            Map<String, List<Result>> resultsByGender = new HashMap<>();
            entriesByGender.keySet().stream().forEach(k -> {
                List<Result> results = entriesByGender.get(k).stream().map(e -> {
                   Result r = new Result();
                   r.setName(e.getContestant().getName());
                   r.setRacenum(e.getKey().getRacenum());
                   r.setClub(e.getContestant().getClub() != null ? e.getContestant().getClub().getName() : "-");
                   r.setRacetime(Utils.simpleTimeFormat.format(e.getRacetime()));
                   r.setLicencenum(e.getLicencenum());
                   return r;
                }).collect(Collectors.toList());
                resultsByGender.put(k, results);
            });
            AgegroupResult agegroupResult = new AgegroupResult(resultsByGender.get("MALE"), resultsByGender.get("FEMALE"));
            groupedResults.put(agegroups.get(agegroup), agegroupResult);
        });
    }

    public Map<Agegroup, AgegroupResult> getGroupedResults() {
        return groupedResults;
    }

    public void setGroupedResults(Map<Agegroup, AgegroupResult> groupedResults) {
        this.groupedResults = groupedResults;
    }
    
}
