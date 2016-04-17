/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Licence;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import util.FileUtils;
import viewmodel.PageData;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("licence")
public class LicenceFacadeREST extends AbstractFacade<Licence> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    ObjectMapper mapper;
    @Inject
    Properties parameters;
    @Inject
    FileUtils fileUtils;

    public LicenceFacadeREST() {
        super(Licence.class);
    }
    
    @GET
    @Path("findbyname/{name}")
    @Produces({"application/json"})
    public List<Licence> findByName(@PathParam("name") String namePart) {
        return em.createQuery("SELECT l FROM Licence l WHERE l.name LIKE :name").
                  setParameter("name", namePart + '%').
                  getResultList();
    }
    
    @GET
    @Path("findbylicence/{licence}")
    @Produces({"application/json"})
    public List<Licence> findByLicence(@PathParam("licence") String licencePart) {
        return em.createQuery("SELECT l FROM Licence l WHERE l.licencenum LIKE :licence").
                  setParameter("licence", licencePart + '%').
                  getResultList();
    }
    
    @GET
    @Path("licenceexists/{licence}")
    public Response licenceExists(@PathParam("licence") String licence) {
        try{
            em.createQuery("SELECT 1 FROM Licence l WHERE l.licencenum = :licence").
                setParameter("licence", licence).getSingleResult();
        }
        catch(NoResultException ex){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
    
    @POST
    @Path("/uploadcsv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json; charset=UTF-8")
    public Response uploadCSV(@FormDataParam("uploadFile") InputStream fileInputStream,
                              @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) throws IOException {
        return fileUtils.uploadCSV(fileInputStream, "licences_");
    }
    
    public int processCSV(String fileName){
        String fullFilePath = parameters.getProperty("uploadFolder") + fileName;
        int ins = em.createNativeQuery("LOAD DATA INFILE '" + fullFilePath + "'" +
                            "INTO TABLE licence " +
                            "FIELDS TERMINATED BY ';' " +
                            "ENCLOSED BY '' " +
                            "LINES TERMINATED BY '\\r\\n' " +
                            "IGNORE 1 ROWS").executeUpdate();
        return ins;
    }
    
    public int deleteLicenceData(){
        return em.createQuery("DELETE FROM Licence l").executeUpdate();
    }
    
    @GET
    @Path("{id}")
    @Produces({"application/json"})
    public Licence find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/json"})
    public List<Licence> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) throws JsonProcessingException {
        PageData<Licence> page = new PageData();
        page.setCount(super.count());
        page.setData(super.findRange(new int[]{from, to}));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(page);
        return Response.ok(json).build();
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
