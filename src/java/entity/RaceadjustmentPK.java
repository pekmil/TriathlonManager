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
public class RaceadjustmentPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "race_id")
    private int raceId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "resultmod_id")
    private int resultmodId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "category_id")
    private int categoryId;

    public RaceadjustmentPK() {
    }

    public RaceadjustmentPK(int raceId, int resultmodId, int categoryId) {
        this.raceId = raceId;
        this.resultmodId = resultmodId;
        this.categoryId = categoryId;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public int getResultmodId() {
        return resultmodId;
    }

    public void setResultmodId(int resultmodId) {
        this.resultmodId = resultmodId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) raceId;
        hash += (int) resultmodId;
        hash += (int) categoryId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaceadjustmentPK)) {
            return false;
        }
        RaceadjustmentPK other = (RaceadjustmentPK) object;
        if (this.raceId != other.raceId) {
            return false;
        }
        if (this.resultmodId != other.resultmodId) {
            return false;
        }
        if (this.categoryId != other.categoryId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RaceadjustmentPK[ raceId=" + raceId + ", resultmodId=" + resultmodId + ", categoryId=" + categoryId + " ]";
    }
    
}
