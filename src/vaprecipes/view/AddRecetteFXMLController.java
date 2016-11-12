/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaprecipes.view;

import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
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
public class AddRecetteFXMLController implements Initializable{
    
    private CatalogueRecetteVM catalogueRecetteVM;
    private CatalogueAromeVM catalogueAromeVM;
    private CatalogueAdditifVM catalogueAdditifVM;
    private RecetteVM recetteVM;
    
    private String dragContent;
    
    private boolean edition;
    private int index;
    
   
    @FXML
    TextField nomRecette, nicotinePG, nicotineVG;
    
    @FXML
    Label qteTotaleRecette, proportionVG, proportionPG, titleAddRecette;
    
    @FXML
    Button addButtonRecette;
    
    @FXML
    Slider proportionBaseSlider;
    
    @FXML
    ListView lvArome, lvAdditif;
    @FXML
    TextField searchAromeTF, searchAdditifTF;
            
    
    @FXML
    ImageView poubelleImageRecette, fioleImage;
    
    @FXML
    ListView lvAromeDispo, lvAdditifDispo;
    
    @FXML
    Label aromePourcentage, basePourcentage, additifPourcentage;
    
    
            
    
    public AddRecetteFXMLController(CatalogueRecetteVM catalogueRecetteVM, CatalogueAromeVM catalogueAromeVM, CatalogueAdditifVM catalogueAdditifVM, 
                                    boolean  edition, RecetteVM recetteOriginale, int index) {
        this.catalogueRecetteVM = catalogueRecetteVM;
        this.catalogueAromeVM = catalogueAromeVM;
        this.catalogueAdditifVM = catalogueAdditifVM;
        this.edition = edition;
        this.index = index;
        
        recetteVM = new RecetteVM("", 20, 50, 50, 0, 0);

        if(edition){
            intializeRecetteToEdit(recetteOriginale);
        }
        
    }
    
    private void intializeRecetteToEdit(RecetteVM recetteOriginal){
        this.recetteVM.setNom(recetteOriginal.getNom());
        this.recetteVM.setQteTotale(recetteOriginal.getQteTotale());
        this.recetteVM.setProportionPG(recetteOriginal.getProportionPG());
        this.recetteVM.setTauxNicotinePG(recetteOriginal.getTauxNicotinePG());
        this.recetteVM.setProportionVG(recetteOriginal.getProportionVG());
        this.recetteVM.setTauxNicotineVG(recetteOriginal.getTauxNicotineVG());
        this.recetteVM.setTauxNicotine(recetteOriginal.getTauxNicotine());
        this.recetteVM.setPoucentageBase(recetteOriginal.getPoucentageBase());
        this.recetteVM.setPourcentageArome(recetteOriginal.getPourcentageArome());
        this.recetteVM.setPourcentageAdditif(recetteOriginal.getPourcentageAdditif());
        for (Object a : recetteOriginal.getListeAromeRecette()){
             String couleur = String.format("#%02X%02X%02X",
                                       (int)( ((AromeVM)a).getCouleur().getRed() * 255),
                                       (int)( ((AromeVM)a).getCouleur().getGreen() * 255),
                                       (int)( ((AromeVM)a).getCouleur().getBlue() * 255));
             
            recetteVM.addArome(new AromeVM(((AromeVM)a).getNom(), couleur, ((AromeVM)a).isFlavorTabac()), ((AromeRecetteVM)a).getQuantite());
        }
        
        for (Object a : recetteOriginal.getListeAdditifRecette()){
             
            recetteVM.addAdditif(new AdditifVM(((AdditifVM)a).getNom(), ((AdditifVM)a).getDescription()), ((AdditifRecetteVM)a).getQuantite());
        }
        
       
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        if(edition){
             titleAddRecette.setText("Modification");
            addButtonRecette.setText("Modifier");
        }
        poubelleImageRecette.setImage(new Image("/image/poubelleClose.png"));
        fioleImage.setImage(new Image("/image/fioleOff.png"));
        
        initializeDataBinding();
        initializeBindingLvArome();
        initializeBindingLvAdditif();
        initializeBindingLvAromeDispo();
        initializeBindingLvAdditifDispo();
        
        initializeListenerPourcentageData();
        
    }
    
