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
import vaprecipes.model.AdditifRecette;
import vaprecipes.model.AromeRecette;
import vaprecipes.model.CatalogueRecette;
import vaprecipes.model.Recette;

/**
 *
 * @author lechiffre
 */
public class CatalogueRecetteVM implements PropertyChangeListener{

    private CatalogueRecette catalogueRecetteModel;
    
    private ObservableList<RecetteVM> listeRecetteVMobs = FXCollections.observableArrayList();
    private final ListProperty<RecetteVM> listeRecetteVM = new SimpleListProperty<>();
        public ObservableList getListeRecetteVM() {return listeRecetteVM.get();}
        public void setListeRecetteVM(ObservableList value) {listeRecetteVM.set(value);}
        public ListProperty listeRecetteVMProperty() {return listeRecetteVM;}

    public CatalogueRecetteVM() {
        catalogueRecetteModel = new CatalogueRecette();
        
        setListeRecetteVM(listeRecetteVMobs);
        
        catalogueRecetteModel.addPropertyChangeListener(this);
        
    }
    
    public void reinitializeRecetteListProperty(){
        setListeRecetteVM(listeRecetteVMobs);
    }
    
    public int sizeListeRecette(){
        return catalogueRecetteModel.getListeRecette().size();
    }

    public void addRecette(RecetteVM r){
        listeRecetteVMobs.add(listeRecetteVMobs.size(),r);
        catalogueRecetteModel.addRecette(r.getRecette());
    }
    
    public void editRecette(RecetteVM r, int index){
        listeRecetteVMobs.set(index, r);
        catalogueRecetteModel.editRecette(r.getRecette(), index);
    }
    
    public void removeRecette(int index){
        catalogueRecetteModel.removeRecette(index);
    }
    
    public void save(){
        catalogueRecetteModel.save();
    }
    public void load(){
        catalogueRecetteModel.load();
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(CatalogueRecette.PROP_LISTERECETTE_ADD_LOAD)){
            Recette r = (Recette)evt.getNewValue();
            
            RecetteVM rVM =  new RecetteVM(r);
            for (AromeRecette a : r.getListeAromeRecette()) {
                rVM.getListeAromeRecette().add(new AromeRecetteVM(a));
            }
            for (AdditifRecette a : r.getListeAdditifRecette()) {
                rVM.getListeAdditifRecette().add(new AdditifRecetteVM(a));
            }
            listeRecetteVMobs.add(((IndexedPropertyChangeEvent)evt).getIndex(), rVM);
        }
        if(evt.getPropertyName().equals(CatalogueRecette.PROP_LISTERECETTE_REMOVE)){
            listeRecetteVMobs.remove(Integer.parseInt(evt.getNewValue().toString()));
        }
    }
    
}
