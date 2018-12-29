/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JFrame;

/**
 *
 * @author rem711
 */
public class Display extends JFrame {
    private FileChooser fileChooser;

    public Display() {
        this.fileChooser = new FileChooser();
    }
    
    public void show() {
        this.fileChooser.open();
    }
    
    public File getFolder() {
        return this.fileChooser.getSelectedFolder();
    }
    
    
}
