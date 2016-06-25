/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import entity.Entry;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author pekmil
 */
public class AbsoluteResults {
    
    private static final SimpleDateFormat simpleTimeFormat;
    
    static{
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
    }
    
    private AgegroupResult absoluteResults;

    public AbsoluteResults(List<Entry> entries) {
        Map<String, List<Entry>> entriesByGender = entries.stream().
                sorted((e1, e2) -> e1.getRacetime().compareTo(e2.getRacetime())).
                collect(Collectors.groupingBy(e -> e.getContestant().getGender()));
        Map<String, List<Result>> resultsByGender = new HashMap<>();
        entriesByGender.keySet().stream().forEach(k -> {
            List<Result> results = entriesByGender.get(k).stream().map(e -> {
               Result r = new Result();
               r.setName(e.getContestant().getName());
               r.setRacenum(e.getKey().getRacenum());
               r.setClub(e.getContestant().getClub() != null ? e.getContestant().getClub().getName() : "-");
               r.setRacetime(simpleTimeFormat.format(e.getRacetime()));
               r.setLicencenum(e.getLicencenum());
               r.setResultmodNames(e.getResultmodNames());
               return r;
            }).collect(Collectors.toList());
            resultsByGender.put(k, results);
        });
        this.absoluteResults = new AgegroupResult(resultsByGender.get("MALE"), resultsByGender.get("FEMALE"));       
    }

    public AgegroupResult getAbsoluteResults() {
        return absoluteResults;
    }

    public void setAbsoluteResults(AgegroupResult absoluteResults) {
        this.absoluteResults = absoluteResults;
    }
    
}
