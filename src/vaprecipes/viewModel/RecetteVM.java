/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import vaprecipes.model.AdditifRecette;
import vaprecipes.model.Arome;
import vaprecipes.model.AromeRecette;
import vaprecipes.model.Recette;
import static vaprecipes.model.Recette.PROP_TAUXNICOTINETOTALE;

/**
 *
 * @author lechiffre
 */
public class RecetteVM implements PropertyChangeListener{

    private Recette recetteModel;
    public Recette getRecette(){
        return recetteModel;
    }
    
    
    private ObservableList<AromeRecetteVM> listeAromeRecetteObs = FXCollections.observableArrayList();
    private final ListProperty<AromeRecetteVM> listeAromeRecette = new SimpleListProperty<>();
        public ObservableList getListeAromeRecette() {return listeAromeRecette.get();}
        public void setListeAromeRecette(ObservableList value) {listeAromeRecette.set(value);}
        public ListProperty listeAromeRecetteProperty() {return listeAromeRecette;}
        
    private ObservableList<AdditifRecetteVM> listeAdditifRecetteObs = FXCollections.observableArrayList();
    private final ListProperty<AdditifRecetteVM> listeAdditifRecette = new SimpleListProperty<>();
        public ObservableList getListeAdditifRecette() {return listeAdditifRecette.get();}
        public void setListeAdditifRecette(ObservableList value) {listeAdditifRecette.set(value);}
        public ListProperty listeAdditifRecetteProperty() {return listeAdditifRecette;}
    
    private final StringProperty nom = new SimpleStringProperty();
        public String getNom() {return nom.get();}
        public void setNom(String value) {nom.set(value);}
        public StringProperty nomProperty() {return nom;}
    
    private final IntegerProperty qteTotale = new SimpleIntegerProperty();
        public int getQteTotale() {return qteTotale.get();}
        public void setQteTotale(int value) {qteTotale.set(value);}
        public IntegerProperty qteTotaleProperty() {return qteTotale;}
    
    private final IntegerProperty proportionPG = new SimpleIntegerProperty();
        public int getProportionPG() {return proportionPG.get();}
        public void setProportionPG(int value) {proportionPG.set(value);}
        public IntegerProperty proportionPGProperty() {return proportionPG;}
        
    private final IntegerProperty tauxNicotinePG = new SimpleIntegerProperty();
        public int getTauxNicotinePG() {return tauxNicotinePG.get();}
        public void setTauxNicotinePG(int value) {tauxNicotinePG.set(value);}
        public IntegerProperty tauxNicotinePGProperty() {return tauxNicotinePG;}

    private final IntegerProperty proportionVG = new SimpleIntegerProperty();
        public int getProportionVG() {return proportionVG.get();}
        public void setProportionVG(int value) {proportionVG.set(value);}
        public IntegerProperty proportionVGProperty() {return proportionVG;}
        
    private final IntegerProperty tauxNicotineVG = new SimpleIntegerProperty();
        public int getTauxNicotineVG() {return tauxNicotineVG.get();}
        public void setTauxNicotineVG(int value) {tauxNicotineVG.set(value);}
        public IntegerProperty tauxNicotineVGProperty() {return tauxNicotineVG;}

    private final DoubleProperty tauxNicotine = new SimpleDoubleProperty();
        public double getTauxNicotine() {return tauxNicotine.get();}
        public void setTauxNicotine(double value) {tauxNicotine.set(value);}
        public DoubleProperty tauxNicotineProperty() {return tauxNicotine;}
        
    private final IntegerProperty poucentageBase = new SimpleIntegerProperty();
        public int getPoucentageBase() {return poucentageBase.get();}
        public void setPoucentageBase(int value) {poucentageBase.set(value);}
        public IntegerProperty poucentageBaseProperty() {return poucentageBase;}
        
    private final IntegerProperty pourcentageArome = new SimpleIntegerProperty();
        public int getPourcentageArome() {return pourcentageArome.get();}
        public void setPourcentageArome(int value) {pourcentageArome.set(value);}
        public IntegerProperty pourcentageAromeProperty() {return pourcentageArome;}
    
    private final IntegerProperty pourcentageAdditif = new SimpleIntegerProperty();
        public int getPourcentageAdditif() {return pourcentageAdditif.get();}
        public void setPourcentageAdditif(int value) {pourcentageAdditif.set(value);}
        public IntegerProperty pourcentageAdditifProperty() {return pourcentageAdditif;}

    
   
    

