/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ejb.StaticParameters;
import entity.Contestant;
import entity.Entry;
import entity.EntryPK;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import producer.ResultEvaluatorFactory;
import resultevaluator.ResultEvaluator;
import resultevaluator.ResultParams;
import util.FileUtils;
import util.JsonBuilder;
import util.Utils;
import viewmodel.EntryData;
import viewmodel.ResultData;
import ws.NotificationEndpoint;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("entry")
public class EntryFacadeREST extends AbstractFacade<Entry> {
    @PersistenceContext(unitName = "TriathlonManagerPU")
    private EntityManager em;
    
    @Inject
    ObjectMapper mapper;
    @Inject
    ResultEvaluatorFactory resultEvaluatorFactory;
    @Inject
    FileUtils fileUtils;
    @Inject
    Properties appParameters;
    
    @EJB
    StaticParameters parameters;
    @EJB
    ClubFacadeREST clubFacade;
    @EJB
    ContestantFacadeREST contestantFacade;
    @EJB
    LicenceFacadeREST licenceFacade;
    
    private final ResultParams resultParams = new ResultParams();
    
    private static final String[] CSV_HEADERS = new String[]{"name", "racenum", 
                                                             "gender", "birthyear", 
                                                             "category", "fromtown", 
                                                             "club", "licencenum", 
                                                             "agegroup", "paid"};
    
