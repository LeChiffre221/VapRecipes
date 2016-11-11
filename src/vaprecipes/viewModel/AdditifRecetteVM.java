/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import vaprecipes.model.AdditifRecette;

/**
 *
 * @author lechiffre
 */
public class AdditifRecetteVM extends AdditifVM{
    
    private AdditifRecette additifRecetteModel;
    
    private final DoubleProperty quantite = new SimpleDoubleProperty();
        public double getQuantite() {return quantite.get();}
        public void setQuantite(double value) {quantite.set(value);}
        public DoubleProperty quantiteProperty() {return quantite;}
    
    
    
    public AdditifRecetteVM(AdditifRecette a) {
        super(a);
        additifRecetteModel = a;
        
        setQuantite(additifRecetteModel.getQuantite());
    }
    
}
