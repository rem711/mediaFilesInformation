/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.JFrame;


/**
 *
 * @author rem711
 */
public class MediaFilesInformation extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Display display = new Display();
        display.show();
        File folder = display.getFolder();
        
        LocalDateTime debut = LocalDateTime.now();
        
        if(folder != null && folder.isDirectory()) {   
            
            
            FileExplorer fe = new FileExplorer(folder, "media");
            fe.populate();
            List<Media> medias = fe.getListMedias();
            System.out.println("Total Medias : " + medias.size() + " files");
            
            fe.setMediaType("movie");
            fe.populate();
            List<Media> movies = fe.getListMedias();
            System.out.println("Total Movies : " + movies.size() + " files");
            
            fe.setMediaType("song");
            fe.populate();
            List<Media> songs = fe.getListMedias();
            System.out.println("Total Songs : " + songs.size() + " files");
        }
        
        LocalDateTime fin = LocalDateTime.now();
        
        long diff = ChronoUnit.SECONDS.between(debut, fin);
        
        System.out.println("****************************************************");
        System.out.println("                     " + diff + "s");
        System.out.println("****************************************************");
    }       
}
