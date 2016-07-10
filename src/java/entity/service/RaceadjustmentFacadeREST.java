/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import entity.Raceadjustment;
import entity.RaceadjustmentPK;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("raceadjustment")
public class RaceadjustmentFacadeREST extends AbstractFacade<Raceadjustment> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;

    private RaceadjustmentPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;raceId=raceIdValue;resultmodId=resultmodIdValue;categoryId=categoryIdValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.RaceadjustmentPK key = new entity.RaceadjustmentPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> raceId = map.get("raceId");
        if (raceId != null && !raceId.isEmpty()) {
            key.setRaceId(new java.lang.Integer(raceId.get(0)));
        }
        java.util.List<String> resultmodId = map.get("resultmodId");
        if (resultmodId != null && !resultmodId.isEmpty()) {
            key.setResultmodId(new java.lang.Integer(resultmodId.get(0)));
        }
        java.util.List<String> categoryId = map.get("categoryId");
        if (categoryId != null && !categoryId.isEmpty()) {
            key.setCategoryId(new java.lang.Integer(categoryId.get(0)));
        }
        return key;
    }

    public RaceadjustmentFacadeREST() {
        super(Raceadjustment.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Raceadjustment entity) {
        super.create(entity);
    }

//    @PUT
//    @Path("{id}")
//    @Consumes({"application/json"})
//    public void edit(@PathParam("id") PathSegment id, Raceadjustment entity) {
//        super.edit(entity);
//    }

//    @DELETE
//    @Path("{raceid}/{categoryid}/{resultmodid}")
//    public void remove(@PathParam("raceid") Integer raceId, 
//                       @PathParam("categoryid") Integer categoryId,
//                       @PathParam("resultmodid") Integer resultmodId) {
//        entity.RaceadjustmentPK key = new RaceadjustmentPK(raceId, resultmodId, categoryId);
//        super.remove(super.find(key));
//    }
    
    public Raceadjustment find(RaceadjustmentPK key) {
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Raceadjustment> findAll() {
        return super.findAll();
    }

//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/json"})
//    public List<Raceadjustment> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }

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
