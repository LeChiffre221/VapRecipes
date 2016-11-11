/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.viewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;
import vaprecipes.model.Arome;
import vaprecipes.model.Complement;

/**
 *
 * @author lechiffre
 */
public class AromeVM implements PropertyChangeListener{

    private Arome arome;
    
    private final StringProperty nom = new SimpleStringProperty();
        public String getNom() {return nom.get();}
        public void setNom(String value) {        nom.set(value);}
        public StringProperty nomProperty() {return nom;}
    
    private final ObjectProperty<Color> couleur = new SimpleObjectProperty<>();
        public Color getCouleur() {return couleur.get();}
        public void setCouleur(Color value) {couleur.set(value);}
        public ObjectProperty couleurProperty() {return couleur;}
    
    private final BooleanProperty flavorTabac = new SimpleBooleanProperty();
        public boolean isFlavorTabac() {return flavorTabac.get();}
        public void setFlavorTabac(boolean value) {flavorTabac.set(value);}
        public BooleanProperty flavorTabacProperty() {return flavorTabac;}

        
    public AromeVM(Arome a) {
        arome = a;
        setNom(arome.getNom());
        setCouleur(Color.web(arome.getCouleur()));
        setFlavorTabac(arome.isFlavorTabac());
       
        //Abonnement au changement du modèle
        arome.addPropertyChangeListener(this);
        
        //Ecoute du changement dans la vue
        this.nom.addListener((o, old, newV) -> arome.setNom(newV));
        this.couleur.addListener((o, old, newV) -> arome.setCouleur(String.format( "#%02X%02X%02X",
                (int)( newV.getRed() * 255 ),
                (int)( newV.getGreen() * 255 ),
                (int)( newV.getBlue() * 255 ) )));
        this.flavorTabac.addListener((o, old, newV) -> arome.setFlavorTabac(newV));
        
    }

    public AromeVM(String nom, String couleur, boolean flavorTabac){
            this(new Arome(nom, couleur, flavorTabac));
        
    }
        
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(Arome.PROP_NOM)){
            setNom(evt.getNewValue().toString());
        }
        
    }
    
}
