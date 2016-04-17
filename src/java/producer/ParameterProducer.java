/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package producer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class ParameterProducer {
    
    @Produces
    public Properties getAppProperties(){
        Properties appProperties = new Properties();
        try{            
            ClassLoader classLoader = this.getClass().getClassLoader();
            InputStream input = classLoader.getResourceAsStream("/app.properties");
            appProperties.load(input);
        }
        catch(IOException ioex){
            ioex.printStackTrace();
        }
        return appProperties;
    }
    
}
