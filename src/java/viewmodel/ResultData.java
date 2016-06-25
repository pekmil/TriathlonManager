/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pekmil
 */
public class ResultData {
    
    private short racenum;
    private Date racetime;
    
    private String racetimemoddesc;
    
    private List<String> resultmodIds;
    private boolean rollback;
    
    public ResultData(){
        this.resultmodIds = new ArrayList<>();
    }

    public short getRacenum() {
        return racenum;
    }

    public void setRacenum(short racenum) {
        this.racenum = racenum;
    }

    public Date getRacetime() {
        return racetime;
    }

    public void setRacetime(Date racetime) {
        this.racetime = racetime;
    }

    public String getRacetimemoddesc() {
        return racetimemoddesc;
    }

    public void setRacetimemoddesc(String racetimemoddesc) {
        this.racetimemoddesc = racetimemoddesc;
    }

    public List<String> getResultmodIds() {
        return resultmodIds;
    }

    public void setResultmodIds(List<String> resultmodIds) {
        this.resultmodIds = resultmodIds;
    }

    public boolean isRollback() {
        return rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
    
}
