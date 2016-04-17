/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.service;

import java.security.Principal;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import util.JsonBuilder;
import viewmodel.UserData;

/**
 *
 * @author pekmil
 */
@Stateless
@Path("auth")
public class AuthEndpoint {
    
    @POST
    @Path("login")
    @Produces({"application/json"})
    public Response login(UserData user, @Context HttpServletRequest req){
        Principal principal = req.getUserPrincipal();
        if(principal == null){
            try {
                req.login(user.getUsername(), user.getPassword());
                HashMap<String, Object> params = new HashMap<>();
                params.put("username", user.getUsername());
                JsonObject jsonMsg = JsonBuilder.getJsonMsg("Sikeresen bejelentkezett!", JsonBuilder.MsgType.SUCCESS, params);
                return Response.ok().entity(jsonMsg).build();
            } catch (ServletException e) {
                HashMap<String, Object> params = new HashMap<>();
                params.put("errorMsg", e.getMessage());
                JsonObject jsonMsg = JsonBuilder.getJsonMsg("Sikertelen bejelentkezés!", JsonBuilder.MsgType.ERROR, params);
                return Response.status(403).entity(jsonMsg).build();
            }
            finally{
                user.setPassword(null);
            }
        }else{
            user.setPassword(null);
            HashMap<String, Object> params = new HashMap<>();
            params.put("username", principal.getName());
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("A felhasználó már be van jelentkezve!", JsonBuilder.MsgType.INFO, params);
            return Response.ok().entity(jsonMsg).build();
        }
    }
    
    @GET
    @Path("logout")
    @Produces({"application/json"})
    public Response logout(@Context HttpServletRequest req){
        try {
            req.logout();
            req.getSession().invalidate();
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Sikeres kijelentkezés!", JsonBuilder.MsgType.SUCCESS, null);
            return Response.ok().entity(jsonMsg).build();
        } catch (ServletException e) {
            HashMap<String, Object> params = new HashMap<>();
            params.put("errorMsg", e.getMessage());
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Sikertelen kijelentkezés!", JsonBuilder.MsgType.ERROR, params);
            return Response.status(500).entity(jsonMsg).build();
        }
    }
    
    @GET
    @Path("status")
    @Produces({"application/json"})
    public Response status(@Context HttpServletRequest req){
        Principal principal = req.getUserPrincipal();
        if(principal == null){
            HashMap<String, Object> params = new HashMap<>();
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("Nincs bejelentkezve!", JsonBuilder.MsgType.WARNING, params);
            return Response.status(403).entity(jsonMsg).build();
        }else{
            HashMap<String, Object> params = new HashMap<>();
            params.put("username", principal.getName());
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("A felhasználó már be van jelentkezve!", JsonBuilder.MsgType.INFO, params);
            return Response.ok().entity(jsonMsg).build();
        }
    }
    
    @GET
    @Path("forbidden")
    @Produces({"application/json"})
    public Response forbidden(){
        HashMap<String, Object> params = new HashMap<>();
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("Hozzáférés megtagadva!", JsonBuilder.MsgType.WARNING, params);
        return Response.status(403).entity(jsonMsg).build();
    }
    
}
