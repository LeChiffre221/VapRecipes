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
import vaprecipes.model.Additif;
import vaprecipes.model.CatalogueAdditif;

/**
 *
 * @author lechiffre
 */
public class CatalogueAdditifVM implements PropertyChangeListener{
    
    private CatalogueAdditif catalogueAdditifModel;
   
    private ObservableList<AdditifVM> listAdditifVMobs = FXCollections.observableArrayList();
    private final ListProperty<AdditifVM> listeAdditifVM = new SimpleListProperty<>();
        public ObservableList getListeAdditifVM() {return listeAdditifVM.get();}
        public void setListeAdditifVM(ObservableList value) {listeAdditifVM.set(value);}
        public ListProperty listeAdditifVMProperty() {return listeAdditifVM;}

    public CatalogueAdditifVM() {
        catalogueAdditifModel = new CatalogueAdditif();
        
        setListeAdditifVM(listAdditifVMobs);
        
        catalogueAdditifModel.addPropertyChangeListener(this);
    }
    
     public void reinitializeAdditifListProperty(){
         setListeAdditifVM(listAdditifVMobs);
    }
    
    public void addAdditif(String nom, String description){
        catalogueAdditifModel.addAdditif(new Additif(nom, description));
    }
    
    public void removeAdditif(int index){
        catalogueAdditifModel.removeAdditif(index);
    }
    
    public int sizeListeAdditif(){
        return catalogueAdditifModel.getListeAdditifs().size();
    }
    
    public void save(){
        catalogueAdditifModel.save();
    }
    public void load(){
       
        catalogueAdditifModel.load();
        
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(CatalogueAdditif.PROP_LISTEADDITIFS_ADD)){
            listAdditifVMobs.add(((IndexedPropertyChangeEvent)evt).getIndex(), new AdditifVM((Additif)evt.getNewValue()));
        }
        if(evt.getPropertyName().equals(CatalogueAdditif.PROP_LISTEADDITIFS_REMOVE)){
            listAdditifVMobs.remove(Integer.parseInt(evt.getNewValue().toString()));
        }
    }
        
     
    
    
}
