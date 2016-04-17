/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filter;

import java.io.IOException;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class CORSResponseFilter implements ContainerResponseFilter {

    @Inject
    Properties parameters;
    
    @Override
    public void filter(ContainerRequestContext requestContext, 
                       ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.add("Access-Control-Allow-Origin", parameters.getProperty("clientServerURL"));
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");			
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type");
    }

}