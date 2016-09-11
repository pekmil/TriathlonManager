/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resultevaluator;

/**
 *
 * @author pekmil
 */
public class ResultParams {
    
    private boolean absolute;
    private boolean team;
    private boolean family;
    private boolean national;

    public ResultParams() {
    }

    public ResultType getResultType(){
        if(absolute) return ResultType.ABSOLUTE;
        else if(team) return ResultType.TEAM;
        else if(family) return ResultType.FAMILY;
        else return ResultType.GROUPED;
    }
    
    public void setAbsolute(boolean absolute) {
        this.absolute = absolute;
    }

    public void setTeam(boolean team) {
        this.team = team;
    }

    public void setFamily(boolean family) {
        this.family = family;
    }

    public boolean isAbsolute() {
        return absolute;
    }

    public boolean isTeam() {
        return team;
    }

    public boolean isFamily() {
        return family;
    }

    public boolean isNational() {
        return national;
    }

    public void setNational(boolean national) {
        this.national = national;
    }
    
}
