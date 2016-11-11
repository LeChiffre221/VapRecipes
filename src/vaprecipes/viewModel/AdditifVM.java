/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import vaprecipes.model.Additif;

/**
 *
 * @author lechiffre
 */
public class AdditifVM implements PropertyChangeListener{
    
    private Additif additifModel;
    
    private final StringProperty nom = new SimpleStringProperty();
        public String getNom() {return nom.get();}
        public void setNom(String value) {nom.set(value);}
        public StringProperty nomProperty() {return nom;}
        
    private final StringProperty description = new SimpleStringProperty();
        public String getDescription() {return description.get();}
        public void setDescription(String value) {description.set(value);}
        public StringProperty descriptionProperty() {return description;}

    public AdditifVM(Additif a) {
        
        additifModel = a;
        
        setNom(additifModel.getNom());
        setDescription(additifModel.getDescription());
        
        additifModel.addPropertyChangeListener(this);
        
        this.nom.addListener((o, old, newV) -> additifModel.setNom(newV));
        this.description.addListener((o, old, newV) -> additifModel.setDescription(newV));
        
       
    }
   

    public AdditifVM(String nom, String description) {
        this(new Additif(nom, description));
    }
    
    

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Additif.PROP_NOM)){
            setNom(evt.getNewValue().toString());
        }
    }
    
    
    
        
}
