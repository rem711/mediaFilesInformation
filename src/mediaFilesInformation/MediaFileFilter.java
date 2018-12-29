package mediaFilesInformation;

import java.io.File;
import java.io.FileFilter;

public class MediaFileFilter implements FileFilter {
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        
        String extension = getExtension(f);
        if (extension != null) {
            if (extension.equals("avi") ||
                extension.equals("mkv") ||
                extension.equals("mp4") ||
                extension.equals("mp3") ||
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
    
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}





