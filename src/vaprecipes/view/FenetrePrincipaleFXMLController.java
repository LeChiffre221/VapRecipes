/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vaprecipes.model.Arome;
import vaprecipes.viewModel.AdditifRecetteVM;
import vaprecipes.viewModel.AdditifVM;
import vaprecipes.viewModel.AromeRecetteVM;
import vaprecipes.viewModel.AromeVM;
import vaprecipes.viewModel.CatalogueAdditifVM;
import vaprecipes.viewModel.CatalogueAromeVM;
import vaprecipes.viewModel.CatalogueRecetteVM;
import vaprecipes.viewModel.RecetteVM;

/**
 *
 * @author lechiffre
 */
public class FenetrePrincipaleFXMLController implements Initializable {
    
    private CatalogueAromeVM catalogueAromeVM;
    private CatalogueAdditifVM catalogueAdditifVM;
    private CatalogueRecetteVM catalogueRecetteVM;

    
    
    @FXML
    Label gestionAromeLabel;
    
    @FXML
    Label nomRecette, qteTotaleRecette, proportionPG, proportionVG, tauxNicotine ;
    
    @FXML
    TextField nomAromeTF, nomAdditifTF;
    
    @FXML
    TextArea descriptionAdditifTA;
    
    @FXML
    ColorPicker colorAromeCP;
    
    @FXML
    CheckBox flavorTabacCB;
    
    @FXML
    ListView lvArome, lvAdditif, lvRecette, lvAromeRecette, lvAdditifRecette;
    @FXML
    TextField searchAromeTF, searchAdditifTF, searchRecetteTF;
    
    @FXML
    ImageView poubelleImageArome, poubelleImageAdditif, poubelleImageRecette;
    
    @FXML
    BorderPane detailAdditif, detailArome;
    @FXML
    SplitPane detailRecette;
    
    
           
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        tabGestionArome();
        tabGestionAdditif();
        
