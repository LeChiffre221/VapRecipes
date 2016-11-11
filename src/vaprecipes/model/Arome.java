/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 *
 * @author lechiffre
 */
public class Arome extends Complement implements Serializable{
    
    private transient  PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private String couleur;
    public static final String PROP_COULEUR = "couleur";

    private boolean flavorTabac;
    public static final String PROP_FLAVORTABAC = "flavorTabac";


    public Arome(String nom, String couleur, boolean flavorTabac) {
        
            setNom(nom);
            setCouleur(couleur);
            setFlavorTabac(flavorTabac);
        
        
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
     * Get the value of couleur
     *
     * @return the value of couleur
     */
    public String getCouleur() {
        return couleur;
    }

    /**
     * Set the value of couleur
     *
     * @param couleur new value of couleur
     */
    public void setCouleur(String couleur) {
        
        String oldCouleur = this.couleur;
        this.couleur = couleur;
        propertyChangeSupport.firePropertyChange(PROP_COULEUR, oldCouleur, couleur);
    }
    
    /**
     * Get the value of flavorTabac
     *
     * @return the value of flavorTabac
     */
    public boolean isFlavorTabac() {
        return flavorTabac;
    }

    /**
     * Set the value of flavorTabac
     *
     * @param flavorTabac new value of flavorTabac
     */
    public void setFlavorTabac(boolean flavorTabac) {
        
        boolean oldFlavorTabac = this.flavorTabac;
        this.flavorTabac = flavorTabac;
        propertyChangeSupport.firePropertyChange(PROP_FLAVORTABAC, oldFlavorTabac, flavorTabac);
        
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
