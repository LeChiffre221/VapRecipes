/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import vaprecipes.model.Arome;
import vaprecipes.model.AromeRecette;

/**
 *
 * @author lechiffre
 */
public class AromeRecetteVM extends AromeVM{

    private AromeRecette aromeRecetteModel;
    
    private final DoubleProperty quantite = new SimpleDoubleProperty();
        public double getQuantite() {return quantite.get();}
        public void setQuantite(double value) {quantite.set(value);}
        public DoubleProperty quantiteProperty() {return quantite;}
    
    
    
    public AromeRecetteVM(AromeRecette a) {
        super(a);
        aromeRecetteModel = a;
        
        setQuantite(aromeRecetteModel.getQuantite());
    }
    
    
    
}