        tabGestionRecette();
        
        
        load();
    }   
    
    private void tabGestionRecette(){
        
        catalogueRecetteVM = new CatalogueRecetteVM();
        
        initializeListViewRecette();
        initializeCellFactoryRecette();
        initializeCellFactoryAromeRecette();
        initializeCellFactoryAdditifRecette();
        
        
        
        
        
        poubelleImageRecette.setImage(new Image("/image/poubelleClose.png"));
    }
    
    private void initializeListViewRecette(){
        lvRecette.itemsProperty().bindBidirectional(catalogueRecetteVM.listeRecetteVMProperty());
        lvRecette.getSelectionModel().selectedItemProperty().addListener((o, old, newV) -> updateRecetteDetail((RecetteVM)old, (RecetteVM)newV));
        initializeSearchRecetteTF();
    }
    
    /**
     * To call the method searchArome to research an aroma
     */
    private void initializeSearchRecetteTF(){
        searchRecetteTF.textProperty().addListener((o, old, newV) ->{
            catalogueRecetteVM.reinitializeRecetteListProperty();
            searchRecette((String)old, (String)newV);
            lvRecette.getSelectionModel().selectFirst();
        });
    }
    
    /**
     * This method allow to filter a listView
     * 
     * @param old
     * @param newV 
     */
    private void searchRecette(String old, String newV){
        String value = newV.toUpperCase();
        ObservableList<RecetteVM> subListObs = FXCollections.observableArrayList();
        
        for(Object entree : lvRecette.getItems()){
            boolean match = true;
            
            String entryText = ((RecetteVM)entree).getNom();
            
            if(!entryText.toUpperCase().contains(value)){
                match = false;
            }
            if(match){
                subListObs.add((RecetteVM)entree);
            }
        }
        lvRecette.setItems(subListObs);
    }
    
    private void updateRecetteDetail(RecetteVM old, RecetteVM newV){
        if(old != null){
            nomRecette.textProperty().unbind();
            qteTotaleRecette.textProperty().unbind();
            proportionPG.textProperty().unbind();
            proportionVG.textProperty().unbind();
            tauxNicotine.textProperty().unbind();
            lvAdditifRecette.itemsProperty().unbind();
            lvAromeRecette.itemsProperty().unbind();
            
                 
        }
        if(newV == null){
            nomRecette.setText("");
            qteTotaleRecette.setText("0");
            proportionPG.setText("0");
            proportionVG.setText("0");
            tauxNicotine.setText("0.0");
            lvAdditifRecette.setItems(null);
            
            lvAromeRecette.setItems(null);
            detailRecette.setDisable(true);
            
        }
        else{
            nomRecette.textProperty().bind(newV.nomProperty());
            qteTotaleRecette.textProperty().bind(newV.qteTotaleProperty().asString());
            proportionPG.textProperty().bind(newV.proportionPGProperty().asString());
            proportionVG.textProperty().bind(newV.proportionVGProperty().asString());
            tauxNicotine.textProperty().bind(newV.tauxNicotineProperty().asString("%.2f"));
            lvAromeRecette.itemsProperty().bind(newV.listeAromeRecetteProperty());
            lvAdditifRecette.itemsProperty().bind(newV.listeAdditifRecetteProperty());
            detailRecette.setDisable(false);
            
            
                           
        }
    }
    
    private void initializeCellFactoryRecette(){
        lvRecette.setCellFactory(param -> {
            
            ListCell<RecetteVM> cell = new ListCell<RecetteVM>(){

                @Override
                protected void updateItem(RecetteVM item, boolean empty) {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if(!empty){
                        textProperty().bind(item.nomProperty());
                    }
                    else{
                        textProperty().unbind();
                        setText("");

                    }
                }    
            };
                
            /**
             * Initialisation du DnD
             */
            cell.setOnDragDetected(event -> {

                if(!cell.isEmpty()){
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvRecette.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    event.consume();
                    
                }
                
            });
            
            
            /**
             * ?????
             */
            poubelleImageRecette.setOnDragOver(event -> {
               if(event.getGestureSource() != poubelleImageRecette && event.getDragboard().hasString()){
                   event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                   
               }
               event.consume();
               
            });
            
            
            /**
             * Etat de la cible quand le DnD entre dans la zone de drop
             */
            poubelleImageRecette.setOnDragEntered(event -> {
                if(event.getGestureSource() != poubelleImageRecette && event.getDragboard().hasString()){
                    poubelleImageRecette.setImage(new Image("/image/poubelleOpen.png"));
                }
     
            });
            
            /**
             * Etat de la cible lorsque que l'objet ne le survole plus
             */
            poubelleImageRecette.setOnDragExited(event -> {
                poubelleImageRecette.setImage(new Image("/image/poubelleClose.png"));
            });
            
            poubelleImageRecette.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                
                if(db.hasString()){
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                    removeRecette(Integer.parseInt(db.getString()));
                    
                }
                
                
            });
            
            return cell;
        });
    }
    private void initializeCellFactoryAromeRecette(){
        lvAromeRecette.setCellFactory(param -> {
            ListCell<AromeRecetteVM> cell = new ListCell<AromeRecetteVM>(){
               
                @Override
                protected void updateItem(AromeRecetteVM item, boolean empty) {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    Circle circle = new Circle(10);

                    if(!empty){
                        textProperty().bind(item.nomProperty().concat(" "+item.getQuantite()+" mL"));

                        item.couleurProperty().addListener((o, old, newV)-> {
                            circle.setFill(Color.rgb((int)(item.getCouleur().getRed()*255.0), (int)(item.getCouleur().getGreen()*255.0), (int)(item.getCouleur().getBlue()*255.0)));

                        });
                        circle.setFill(Color.rgb((int)(item.getCouleur().getRed()*255.0), (int)(item.getCouleur().getGreen()*255.0), (int)(item.getCouleur().getBlue()*255.0)));
                        setGraphic(circle);

                    }
                    else{
                        textProperty().unbind();
                        setText("");
                        setGraphic(null);
                    }

                }  
            };
            return cell;
        });
    }
    private void initializeCellFactoryAdditifRecette(){
        lvAdditifRecette.setCellFactory(param -> {
            ListCell<AdditifRecetteVM> cell = new ListCell<AdditifRecetteVM>(){
               
                @Override
                protected void updateItem(AdditifRecetteVM item, boolean empty) {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.                   
                    
                    if(!empty){
                        textProperty().bind(item.nomProperty().concat(" "+item.getQuantite()+" mL"));
                    }
                    else{
                        textProperty().unbind();
                        setText("");
                    }
                } 
            };
            return cell;
        });
    }
   
    private void removeRecette(int index){
        catalogueRecetteVM.removeRecette(index);
    }
    
    @FXML
    private void onClickAddRecette() throws IOException{
        int sizeListRecetteTmp = catalogueRecetteVM.sizeListeRecette();
        
        catalogueAdditifVM.reinitializeAdditifListProperty();
        catalogueAromeVM.reinitializeAromeListProperty();
        
        Stage stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddRecetteFXML.fxml"));
        loader.setController(new AddRecetteFXMLController(catalogueRecetteVM, catalogueAromeVM, catalogueAdditifVM, false, null, 0));
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        stage.setTitle("Ajouter une recette");
        
        Stage stage1 = (Stage)searchRecetteTF.getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stage1);
        stage.showAndWait();
        
        if(sizeListRecetteTmp != catalogueRecetteVM.sizeListeRecette()){
            lvRecette.getSelectionModel().selectLast();
        }
        
        catalogueAdditifVM.reinitializeAdditifListProperty();
        catalogueAromeVM.reinitializeAromeListProperty();
    }
    
    @FXML
    private void onClickEditRecette()  throws IOException{
        int sizeListRecetteTmp = catalogueRecetteVM.sizeListeRecette();
        
        catalogueAdditifVM.reinitializeAdditifListProperty();
        catalogueAromeVM.reinitializeAromeListProperty();
        
        Stage stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddRecetteFXML.fxml"));
        
        RecetteVM recetteAEditer = (RecetteVM) lvRecette.getSelectionModel().getSelectedItem();
        int index = lvRecette.getSelectionModel().getSelectedIndex();
        loader.setController(new AddRecetteFXMLController(catalogueRecetteVM, catalogueAromeVM, catalogueAdditifVM, true, recetteAEditer, index));
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        stage.setTitle("Modifier une recette");
        
        Stage stage1 = (Stage)searchRecetteTF.getScene().getWindow();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(stage1);
        stage.showAndWait();
        
        if(sizeListRecetteTmp != catalogueRecetteVM.sizeListeRecette()){
            lvRecette.getSelectionModel().selectLast();
        }
        else{
            lvRecette.getSelectionModel().select(index);
        }
        
        catalogueAdditifVM.reinitializeAdditifListProperty();
        catalogueAromeVM.reinitializeAromeListProperty();
    }
    
    
    //===========================================================================
    //==============================GESTION ADDITIF ===============================
    //===========================================================================
    
    private void tabGestionAdditif(){
        catalogueAdditifVM = new CatalogueAdditifVM();
        
        initializeListViewAdditif();
        initializeCellFactoryAdditif();
        initializeSearchAdditifTF();
        
       
        
        poubelleImageAdditif.setImage(new Image("/image/poubelleClose.png"));
    }
    
    private void initializeListViewAdditif(){
        lvAdditif.itemsProperty().bindBidirectional(catalogueAdditifVM.listeAdditifVMProperty());
        lvAdditif.getSelectionModel().selectedItemProperty().addListener((o, old, newV)
                                                        -> updateAdditifDetail((AdditifVM)old, (AdditifVM)newV));
    }
    
    private void updateAdditifDetail(AdditifVM old, AdditifVM newV){
        if(old != null){
            nomAdditifTF.textProperty().unbindBidirectional(old.nomProperty());
            descriptionAdditifTA.textProperty().unbindBidirectional(old.descriptionProperty());
                 
        }
        if(newV == null){
            nomAdditifTF.setText("");
            descriptionAdditifTA.setText("");
            detailAdditif.setDisable(true);
            
        }
        else{
            nomAdditifTF.textProperty().bindBidirectional(newV.nomProperty());
            descriptionAdditifTA.textProperty().bindBidirectional(newV.descriptionProperty());
            detailAdditif.setDisable(false);
                           
        }
        
    } 
    private void initializeCellFactoryAdditif(){
        lvAdditif.setCellFactory(param -> {
            
            ListCell<AdditifVM> cell = new ListCell<AdditifVM>(){
               
                @Override
                protected void updateItem(AdditifVM item, boolean empty) {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    if(!empty){
                        textProperty().bind(item.nomProperty());
                    }
                    else{
                        textProperty().unbind();
                        setText("");

                    }

                }
            };
            
            
            
            /**
             * Initialisation du DnD
             */
            cell.setOnDragDetected(event -> {

                if(!cell.isEmpty()){
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvAdditif.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    event.consume();
                    
                }
                
            });
            
            
            /**
             * ?????
             */
            poubelleImageAdditif.setOnDragOver(event -> {
               if(event.getGestureSource() != poubelleImageAdditif && event.getDragboard().hasString()){
                   event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                   
               }
               event.consume();
               
            });
            
            
            /**
             * Etat de la cible quand le DnD entre dans la zone de drop
             */
            poubelleImageAdditif.setOnDragEntered(event -> {
                if(event.getGestureSource() != poubelleImageAdditif && event.getDragboard().hasString()){
                    poubelleImageAdditif.setImage(new Image("/image/poubelleOpen.png"));
                }
     
            });
            
            /**
             * Etat de la cible lorsque que l'objet ne le survole plus
             */
            poubelleImageAdditif.setOnDragExited(event -> {
                poubelleImageAdditif.setImage(new Image("/image/poubelleClose.png"));
            });
            
            poubelleImageAdditif.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                
                if(db.hasString()){
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                    removeAdditif(Integer.parseInt(db.getString()));
                    
                }
                
                
            });
                        
            
            return cell;
            
        });
    }
    
    /**
     * To remove an additif with his index in the liste
     * @param index 
     */
    private void removeAdditif(int index){
        
            catalogueAdditifVM.removeAdditif(index);
            
            lvAdditif.getSelectionModel().select(index);
            
            if(lvAdditif.getSelectionModel().selectedItemProperty() == null){
                lvAdditif.getSelectionModel().selectLast();

            }
        
    }
    
    /**
     * Same method initializeSearchAromeTF
     */
    private void initializeSearchAdditifTF(){
        searchAdditifTF.textProperty().addListener((o, old, newV) ->{
            catalogueAdditifVM.reinitializeAdditifListProperty();
            searchAdditif((String)old, (String)newV);
            lvAdditif.getSelectionModel().selectFirst();
        });
    }
    
    /**
     * Same method searchArome
     * @param old
     * @param newV 
     */
    private void searchAdditif(String old, String newV){
        String value = newV.toUpperCase();
        ObservableList<AdditifVM> subListObs = FXCollections.observableArrayList();
        
        for(Object entree : lvAdditif.getItems()){
            boolean match = true;
            
            String entryText = ((AdditifVM)entree).getNom();
            
            if(!entryText.toUpperCase().contains(value)){
                match = false;
            }
            if(match){
                subListObs.add((AdditifVM)entree);
            }
        }
        lvAdditif.setItems(subListObs);
    }
    
    /**
     * Catch the event on the button "AddArome" and open a window to add a flavor
     * @throws IOException 
     */
    @FXML
    private void onClickAddAdditif() throws IOException{
        
        int sizeListAdditifTmp = catalogueAdditifVM.sizeListeAdditif();
        
        Stage stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddAdditifFXML.fxml"));
        loader.setController(new AddAdditifFXMLController(catalogueAdditifVM));
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        stage.setTitle("Ajouter un additif");
        stage.showAndWait();
        
        if(sizeListAdditifTmp != catalogueAdditifVM.sizeListeAdditif()){
            lvAdditif.getSelectionModel().selectLast();
        }
        
        
    }
    
    //===========================================================================
    //==============================GESTION AROME ===============================
    //===========================================================================
    
    private void tabGestionArome(){
        
        catalogueAromeVM = new CatalogueAromeVM();
         
        initializeListViewArome();
        
        initializeCellFactoryArome();
        
       
        
        initializeSearchAromeTF();
        
        poubelleImageArome.setImage(new Image("/image/poubelleClose.png"));
        
      
        
    }
    
    /**
     * Initialize listView Bindind
     */
    private void initializeListViewArome(){
        lvArome.itemsProperty().bindBidirectional(catalogueAromeVM.listeAromeVMProperty());
        lvArome.getSelectionModel().selectedItemProperty().addListener((o, old, newV)
                                                        -> updateAromeDetail((AromeVM)old, (AromeVM)newV));
    }
    
    
    /**
     * To initialize the state of each cell in the "AromeListView"
     */
    private void initializeCellFactoryArome(){
        
        lvArome.setCellFactory(param -> {
            
            ListCell<AromeVM> cell = new ListCell<AromeVM>(){
                
                

                @Override
                protected void updateItem(AromeVM item, boolean empty) {
                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

                    Circle circle = new Circle(10);

                    if(!empty){
                        textProperty().bind(item.nomProperty());

                        item.couleurProperty().addListener((o, old, newV)-> {
                            circle.setFill(Color.rgb((int)(item.getCouleur().getRed()*255.0), (int)(item.getCouleur().getGreen()*255.0), (int)(item.getCouleur().getBlue()*255.0)));

                        });
                        circle.setFill(Color.rgb((int)(item.getCouleur().getRed()*255.0), (int)(item.getCouleur().getGreen()*255.0), (int)(item.getCouleur().getBlue()*255.0)));
                        setGraphic(circle);

                    }
                    else{
                        textProperty().unbind();
                        setText("");
                        setGraphic(null);
                    }

                }
            };
            
            
            
            /**
             * Initialisation du DnD
             */
            cell.setOnDragDetected(event -> {

                if(!cell.isEmpty()){
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvArome.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    
                    event.consume();
                    
                }
                
            });
            
            
            /**
             * ?????
             */
            poubelleImageArome.setOnDragOver(event -> {
               if(event.getGestureSource() != poubelleImageArome && event.getDragboard().hasString()){
                   event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                   
               }
               event.consume();
               
            });
            
            
            /**
             * Etat de la cible quand le DnD entre dans la zone de drop
             */
            poubelleImageArome.setOnDragEntered(event -> {
                if(event.getGestureSource() != poubelleImageArome && event.getDragboard().hasString()){
                    poubelleImageArome.setImage(new Image("/image/poubelleOpen.png"));
                }
     
            });
            
            /**
             * Etat de la cible lorsque que l'objet ne le survole plus
             */
            poubelleImageArome.setOnDragExited(event -> {
                poubelleImageArome.setImage(new Image("/image/poubelleClose.png"));
            });
            
            poubelleImageArome.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;
                
                if(db.hasString()){
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                    removeArome(Integer.parseInt(db.getString()));
                    
                }
                
                
            });
                        
            
            return cell;
            
        });
    }
   
    /**
     * To update the detail of the flavor select in the "AromeListView"
     * @param old
     * @param newV 
     */
    private void updateAromeDetail(AromeVM old, AromeVM newV){
        if(old != null){
            nomAromeTF.textProperty().unbindBidirectional(old.nomProperty());
            colorAromeCP.valueProperty().unbindBidirectional(old.couleurProperty());
            flavorTabacCB.selectedProperty().unbindBidirectional(old.flavorTabacProperty());      
        }
        if(newV == null){
            nomAromeTF.setText("");
            colorAromeCP.valueProperty().setValue(Color.web("#FFFFFF"));
            flavorTabacCB.selectedProperty().setValue(false);   

            detailArome.setDisable(true);
            
        }
        else{
            nomAromeTF.textProperty().bindBidirectional(newV.nomProperty());
            colorAromeCP.valueProperty().bindBidirectional(newV.couleurProperty());
            flavorTabacCB.selectedProperty().bindBidirectional(newV.flavorTabacProperty());   
            detailArome.setDisable(false);
            
        }
    }
    
    /**
     * To call the method searchArome to research an aroma
     */
    private void initializeSearchAromeTF(){
        searchAromeTF.textProperty().addListener((o, old, newV) ->{
            catalogueAromeVM.reinitializeAromeListProperty();
            searchArome((String)old, (String)newV);
            lvArome.getSelectionModel().selectFirst();
        });
    }
    
    /**
     * This method allow to filter a listView
     * 
     * @param old
     * @param newV 
     */
    private void searchArome(String old, String newV){
        String value = newV.toUpperCase();
        ObservableList<AromeVM> subListObs = FXCollections.observableArrayList();
        
        for(Object entree : lvArome.getItems()){
            boolean match = true;
            
            String entryText = ((AromeVM)entree).getNom();
            
            if(!entryText.toUpperCase().contains(value)){
                match = false;
            }
            if(match){
                subListObs.add((AromeVM)entree);
            }
        }
        lvArome.setItems(subListObs);
    }
    
    /**
     * To Remove an aroma with his index in the list
     * @param index 
     */
    private void removeArome(int index){
        
            catalogueAromeVM.removeArome(index);
            
            lvArome.getSelectionModel().select(index);
            
            if(lvArome.getSelectionModel().selectedItemProperty() == null){
                lvArome.getSelectionModel().selectLast();

            }
        
    }
    
    /**
     * Catch the event on the button "AddArome" and open a window to add a flavor
     * @throws IOException 
     */
    @FXML
    private void onClickAddArome() throws IOException{
        int sizeListAromeTmp = catalogueAromeVM.sizeListeArome();
        
        
        Stage stage = new Stage();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AddAromeFXML.fxml"));
            loader.setController(new AddAromeFXMLController(catalogueAromeVM));
        Scene scene = new Scene(loader.load());
        
        stage.setScene(scene);
        stage.setTitle("Ajouter un arôme");
        stage.showAndWait();
        
        if(sizeListAromeTmp != catalogueAromeVM.sizeListeArome()){
            lvArome.getSelectionModel().selectLast();
        }
        
    }
    
    
    //===========================================================================
    //==============================GESTION GENERALE ===============================
    //===========================================================================
    
    
    
    
    /**
     * Catch the event when a click is detected on saveButton 
     */
    @FXML
    private void onClickSave(){
        
        catalogueAromeVM.save();
        catalogueAdditifVM.save();
        catalogueRecetteVM.save();
        
    }
    
    @FXML
    private void onClickExit(){
        Stage stage = (Stage)nomAromeTF.getScene().getWindow();
        stage.close();
    }
    
    private void load(){
        catalogueAromeVM.load();
        catalogueAdditifVM.load();
        catalogueRecetteVM.load();
        
        lvArome.getSelectionModel().selectFirst();
        lvAdditif.getSelectionModel().selectFirst();
        lvRecette.getSelectionModel().selectFirst();
    }
          
    
}
