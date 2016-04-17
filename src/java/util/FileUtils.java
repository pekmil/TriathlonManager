/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;

/**
 *
 * @author pekmil
 */
@Named
@ApplicationScoped
public class FileUtils {
    
    @Inject
    Properties parameters;
    
    public Response uploadCSV(InputStream fileStream, String fileNamePrefix) throws IOException{
        String fileName;
        String uploadFileName;
        try {
            fileName = fileNamePrefix + Utils.simpleDateFormat.format(new Date()) + "_" + System.currentTimeMillis() + ".csv";
            uploadFileName = writeToFileServer(fileStream, fileName);
        }
        catch(IOException ioe){
            HashMap<String, Object> params = new HashMap<>();
            params.put("errorMsg", ioe.getMessage());
            JsonObject jsonMsg = JsonBuilder.getJsonMsg("A fájl feltöltése közben hiba történt!", JsonBuilder.MsgType.ERROR, params);
            return Response.status(500).entity(jsonMsg).build();
        }
        finally{
            fileStream.close();
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("uploadFileName", fileName);
        JsonObject jsonMsg = JsonBuilder.getJsonMsg("A fájl feltöltése sikerült!", JsonBuilder.MsgType.SUCCESS, params);
        return Response.ok(jsonMsg).build();
    }
    
    public String writeToFileServer(InputStream inputStream, String fileName) throws IOException {
        String qualifiedUploadFilePath = parameters.getProperty("uploadFolder") + fileName;
        try(OutputStream outputStream = new FileOutputStream(new File(qualifiedUploadFilePath))){
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        }
        return qualifiedUploadFilePath;
    }
    
}
