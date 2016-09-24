/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pekmil
 */
public class EntryOption {
    
    private String id;
    private String displayName;
    private String dbField;
    private boolean freeValue;
    
    private List<ValueOption> values;   
    private int selectedValue;
    private String value;
    
    private List<Integer> selectedRacenums;
    
    private int selectedRaceId;

    public EntryOption() {
   
    }

    public EntryOption(String id, String displayName, String dbField, boolean freeValue) {
        this.id = id;
        this.displayName = displayName;
        this.dbField = dbField;
        this.freeValue = freeValue;
        this.values = new ArrayList<>();
        this.selectedRacenums = new ArrayList<>();
    }
    
    public void addValue(String displayName, Object dbValue){
        values.add(new ValueOption(displayName, dbValue));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonIgnore
    @XmlTransient
    public String getDbField() {
        return dbField;
    }

    public List<ValueOption> getValues() {
        return values;
    }

    public void setValues(List<ValueOption> values) {
        this.values = values;
    }

    public int getSelectedValue() {
        return selectedValue;
    }

    public void setSelectedValue(int selectedValue) {
        this.selectedValue = selectedValue;
    }

    public List<Integer> getSelectedRacenums() {
        return selectedRacenums;
    }

    public void setSelectedRacenums(List<Integer> selectedRacenums) {
        this.selectedRacenums = selectedRacenums;
    }

    public int getSelectedRaceId() {
        return selectedRaceId;
    }

    public void setSelectedRaceId(int selectedRaceId) {
        this.selectedRaceId = selectedRaceId;
    }

    public boolean isFreeValue() {
        return freeValue;
    }

    public void setFreeValue(boolean freeValue) {
        this.freeValue = freeValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
