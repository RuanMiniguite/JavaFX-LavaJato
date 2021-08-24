package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;

public class FXMLCadastrosClientesController implements Initializable {
    
    @FXML
    private TableView<Cliente> tableViewClientes;
    
    @FXML
    private TableColumn<Cliente, String> tableColumnCLienteNome;
    
    @FXML
    private TableColumn<Cliente, String> tableColumnCLienteCpf;
    
    @FXML
    private Button buttonInserir;
    
    @FXML
    private Button buttonAlterar;
    
    @FXML
    private Button buttonRemover;
    
    @FXML
    private Label labelClienteCodigo;
    
    @FXML
    private Label labelClienteNome;
    
    @FXML
    private Label labelClienteCpf;
    
    @FXML
    private Label labelClienteTelefone;
    
    private List<Cliente> listClientes;
    private ObservableList<Cliente> observableListClientes;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO.setConnection(connection);
        carregarTableViewClientes();
        selecionarItemTableViewClientes(null);
        
        tableViewClientes.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewClientes(newValue));
    }

    public void carregarTableViewClientes() {
        tableColumnCLienteNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnCLienteCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        listClientes = clienteDAO.listar();
        
        observableListClientes = FXCollections.observableArrayList(listClientes);
        tableViewClientes.setItems(observableListClientes); 
    }
    
    public void selecionarItemTableViewClientes(Cliente cliente) {
        if (cliente != null) {
            labelClienteCodigo.setText(String.valueOf(cliente.getCodCliente()));
            labelClienteNome.setText(cliente.getNome());
            labelClienteCpf.setText(cliente.getCpf());
            labelClienteTelefone.setText(cliente.getTelefone());
        } else {
            labelClienteCodigo.setText("");
            labelClienteNome.setText("");
            labelClienteCpf.setText("");
            labelClienteTelefone.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Cliente cliente = new Cliente();
        boolean buttonConfirmarClicked = showFXMLCadastrosClientesDialog(cliente);
        if(buttonConfirmarClicked) {
            clienteDAO.inserir(cliente);
            carregarTableViewClientes();
        }
    }
    
    @FXML
    public void hanbleButtonAlterar() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrosClientesDialog(cliente);
            if (buttonConfirmarClicked) {
                clienteDAO.alterar(cliente);
                carregarTableViewClientes();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um cliente na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    @FXML
    public void hanbleButtonRemover() throws IOException {
        Cliente cliente = tableViewClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            clienteDAO.remover(cliente);
            carregarTableViewClientes();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um cliente na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    public boolean showFXMLCadastrosClientesDialog(Cliente cliente) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrosClientesDialogController.class.getResource("/javafxmvc/view/FXMLCadastrosClientesDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Clientes");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(null);
        dialogStage.initOwner(this.tableViewClientes.getScene().getWindow());
        
        FXMLCadastrosClientesDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCliente(cliente);
        
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
        
        return controller.isButtonConfirmarClicked();
       
    }
    
    
    public void cssAlert(Alert alert){
        DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
               getClass().getResource("/javafxmvc/view/Tema_dark.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
    }
    
}
