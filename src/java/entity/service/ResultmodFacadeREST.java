/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Resultmod;
import java.util.List;
import javax.annotation.security.PermitAll;
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
@Path("resultmod")
public class ResultmodFacadeREST extends AbstractFacade<Resultmod> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    Event<Resultmod> resultmodEvent;

    public ResultmodFacadeREST() {
        super(Resultmod.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    @Path("admin")
    public void create(Resultmod entity) {
        super.create(entity);
        em.flush();
        resultmodEvent.fire(entity);
    }

    @PUT
    @Path("admin/{id}")
    @Consumes({"application/json"})    
    public void edit(@PathParam("id") Integer id, Resultmod entity) {
        super.edit(entity);
        em.flush();
        resultmodEvent.fire(entity);
    }

    @DELETE
    @Path("admin/{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("admin/{id}")
    @Produces({"application/json"})
    public Resultmod find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    @PermitAll
    public List<Resultmod> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Resultmod> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
