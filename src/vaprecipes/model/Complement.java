/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 *
 * @author lechiffre
 */
public abstract class Complement implements Serializable{
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    
    protected String nom;
    public static final String PROP_NOM = "nom";

    public Complement(String nom){
        setNom(nom);
    }

    public Complement() {
    }

    /**
     * Get the value of nom
     *
     * @return the value of nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set the value of nom
     *
     * @param nom new value of nom
     */
    public void setNom(String nom){
       
        if(nom.isEmpty()){
           nom = "--Aucun--";
        }
        
        String oldNom = this.nom;
        this.nom = nom;
        propertyChangeSupport.firePropertyChange(PROP_NOM, oldNom, nom);
        
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
