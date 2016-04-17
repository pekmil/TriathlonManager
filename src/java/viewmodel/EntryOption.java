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
    
    private List<ValueOption> values;
    
    private int selectedValue;
    private List<Integer> selectedRacenums;
    private int selectedRaceId;

    public EntryOption() {
   
    }

    public EntryOption(String id, String displayName, String dbField) {
        this.id = id;
        this.displayName = displayName;
        this.dbField = dbField;
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
    
}
