/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.io.File;

/**
 *
 * @author rem711
 */
public class SongFileFilter extends MediaFileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("mp3") ||
                extension.equals("wav") ||
                extension.equals("wmv") ||
                extension.equals("flac"))  {
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
