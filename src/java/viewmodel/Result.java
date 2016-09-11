/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.List;

/**
 *
 * @author pekmil
 */
public class Result {
        
    private String name;
    private int birthYear;
    private String fromTown;
    private int racenum;
    private String club;
    private String licencenum;
    private String racetime;
    private String statusString;
    private String status;
    private Date rt;
    private List<String> resultmodNames;

    public Result() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public int getRacenum() {
        return racenum;
    }

    public void setRacenum(int racenum) {
        this.racenum = racenum;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getRacetime() {
        return racetime;
    }

    public void setRacetime(String racetime) {
        this.racetime = racetime;
    }

    public String getLicencenum() {
        return licencenum;
    }

    public void setLicencenum(String licencenum) {
        this.licencenum = licencenum;
    }

    @JsonIgnore
    public Date getRt() {
        return rt;
    }

    public void setRt(Date rt) {
        this.rt = rt;
    }

    public List<String> getResultmodNames() {
        return resultmodNames;
    }

    public void setResultmodNames(List<String> resultmodNames) {
        this.resultmodNames = resultmodNames;
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

