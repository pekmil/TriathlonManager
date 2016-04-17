/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 *
 * @author pekmil
 */
public class JsonBuilder {
    
    public enum MsgType {        
        SUCCESS("success"), 
        ERROR("error"),
        WARNING("warning"),
        INFO("info");
        
        private final String type;

        private MsgType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
        
    }
    
    public static JsonObject getJsonMsg(String msg, MsgType type, HashMap<String, Object> params){
        final JsonObjectBuilder paramObj = Json.createObjectBuilder();
        if(params != null){
            params.entrySet().stream().forEach(e ->{
                paramObj.add(e.getKey(), e.getValue().toString());
            });
        }
        JsonObjectBuilder msgObj = Json.createObjectBuilder().
            add("msg", msg).
            add("type", type.getType());
        if(params != null) msgObj.add("params", paramObj.build());
        return msgObj.build();
    }
    
}
