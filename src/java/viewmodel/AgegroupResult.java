/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewmodel;

import java.util.List;

/**
 *
 * @author pekmil
 */
public class AgegroupResult {

    private List<Result> maleResults;
    private List<Result> femaleResults;

    public AgegroupResult(List<Result> maleResults, List<Result> femaleResults) {
        this.maleResults = maleResults;
        this.femaleResults = femaleResults;
    }

    public List<Result> getMaleResults() {
        return maleResults;
    }

    public List<Result> getFemaleResults() {
        return femaleResults;
    }

    public void setMaleResults(List<Result> maleResults) {
        this.maleResults = maleResults;
    }

    public void setFemaleResults(List<Result> femaleResults) {
        this.femaleResults = femaleResults;
    }

}
