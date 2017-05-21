package main;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Flight;
import rabbit.Recv;
import rabbit.Send;
import service.IFlightService;

import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/*
 * Created by Dragos on 3/30/2017.
 */
public class MainClient extends UnicastRemoteObject implements Initializable, Serializable
{
    @FXML private TableView<Flight> mainTable;
    @FXML private Button logoutButton;
    @FXML private Button buyButton;
    @FXML private TableView<Flight> buyTable;
    @FXML private TextField addClient;
    @FXML private TextArea addAdress;
    @FXML private TextField addTickets;
    @FXML private TextField searchDestination;
    @FXML private TextField searchDeparture;
    @FXML private Button searchButton;
    @FXML private TableView<Flight> searchTable;
    @FXML private TableColumn<Flight, String> destination;
    @FXML private TableColumn<Flight, Date> datehour;
    @FXML private TableColumn<Flight, String> airport;
    @FXML private TableColumn<Flight, Integer> freeseats;

    @FXML private TableColumn<Flight, String> destination2;
    @FXML private TableColumn<Flight, Date> datehour2;
    @FXML private TableColumn<Flight, String> airport2;
    @FXML private TableColumn<Flight, Integer> freeseats2;

    @FXML private TableColumn<Flight, String> destination3;
    @FXML private TableColumn<Flight, Date> datehour3;
    @FXML private TableColumn<Flight, Integer> freeseats3;
    @FXML private TableColumn<Flight, String> airport3;

    final Main loginManager;
    private IFlightService server;
    private ObservableList zboruri;
    private List<Flight> lista = new ArrayList<Flight>();


    public MainClient(IFlightService server,final Main loginManager) throws RemoteException
    {
        this.server = server;
        this.loginManager = loginManager;
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        try {
            lista = server.getAllFlights();
            this.zboruri = FXCollections.observableArrayList(lista);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

            zboruri.addListener(new ListChangeListener()
            {
                @Override
                public void onChanged(Change change)
                {

                }
            });

            destination.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
            datehour.setCellValueFactory(new PropertyValueFactory<Flight, Date>("datehour"));
            airport.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
            freeseats.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("freeseats"));


            destination2.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
            airport2.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
            datehour2.setCellValueFactory(new PropertyValueFactory<Flight, Date>("datehour"));
            freeseats2.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("freeseats"));

            destination3.setCellValueFactory(new PropertyValueFactory<Flight, String>("destination"));
            airport3.setCellValueFactory(new PropertyValueFactory<Flight, String>("airport"));
            datehour3.setCellValueFactory(new PropertyValueFactory<Flight, Date>("datehour"));
            freeseats3.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("freeseats"));

            /*
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            refreshTable();
                        }
                    });
                }
            }, 0, 5000);
            */

            Recv.init(message -> {
                System.out.println("Need to refresh flights!");
                Platform.runLater(() -> {
                    refreshTable();
                });
            });


            mainTable.getItems().setAll(FXCollections.observableArrayList(zboruri));
            buyTable.getItems().setAll(FXCollections.observableArrayList(zboruri));

    }

    private void refreshTable()
    {
        List<Flight> listanoua = new ArrayList<>();
        try {
            listanoua = server.getAllFlights();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        zboruri.setAll(listanoua);
        mainTable.getItems().setAll(FXCollections.observableArrayList(zboruri));
    }

    @FXML
    public void setLogoutAction() throws RemoteException
    {
        loginManager.logOut();
    }

    @FXML
    public void setRefreshAction()
    {
        try
        {
            List<Flight> retur = server.getAllFlights();
            zboruri.setAll(retur);
            buyTable.getItems().setAll(FXCollections.observableArrayList(zboruri));
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void setSearchAction()
    {
                String depart = searchDeparture.getText();
                String destin = searchDestination.getText();
                if (depart.isEmpty() && destin.isEmpty())
                {
                    showErrorMessage("Complete at least one of the search fields");
                }
                else if (!depart.isEmpty() && !destin.isEmpty())
                {
                    try {
                        List<Flight> fin = server.findByDestinationAndDate(destin, depart);
                        searchTable.getItems().setAll(fin);
                        buyTable.getItems().setAll(fin);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else if (!depart.isEmpty() && destin.isEmpty())
                {
                    try {
                        List<Flight> fin = server.findByDate(depart);
                        searchTable.getItems().setAll(fin);
                        buyTable.getItems().setAll(fin);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else if (depart.isEmpty() && !destin.isEmpty())
                {
                    try {
                        List<Flight> fin = server.findByDestination(destin);
                        searchTable.getItems().setAll(fin);
                        buyTable.getItems().setAll(fin);
                    }
                    catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
    }

    @FXML
    public void setBuyAction()
    {
                String client = addClient.getText();
                String nrticket = addTickets.getText();
                String address = addAdress.getText();
                Flight fly = buyTable.getSelectionModel().getSelectedItem();
                int id = fly.getFlightId();
                if (client.isEmpty() || nrticket.isEmpty() || address.isEmpty())
                {
                    showErrorMessage("Fill all the fields with the necessary information");
                }
                else
                    {
                        try {
                            int ok = server.buyFlight(id, client, Integer.parseInt(nrticket), address);
                        if (ok == 1)
                        {
                            Send.notifyChange();
                            showMessage(Alert.AlertType.CONFIRMATION,"Buy","The ticket was bought with success");
                            refreshTable();
                            buyTable.getItems().setAll(FXCollections.observableArrayList(zboruri));

                        }
                        else
                        {
                            showErrorMessage("The ticket couldn't be bought");
                        }
                        }
                        catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
    }
    private static void showMessage(Alert.AlertType type, String header, String text)
    {
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
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
