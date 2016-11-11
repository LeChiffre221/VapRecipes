/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lechiffre
 */
public class CatalogueArome implements Serializable{
    
    private transient  PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
    private List<Arome> listAromes;
    public static final String PROP_LISTAROMES = "listAromes";
    public static final String PROP_LISTAROMES_ADD = "listAromesAdd";
    public static final String PROP_LISTAROMES_REMOVE = "listAromesRemove";

    
    public CatalogueArome() {
        setListAromes(new ArrayList<Arome>());
    }
    
    
  
    
    /**
     * Get the value of listAromes
     *
     * @return the value of listAromes
     */
    public List<Arome> getListAromes() {
        return Collections.unmodifiableList(listAromes);
    }

    /**
     * Set the value of listAromes
     *
     * @param listAromes new value of listAromes
     */
    public void setListAromes(List<Arome> listAromes) {
        List<Arome> oldListAromes = this.listAromes;
        this.listAromes = listAromes;
        propertyChangeSupport.firePropertyChange(PROP_LISTAROMES, oldListAromes, listAromes);
    }
    
    public void removeArome(int index){
        listAromes.remove(index);
        
        propertyChangeSupport.firePropertyChange(PROP_LISTAROMES_REMOVE, null, index);
    }
    
    /**
     * To add an aroma (un Arome) to the list
     * @param a 
     */
    public void addArome(Arome a){
        listAromes.add(a);
        
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTAROMES_ADD, listAromes.size()-1, null, a);
    }
    
    public void save(){
        
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveArome.serial"));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void load(){
        
        
        CatalogueArome c = new CatalogueArome();
                 
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveArome.serial"));
            
            c = (CatalogueArome)ois.readObject();
            
            ois.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        c.getListAromes().forEach(unArome -> this.addArome(unArome));
                 
        
    }
    

    public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        propertyChangeSupport = new PropertyChangeSupport(this);
            
    }


    /**
     * Add PropertyChangeListener.
     *
     * @param listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Remove PropertyChangeListener.
     *
     * @param listener
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
