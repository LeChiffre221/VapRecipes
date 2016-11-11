/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import vaprecipes.viewModel.CatalogueAdditifVM;

/**
 *
 * @author lechiffre
 */
public class AddAdditifFXMLController {
    private CatalogueAdditifVM catalogueAdditifVM;

    @FXML
    TextField nomAdditifTF;
    
    @FXML
    TextArea descriptionAdditifTA;
    
    
    
    public AddAdditifFXMLController(CatalogueAdditifVM catalogueAdditifVM) {
        this.catalogueAdditifVM = catalogueAdditifVM;
    }
    
    @FXML
    private void onClickAddAdditif(){
        Stage stage = (Stage)nomAdditifTF.getScene().getWindow();
        
        catalogueAdditifVM.addAdditif(nomAdditifTF.getText(), descriptionAdditifTA.getText());
        
        stage.close();
    }
    
    @FXML
    private void onClickAnnuler(){
        Stage stage = (Stage)nomAdditifTF.getScene().getWindow();
       
        stage.close();
    }
}
