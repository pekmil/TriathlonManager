/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultevaluator;

import entity.Agegroup;
import entity.Entry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import viewmodel.FamilyMemberResult;
import viewmodel.FamilyResult;

/**
 *
 * @author pekmil
 */
public class FamilyResultEvaluator extends ResultEvaluator {

    private final List<String> BONUS_POINT_CATEGORIES;
    private final int BONUS_POINT;
    private final int MAX_RANK_POINT;       

    public FamilyResultEvaluator(Properties appProperties) {
        super(appProperties);
        BONUS_POINT_CATEGORIES = new ArrayList<>();
        BONUS_POINT = Integer.valueOf(appProperties.getProperty("familyBonusCategoryPoint"));
        MAX_RANK_POINT = Integer.valueOf(appProperties.getProperty("familyMaxRankPoint"));
        BONUS_POINT_CATEGORIES.addAll(Arrays.asList(appProperties.getProperty("familyBonusCategories").split(",")));
    }

    @Override
    public Object getResults(List<Entry> entries, Map<String, Agegroup> agegroups) {
        return calculateFamilyResults(getRaceResults(entries));
    }
        
    private List<FamilyResult> calculateFamilyResults(Map<String, Map<String, List<Entry>>> raceResults) {
        Map<String, FamilyResult> resultMap = new HashMap<>();
        List<FamilyResult> results = new ArrayList<>();
        raceResults.values().stream().forEach(map -> {
            map.keySet().forEach(key -> {
                List<Entry> entries = map.get(key);
                entries.stream().filter((e) -> (e.getFamilyentry() != null)).forEach((e) -> {
                    String familyName = e.getFamilyentry().getName();
                    if(!resultMap.containsKey(familyName)){
                        resultMap.put(familyName, new FamilyResult(familyName));
                    }
                    resultMap.get(familyName).addMemberResult(mapEntry(e, entries.indexOf(e)));
                });
            });
        });
        results.addAll(resultMap.values());
        Collections.sort(results, (fra, frb) -> frb.getFamilyPoint() - fra.getFamilyPoint());
        return results;
    }
    
    private Map<String, Map<String, List<Entry>>> getRaceResults(List<Entry> entries){
        final Map<String, List<Entry>> entriesByAgegroup = entries.stream().
                sorted((e1, e2) -> e1.getRacetime().compareTo(e2.getRacetime())).
                collect(Collectors.groupingBy(Entry::getAgegroupName));
        final Map<String, Map<String, List<Entry>>> groupedEntries = new HashMap<>();
        entriesByAgegroup.keySet().stream().forEach((agegroup) -> {
            Map<String, List<Entry>> entriesByGender = entriesByAgegroup.get(agegroup).stream().
                collect(Collectors.groupingBy(e -> e.getContestant().getGender()));
            groupedEntries.put(agegroup, entriesByGender);
        });
        return groupedEntries;
    }
    
    private FamilyMemberResult mapEntry(Entry entry, int rank){
        String name = entry.getContestant().getName();
        int bonusPoint = BONUS_POINT_CATEGORIES.contains(entry.getCategory().getName()) ? BONUS_POINT : 0;
        int rankPoint = Math.max(0, MAX_RANK_POINT - rank);
        return new FamilyMemberResult(name, bonusPoint, rankPoint);
    }
    
}
