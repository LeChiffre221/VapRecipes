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
import static vaprecipes.model.CatalogueArome.PROP_LISTAROMES_ADD;
import static vaprecipes.model.CatalogueArome.PROP_LISTAROMES_REMOVE;

/**
 *
 * @author lechiffre
 */
public class CatalogueAdditif implements Serializable{
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    
    private List<Additif> listeAdditifs;
    public static final String PROP_LISTEADDITIFS = "listeAdditifs";
    public static final String PROP_LISTEADDITIFS_ADD = "listeAdditifsAdd";
    public static final String PROP_LISTEADDITIFS_REMOVE = "listeAdditifsRemove";

    public CatalogueAdditif() {
        setListeAdditifs(new ArrayList<>());
    }

    
    
    
    /**
     * Get the value of listeAdditifs
     *
     * @return the value of listeAdditifs
     */
    public List<Additif> getListeAdditifs() {
        return Collections.unmodifiableList(listeAdditifs);
    }

    /**
     * Set the value of listeAdditifs
     *
     * @param listeAdditifs new value of listeAdditifs
     */
    public void setListeAdditifs(List<Additif> listeAdditifs) {
        List<Additif> oldListeAdditifs = this.listeAdditifs;
        this.listeAdditifs = listeAdditifs;
        propertyChangeSupport.firePropertyChange(PROP_LISTEADDITIFS, oldListeAdditifs, listeAdditifs);
    }
    
    public void removeAdditif(int index){
        listeAdditifs.remove(index);
        
        propertyChangeSupport.firePropertyChange(PROP_LISTEADDITIFS_REMOVE, null, index);
    }
    
    /**
     * To add an additive (un Additif) to the list
     * @param a 
     */
    public void addAdditif(Additif a){
        listeAdditifs.add(a);
        
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTEADDITIFS_ADD, listeAdditifs.size()-1, null, a);
    }
    
    public void save(){
        
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saveAdditif.serial"));
            oos.writeObject(this);
            oos.flush();
            oos.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
    
    public void load(){
        
        
        CatalogueAdditif c = new CatalogueAdditif();
                 
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("saveAdditif.serial"));
            
            c = (CatalogueAdditif)ois.readObject();
            
            ois.close();
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
       
        c.getListeAdditifs().forEach(unAdditif -> this.addAdditif(unAdditif));
                 
        
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
