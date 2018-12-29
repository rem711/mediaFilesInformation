/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaFilesInformation;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author rem711
 */
public class Chrono {
    private LocalDateTime begin;
    private LocalDateTime end;
    
    public Chrono() {
        this.begin = LocalDateTime.now();
        this.end = LocalDateTime.now();
    }
    
    public LocalDateTime getBegin() {
        return this.begin;
    }
    
    public void setBegin() {
        this.begin = LocalDateTime.now();
    }
    
    public LocalDateTime getEnd() {
        return this.end;
    }
    
    public void setEnd() {
        this.end = LocalDateTime.now();
    }
    
    public long difference() {
        return ChronoUnit.SECONDS.between(this.begin, this.end);
    }
    
}
