/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author rem711
 */
public class FileChooser extends JPanel {
    private JFileChooser chooser;
    private File folder;
    
    public FileChooser() {
        this.chooser = new JFileChooser();
        this.chooser.setCurrentDirectory(new java.io.File("D:/")); // start at application current directory
        this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        //this.chooser.setAcceptAllFileFilterUsed(false);
        this.folder = null;
    }
    
    public void open()
    {
        if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            this.folder = this.chooser.getSelectedFile();
        }
        else {
            this.folder = null;
        }
    }
    
    public File getSelectedFolder() {
        return this.folder;
    }
}