    public RecetteVM(Recette r) {
        recetteModel = r;
        
        setNom(recetteModel.getNom());
        setQteTotale(recetteModel.getQteTotale());
        setProportionPG(recetteModel.getProportionPG());
        setTauxNicotinePG(recetteModel.getTauxNicotinePG());
        setProportionVG(recetteModel.getProportionVG());
        setTauxNicotineVG(recetteModel.getTauxNicotineVG());
        setTauxNicotine(recetteModel.getTauxNicotineTotale());
        
        setListeAromeRecette(listeAromeRecetteObs);
        setListeAdditifRecette(listeAdditifRecetteObs);
        
        updatePourcentageData();
        
        recetteModel.addPropertyChangeListener(this);
        
        nom.addListener((o, old, newV) -> recetteModel.setNom(newV));
        proportionPG.addListener((o, old, newV) -> recetteModel.setProportionPG(newV.intValue()));
        proportionVG.addListener((o, old, newV) -> recetteModel.setProportionVG(newV.intValue()));
        tauxNicotinePG.addListener((o, old, newV) -> recetteModel.setTauxNicotinePG(newV.intValue()));
        tauxNicotinePG.addListener((o, old, newV) -> recetteModel.setTauxNicotineTotale());
        tauxNicotineVG.addListener((o, old, newV) -> recetteModel.setTauxNicotineVG(newV.intValue()));
        tauxNicotineVG.addListener((o, old, newV) -> recetteModel.setTauxNicotineTotale());
        
        
    }

    public RecetteVM(String nom, int qteTotale, int proportionPG, int proportionVG, int tauxNicotinePG, int tauxNicotineVG) {
        this(new Recette(nom, qteTotale, proportionPG, proportionVG, tauxNicotinePG, tauxNicotineVG));
    }
    
    public void updatePourcentageData(){
        setPoucentageBase(recetteModel.getPourcentageBase());
        setPourcentageArome(recetteModel.getPourcentageArome());
        setPourcentageAdditif(recetteModel.getPourcentageAdditif());
        
    }
    
    public void removeArome(int index){
        recetteModel.removeArome(index);
    }
    
    public void addArome(AromeVM a, double quantite){
        String couleur  = String.format( "#%02X%02X%02X",
                (int)( a.getCouleur().getRed() * 255 ),
                (int)( a.getCouleur().getGreen() * 255 ),
                (int)( a.getCouleur().getBlue() * 255 ));
        recetteModel.addArome(new AromeRecette(a.getNom(), couleur, a.isFlavorTabac(), quantite));
    }
    
    public void removeAdditif(int index){
        recetteModel.removeAdditf(index);
    }
    
    public void addAdditif(AdditifVM a, double quantite){      
        recetteModel.addAdditif(new AdditifRecette(a.getNom(), a.getDescription(), quantite));
    }
    
    public void updateTauxNicotine(){
        recetteModel.setTauxNicotineTotale();
    }
    public double getSommeQteAromeQteAdditif(){
        return recetteModel.getSommeQteAromeQteAdditif();
    }

    
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Recette.PROP_TAUXNICOTINETOTALE)){
            tauxNicotine.set(Double.parseDouble(evt.getNewValue().toString()));
        }
        
        if(evt.getPropertyName().equals(Recette.PROP_LISTAROMERECETTE_ADD)){
            listeAromeRecetteObs.add(((IndexedPropertyChangeEvent)evt).getIndex(), new AromeRecetteVM((AromeRecette)evt.getNewValue()));
        }
        if(evt.getPropertyName().equals(Recette.PROP_LISTAROMERECETTE_REMOVE)){
            listeAromeRecetteObs.remove(((IndexedPropertyChangeEvent)evt).getIndex());
        }
        if(evt.getPropertyName().equals(Recette.PROP_LISTEADDITIFRECETTE_ADD)){
            listeAdditifRecetteObs.add(((IndexedPropertyChangeEvent)evt).getIndex(), new AdditifRecetteVM((AdditifRecette)evt.getNewValue()));
        }
        if(evt.getPropertyName().equals(Recette.PROP_LISTEADDITIFRECETTE_REMOVE)){
            listeAdditifRecetteObs.remove(((IndexedPropertyChangeEvent)evt).getIndex());
        }
    }
    
}
