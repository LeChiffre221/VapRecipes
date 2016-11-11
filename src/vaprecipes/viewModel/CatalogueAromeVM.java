/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vaprecipes.model.Arome;
import vaprecipes.model.CatalogueArome;

/**
 *
 * @author lechiffre
 */
public class CatalogueAromeVM implements PropertyChangeListener{

    private CatalogueArome catalogueAromeModel;
    
    private ObservableList<AromeVM> listeAromeVMobs = FXCollections.observableArrayList();
    private final ListProperty<AromeVM> listeAromeVM = new SimpleListProperty<>();
        public ObservableList getListeAromeVM() {return listeAromeVM.get();}
        public void setListeAromeVM(ObservableList value) {listeAromeVM.set(value);}
        public ListProperty listeAromeVMProperty() {return listeAromeVM;}

    public CatalogueAromeVM() {
        catalogueAromeModel = new CatalogueArome();
        
        setListeAromeVM(listeAromeVMobs);
        
        catalogueAromeModel.addPropertyChangeListener(this);
    }
    
    public void reinitializeAromeListProperty(){
        setListeAromeVM(listeAromeVMobs);
    }
    
    public void addArome(String nom, String couleur, boolean flavorTabac){
        catalogueAromeModel.addArome(new Arome(nom, couleur, flavorTabac));
    }
    
    public void removeArome(int index){
        catalogueAromeModel.removeArome(index);
    }
    
    public int sizeListeArome(){
        return catalogueAromeModel.getListAromes().size();
    }
    
    public void save(){
        catalogueAromeModel.save();
    }
    public void load(){
       
        catalogueAromeModel.load();
        
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(CatalogueArome.PROP_LISTAROMES_ADD)){
            listeAromeVMobs.add(((IndexedPropertyChangeEvent)evt).getIndex(), new AromeVM((Arome)evt.getNewValue()));
        }
        if(evt.getPropertyName().equals(CatalogueArome.PROP_LISTAROMES_REMOVE)){
            listeAromeVMobs.remove(Integer.parseInt(evt.getNewValue().toString()));
        }
    }
    
    
    
}