    private EntryPK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;raceId=raceIdValue;racenum=racenumValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        entity.EntryPK key = new entity.EntryPK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> raceId = map.get("raceId");
        if (raceId != null && !raceId.isEmpty()) {
            key.setRaceId(new java.lang.Integer(raceId.get(0)));
        }
        java.util.List<String> racenum = map.get("racenum");
        if (racenum != null && !racenum.isEmpty()) {
            key.setRacenum(new java.lang.Short(racenum.get(0)));
        }
        return key;
    }

    public EntryFacadeREST() {
        super(Entry.class);
    }

    @POST
    @Override
    @Consumes({"application/json"})
    public void create(Entry entity) {
        super.create(entity);       
    }
    
    @POST
    @Path("{raceid}")
    @Consumes({"application/json"})
    public void create(@PathParam("raceid") Integer raceid, EntryData data) {
        insertEntry(data, raceid);
    }
    
    @POST
    @Path("result/{raceid}")
    @Produces({"application/json"})
    public Response setRacetime(@PathParam("raceid") Integer raceid, ResultData resultData) {
        EntryPK key = new EntryPK(raceid, resultData.getRacenum());
        Entry entry = em.find(Entry.class, key, LockModeType.PESSIMISTIC_WRITE);
        if(entry.getStatus().equals("CHECKED") && entry.getRacetime() == null){
            entry.setFinishtime(new Date());
            entry.setRacetime(resultData.getRacetime());
            entry.setStatus("FINISHED");
            em.merge(entry);
        }
        else{
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Az eredmény már rögzítésre került vagy nem a megfelelő állapotban van!", JsonBuilder.MsgType.WARNING, null);
            return Response.status(500).entity(jsonMsg).build();
        }
        String msg = "Eredmény rögzítve: " + entry.getContestant().getName() +
                     " (" + entry.getKey().getRacenum() + ") - " +
                     Utils.simpleTimeFormat.format(entry.getRacetime());
        JsonObject jsonMsg = JsonBuilder.getJsonMsg(msg, JsonBuilder.MsgType.INFO, null);
        NotificationEndpoint.send(msg);
        return Response.ok(jsonMsg).build();
    }
        
    public Response modifyRacetime(Integer raceid, ResultData resultData) {
        EntryPK key = new EntryPK(raceid, resultData.getRacenum());
        Entry entry = em.find(Entry.class, key, LockModeType.PESSIMISTIC_WRITE);
        if(entry.getStatus().equals("FINISHED") && entry.getRacetime() != null){
            entry.appendRacetimeMod(resultData.getRacetimemoddesc());
            entry.setRacetime(resultData.getRacetime());
            em.merge(entry);
        }
        else{
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Az eredmény nem a megfelelő állapotban van!", JsonBuilder.MsgType.WARNING, null);
            return Response.status(500).entity(jsonMsg).build();
        }
        String msg = "Eredmény módosítva: " + entry.getContestant().getName() +
                     " (" + entry.getKey().getRacenum() + ") - " +
                     Utils.simpleTimeFormat.format(entry.getRacetime());
        JsonObject jsonMsg = JsonBuilder.getJsonMsg(msg, JsonBuilder.MsgType.INFO, null);
        return Response.ok(jsonMsg).build();
    }
    
    @GET
    @Path("finishedresults/{raceid}")
    @Produces({"application/json"})
    public List<Entry> getFinishedResults(@PathParam("raceid") Integer raceid){
        List<Entry> entries = (List)em.createQuery("SELECT e FROM Entry e WHERE e.key.raceId = :raceid AND e.status = 'FINISHED' AND e.racetime IS NOT NULL")
        .setParameter("raceid", raceid)
        .getResultList();
        return entries;
    }
    
    @GET
    @Path("raceresults/{raceid}/{categoryid}")
    @Produces({"application/json"})
    public Response getRaceResultsForCategory(@PathParam("raceid") Integer raceid, 
                                              @PathParam("categoryid") Integer categoryid,
                                              @QueryParam("national") Integer national,
                                              @QueryParam("absolute") Integer absolute,
                                              @QueryParam("team") Integer team,
                                              @QueryParam("family") Integer family) throws JsonProcessingException {
        String queryString = "SELECT e FROM Entry e " +
                       "WHERE e.key.raceId = :raceid " + 
                           "AND e.status = 'FINISHED' " +
                           "AND e.racetime IS NOT NULL";
        
        if(family != 1){
            queryString = queryString + " AND e.category.id = :categoryid";
        }
        if(national == 1){
            queryString = queryString + " AND e.licencenum IS NOT NULL AND e.licencenum != ''";
        }
        if(team == 1){
            queryString = queryString + " AND e.contestant.club IS NOT NULL";
        }
        Query query = em.createQuery(queryString).setParameter("raceid", raceid);
        if(family != 1) query.setParameter("categoryid", categoryid);
        List<Entry> entries = (List)query.getResultList();
        resultParams.setAbsolute(absolute == 1);
        resultParams.setFamily(family == 1);
        resultParams.setTeam(team == 1);
        ResultEvaluator results = resultEvaluatorFactory.getResultEvaluator(resultParams.getResultType());
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(results.getResults(entries, parameters.getAgegroups()));
        return Response.ok(json).build();
    }
    
    @POST
    @Path("/uploadcsv")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/json; charset=UTF-8")
    public Response uploadCSV(@FormDataParam("uploadFile") InputStream fileInputStream,
                              @FormDataParam("uploadFile") FormDataContentDisposition fileFormDataContentDisposition) throws IOException {
        return fileUtils.uploadCSV(fileInputStream, "preentries_");
    }
 
    @POST
    @Path("/processcsv/{raceid}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces("application/json; charset=UTF-8")
    public Response processCSV(@FormParam("filename") String fileName, @PathParam("raceid") Integer raceid){
        parameters.initClubs();
        List<String> invalidLicences = new ArrayList<>();
        invalidLicences.add("nev;rajtszam;licensz");
        try(Reader in = new InputStreamReader(new FileInputStream(appParameters.getProperty("uploadFolder") + fileName), "UTF-8")) {
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withSkipHeaderRecord()
                                                         .withDelimiter(';')
                                                         .withHeader(CSV_HEADERS)
                                                         .withNullString("")
                                                         .parse(in);
            int count = 0;
            for (CSVRecord record : records) {
                EntryData ed = new EntryData();
                ed.setPreentry(true);
                ed.setStatus("PRE");
                ed.setName(record.get("name"));
                ed.setRacenum(Short.valueOf(record.get("racenum")));
                ed.setGender(record.get("gender"));
                ed.setBirthYear(Short.valueOf(record.get("birthyear")));
                ed.setCategory(record.get("category"));
                ed.setFromTown(record.get("fromtown"));
                ed.setClubName(record.get("club"));
                ed.setAgegroup(record.get("agegroup"));
                ed.setPaid(record.get("paid").equals("IGEN"));
                ed.setRemainingpayment(record.get("paid").matches("[1-9]\\d{0,10}") ? Integer.parseInt(record.get("paid")) : 0);
                ed.setLicencenum(record.get("licencenum"));
                if(ed.getLicencenum() != null && !ed.getLicencenum().isEmpty()){
                    if(!licenceFacade.exists(ed.getLicencenum())){
                        invalidLicences.add(ed.getName() + ";" + ed.getRacenum() + ";" + ed.getLicencenum());
                    }
                }
                insertEntry(ed, raceid);
                ++count;
            }
            Files.write(Paths.get(appParameters.getProperty("uploadFolder"), "invalid_licences_" + fileName), invalidLicences, Charset.forName("UTF-8"));
            return Response.ok(String.valueOf(count)).build();
        } catch (FileNotFoundException ex) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("fileName", fileName);
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("A megadott fájl nem található!", JsonBuilder.MsgType.ERROR, params);
            return Response.status(500).entity(jsonMsg).build();
        } catch (IOException ex) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("fileName", fileName);
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Hiba a CSV fájl beolvasása közben!", JsonBuilder.MsgType.ERROR, params);
            return Response.status(500).entity(jsonMsg).build();
        }
    }
    
    private void insertEntry(EntryData ed, Integer raceId){
        Contestant c = new Contestant();
        c.setBirthyear(ed.getBirthYear());
        c.setFromtown(ed.getFromTown());
        c.setGender(ed.getGender());
        c.setName(ed.getName());
        String clubName = ed.getClubName();
        if(clubName != null){
            if(parameters.getClub(clubName) == null){
                clubFacade.insert(clubName);
            }
            c.setClub(parameters.getClub(clubName));
        }
        contestantFacade.create(c);
        em.flush();
        Entry e = new Entry(raceId, ed.getRacenum());
        e.setContestant(c);
        e.setAgegroup(parameters.getAgegroup(ed.getAgegroup()));
        e.setCategory(parameters.getCategory(ed.getCategory()));
        e.setEntrytime(new Date());
        e.setPreentry(ed.isPreentry());
        e.setStatus(ed.getStatus());
        e.setPaid(ed.isPaid());
        e.setRemainingpayment(ed.getRemainingpayment());
        e.setLicencenum(ed.getLicencenum());
        create(e);
    }
    
    public int modifyEntries(int raceId, List<Integer> racenums, String dbField, Object dbValue){
        StringBuilder query = new StringBuilder("UPDATE Entry e SET ");
        query.append("e.").append(dbField).append(" = :value ")
             .append("WHERE e.key.raceId = :raceid AND e.key.racenum IN :racenums");
        return em.createQuery(query.toString()).setParameter("value", dbValue)
                                        .setParameter("raceid", raceId)
                                        .setParameter("racenums", racenums)
                 .executeUpdate();
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/json"})
    public void edit(@PathParam("id") PathSegment id, Entry entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("raceid/{raceid}/racenum/{racenum}")
    public void remove(@PathParam("raceid") int raceid, @PathParam("racenum") short racenum) {
        entity.EntryPK key = new EntryPK(raceid, racenum);
        super.remove(super.find(key));
    }

    @GET
    @Path("raceid/{raceid}/racenum/{racenum}")
    @Produces({"application/json"})
    public Entry find(@PathParam("raceid") int raceid, @PathParam("racenum") short racenum) {
        entity.EntryPK key = new EntryPK(raceid, racenum);
        return super.find(key);
    }

    @GET
    @Path("rid/{id}")
    @Produces({"application/json"})
    public List<Entry> findAll(@PathParam("id") Integer id) {
        List<Entry> result = (List)em.createQuery("SELECT e FROM Entry e WHERE e.key.raceId = :raceid")
        .setParameter("raceid", id)
        .getResultList();        
        return result;
    }
    
    @GET
    @Override
    @Produces({"application/json"})
    public List<Entry> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/json"})
    public List<Entry> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
