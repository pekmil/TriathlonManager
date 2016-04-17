/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Agegroup;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("agegroup")
public class AgegroupFacadeREST extends AbstractFacade<Agegroup> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    Event<Agegroup> createAgegroupEvent;

    public AgegroupFacadeREST() {
        super(Agegroup.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Agegroup entity) {
        super.create(entity);
        em.flush();
        createAgegroupEvent.fire(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Agegroup entity) {
        super.edit(entity);
    }
    
    @PUT
    @Path("incrementyears")
    public void incrementYears() {
        em.createQuery("UPDATE Agegroup a SET a.startyear = a.startyear + 1 WHERE a.id > 0").executeUpdate();
    }
    
    @PUT
    @Path("decrementyears")
    public void decrementYears() {
        em.createQuery("UPDATE Agegroup a SET a.startyear = a.startyear - 1 WHERE a.id > 0").executeUpdate();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Agegroup find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Agegroup> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Agegroup> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
