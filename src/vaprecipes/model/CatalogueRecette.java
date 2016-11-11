/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lechiffre
 */
public class CatalogueRecette implements Serializable{
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private List<Recette> listeRecette;
    public static final String PROP_LISTERECETTE = "listeRecette";
    public static final String PROP_LISTERECETTE_ADD = "listeRecetteAdd";
    public static final String PROP_LISTERECETTE_ADD_LOAD = "listeRecetteAddLoad";
    public static final String PROP_LISTERECETTE_REMOVE = "listeRecetteRemove";

    public CatalogueRecette() {
    
        setListeRecette(new ArrayList<>());
    }

    
    public void addRecette(Recette r){

        listeRecette.add(r);
        
    }
    public void addRecetteLoad(Recette r){

        listeRecette.add(r);
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTERECETTE_ADD_LOAD, listeRecette.size()-1, null, r);

    }
    
    public void removeRecette(int index){
        listeRecette.remove(index);
        
        propertyChangeSupport.firePropertyChange(PROP_LISTERECETTE_REMOVE, null, index);
    }
    
    /**
     * Get the value of listeRecette
     *
     * @return the value of listeRecette
     */
    public List<Recette> getListeRecette() {
        return Collections.unmodifiableList(listeRecette);
    }

    /**
     * Set the value of listeRecette
     *
     * @param listeRecette new value of listeRecette
     */
    public void setListeRecette(List<Recette> listeRecette) {
        List<Recette> oldListeRecette = this.listeRecette;
        this.listeRecette = listeRecette;
        propertyChangeSupport.firePropertyChange(PROP_LISTERECETTE, oldListeRecette, listeRecette);
    }

    public void save(){
        
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveRecette.serial"));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void load(){
        
        
        CatalogueRecette c = new CatalogueRecette();
                 
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveRecette.serial"));
            
            c = (CatalogueRecette)ois.readObject();
            
            ois.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        
        c.getListeRecette().forEach(uneRecette -> {
            
            this.addRecetteLoad(uneRecette);
        });
                 
        
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
        if(propertyChangeSupport == null){
            propertyChangeSupport = new PropertyChangeSupport(this);
        }
        
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
