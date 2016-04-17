/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

/**
 *
 * @author pekmil
 */
public class FamilyMemberResult {
    
    private String name;
    
    private int bonusPoint;
    private int rankPoint;
    
    public FamilyMemberResult(){}

    public FamilyMemberResult(String name, int bonusPoint, int rankPoint) {
        this.name = name;
        this.bonusPoint = bonusPoint;
        this.rankPoint = rankPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }

    public int getRankPoint() {
        return rankPoint;
    }

    public void setRankPoint(int rankPoint) {
        this.rankPoint = rankPoint;
    }
    
}
