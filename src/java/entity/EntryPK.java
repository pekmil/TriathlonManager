/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author pekmil
 */
@Embeddable
public class EntryPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id")
    private int raceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contestant_id")
    private int contestantId;

    public EntryPK() {
    }

    public EntryPK(int raceId, int contestantId) {
        this.raceId = raceId;
        this.contestantId = contestantId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getContestantId() {
        return contestantId;
    }

    public void setContestantId(int contestantId) {
        this.contestantId = contestantId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) raceId;
        hash += (int) contestantId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntryPK)) {
            return false;
        }
        EntryPK other = (EntryPK) object;
        if (this.raceId != other.raceId) {
            return false;
        }
        if (this.contestantId != other.contestantId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.EntryPK[ raceId=" + raceId + ", contestantId=" + contestantId + " ]";
    }
    
}
