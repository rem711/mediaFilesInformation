/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rem711
 */
public class FileExplorer {
    private File initFolder;
    private MovieFileFilter movieFilter;
    private SongFileFilter songFilter;
    private MediaFileFilter mediaFilter;
    private MediaFileFilter filter;
    private String mediaType;    
    private volatile List<Media> listMedias;
    private ReentrantLock lock = new ReentrantLock();
    private ExecutorService executorService;
    
    
    

    public FileExplorer(File initFolder, String filterType) {
        this.setInitFolder(initFolder);
        
        this.movieFilter = new MovieFileFilter();
        this.songFilter = new SongFileFilter();
        this.mediaFilter = new MediaFileFilter();
        
        this.setMediaType(filterType);
        this.setListMedias();
    }

    public File getInitFolder() {
        return initFolder;
    }

    public void setInitFolder(File initFolder) {
        if(!initFolder.isDirectory()) {
            throw(new java.lang.Error("The FileExplorer need a folder attribute : " + initFolder.getPath()));
        }
        
        this.initFolder = initFolder;
        this.setListMedias();
    }

    public MediaFileFilter getFilter() {
        return filter;
    }

    private void setFilter() {
        switch(this.mediaType) {
            case "movie" :
                this.filter = this.movieFilter;
                break;
            case "song" : 
                this.filter = this.songFilter;
                break;
            default :
                this.filter = this.mediaFilter;
        }        
    }

    public List<Media> getListMedias() {
        return this.listMedias;
    }
    
    private void setListMedias() {
        this.listMedias = new ArrayList<Media>();
    }

    public String getMediaType() {
        return mediaType;
    }
    
    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
        this.setFilter();
    }
    
    private List<Media> recursiveExplore(File folder) {
        List<Media> medias = new ArrayList<Media>();
        File[] filesList = folder.listFiles(this.filter);
        
        for (File file : filesList) {
            if (file.isFile()) {
                if(movieFilter.accept(file)) {
                    medias.add(new Movie(file));
                }
                else if(songFilter.accept(file)) {
                    medias.add(new Song(file));
                }
                else {
                    medias.add(new Media(file));
                }
            }
            if(file.isDirectory()) {
                List<Media> loop = recursiveExplore(file);
                medias.addAll(loop);
            }
        }
        
        return medias;
    }
    
    // about 2.5 times faster than recursiveExplore
    private void exploreWithThreads() {
        this.executorService = Executors.newFixedThreadPool(3);
        threadExplore(this.initFolder);
        
        int count = 1;
        while(count > 0) {
            count = ((ThreadPoolExecutor)this.executorService).getActiveCount();
            try {
                sleep(250);
            } catch (InterruptedException ex) {
                Logger.getLogger(FileExplorer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        this.executorService.shutdown();
    }
    
    private void threadExplore(File folder) {
        File[] filesList = folder.listFiles(this.filter);
        List<Media> threadListMedia  = new ArrayList<Media>();
        
        for (File file : filesList) {
            if (file.isFile()) {
                if(movieFilter.accept(file)) {
                    threadListMedia.add(new Movie(file));
                }
                else if(songFilter.accept(file)) {
                    threadListMedia.add(new Song(file));
                }
                else {
                    threadListMedia.add(new Media(file));
                }
            }
            if(file.isDirectory()) {        
                Future future = executorService.submit(new Runnable() {
                    public void run() {
                        threadExplore(file);
                    }
                });     
                
            }
        }
        
        if(!threadListMedia.isEmpty()) {
            this.lock.lock();    
            try {
                this.listMedias.addAll(threadListMedia);
            }
            finally {
                this.lock.unlock();
            }
        }   
    }
    
    public void populate() {
        this.setListMedias();
        //this.listMedias = this.recursiveExplore(this.initFolder);
        this.exploreWithThreads();
    }
    
    public void stop() {
        this.executorService.shutdownNow();
    }
}


