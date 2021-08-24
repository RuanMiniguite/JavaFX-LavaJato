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
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Funcionario;

public class FXMLCadastrosFuncionariosController implements Initializable {
    
    @FXML
    private TableView<Funcionario> tableViewFuncionarios;
    
    @FXML
    private TableColumn<Funcionario, String> tableColumnFuncionarioNome;
    
    @FXML
    private TableColumn<Funcionario, String> tableColumnFuncionarioCpf;
    
    @FXML
    private Button buttonInserir;
    
    @FXML
    private Button buttonAlterar;
    
    @FXML
    private Button buttonRemover;
    
    @FXML
    private Label labelFuncionarioCodigo;
    
    @FXML
    private Label labelFuncionarioNome;
    
    @FXML
    private Label labelFuncionarioCpf;
    
    @FXML
    private Label labelFuncionarioTelefone;
    
    private List<Funcionario> listFuncionarios;
    private ObservableList<Funcionario> observableListFuncionarios;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        funcionarioDAO.setConnection(connection);
        carregarTableViewFuncionarios();
        selecionarItemTableViewFuncionarios(null);
        
        tableViewFuncionarios.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewFuncionarios(newValue));
    }

    public void carregarTableViewFuncionarios() {
        tableColumnFuncionarioNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnFuncionarioCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        
        listFuncionarios = funcionarioDAO.listar();
        
        observableListFuncionarios = FXCollections.observableArrayList(listFuncionarios);
        tableViewFuncionarios.setItems(observableListFuncionarios); 
    }
    
    public void selecionarItemTableViewFuncionarios(Funcionario funcionario) {
        if (funcionario != null) {
            labelFuncionarioCodigo.setText(String.valueOf(funcionario.getCodFuncionario()));
            labelFuncionarioNome.setText(funcionario.getNome());
            labelFuncionarioCpf.setText(funcionario.getCpf());
            labelFuncionarioTelefone.setText(funcionario.getTelefone());
        } else {
            labelFuncionarioCodigo.setText("");
            labelFuncionarioNome.setText("");
            labelFuncionarioCpf.setText("");
            labelFuncionarioTelefone.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Funcionario funcionario = new Funcionario();
        boolean buttonConfirmarClicked = showFXMLCadastrosFuncionariosDialog(funcionario);
        if(buttonConfirmarClicked) {
            funcionarioDAO.inserir(funcionario);
            carregarTableViewFuncionarios();
        }
    }
    
    @FXML
    public void hanbleButtonAlterar() throws IOException {
        Funcionario funcionario = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrosFuncionariosDialog(funcionario);
            if (buttonConfirmarClicked) {
                funcionarioDAO.alterar(funcionario);
                carregarTableViewFuncionarios();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um funcionario na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    @FXML
    public void hanbleButtonRemover() throws IOException {
        Funcionario funcionario = tableViewFuncionarios.getSelectionModel().getSelectedItem();
        if (funcionario != null) {
            if(funcionarioDAO.buscarFucionario(funcionario)){
            
            funcionarioDAO.remover(funcionario);
            carregarTableViewFuncionarios();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("O Funcionario não pode ser removido!");
                cssAlert(alert);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um funcionario na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    public boolean showFXMLCadastrosFuncionariosDialog(Funcionario funcionario) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrosFuncionariosDialogController.class.getResource("/javafxmvc/view/FXMLCadastrosFuncionariosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Funcionarios");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(null);
        dialogStage.initOwner(this.tableViewFuncionarios.getScene().getWindow());
        
        FXMLCadastrosFuncionariosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFuncionario(funcionario);
        
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
