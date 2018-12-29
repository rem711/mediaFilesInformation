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
public class Movie extends Media {
    private String[] audio;
    private String[] subtitles;

    public Movie(File file) {
        super(file);
        
        MovieFileFilter filter = new MovieFileFilter();
        
        if(!file.isFile()) {
            throw(new java.lang.Error("The movie has to be a file : " + file.getPath()));
        }
        if(!filter.accept(file)) {
            throw(new java.lang.Error("The file has to be a referenced movie in MovieFileFilter : " + file.getPath()));
        }
        
        this.audio = new String[0];
        this.subtitles = new String[0];
        
        MediaInfo info    = new MediaInfo();
        info.open(file);
        
        // initialize audio sources and format them
        //if(!info.get(MediaInfo.StreamKind.General, 0, "Audio_Language_List").isEmpty()) {
            this.audio = info.get(MediaInfo.StreamKind.General, 0, "Audio_Language_List").split(" / ");
            String[] audio_formats = info.get(MediaInfo.StreamKind.General, 0, "Audio_Format_List").split(" / ");        
            for(short audio_track = 0; audio_track < this.audio.length; audio_track++) {
                if(this.audio[audio_track].isEmpty()) {
                    this.audio[audio_track] = "Unknown";
                }
                String audio_format = audio_formats[audio_track];
                if(!audio_format.isEmpty()) {
                    this.audio[audio_track] += " (" +  audio_format + ")";
                }                
            }      
        //}          
        
        // initialize subtitles sources and format them
        if(!info.get(MediaInfo.StreamKind.General, 0, "Text_Language_List").isEmpty()) {
            this.subtitles = info.get(MediaInfo.StreamKind.General, 0, "Text_Language_List").split(" / ");            
            for(short subtitles_track = 0; subtitles_track < this.subtitles.length; subtitles_track++) {
                String subName = info.get(MediaInfo.StreamKind.Text, subtitles_track, "Title");
                if(!subName.isEmpty()) {
                    this.subtitles[subtitles_track] += " (" + subName + ")";
                }
            }
        }
        
    }

    public String[] getAudio() {
        return audio;
    }

    public String[] getSubtitles() {
        return subtitles;
    }  
    
    public String toString() {
        String s = "";
        
        s += super.toString();
        
        if(this.audio.length > 1) {
            s += "Audio : \n";
            for(String audio_track : this.audio) {
                s += "  - " + audio_track + "\n";
            }
        }
        else {
            s += "Audio : " + this.audio[0] + "\n";
        }
        
        if(this.subtitles.length > 0) {
            s += "Subtitles : \n";
            for(String subtitles_track : this.subtitles) {
                s += "  - " + subtitles_track + "\n";
            }
        }
        else {
            s += "No subtitles \n";
        }
        
        return s;
    }
}