    private void initializeDataBinding(){
        nomRecette.textProperty().bindBidirectional(recetteVM.nomProperty());
        qteTotaleRecette.textProperty().bind(recetteVM.qteTotaleProperty().asString());
        proportionPG.textProperty().bindBidirectional(recetteVM.proportionPGProperty(), new NumberStringConverter());
        proportionVG.textProperty().bindBidirectional(recetteVM.proportionVGProperty(), new NumberStringConverter());
        
        proportionBaseSlider.valueProperty().bindBidirectional(recetteVM.proportionPGProperty());
        
        proportionPG.textProperty().bindBidirectional(proportionBaseSlider.valueProperty(), new StringConverter() {
            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }

            @Override
            public String toString(Object object) {
                return String.format("%.0f", object);
            }
        });
        proportionVG.textProperty().bind(Bindings.subtract(100, recetteVM.proportionPGProperty()).asString());
        
        nicotinePG.textProperty().bindBidirectional(recetteVM.tauxNicotinePGProperty(), new StringConverter() {
            @Override
            public String toString(Object object) {
                return object.toString();
            }

            @Override
            public Object fromString(String string) {
                if (!string.matches("\\d*")) {
                    nicotinePG.setText(string.replaceAll("[^\\d]", ""));
                    return string.replaceAll("[^\\d]", "");
                }
                
                
                return Integer.parseInt(string);
            }
        });
        nicotineVG.textProperty().bindBidirectional(recetteVM.tauxNicotineVGProperty(), new StringConverter() {
            @Override
            public String toString(Object object) {
                return object.toString();
            }

            @Override
            public Object fromString(String string) {
                if (!string.matches("\\d*")) {
                    nicotineVG.setText(string.replaceAll("[^\\d]", ""));
                    return string.replaceAll("[^\\d]", "");
                }
                
               
                return Integer.parseInt(string);
            }
        });
    }
    
    private void initializeBindingLvArome(){
        lvArome.itemsProperty().bind(recetteVM.listeAromeRecetteProperty());
        initializeCellFactoryLvArome();
       
        
    }
    private void initializeCellFactoryLvArome(){
        lvArome.setCellFactory(param -> {
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
            
            /**
            * Initialisation du DnD
            */
            cell.setOnDragDetected(event -> {

                if(!cell.isEmpty()){
                    dragContent = "AromeRecette";
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
            poubelleImageRecette.setOnDragOver(event -> {
               if(event.getGestureSource() != poubelleImageRecette && event.getDragboard().hasString()){
                   if(dragContent.equals("AromeRecette") || dragContent.equals("AdditifRecette")){
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                   }

               }
               event.consume();

            });


            /**
             * Etat de la cible quand le DnD entre dans la zone de drop
             */
            poubelleImageRecette.setOnDragEntered(event -> {
                if(event.getGestureSource() != poubelleImageRecette && event.getDragboard().hasString()){
                    if(dragContent.equals("AromeRecette") || dragContent.equals("AdditifRecette")){
                        poubelleImageRecette.setImage(new Image("/image/poubelleOpen.png"));
                    }
                }

            });

            /**
             * Etat de la cible lorsque que l'objet ne le survole plus
             */
            poubelleImageRecette.setOnDragExited(event -> {
               if(dragContent.equals("AromeRecette") || dragContent.equals("AdditifRecette")){
                    poubelleImageRecette.setImage(new Image("/image/poubelleClose.png"));
                }
            });

            poubelleImageRecette.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;

                if(db.hasString()){
                    success = true;
                    event.setDropCompleted(success);
                    event.consume();
                    
                    if(dragContent.equals("AromeRecette")){
                        removeArome(Integer.parseInt(db.getString()));
                    }
                    if(dragContent.equals("AdditifRecette")){
                        removeAdditif(Integer.parseInt(db.getString()));
                    }
                }


            });
            
            
            return cell;
        });
    }
    
    private void initializeBindingLvAdditif(){
        lvAdditif.itemsProperty().bind(recetteVM.listeAdditifRecetteProperty());
        initializeCellFactoryLvAdditif();
    }
    private void initializeCellFactoryLvAdditif(){
        lvAdditif.setCellFactory(param -> {
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
            
            /**
            * Initialisation du DnD
            */
            cell.setOnDragDetected(event -> {

                if(!cell.isEmpty()){
                    dragContent = "AdditifRecette";
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvAdditif.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    event.consume();

                }

            });
            
            return cell;
        });
    }
    
    private void removeArome(int index){
        recetteVM.removeArome(index);
    }
    private void addArome(int index){
        double quantite = askQuantiteDialogArome(false);
        
        if(quantite > 0){
            recetteVM.addArome((AromeVM)catalogueAromeVM.getListeAromeVM().get(index), quantite);
        }
        else if(quantite == -1){
            erreurQteDialog();
        }
        
        
    }
    
    private void removeAdditif(int index){
        recetteVM.removeAdditif(index);
    }
    private void addAdditif(int index){
        double quantite = askQuantiteDialogAdditif(false);
        
        if(quantite > 0){
            recetteVM.addAdditif((AdditifVM)catalogueAdditifVM.getListeAdditifVM().get(index), quantite);
        }
        else if(quantite == -1){
            erreurQteDialog();
        }
        
    }
    
    
    private void initializeBindingLvAromeDispo(){
        lvAromeDispo.itemsProperty().bindBidirectional(catalogueAromeVM.listeAromeVMProperty());
        initializeCellFactoryLvAromeDispo();
        initializeSearchAromeTF();
        
    }
    private void initializeCellFactoryLvAromeDispo(){
        
        lvAromeDispo.setCellFactory(param -> {
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
                    dragContent = "Arome";
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvAromeDispo.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    event.consume();

                }
                
            });
               
            
            return cell;
        });
           
    }
    
    /**
     * To call the method searchArome to research an aroma
     */
    private void initializeSearchAromeTF(){
        searchAromeTF.textProperty().addListener((o, old, newV) ->{
            catalogueAromeVM.reinitializeAromeListProperty();
            searchArome((String)old, (String)newV);
            lvAromeDispo.getSelectionModel().selectFirst();
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
        
        for(Object entree : lvAromeDispo.getItems()){
            boolean match = true;
            
            String entryText = ((AromeVM)entree).getNom();
            
            if(!entryText.toUpperCase().contains(value)){
                match = false;
            }
            if(match){
                subListObs.add((AromeVM)entree);
            }
        }
        lvAromeDispo.setItems(subListObs);
    }
    
    private void initializeBindingLvAdditifDispo(){
        lvAdditifDispo.itemsProperty().bindBidirectional(catalogueAdditifVM.listeAdditifVMProperty());
        initializeCellFactoryLvAdditifDispo();
        initializeSearchAdditifTF();
    }
    private void initializeCellFactoryLvAdditifDispo(){
        lvAdditifDispo.setCellFactory(param -> {
            
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
                    dragContent = "Additif";
                    Dragboard db = cell.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cc = new ClipboardContent();
                    cc.putString(lvAdditifDispo.getSelectionModel().selectedIndexProperty().getValue().toString());
                    db.setContent(cc);
                    event.consume();
                    
                }
                
            });
            
            
            /**
             * ?????
             */
            fioleImage.setOnDragOver(event -> {
               if(event.getGestureSource() != fioleImage && event.getDragboard().hasString()){
                    if(dragContent.equals("Additif") || dragContent.equals("Arome")){
                        
                    
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                    }
               }
               event.consume();
               
            });
            
            
            /**
             * Etat de la cible quand le DnD entre dans la zone de drop
             */
            fioleImage.setOnDragEntered(event -> {
                if(event.getGestureSource() != fioleImage && event.getDragboard().hasString()){
                    if(dragContent.equals("Additif") || dragContent.equals("Arome")){
                        fioleImage.setImage(new Image("/image/fioleOn.png"));
                    }
                }
     
            });
            
            /**
             * Etat de la cible lorsque que l'objet ne le survole plus
             */
            fioleImage.setOnDragExited(event -> {
                if(dragContent.equals("Additif") || dragContent.equals("Arome")){
                    
                    fioleImage.setImage(new Image("/image/fioleOff.png"));
                }
            });
            
            fioleImage.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                boolean success = false;      
 
                    if(db.hasString()){

                        success = true;
                        event.setDropCompleted(success);
                        event.consume();
                        if(dragContent.equals("Additif")){
                            addAdditif(Integer.parseInt(db.getString()));
                        }
                        if(dragContent.equals("Arome")){
                            addArome(Integer.parseInt(db.getString()));
                        }

                    }
                
            });
            
            return cell;
        });
    }
            
     /**
     * Same method initializeSearchAromeTF
     */
    private void initializeSearchAdditifTF(){
        searchAdditifTF.textProperty().addListener((o, old, newV) ->{
            catalogueAdditifVM.reinitializeAdditifListProperty();
            searchAdditif((String)old, (String)newV);
            lvAdditifDispo.getSelectionModel().selectFirst();
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
        
        for(Object entree : lvAdditifDispo.getItems()){
            boolean match = true;
            
            String entryText = ((AdditifVM)entree).getNom();
            
            if(!entryText.toUpperCase().contains(value)){
                match = false;
            }
            if(match){
                subListObs.add((AdditifVM)entree);
            }
        }
        lvAdditifDispo.setItems(subListObs);
    }
    
    public void initializeListenerPourcentageData(){
       recetteVM.listeAromeRecetteProperty().addListener((o, old, newV)->recetteVM.updatePourcentageData());
       recetteVM.listeAdditifRecetteProperty().addListener((o, old, newV) -> recetteVM.updatePourcentageData());
       
       basePourcentage.textProperty().bind(recetteVM.poucentageBaseProperty().asString());
       aromePourcentage.textProperty().bind(recetteVM.pourcentageAromeProperty().asString());
       additifPourcentage.textProperty().bind(recetteVM.pourcentageAdditifProperty().asString());
       
       
    }
    
    @FXML
    private void onClickAddRecette(){
        
        
        recetteVM.updateTauxNicotine();
        if(edition){
           
            catalogueRecetteVM.editRecette(recetteVM, index);
            
        }
        else{
            catalogueRecetteVM.addRecette(recetteVM);
        }
        Stage stage = (Stage)nomRecette.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void onClickAnnuler(){
        Stage stage = (Stage)nomRecette.getScene().getWindow();
        stage.close();
    }
    
    private double askQuantiteDialogArome(boolean error){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Quantité de l'arôme ?");
        dialog.setHeaderText("Vous devez indiquer en quel quantité vous souhaitez ajouter cet arôme");
        if(error){
            dialog.setContentText("Quantité (en mL) ATTENTION VOUS DEVEZ SAISIR UN ENTIER OU UN NOMBRE DECIMAL POSITIF :");

        }else{
            dialog.setContentText("Quantité (en mL) :");

        }

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            double qteTotaleTheorique, quantite = 0;
            

            try{
                quantite = Double.parseDouble(result.get());
                qteTotaleTheorique = recetteVM.getSommeQteAromeQteAdditif() + quantite;
                
                if(quantite <= 0){
                    quantite = askQuantiteDialogArome(true);
                }
                else if(qteTotaleTheorique > (double)recetteVM.getQteTotale()){
                    return -1;
                }
            }catch(NumberFormatException e){
                quantite = askQuantiteDialogArome(true);
            }
            return quantite;
        }
        return 0;
    }
    
    private double askQuantiteDialogAdditif(boolean error){
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Quantité de l'additif ?");
        dialog.setHeaderText("Vous devez indiquer en quel quantité vous souhaitez ajouter cet additif");
        if(error){
            dialog.setContentText("Quantité (en mL) ATTENTION VOUS DEVEZ SAISIR UN ENTIER OU UN NOMBRE DECIMAL POSITIF :");

        }else{
            dialog.setContentText("Quantité (en mL) :");

        }

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            double qteTotaleTheorique, quantite = 0;

            try{
                quantite = Double.parseDouble(result.get());
                qteTotaleTheorique = recetteVM.getSommeQteAromeQteAdditif() + quantite;

                
                if(quantite <= 0){
                    quantite = askQuantiteDialogArome(true);
                } 
                else if(qteTotaleTheorique > (double)recetteVM.getQteTotale()){
                    return -1;
                }
            }catch(NumberFormatException e){
                quantite = askQuantiteDialogAdditif(true);
            }
            
            return quantite;
        }
        return 0;
        
    }
    
    private void erreurQteDialog(){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("ERREUR");
        alert.setHeaderText("ATTENTION : La quantité d'arôme et d'additif ne peut PAS dépasser la quantité totale !!!");
  

        alert.showAndWait();
    }
}
