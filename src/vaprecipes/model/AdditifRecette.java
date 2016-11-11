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
public class AdditifRecette extends Additif implements Serializable{
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  
    private double quantite;
    public static String PROP_QUANTITE = "quantite";

    
    public AdditifRecette(String nom, String description, double quantite) {
        super(nom, description);
        setQuantite(quantite);
    }
    
    
    /**
     * Get the value of quantite
     *
     * @return the value of quantite
     */
    public double getQuantite() {
        return quantite;
    }

    /**
     * Set the value of quantite
     *
     * @param quantite new value of quantite
     */
    public void setQuantite(double quantite) {
        double oldQuantite = this.quantite;
        this.quantite = quantite;
        propertyChangeSupport.firePropertyChange(PROP_QUANTITE, oldQuantite, quantite);
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
