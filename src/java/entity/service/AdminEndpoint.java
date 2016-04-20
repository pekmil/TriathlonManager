/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.JsonBuilder;
import viewmodel.EntryOption;
import viewmodel.ResultData;
import viewmodel.ValueOption;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("admin")
public class AdminEndpoint {
    
    private static final Map<String, EntryOption> entryOptions = new HashMap<>();
    
    static {
        EntryOption statusOption = new EntryOption("STATUS", "Nevezés státusza", "status");
        statusOption.addValue("Bejelentkezett", "CHECKED");
        statusOption.addValue("Célba ért", "FINISHED");
        statusOption.addValue("Nem jött el", "NOTPRESENT");
        statusOption.addValue("Kizárt", "DSQ");
        statusOption.addValue("Nem ért célba", "DNF");
        statusOption.addValue("Előnevezett", "PRE");
        EntryOption paidOption = new EntryOption("PAID", "Nevezés fizetve?", "paid");
        paidOption.addValue("Fizetett", true);
        paidOption.addValue("Nem fizetett", false);
        entryOptions.put(statusOption.getId(), statusOption);
        entryOptions.put(paidOption.getId(), paidOption);
    }
    
    @Inject
    ObjectMapper mapper;
    @Inject
    RaceFacadeREST raceFacade;
    @Inject
    EntryFacadeREST entryFacade;
    @Inject
    LicenceFacadeREST licenceFacade;
    
    @DELETE
    @Path("deleteracedata/{raceid}")
    @Produces({"application/json"})
    public Response deleteRaceData(@PathParam("raceid") Integer raceid){
        raceFacade.truncateRaceData(raceid);
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("A versenyadatok törlése sikeresen megtörtént!", JsonBuilder.MsgType.SUCCESS, null);
        return Response.ok().entity(jsonMsg).build();
    }
    
    @GET
    @Path("entryoptions")
    @Produces({"application/json"})
    public Response getEntryOptions() throws JsonProcessingException{
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(entryOptions.values());
        return Response.ok(json).build();
    }
    
    @POST
    @Path("modifyentries")
    @Produces({"application/json"})
    public Response modifyEntries(EntryOption option){
        EntryOption selectedOption = entryOptions.get(option.getId());
        ValueOption selectedValue = entryOptions.get(option.getId()).getValues().get(option.getSelectedValue());
        int mod = entryFacade.modifyEntries(option.getSelectedRaceId(), 
                                            option.getSelectedRacenums(), 
                                            selectedOption.getDbField(), 
                                            selectedValue.getDbValue());
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("A nevezések tömeges módosítása sikeresen megtörtént! (" + mod + " db)", JsonBuilder.MsgType.SUCCESS, null);
        return Response.ok().entity(jsonMsg).build();
    }
    
    @POST
    @Path("loadlicencedata")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({"application/json"})
    public Response processCSV(@FormParam("filename") String fileName){
        int ins = licenceFacade.processCSV(fileName);
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("A licence adatok beolvasása sikeresen megtörtént! (" + ins + " db)", JsonBuilder.MsgType.SUCCESS, null);
        return Response.ok().entity(jsonMsg).build();
    }
    
    @DELETE
    @Path("deletelicencedata")
    @Produces({"application/json"})
    public Response deleteLicenceData(){
        int del = licenceFacade.deleteLicenceData();
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("A licence adatok törlése sikeresen megtörtént! (" + del + " db)", JsonBuilder.MsgType.SUCCESS, null);
        return Response.ok().entity(jsonMsg).build();
    }
    
    @POST
    @Path("modifyresult/{raceid}")
    @Produces({"application/json"})
    public Response modifyRacetime(@PathParam("raceid") Integer raceid, ResultData resultData){
        return entryFacade.modifyRacetime(raceid, resultData);
    }
    
}
