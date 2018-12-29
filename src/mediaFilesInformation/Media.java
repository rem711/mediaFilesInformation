/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.io.File;
import com.abercap.mediainfo.api.*;

/**
 *
 * @author rem711
 */
public class Media {
    private File file;
    private String title;
    private String duration;
    private String size;

    public Media(File file) {
        MediaFileFilter filter = new MediaFileFilter();
        
        if(!file.isFile()) {
            throw(new java.lang.Error("The media has to be a file : " + file.getPath()));
        }
        if(!filter.accept(file)) {
            throw(new java.lang.Error("The file has to be a referenced media in MediaFileFilter : " + file.getPath()));
        }
        
        this.file = file;
        
        MediaInfo info    = new MediaInfo();
        info.open(file);
        
        this.title = info.get(MediaInfo.StreamKind.General, 0, "Title");
        if(this.title.isEmpty()) {
            this.title = info.get(MediaInfo.StreamKind.General, 0, "FileName");
        }
        
        this.duration = info.get(MediaInfo.StreamKind.General, 0, "Duration/String");
        
        this.size = info.get(MediaInfo.StreamKind.General, 0, "FileSize/String");  
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
    
    public String getDirectory() {
        return this.file.getParentFile().getName();
    }
    
    public String toString() {
        String s = "";
        
        s += "From " + this.getDirectory() + ", " + this.getTitle() + "\n";
        s += "Duration : " + this.getDuration() + "\n";
        s += "Size : " + this.getSize() + "\n";
        
        return s;
    }
    
    
}
