/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursemenu;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author LeeSeungmin
 */
public class CourseDetails 
{
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty day;
    private final StringProperty time;
    private final StringProperty prof;
    
    public CourseDetails(String id, String name, String day, String time, String prof)
    {       
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.day = new SimpleStringProperty(day);
        this.time = new SimpleStringProperty(time);
        this.prof = new SimpleStringProperty(prof);
    }
    
    public String getId()
    {
        return id.get();
    }
    
    public String getName()
    {
        return name.get();
    }
    
    public String getDay()
    {
        return day.get();
    }
    
    public String getTime()
    {
        return time.get();
    }
    
    public String getProf()
    {
        return prof.get();
    }
    
    public void setId(String id)
    {
        this.id.set(id);
    }
    
    public void setName(String name)
    {
        this.name.set(name);
    }
    
    public void setDay(String day)
    {
        this.day.set(day);
    }
    
    public void setTime(String time)
    {
        this.time.set(time);
    }
    
    public void setProf(String prof)
    {
        this.prof.set(prof);
    }
    
    public StringProperty idProperty()
    {
        return id;
    }
    
    public StringProperty nameProperty()
    {
        return name;
    }
    
    public StringProperty dayProperty()
    {
        return day;
    }
    
    public StringProperty timeProperty()
    {
        return time;
    }
    
    public StringProperty profProperty()
    {
        return prof;
    }
}
