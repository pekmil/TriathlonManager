/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package document;

import java.io.File;

/**
 *
 * @author pekmil
 */
public abstract class Document {
    
    protected String fileName;
    protected final String serverSaveFolder;
    
    public Document(String serverSaveFolder){
        this.serverSaveFolder = serverSaveFolder;
    }
    
    public abstract boolean generate();
    
    public Document withFileName(String fileName){
        this.fileName = fileName;
        return this;
    }
    
    public File getDocumentFile(){
        return new File(serverSaveFolder + fileName);
    }

    public String getFileName() {
        return fileName;
    }        
    
}
