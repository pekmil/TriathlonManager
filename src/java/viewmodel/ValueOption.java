/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pekmil
 */
public class ValueOption {
    
    private String displayName;
    private Object dbValue;

    public ValueOption() {
    
    }

    public ValueOption(String displayName, Object dbValue) {
        this.displayName = displayName;
        this.dbValue = dbValue;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    
    @JsonIgnore
    @XmlTransient
    public Object getDbValue() {
        return dbValue;
    }

    public void setDbValue(Object dbValue) {
        this.dbValue = dbValue;
    }
    
}
