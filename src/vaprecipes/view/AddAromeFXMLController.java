/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vaprecipes.viewModel.CatalogueAromeVM;

/**
 *
 * @author lechiffre
 */
public class AddAromeFXMLController{
    
    private CatalogueAromeVM catalogueAromeVM;

    @FXML
    TextField nomAromeTF;
    
    @FXML
    ColorPicker colorAromeCP;
    
    @FXML
    CheckBox flavorTabacCB;
    
    
    
    public AddAromeFXMLController(CatalogueAromeVM catalogueAromeVM) {
        this.catalogueAromeVM = catalogueAromeVM;
    }
    
    @FXML
    private void onClickAddArome(){
        Stage stage = (Stage)nomAromeTF.getScene().getWindow();
        
        String couleur = String.format("#%02X%02X%02X",
                                       (int)( colorAromeCP.getValue().getRed() * 255),
                                       (int)( colorAromeCP.getValue().getGreen() * 255),
                                       (int)( colorAromeCP.getValue().getBlue() * 255));
        
        catalogueAromeVM.addArome(nomAromeTF.getText(), couleur, flavorTabacCB.selectedProperty().getValue());
        
        stage.close();
    }
    
    @FXML
    private void onClickAnnuler(){
        Stage stage = (Stage)nomAromeTF.getScene().getWindow();
       
        stage.close();
    }
    
    
    
}
