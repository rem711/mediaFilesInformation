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
public class Song extends Media {
    private String artist;
    private String album;
    private String trackNumber;
    private String genre;

    public Song(File file) {
        super(file);
        
        SongFileFilter filter = new SongFileFilter();
        
        if(!file.isFile()) {
            throw(new java.lang.Error("The song has to be a file : " + file.getPath()));
        }
        if(!filter.accept(file)) {
            throw(new java.lang.Error("The file has to be a referenced song in SongFileFilter : " + file.getPath()));
        }
        
        MediaInfo info = new MediaInfo();
        info.open(file);
        
        this.artist = info.get(MediaInfo.StreamKind.General, 0, "Album/Performer");
        if(this.artist.isEmpty()) this.artist = "Unknown";
        this.album = info.get(MediaInfo.StreamKind.General, 0, "Album");
        if(this.album.isEmpty()) this.album = "Unknown";
        this.trackNumber = info.get(MediaInfo.StreamKind.General, 0, "Track/Position");
        if(this.trackNumber.isEmpty()) this.trackNumber = "Unknown";
        this.genre = info.get(MediaInfo.StreamKind.General, 0, "Genre");   
        if(this.genre.isEmpty()) this.genre = "Unknown";
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTrackNumber() {
        return trackNumber;
    }

    public String getGenre() {
        return genre;
    }

    public String toString() {
        String s = "";
        
        s += super.toString();
        s += "Artist : " + this.getArtist() + "\n";
        s += "Album : " + this.getAlbum() + "\n";
        s += "Track number : " + this.getTrackNumber() + "\n";
        s += "Genre : " + this.getGenre() + "\n";
        
        return s;
    }
    
}
