package view;

import exceptions_package.SmsHandyException;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import smsHandy.Message;
import smsHandy.PrepaidSmsHandy;
import smsHandy.SmsHandy;
import smsHandy.TariffPlanSmsHandy;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ChatviewController implements Initializable {

    @FXML
    private TableView<Message> messageTable1;
    @FXML
    private TableColumn<Message, String> messageColumn1;
    @FXML
    private TableColumn<Message, String> senderColumn1;
    @FXML
    private TableColumn<Message, String> dateColumn1;


    @FXML
    private TableView<Message> messageTable2;
    @FXML
    private TableColumn<Message, String> messageColumn2;
    @FXML
    private TableColumn<Message, String> senderColumn2;
    @FXML
    private TableColumn<Message, String> dateColumn2;


    @FXML
    private ChoiceBox handy1ChoiceBox;
    @FXML
    private ChoiceBox handy2ChoiceBox;
    @FXML
    private TextField amount1;
    @FXML
    private TextField amount2;
    @FXML
    private ImageView letterImageView;

    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private Label labelTitle1;
    @FXML
    private Label labelTitle2;
    @FXML
    private Label amountLabel1;
    @FXML
    private Label amountLabel2;

    private List<SmsHandy> smsHandyList = new ArrayList<>();
    private String sent = "Sent messages";
    private String received = "Received messages";


    private List<Message> receivedMessagesList = new ArrayList<>();
    private List<Message> sentMessagesList = new ArrayList<>();

    private List<Message> receivedMessagesList2 = new ArrayList<>();
    private List<Message> sentMessagesList2 = new ArrayList<>();

    private TariffPlanSmsHandy tariffPlanSmsHandy;
    private PrepaidSmsHandy prepaidSmsHandy;

    /**
     * Methode macht Hauptfenster auf
     * @param event
     * @throws IOException
     */
    public void changeScreenButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("view.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    /**
     * Diese Methode dient dazu Daten aus der Tabelle von HaupFenster zu bekommen und fuer Initialisieren ChoiceBox
     * @param smsHandies
     */
    public void setSmsHandy(List<SmsHandy> smsHandies) {

        smsHandyList.addAll(smsHandies);
        for (SmsHandy smsHandy : smsHandyList) {
            handy1ChoiceBox.getItems().add(smsHandy.getNumber());
            handy2ChoiceBox.getItems().add(smsHandy.getNumber());

        }
    }

    /**
     * Initialisierung Elementen auf dem Chat-Fenster
     * @param location
     * @param resources
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messageColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("content"));
        senderColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("to"));
        dateColumn1.setCellValueFactory(new PropertyValueFactory<Message, String>("date"));

        messageColumn2.setCellValueFactory(new PropertyValueFactory<Message, String>("content"));
        senderColumn2.setCellValueFactory(new PropertyValueFactory<Message, String>("to"));
        dateColumn2.setCellValueFactory(new PropertyValueFactory<Message, String>("date"));

        //Wenn ein Element in ChoiceBox ausgewaehlt wurde sortieren: ist Objekt Prepaid oder Tariffplan
        handy1ChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < smsHandyList.size(); i++) {
                        if (smsHandyList.get(i) instanceof PrepaidSmsHandy) {
                            prepaidSmsHandy = (PrepaidSmsHandy) smsHandyList.get(i);
                            amountLabel1.setText("100");
                        } else if (smsHandyList.get(i) instanceof TariffPlanSmsHandy){
                            tariffPlanSmsHandy = (TariffPlanSmsHandy) smsHandyList.get(i);
                            amountLabel1.setText("100");
                        }
                    }

                }


        });

        //Wenn ein Element in ChoiceBox ausgewaehlt wurde sortieren: ist Objekt Prepaid oder Tariffplan
        handy2ChoiceBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (int i = 0; i < smsHandyList.size(); i++) {
                    if (smsHandyList.get(i) instanceof PrepaidSmsHandy) {
                        prepaidSmsHandy = (PrepaidSmsHandy) smsHandyList.get(i);
                        amountLabel2.setText("100");
                    } else if (smsHandyList.get(i) instanceof TariffPlanSmsHandy){
                        tariffPlanSmsHandy = (TariffPlanSmsHandy) smsHandyList.get(i);
                        amountLabel2.setText("100");
                    }
                }

            }


        });



        }

    /**
     * Aufladen der Guthaben
     * @throws SmsHandyException
     */
    public void uploadMoney1() throws SmsHandyException {
        if (handy1ChoiceBox.getValue().toString().equals(tariffPlanSmsHandy.getNumber())) {
            int amount = Integer.parseInt(amount1.getText());
            tariffPlanSmsHandy.getProvider().deposit(tariffPlanSmsHandy.getNumber(),tariffPlanSmsHandy.getProvider().getCreditsForSmsHandy(tariffPlanSmsHandy.getNumber())+amount);
            amountLabel1.setText(String.valueOf(tariffPlanSmsHandy.getProvider().getCreditsForSmsHandy(tariffPlanSmsHandy.getNumber())));

        } else if (handy1ChoiceBox.getValue().toString().equals(prepaidSmsHandy.getNumber())) {
            prepaidSmsHandy.deposit(Integer.parseInt(amount1.getText()));
            amountLabel1.setText(String.valueOf(prepaidSmsHandy.getProvider().getCreditsForSmsHandy(prepaidSmsHandy.getNumber())));
        }

    }
    /**
     * Aufladen der Guthaben
     * @throws SmsHandyException
     */
    public void uploadMoney2() throws SmsHandyException {
        if (handy2ChoiceBox.getValue().toString().equals(tariffPlanSmsHandy.getNumber())) {
            int amount = Integer.parseInt(amount2.getText());
            tariffPlanSmsHandy.getProvider().deposit(tariffPlanSmsHandy.getNumber(),tariffPlanSmsHandy.getProvider().getCreditsForSmsHandy(tariffPlanSmsHandy.getNumber())+amount);
            amountLabel2.setText(String.valueOf(tariffPlanSmsHandy.getProvider().getCreditsForSmsHandy(tariffPlanSmsHandy.getNumber())));

        } else if (handy2ChoiceBox.getValue().toString().equals(prepaidSmsHandy.getNumber())) {
            prepaidSmsHandy.deposit(Integer.parseInt(amount2.getText()));
            amountLabel2.setText(String.valueOf(prepaidSmsHandy.getProvider().getCreditsForSmsHandy(prepaidSmsHandy.getNumber())));
        }
    }

    /**
     * Bearbeitung des SMS-Versands
     * @throws SmsHandyException
     */
    public void onPressedSendButton() throws SmsHandyException {

        for (int i = 0; i < smsHandyList.size(); i++) {
            if (handy1ChoiceBox.getValue() == null) {
                getWarning();
                return;
            } else if (textField1.getText().trim().equals("")) {
                getWarning2();
                return;
            } else if (!handy1ChoiceBox.getValue().toString().equals(null)) {

                if (handy1ChoiceBox.getValue().toString().equals(tariffPlanSmsHandy.getNumber())) {
                    String content = textField1.getText();
                    String sender = handy2ChoiceBox.getValue().toString();
                    Date date = new Date();
                    Message message = new Message(content, date, tariffPlanSmsHandy.getNumber(), sender);
                    messageTable1.getItems().add(message);
                    labelTitle1.setText(sent);
                    labelTitle2.setText(received);
                    messageTable2.getItems().add(message);
                    sentMessagesList.add(message);
                    receivedMessagesList2.add(message);
                    for (SmsHandy smsHandy2 : smsHandyList) {
                        if (smsHandy2.getNumber().equals(sender)) {
                            tariffPlanSmsHandy.sendSms(smsHandy2.getNumber(), message.getContent());

                            tariffPlanSmsHandy.payForSms();


                            amountLabel1.setText(String.valueOf(tariffPlanSmsHandy.getRemainingFreeSms()));
                            System.out.println(tariffPlanSmsHandy.getRemainingFreeSms());
                            System.out.println(tariffPlanSmsHandy.getProvider().getCreditsForSmsHandy(tariffPlanSmsHandy.getNumber()));
                        }
                    }

                } else if (handy1ChoiceBox.getValue().toString().equals(prepaidSmsHandy.getNumber())) {
                    String content = textField1.getText();
                    String sender = handy2ChoiceBox.getValue().toString();
                    Date date = new Date();
                    Message message = new Message(content, date, prepaidSmsHandy.getNumber(), sender);
                    messageTable1.getItems().add(message);
                    labelTitle1.setText(sent);
                    labelTitle2.setText(received);
                    messageTable2.getItems().add(message);
                    sentMessagesList.add(message);
                    receivedMessagesList2.add(message);
                    for (SmsHandy smsHandy2 : smsHandyList) {
                        if (smsHandy2.getNumber().equals(sender)) {
                            prepaidSmsHandy.sendSms(smsHandy2.getNumber(), message.getContent());
                            prepaidSmsHandy.payForSms();
                           amountLabel1.setText(String.valueOf(prepaidSmsHandy.getProvider().getCreditsForSmsHandy(prepaidSmsHandy.getNumber())));
                        }
                    }


                }
                final Image image2 = new Image(ChatviewController.class.getResourceAsStream("rotate.png"));
                letterImageView.setImage(image2);

                RotateTransition rotateTransition = new RotateTransition();
                rotateTransition.setDuration(Duration.seconds(2));
                rotateTransition.setAutoReverse(true);
                rotateTransition.setByAngle(360);
                rotateTransition.setNode(letterImageView);
                rotateTransition.play();
                rotateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        letterImageView.setImage(null);
                    }
                });
                textField1.setText("");
            }
            return;

        }
    }



    /**
     * Bearbeitung des SMS-Versands
     * @throws SmsHandyException
     */
    public void onPressedSendButton2() throws SmsHandyException {

        for (int i = 0; i < smsHandyList.size(); i++) {
            if (handy2ChoiceBox.getValue() == null) {
                getWarning();
                return;
            } else if (textField2.getText().trim().equals("")) {
                getWarning2();
                return;
            } else if (!handy2ChoiceBox.getValue().toString().equals(null)) {

                if (handy2ChoiceBox.getValue().toString().equals(tariffPlanSmsHandy.getNumber())) {
                    String content = textField2.getText();
                    String sender = handy1ChoiceBox.getValue().toString();
                    Date date = new Date();
                    Message message = new Message(content, date, tariffPlanSmsHandy.getNumber(), sender);
                    messageTable1.getItems().add(message);
                    labelTitle2.setText(sent);
                    labelTitle1.setText(received);
                    messageTable2.getItems().add(message);
                    sentMessagesList2.add(message);
                    receivedMessagesList.add(message);
                    for (SmsHandy smsHandy2 : smsHandyList) {
                        if (smsHandy2.getNumber().equals(sender)) {
                            tariffPlanSmsHandy.sendSms(smsHandy2.getNumber(), message.getContent());
                            tariffPlanSmsHandy.payForSms();
                            amountLabel2.setText(String.valueOf(tariffPlanSmsHandy.getRemainingFreeSms()));
                        }
                    }

                } else if (handy2ChoiceBox.getValue().toString().equals(prepaidSmsHandy.getNumber())) {
                    String content = textField2.getText();
                    String sender = handy1ChoiceBox.getValue().toString();
                    Date date = new Date();
                    Message message = new Message(content, date, prepaidSmsHandy.getNumber(), sender);
                    messageTable2.getItems().add(message);
                    labelTitle1.setText(sent);
                    labelTitle2.setText(received);
                    messageTable1.getItems().add(message);
                    sentMessagesList2.add(message);
                    receivedMessagesList.add(message);
                    for (SmsHandy smsHandy2 : smsHandyList) {
                        if (smsHandy2.getNumber().equals(sender)) {
                            prepaidSmsHandy.sendSms(smsHandy2.getNumber(), message.getContent());
                            prepaidSmsHandy.payForSms();
                            amountLabel2.setText(String.valueOf(prepaidSmsHandy.getProvider().getCreditsForSmsHandy(prepaidSmsHandy.getNumber())));
                        }
                    }


                }
                final Image image2 = new Image(ChatviewController.class.getResourceAsStream("rotate.png"));
                letterImageView.setImage(image2);

                RotateTransition rotateTransition = new RotateTransition();
                rotateTransition.setDuration(Duration.seconds(2));
                rotateTransition.setAutoReverse(true);
                rotateTransition.setByAngle(360);
                rotateTransition.setNode(letterImageView);
                rotateTransition.play();
                rotateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent arg0) {
                        letterImageView.setImage(null);
                    }
                });

            }
            textField1.setText("");

            return;

        }
    }

    /**
     * Wenn Radiobutton Sent message gecklickt wurde, dann zeigen wir nur entsprechendes SMS
     */
    public void onSentButtonClicked(){
        messageTable1.getItems().clear();
        labelTitle1.setText(sent);
        for (Message message: sentMessagesList){
            messageTable1.getItems().add(message);
        }
    }

    /**
     * Wenn Radiobutton Received message gecklickt wurde, dann zeigen wir nur entsprechendes SMS
     */
    public void onReceivedButtonClicked(){
        messageTable1.getItems().clear();
        labelTitle1.setText(received);
        for (Message message: receivedMessagesList){
            messageTable1.getItems().add(message);
        }
    }

    /**
     * Wenn Radiobutton Sent message gecklickt wurde, dann zeigen wir nur entsprechendes SMS
     */
    public void onSentButtonClicked2(){
        messageTable2.getItems().clear();
        labelTitle2.setText(sent);
        for (Message message: sentMessagesList2){
            messageTable2.getItems().add(message);
        }
    }

    /**
     * Wenn Radiobutton Received message gecklickt wurde, dann zeigen wir nur entsprechendes SMS
     */
    public void onReceivedButtonClicked2(){
        messageTable2.getItems().clear();
        labelTitle2.setText(received);
        for (Message message: receivedMessagesList2){
            messageTable2.getItems().add(message);
        }
    }

    /**
     * Wenn keine Handy ausgewaehlt wurde, dann wird diese Methode Meldung geben mit Alert mach so
     */
    public void getWarning(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Select phone!");
        alert.setContentText("Before sending a message, you must select the sender and receiver");

        alert.showAndWait();
    }

    /**
     * Wenn man kein Text geschrieben hat, dann wird diese Methode warnen schreib was!
     */
    public void getWarning2(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning Dialog");
        alert.setHeaderText("Please type message!");
        alert.setContentText("You can'nt send empty message!");

        alert.showAndWait();
    }
}
