/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author lechiffre
 */
public class Additif extends Complement implements Serializable {
      
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    
    private String description;
    public static final String PROP_DESCRIPTION = "description";

    public Additif(String nom, String description) {
        setNom(nom);
        setDescription(description);
    }

    @Override
    public void setNom(String nom) {
        if(nom.isEmpty()){
           nom = "--Aucun--";
        }
        
        String oldNom = this.nom;
        
        if(nom == "--Aucun--"){
            oldNom = null;
        }
        
        this.nom = nom;
        propertyChangeSupport.firePropertyChange(PROP_NOM, oldNom, nom);
    }
    
    
    
    /**
     * Get the value of description
     *
     * @return the value of description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the value of description
     *
     * @param description new value of description
     */
    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        propertyChangeSupport.firePropertyChange(PROP_DESCRIPTION, oldDescription, description);
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
