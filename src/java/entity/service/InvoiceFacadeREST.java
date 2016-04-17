/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Entry;
import entity.Invoice;
import java.util.List;
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

/**
 *
 * @author pekmil
 */
@Stateless
@Path("invoice")
public class InvoiceFacadeREST extends AbstractFacade<Invoice> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    ObjectMapper mapper; 

    public InvoiceFacadeREST() {
        super(Invoice.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Invoice entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") Integer id, Invoice entity) {
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
    public Invoice find(@PathParam("id") Integer id) {
        return super.find(id);
    }

//    @GET
//    @Override
//    @Produces({"application/json"})
//    public List<Invoice> findAll() {
//        return super.findAll();
//    }
    
    @GET
    @Path("rid/{id}")
    @Produces({"application/json"})
    public Response findAllWithEntries(@PathParam("id") Integer id) throws JsonProcessingException {
        em.flush();
        List<Invoice> invoices = (List)em.createQuery("SELECT DISTINCT i FROM Invoice i LEFT JOIN i.entries as e WHERE e.key.raceId = :raceid")
        .setParameter("raceid", id)
        .getResultList();
        invoices.stream().forEach((invoice) -> {
            //if(invoice.getEntries().isEmpty()){
                List<Entry> entries = (List)em.createQuery("SELECT e FROM Entry e WHERE e.invoice.id = :invoiceid")
                        .setParameter("invoiceid", invoice.getId())
                        .getResultList();
                invoice.setEntries(entries);
            //}
        });
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(invoices);
        return Response.ok(json).build();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Invoice> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
