/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import ejb.StaticParameters;
import entity.Race;
import entity.Raceadjustment;
import entity.RaceadjustmentPK;
import java.util.List;
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

/**
 *
 * @author pekmil
 */
@Stateless
@Path("race")
public class RaceFacadeREST extends AbstractFacade<Race> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    RaceadjustmentFacadeREST raceadjustmentFacade;
    
    @EJB
    StaticParameters parameters;

    public RaceFacadeREST() {
        super(Race.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Race entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Race entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    @DELETE
    @Path("{raceid}/{categoryid}/{resultmodid}")
    public void removeAdjustment(@PathParam("raceid") Integer raceId, 
                                 @PathParam("categoryid") Integer categoryId,
                                 @PathParam("resultmodid") Integer resultmodId) {
        Race r = super.find(raceId);
        Raceadjustment ra = raceadjustmentFacade.find(
                        new RaceadjustmentPK(raceId, resultmodId, categoryId));
        r.getRaceadjustments().remove(ra);
        raceadjustmentFacade.remove(ra);
        parameters.reloadRaceadjustments();
    }
    
    @POST
    @Path("adjustment")
    @Consumes({"application/json"})
    public void createAdjustment(Raceadjustment entity) {
        Race r = super.find(entity.getKey().getRaceId());
        raceadjustmentFacade.create(entity);
        em.flush();
        Raceadjustment ra = raceadjustmentFacade.find(
                        new RaceadjustmentPK(entity.getKey().getRaceId(),
                                             entity.getKey().getResultmodId(),
                                             entity.getKey().getCategoryId()));
        r.getRaceadjustments().add(ra);
        parameters.reloadRaceadjustments();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Race find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    
    @GET
    @Path("tid/{id}")
    @Produces({"application/json"})
    public List<Race> findAll(@PathParam("id") Integer id) {
        List<Race> result = (List)em.createQuery("SELECT r FROM Race r WHERE r.tournament.id = :tournamentid")
        .setParameter("tournamentid", id)
        .getResultList();
        return result;
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Race> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Race> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    public void truncateRaceData(int raceid){
        List<Integer> contestantIds = em.createQuery("SELECT e.contestant.id FROM Entry e WHERE e.key.raceId = :raceid")
                                        .setParameter("raceid", raceid).getResultList();
        em.createQuery("DELETE FROM Entry e WHERE e.key.raceId = :raceid")
          .setParameter("raceid", raceid)
          .executeUpdate();
        em.flush();
        em.createQuery("DELETE FROM Contestant c WHERE c.id IN :contestantids")
          .setParameter("contestantids", contestantIds)
          .executeUpdate();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
