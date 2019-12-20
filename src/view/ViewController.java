package view;

import exceptions_package.SmsHandyException;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import smsHandy.PrepaidSmsHandy;
import smsHandy.Provider;
import smsHandy.SmsHandy;
import smsHandy.TariffPlanSmsHandy;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ViewController implements Initializable {

    @FXML private TableView<SmsHandy> smsHandyTable;
    @FXML private TableColumn<SmsHandy, String> typeNameColumn;
    @FXML private TableColumn<SmsHandy, String> modelColumn;
    @FXML private TableColumn<SmsHandy, String> numberColumn;
    @FXML private TableColumn<SmsHandy, String> providerColumn;

    private List<SmsHandy>prepaid = new ArrayList<>();


    private Provider o2 = new Provider("O2");
    private Provider vodafone = new Provider("Vodafone");


    @FXML private ChoiceBox typeChoiceBox;
    @FXML private ChoiceBox deleteProvChoiceBox;
    @FXML private TextField modelTextField;
    @FXML private TextField numberTextField;
    @FXML private ChoiceBox providerChoiceBox;
    @FXML private TextField newProviderTextfield;
    @FXML private ChoiceBox changeProviderChoiceBox;


    @FXML private Label resulLabel;


    /**
     * Methode als erste aufgerufen, fuer Initialisierung ChoiceBoxes und fuer Verknuepfung TableView
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String defaulValue="Choose provider";

        try {
            smsHandyTable.setItems(getHandies());
        } catch (SmsHandyException e) {
            e.printStackTrace();
        }

        deleteProvChoiceBox.getItems().add(o2.getName());
        deleteProvChoiceBox.getItems().add(vodafone.getName());
        deleteProvChoiceBox.getItems().add(defaulValue);
        deleteProvChoiceBox.setValue(defaulValue);

        changeProviderChoiceBox.getItems().add(defaulValue);
        changeProviderChoiceBox.getItems().add(o2.getName());
        changeProviderChoiceBox.getItems().add(vodafone.getName());
        changeProviderChoiceBox.setValue(defaulValue);

        providerChoiceBox.getItems().add(o2.getName());
        providerChoiceBox.getItems().add(vodafone.getName());


        typeNameColumn.setCellValueFactory(new PropertyValueFactory<SmsHandy, String>("type"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<SmsHandy, String>("modelName"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<SmsHandy, String>("number"));
        providerColumn.setCellValueFactory(new PropertyValueFactory<SmsHandy, String>("providerName"));

    }


    /**
     * Methode fuer das Erstellen neues Handy
     * @throws SmsHandyException
     */
    public void putNewHandy() throws SmsHandyException {
        if (typeChoiceBox.getValue().equals("tariffPlan")){
            Provider provider = new Provider(providerChoiceBox.getValue().toString());
            TariffPlanSmsHandy tariffPlanSmsHandy = new TariffPlanSmsHandy(modelTextField.getText(),numberTextField.getText(), provider.getName(),provider);
            smsHandyTable.getItems().add(tariffPlanSmsHandy);


        }else {
            Provider provider = new Provider(providerChoiceBox.getValue().toString());
            PrepaidSmsHandy prepaidSmsHandy = new PrepaidSmsHandy(modelTextField.getText(),numberTextField.getText(),provider.getName(),provider);
            smsHandyTable.getItems().add(prepaidSmsHandy);
            prepaid.add(prepaidSmsHandy);

        }

    }

    /**
     * Methode fuer das Erstellen Provider
     */
    public void putNewProvider(){
        Provider provider = new Provider(newProviderTextfield.getText());
        resulLabel.setText("You have successfully created new provider with name: "+provider.getName()+"!");
        deleteProvChoiceBox.getItems().add(provider.getName());
        providerChoiceBox.getItems().add(provider.getName());
        changeProviderChoiceBox.getItems().add(provider.getName());
        newProviderTextfield.setText("");

    }

    /**
     * Methode fuer das Loeschen Provider
     */
    public void deleteProvider(){
        smsHandyTable.getItems().parallelStream().filter(smsHandy1 -> smsHandy1.getProviderName().equals(deleteProvChoiceBox.getValue())).forEach(smsHandy1 -> smsHandyTable.getItems().remove(smsHandy1));
        deleteProvChoiceBox.getItems().remove(deleteProvChoiceBox.getValue());
        providerChoiceBox.getItems().remove(deleteProvChoiceBox.getValue());
        changeProviderChoiceBox.getItems().remove(deleteProvChoiceBox.getValue());
    }

    /**
     * Methode fuer das Loeschen des Handys
     * @throws RuntimeException
     */
    public void deleteHandy() throws RuntimeException{
        ObservableList <SmsHandy> selectedRows, smsHandies;
        smsHandies = smsHandyTable.getItems();
        selectedRows= smsHandyTable.getSelectionModel().getSelectedItems();
        for(SmsHandy smsHandy: selectedRows){
            smsHandies.remove(smsHandy);
        }
    }

    /**
     * Methode fuer das Wechseln Provider
     */
    public void changeProvider(){
        String provider = (String) changeProviderChoiceBox.getValue();

        ObservableList <SmsHandy> selectedRows;
        selectedRows= smsHandyTable.getSelectionModel().getSelectedItems();
        selectedRows.get(0).setProvider(provider);
        smsHandyTable.refresh();
    }

    /**
     * Methode fuer das Oeffnen Chat Fenster
     * @param event
     * @throws IOException
     */
    public void changeSceneToChatView(ActionEvent event) throws IOException
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("chat_view.fxml"));
        Parent tableViewParent = loader.load();

        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        ChatviewController controller = loader.getController();
        controller.setSmsHandy(smsHandyTable.getItems());

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     *
     * @return ObservableList fuer TableView
     * @throws SmsHandyException
     */
    public ObservableList<SmsHandy> getHandies() throws SmsHandyException {

        PrepaidSmsHandy huawei = new PrepaidSmsHandy("Huawei P20 pro","01-01-01-01", o2.getName(),o2);
        TariffPlanSmsHandy iphone = new TariffPlanSmsHandy("Iphone X","02-02-02-02", vodafone.getName(),vodafone);

        ObservableList<SmsHandy> handies = FXCollections.observableArrayList();
        handies.add(huawei);
        handies.add(iphone);
        typeChoiceBox.getItems().add(huawei.getType());
        typeChoiceBox.getItems().add(iphone.getType());
        prepaid.add(huawei);


        return handies;
    }


}
