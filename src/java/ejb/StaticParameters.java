/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entity.Agegroup;
import entity.Category;
import entity.Club;
import entity.StaticParameter;
import entity.service.AgegroupFacadeREST;
import entity.service.CategoryFacadeREST;
import entity.service.ClubFacadeREST;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

/**
 *
 * @author pekmil
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class StaticParameters {
    
    private HashMap<String, Category> categories;
    private HashMap<String, Agegroup> agegroups;
    private HashMap<String, Club> clubs;
    
    @EJB
    private CategoryFacadeREST categoryFacade;
    @EJB
    private AgegroupFacadeREST agegroupFacade;
    @EJB
    private ClubFacadeREST clubFacade;
    
    @PostConstruct
    public void init(){
        categories = new HashMap<>();
        populateMap(categories, categoryFacade.findAll());
        agegroups = new HashMap<>();
        populateMap(agegroups, agegroupFacade.findAll());
        clubs = new HashMap<>();
        populateMap(clubs, clubFacade.findAll());        
    }
    
    
    @Lock(LockType.WRITE)
    public void initClubs(){
        clubs.clear();
        populateMap(clubs, clubFacade.findAll());
    }
    
    @Lock(LockType.READ)
    public Category getCategory(String name){
        return categories.get(name);
    }
    
    @Lock(LockType.WRITE)
    public void addCategory(@Observes Category category){
        categories.put(category.getName(), category);
    }
    
    @Lock(LockType.READ)
    public Agegroup getAgegroup(String name){
        return agegroups.get(name);
    }
    
    @Lock(LockType.WRITE)
    public void addAgegroup(@Observes Agegroup agegroup){
        agegroups.put(agegroup.getName(), agegroup);
    }
    
    public Map<String, Agegroup> getAgegroups(){
        return agegroups;
    }

    public HashMap<String, Category> getCategories() {
        return categories;
    }
    
    @Lock(LockType.READ)
    public Club getClub(String name){
        return clubs.get(name);
    }
    
    @Lock(LockType.WRITE)
    public void addClub(@Observes Club club){
        clubs.put(club.getName(), club);
    }        
    
    private <T extends StaticParameter> void populateMap(Map<String, T> map, List<T> elements){
        elements.stream().forEach(e ->{
            map.put(e.getParameterName(), e);
        });
    }
    
}
