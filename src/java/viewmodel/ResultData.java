/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.Date;

/**
 *
 * @author pekmil
 */
public class ResultData {
    
    private short racenum;
    private Date racetime;
    
    public ResultData(){
        
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
    
}
