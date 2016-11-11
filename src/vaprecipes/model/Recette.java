/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lechiffre
 */
public class Recette implements Serializable{
    
    private transient PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

   
    private List<AromeRecette> listAromeRecette;
    public static final String PROP_LISTAROMERECETTE = "listAromeRecette";
    public static final String PROP_LISTAROMERECETTE_ADD = "listAromeRecetteAdd";
    public static final String PROP_LISTAROMERECETTE_REMOVE = "listAromeRecetteRemove";
    
    private List<AdditifRecette> listeAdditifRecette;
    public static final String PROP_LISTEADDITIFRECETTE = "listeAdditifRecette";
    public static final String PROP_LISTEADDITIFRECETTE_ADD = "listeAdditifRecetteAdd";
    public static final String PROP_LISTEADDITIFRECETTE_REMOVE = "listeAdditifRecetteRemove";

    
    private String nom;
    public static final String PROP_NOM = "nom";
    
    private int qteTotale;
    public static final String PROP_QTETOTALE = "qteTotale";
    
    private int proportionPG;
    public static final String PROP_PROPORTIONPG = "proportionPG";

    private int proportionVG;
    public static final String PROP_PROPORTIONVG = "proportionVG";
    
    private double tauxNicotineTotale;
    public static final String PROP_TAUXNICOTINETOTALE = "tauxNicotineTotale";
    
    private int tauxNicotinePG;
    public static final String PROP_TAUXNICOTINEPG = "tauxNicotinePG";
    
    private int tauxNicotineVG;
    public static final String PROP_TAUXNICOTINEVG = "tauxNicotineVG";

    public Recette(String nom, int qteTotale, int proportionPG, int proportionVG, int tauxNicotinePG, int tauxNicotineVG) {
        
        setListeAromeRecette(new ArrayList<>());
        setListeAdditifRecette(new ArrayList<>());
        
        setNom(nom);
        setQteTotale(qteTotale);
        setProportionPG(proportionPG);
        setProportionVG(proportionVG);
        setTauxNicotinePG(tauxNicotinePG);
        setTauxNicotineVG(tauxNicotineVG);
        setTauxNicotineTotale();
              
        
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
    public void setNom(String nom) {
        String oldNom = this.nom;
        this.nom = nom;
        propertyChangeSupport.firePropertyChange(PROP_NOM, oldNom, nom);
    }

    /**
     * Get the value of qteTotale
     *
     * @return the value of qteTotale
     */
    public int getQteTotale() {
        return qteTotale;
    }

    /**
     * Set the value of qteTotale
     *
     * @param qteTotale new value of qteTotale
     */
    public void setQteTotale(int qteTotale) {
        int oldQteTotale = this.qteTotale;
        this.qteTotale = qteTotale;
        propertyChangeSupport.firePropertyChange(PROP_QTETOTALE, oldQteTotale, qteTotale);
    }
    
    /**
     * Get the value of proportionPG
     *
     * @return the value of proportionPG
     */
    public int getProportionPG() {
        return proportionPG;
    }

    /**
     * Set the value of proportionPG
     *
     * @param proportionPG new value of proportionPG
     */
    public void setProportionPG(int proportionPG) {
        int oldProportionPG = this.proportionPG;
        this.proportionPG = proportionPG;
        propertyChangeSupport.firePropertyChange(PROP_PROPORTIONPG, oldProportionPG, proportionPG);
    }
    
    /**
     * Get the value of proportionVG
     *
     * @return the value of proportionVG
     */
    public int getProportionVG() {
        return proportionVG;
    }

    /**
     * Set the value of proportionVG
     *
     * @param proportionVG new value of proportionVG
     */
    public void setProportionVG(int proportionVG) {
        int oldProportionVG = this.proportionVG;
        this.proportionVG = proportionVG;
        propertyChangeSupport.firePropertyChange(PROP_PROPORTIONVG, oldProportionVG, proportionVG);
    }
    
    /**
     * Get the value of tauxNicotineTotale
     *
     * @return the value of tauxNicotineTotale
     */
    public double getTauxNicotineTotale() {
        return tauxNicotineTotale;
    }

    /**
     * Set the value of tauxNicotineTotale
     *
     * @param tauxNicotineTotale new value of tauxNicotineTotale
     */
    public void setTauxNicotineTotale() {
        System.out.println(tauxNicotinePG+" / " + proportionPG+" / " +getPourcentageBase());
        double tauxNicotineTotale = (tauxNicotinePG*proportionPG/100.0+tauxNicotineVG*proportionVG/100.0)*getPourcentageBase()/100; 
        double oldTauxNicotineTotale = this.tauxNicotineTotale;
        this.tauxNicotineTotale = tauxNicotineTotale;
        propertyChangeSupport.firePropertyChange(PROP_TAUXNICOTINETOTALE, oldTauxNicotineTotale, tauxNicotineTotale);
    }
    
    public int getPourcentageBase(){
        int pourcentageBase;
        
        pourcentageBase = 100 - getPourcentageArome() - getPourcentageAdditif();
        System.out.println(pourcentageBase);
        return pourcentageBase;
    }
    
    public int getPourcentageArome(){
        int poucentageArome;
        double qteArome = 0;
        
        for (AromeRecette aromeRecette : listAromeRecette) {
            qteArome += aromeRecette.getQuantite();
        }

        poucentageArome = (int)(qteArome / qteTotale * 100);
        
        return poucentageArome;
    }
    
    public int getPourcentageAdditif(){
        int pourcentageAdditif;
        double qteAdditif = 0;
        
        for (AdditifRecette additifRecette : listeAdditifRecette) {
            qteAdditif += additifRecette.getQuantite();
        }
        
        pourcentageAdditif = (int)(qteAdditif / qteTotale * 100);
        
        return pourcentageAdditif;
    }

    /**
     * Get the value of tauxNicotinePG
     *
     * @return the value of tauxNicotinePG
     */
    public int getTauxNicotinePG() {
        return tauxNicotinePG;
    }

    /**
     * Set the value of tauxNicotinePG
     *
     * @param tauxNicotinePG new value of tauxNicotinePG
     */
    public void setTauxNicotinePG(int tauxNicotinePG) {
        int oldTauxNicotinePG = this.tauxNicotinePG;
        this.tauxNicotinePG = tauxNicotinePG;
        propertyChangeSupport.firePropertyChange(PROP_TAUXNICOTINEPG, oldTauxNicotinePG, tauxNicotinePG);
    }
        
    /**
     * Get the value of tauxNicotineVG
     *
     * @return the value of tauxNicotineVG
     */
    public int getTauxNicotineVG() {
        return tauxNicotineVG;
    }

    /**
     * Set the value of tauxNicotineVG
     *
     * @param tauxNicotineVG new value of tauxNicotineVG
     */
    public void setTauxNicotineVG(int tauxNicotineVG) {
        int oldTauxNicotineVG = this.tauxNicotineVG;
        this.tauxNicotineVG = tauxNicotineVG;
        propertyChangeSupport.firePropertyChange(PROP_TAUXNICOTINEVG, oldTauxNicotineVG, tauxNicotineVG);
    }
    
    
    public void removeArome(int index){       
        listAromeRecette.remove(index);
        setTauxNicotineTotale();
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTAROMERECETTE_REMOVE, index, null, null);
    }
    
