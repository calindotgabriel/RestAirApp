package main;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import service.ILoginService;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;

/*
 * Created by Dragos on 3/29/2017.
 */
public class LoginClient extends UnicastRemoteObject implements Initializable, Serializable {

    private ILoginService server;
    @FXML private TextField user;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;

    public LoginClient(ILoginService server) throws RemoteException
    {
        this.server = server;
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        passwordField.setPromptText("Your password");
        user.setPromptText("Your username");
    }

    public void initManager(final Main loginManager) throws RemoteException
    {
        loginButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String userName = user.getText();
                    String password = passwordField.getText();
                    if (userName == null) {
                        showErrorMessage("Dati un username");
                    } else if (password == null) {
                        showErrorMessage("Dati o parola");
                    } else {
                        String response = server.login(userName, password);
                        if (response != null && response.compareTo("error") == 0) {
                            showErrorMessage("Username sau parola invalida");
                            initManager(loginManager);
                        } else {
                            loginManager.authenticated();
                        }
                    }
                }
                catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void showMessage(Alert.AlertType type)
    {
        Alert message=new Alert(type);
        message.setHeaderText("Succes");
        message.setContentText("Succes");
        message.showAndWait();
    }

    private static void showErrorMessage(String text)
    {
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}
