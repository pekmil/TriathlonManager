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
public class EntryData {
    
    private short birthYear;
    private String fromTown;
    private String gender;
    private String name;
    private String clubName;
    
    private String agegroup;
    private String category;
    private String racenum;
    private boolean preentry;
    private String status;
    private boolean paid;
    private int remainingpayment;
    private String licencenum;

    public EntryData() {
        this.paid = false;
        this.remainingpayment = 0;
        this.licencenum = "";
    }

    public short getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(short birthYear) {
        this.birthYear = birthYear;
    }   

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(String agegroup) {
        this.agegroup = agegroup;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRacenum() {
        return racenum;
    }

    public void setRacenum(String racenum) {
        this.racenum = racenum;
    }

    public boolean isPreentry() {
        return preentry;
    }

    public void setPreentry(boolean preentry) {
        this.preentry = preentry;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getRemainingpayment() {
        return remainingpayment;
    }

    public void setRemainingpayment(int remainingpayment) {
        this.remainingpayment = remainingpayment;
    }

    public String getLicencenum() {
        return licencenum;
    }

    public void setLicencenum(String licencenum) {
        this.licencenum = licencenum;
    }
    
}
