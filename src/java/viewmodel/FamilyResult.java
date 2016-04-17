/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pekmil
 */
public class FamilyResult {
    
    private String familyName;
    private int familyPoint;
    
    private List<FamilyMemberResult> memberResults;

    public FamilyResult(String familyName) {
        this.familyName = familyName;
        memberResults = new ArrayList<>();
    }
    
    public void addMemberResult(FamilyMemberResult result){
        memberResults.add(result);
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getFamilyPoint() {
        this.familyPoint = memberResults.stream().mapToInt(mr -> mr.getBonusPoint() + mr.getRankPoint()).sum();
        return familyPoint;
    }

    public void setFamilyPoint(int familyPoint) {
        this.familyPoint = familyPoint;
    }

    public List<FamilyMemberResult> getMemberResults() {
        return memberResults;
    }

    public void setMemberResults(List<FamilyMemberResult> memberResults) {
        this.memberResults = memberResults;
    }
    
}
