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
import javafxmvc.model.dao.ItensProdutoDAO;
import javafxmvc.model.dao.OrdemServicoDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Produto;

public class FXMLCadastrosProdutosController implements Initializable {
    
    @FXML
    private TableView<Produto> tableViewProdutos;
    
    @FXML
    private TableColumn<Produto, String> tableColumnCodigoProduto;
    
    @FXML
    private TableColumn<Produto, String> tableColumnNomeProduto;
    
    @FXML
    private Button buttonInserir;
    
    @FXML
    private Button buttonAlterar;
    
    @FXML
    private Button buttonRemover;
    
    @FXML
    private Label labelProdutoCodigo;
    
    @FXML
    private Label labelProdutoNome;
    
    @FXML
    private Label labelProdutoEstoque;
    
    @FXML
    private Label labelProdutoPreco;
    
    private List<Produto> listProdutos;
    private ObservableList<Produto> observableListProdutos;
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        carregarTableViewProdutos();
        selecionarItemTableViewProdutos(null);
        
        tableViewProdutos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> selecionarItemTableViewProdutos(newValue));
    }

    public void carregarTableViewProdutos() {
        tableColumnCodigoProduto.setCellValueFactory(new PropertyValueFactory<>("codProduto"));
        tableColumnNomeProduto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        
        listProdutos = produtoDAO.listar();
        
        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewProdutos.setItems(observableListProdutos); 
    }
    
    public void selecionarItemTableViewProdutos(Produto produto) {
        if (produto != null) {
            labelProdutoCodigo.setText(String.valueOf(produto.getCodProduto()));
            labelProdutoNome.setText(produto.getNome());
            labelProdutoEstoque.setText(String.valueOf(produto.getEstoque()));
            labelProdutoPreco.setText(String.valueOf(produto.getPreco()));
        } else {
            labelProdutoCodigo.setText("");
            labelProdutoNome.setText("");
            labelProdutoEstoque.setText("");
            labelProdutoPreco.setText("");
        }
    }
    
    @FXML
    public void handleButtonInserir() throws IOException {
        Produto produto = new Produto();
        boolean buttonConfirmarClicked = showFXMLCadastrosProdutosDialog(produto);
        if(buttonConfirmarClicked) {
            produtoDAO.inserir(produto);
            carregarTableViewProdutos();
        }
    }
    
    @FXML
    public void handleButtonAlterar() throws IOException {
        Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produto != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrosProdutosDialog(produto);
            if (buttonConfirmarClicked) {
                produtoDAO.alterar(produto);
                carregarTableViewProdutos();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um produto na tabela!");
             cssAlert(alert);
            alert.show();
        }
    }
    
    @FXML
    public void handleButtonRemover() throws IOException {
        Produto produto = tableViewProdutos.getSelectionModel().getSelectedItem();
        if (produto != null) {
            if(produtoDAO.buscarUtilizados(produto)){
            
                produtoDAO.remover(produto);
                carregarTableViewProdutos();
            
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("O produto não pode ser removido!");
                cssAlert(alert);
                alert.show();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, selecione um produto na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    public boolean showFXMLCadastrosProdutosDialog(Produto produto) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrosClientesDialogController.class.getResource("/javafxmvc/view/FXMLCadastrosProdutosDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Produtos");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(null);
        dialogStage.initOwner(this.tableViewProdutos.getScene().getWindow());
        
        FXMLCadastrosProdutosDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setProduto(produto);
        
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
