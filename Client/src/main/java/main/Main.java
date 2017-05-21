package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rabbit.Send;
import service.IFlightService;
import service.ILoginService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;

/**
 * Created by Dragos on 3/29/2017.
 */
public class Main extends Application
{
    private FXMLLoader loader;
    private FXMLLoader loader2;
    private Stage primaryStage;
    private AnchorPane rootLayout1;
    private TabPane rootLayout2;

    private Scene scene1;
    private Scene scene2;
    private LoginClient controlLogin;

    private static void execute(String sql)
    {

    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage primaryStage) throws RemoteException
    {
        this.primaryStage = primaryStage;
        loader = new FXMLLoader();
        loader2 = new FXMLLoader();
        Send.EXCHANGE_NAME = "topic_logs";
        try
        {
            String pathToFxml = "client/src/main/resources/LoginWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader.setLocation(fxmlUrl);

            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:ClientBean.xml");
            ILoginService server=(ILoginService)factory.getBean("loginService");
            System.out.println("Obtained a reference to remote chat server");

            controlLogin = new LoginClient(server);
            loader.setController(controlLogin);
            rootLayout1 = loader.load();
            scene1 = new Scene(rootLayout1);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        try
        {
            String pathToFxml = "client/src/main/resources/MainWindow.fxml";
            URL fxmlUrl = new File(pathToFxml).toURI().toURL();
            loader2.setLocation(fxmlUrl);

            ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:ClientBean.xml");
            IFlightService server=(IFlightService)factory.getBean("flightService");
            System.out.println("Obtained a reference to remote chat server");

            MainClient controlMain = new MainClient(server,this);
            loader2.setController(controlMain);
            rootLayout2 = loader2.load();
            scene2 = new Scene(rootLayout2);
        }

        catch (IOException ex)
        {
            ex.printStackTrace();
        }

        LoginView();
    }

    public void authenticated()
    {
        MainView();
    }

    public void logOut() throws RemoteException
    {
        LoginView();
    }

    public void LoginView() throws RemoteException
    {
        primaryStage.setScene(scene1);
        primaryStage.show();
        controlLogin.initManager(this);
    }

    private void MainView()
    {
        primaryStage.setScene(scene2);
        primaryStage.show();
    }
}
