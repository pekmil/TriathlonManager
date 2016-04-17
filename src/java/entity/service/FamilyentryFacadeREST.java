/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Entry;
import entity.Familyentry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
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
import javax.ws.rs.core.Response;
import resultevaluator.FamilyResultEvaluator;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("familyentry")
public class FamilyentryFacadeREST extends AbstractFacade<Familyentry> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    ObjectMapper mapper;
    
    @Inject
    FamilyResultEvaluator familyResultEvaluator;
    
    @EJB
    EntryFacadeREST entryFacade;

    public FamilyentryFacadeREST() {
        super(Familyentry.class);
    }
    
    @GET
    @Path("rid/{id}")
    @Produces({"application/json"})
    public Response findAllWithEntries(@PathParam("id") Integer id) throws JsonProcessingException {
        em.flush();
        List<Entry> familyentries = (List)em.createQuery("SELECT e FROM Entry e WHERE e.key.raceId = :raceid AND e.familyentry IS NOT NULL")
        .setParameter("raceid", id)
        .getResultList();
        Map<String, List<Entry>> entriesByFamilyentry = familyentries.stream().collect(Collectors.groupingBy(e -> e.getFamilyentry().getName()));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entriesByFamilyentry);
        return Response.ok(json).build();
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Familyentry entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Familyentry entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Familyentry find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Familyentry> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Familyentry> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
