/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class ObjectMapperProducer {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    @Produces
    public static ObjectMapper getObjectMapper(){
        return mapper;
    }
    
}