    public void addArome(AromeRecette a){
        listAromeRecette.add(a);
        setTauxNicotineTotale();
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTAROMERECETTE_ADD, listAromeRecette.size()-1, null, a);
    }
    


    /**
     * Get the value of listAromeRecette
     *
     * @return the value of listAromeRecette
     */
    public List<AromeRecette> getListeAromeRecette() {
        return Collections.unmodifiableList(listAromeRecette);
    }

    /**
     * Set the value of listAromeRecette
     *
     * @param listAromeRecette new value of listAromeRecette
     */
    public void setListeAromeRecette(List<AromeRecette> listAromeRecette) {
        List<AromeRecette> oldListAromeRecette = this.listAromeRecette;
        this.listAromeRecette = listAromeRecette;
        propertyChangeSupport.firePropertyChange(PROP_LISTAROMERECETTE, oldListAromeRecette, listAromeRecette);
    }
    
    
    public void removeAdditf(int index){       
        listeAdditifRecette.remove(index);
        setTauxNicotineTotale();
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTEADDITIFRECETTE_REMOVE, index, null, null);
    }
    
    public void addAdditif(AdditifRecette a){
        listeAdditifRecette.add(a);
        setTauxNicotineTotale();
        propertyChangeSupport.fireIndexedPropertyChange(PROP_LISTEADDITIFRECETTE_ADD, listeAdditifRecette.size()-1, null, a);
    }
    
    public List<AdditifRecette> getListeAdditifRecette() {
        return Collections.unmodifiableList(listeAdditifRecette);
    }

    public void setListeAdditifRecette(List<AdditifRecette> listeAdditifRecette) {
        List<AdditifRecette> oldListeAdditifRecette = this.listeAdditifRecette;
        this.listeAdditifRecette = listeAdditifRecette;
        propertyChangeSupport.firePropertyChange(PROP_LISTEADDITIFRECETTE, oldListeAdditifRecette, listeAdditifRecette);
    }
    
    public double getSommeQteAromeQteAdditif(){
        double qte = 0;
        
        for (AromeRecette aromeRecette : listAromeRecette) {
            qte += aromeRecette.getQuantite();
        }
        for (AdditifRecette additifRecette : listeAdditifRecette) {
            qte += additifRecette.getQuantite();
        }
        
        return qte;
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
