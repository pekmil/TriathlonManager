/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import filter.CORSResponseFilter;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;


/**
 *
 * @author pekmil
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        resources.add(CORSResponseFilter.class);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(entity.service.AdminEndpoint.class);
        resources.add(entity.service.AgegroupFacadeREST.class);
        resources.add(entity.service.AuthEndpoint.class);
        resources.add(entity.service.CategoryFacadeREST.class);
        resources.add(entity.service.ClubFacadeREST.class);
        resources.add(entity.service.ContestantFacadeREST.class);
        resources.add(entity.service.EntryFacadeREST.class);
        resources.add(entity.service.FamilyentryFacadeREST.class);
        resources.add(entity.service.InvoiceFacadeREST.class);
        resources.add(entity.service.LicenceFacadeREST.class);
        resources.add(entity.service.RaceFacadeREST.class);
        resources.add(entity.service.ResultmodFacadeREST.class);
        resources.add(entity.service.TournamentFacadeREST.class);
    }
    
}
