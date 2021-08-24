package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxmvc.model.dao.FuncionarioDAO;

import javafxmvc.model.dao.ItensServicoDAO;
import javafxmvc.model.dao.ServicoDAO;
import javafxmvc.model.dao.OrdemServicoDAO;
import javafxmvc.model.dao.ItensProdutoDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.ItensServico;
import javafxmvc.model.domain.Servico;
import javafxmvc.model.domain.OrdemServico;
import javafxmvc.model.domain.ItensProduto;
import javafxmvc.model.domain.Produto;



public class FXMLTelaServicoController implements Initializable {

    //Tabela Servico
    @FXML
    private TableView<OrdemServico> tableViewS;
    @FXML
    private TableColumn<OrdemServico, Integer> tableColumnSCod;    
    @FXML
    private TableColumn<OrdemServico, Integer> tableColumnSData;     
    @FXML
    private TableColumn<OrdemServico, Integer> tableColumnSCliente;
     
    //Grid
    @FXML
    private Label LabelSCliente;
    @FXML
    private Label LabelSData;
    @FXML
    private Label LabelSValor;  
    @FXML
    private Label LabelSValorPago;
    @FXML
    private Label LabelSPago;
    @FXML
    private Label LabelSDesconto;
    @FXML
    private Label LabelSFuncionario;
    @FXML
    private Button Inserir;
    @FXML
    private Button Alterar;
    @FXML
    private Button Remover;

    //Tabela Produto
    @FXML
    private TableView<ItensProduto> tableViewSProduto;
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnSPreco;    
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnSProduto;   
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnSQtd;
    
    //Tabela Serviço
    
    @FXML
    private TableView<ItensServico> tableViewSServico;
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnSServico;    
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnSQtdServico;   
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnSPrecoServico;
    
    
    //AUXILIARES
    private List<OrdemServico> listOrdemServico;
    private List<ItensProduto> listItensProdutos;
    private List<ItensServico> listItensServicos;
    
    private ObservableList<OrdemServico> observableListOrdemServico;
    private ObservableList<ItensProduto> observableListItensProdutos;
    private ObservableList<ItensServico> observableListItensServico;
    
    //BANCO DE DADOS
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
    private final ItensServicoDAO itensServicoDAO = new ItensServicoDAO();
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ItensProdutoDAO itensProdutoDAO = new ItensProdutoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
       
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ordemServicoDAO.setConnection(connection);
        itensProdutoDAO.setConnection(connection);
        itensServicoDAO.setConnection(connection);
        
        carregartableviewServico();
        selecionarItemTableViewServico(null);
        
        tableViewS.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTableViewServico(newValue));
    }    
    
    public void carregartableviewServico(){
        tableColumnSCod.setCellValueFactory(new PropertyValueFactory <>("codOrdemServico"));
        tableColumnSData.setCellValueFactory(new PropertyValueFactory<>("data"));
        tableColumnSCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));
        
        listOrdemServico = ordemServicoDAO.listar();
        
        observableListOrdemServico = FXCollections.observableArrayList(listOrdemServico);
        tableViewS.setItems(observableListOrdemServico);
    
    }
    
    public void selecionarItemTableViewServico(OrdemServico ordemServico){
        if(ordemServico != null){
            LabelSCliente.setText(String.valueOf(ordemServico.getCliente()));                
            LabelSValor.setText(String.format("%.2f", ordemServico.getTotal()));
            LabelSFuncionario.setText(String.valueOf(ordemServico.getFuncionario()));
            LabelSDesconto.setText(String.format("%.2f", ordemServico.getDesconto()));
            LabelSValorPago.setText(String.format("%.2f",  ordemServico.getTotal() - ordemServico.getDesconto()));
            
            
            if(ordemServico.getPago() == true){
                LabelSPago.setText("Sim"); 
            }else{
                LabelSPago.setText("Não");
            }
            
            selecionarItemTableViewServicoProduto(ordemServico);

        }else{
            LabelSCliente.setText("");
            LabelSFuncionario.setText(""); 
            LabelSPago.setText("");
            LabelSValor.setText("");        
            LabelSDesconto.setText("");
            LabelSValorPago.setText("");
        }
    }
    
    public void selecionarItemTableViewServicoProduto(OrdemServico ordemServico){
        tableColumnSProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnSQtd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnSPreco.setCellValueFactory(new  PropertyValueFactory<>("preco"));
        
        
        tableColumnSServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        tableColumnSQtdServico.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnSPrecoServico.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        
        listItensProdutos = itensProdutoDAO.listarPorServicoProduto(ordemServico);
        observableListItensProdutos = FXCollections.observableArrayList(listItensProdutos);
        tableViewSProduto.setItems(observableListItensProdutos);
        
        listItensServicos = itensServicoDAO.listarPorServicoServicos(ordemServico);
        observableListItensServico = FXCollections.observableArrayList(listItensServicos);
        tableViewSServico.setItems(observableListItensServico);
    }
    
    
    @FXML
    public void handleButtonInserirServico() throws IOException, SQLException {
        OrdemServico ordemServico = new OrdemServico();
        List<ItensServico> listItensServico = new ArrayList<>();
        List<ItensProduto> listOrdemServicoProdutos = new ArrayList<>();
        ordemServico.setItensServico(listItensServico);
        ordemServico.setItensProduto(listOrdemServicoProdutos);
        
        boolean buttonConfirmarClick = showFXMLTelaServicoDialog(ordemServico);
        
        if(buttonConfirmarClick){
            try {
                connection.setAutoCommit(false);
                ordemServicoDAO.setConnection(connection);
                ordemServicoDAO.inserir(ordemServico);
                
                itensServicoDAO.setConnection(connection);
                itensProdutoDAO.setConnection(connection);
                produtoDAO.setConnection(connection);
                servicoDAO.setConnection(connection);
                funcionarioDAO.setConnection(connection);
                
                for(ItensServico lisItensServico : ordemServico.getItensServico()){
                    Servico lavagem = lisItensServico.getServico();
                    lisItensServico.setOrdemservico(ordemServicoDAO.buscarUltimoServico());
                    itensServicoDAO.inserir(lisItensServico);
                }
                
                for(ItensProduto lisOrdemServicoProdutos : ordemServico.getItensProduto()){
                    Produto produto = lisOrdemServicoProdutos.getProduto();
                    lisOrdemServicoProdutos.setOrdemservico(ordemServicoDAO.buscarUltimoServico());
                    itensProdutoDAO.inserir(lisOrdemServicoProdutos);
                    produto.setEstoque(produto.getEstoque() - lisOrdemServicoProdutos.getQuantidade());
                    produtoDAO.alterar(produto);
                }
                
                connection.commit();
                carregartableviewServico();
            } catch (SQLException ex) {
                try {
                    connection.rollback();
                } catch (SQLException ex1) {
                    Logger.getLogger(FXMLTelaServicoController.class.getName()).log(Level.SEVERE, null, ex1);
                }
                Logger.getLogger(FXMLTelaServicoController.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
    }
    
    public void handleButtonRemoverServico() throws IOException, SQLException{
        OrdemServico ordemServico = tableViewS.getSelectionModel().getSelectedItem();
        if(ordemServico != null){
            connection.setAutoCommit(false);
            
            ordemServicoDAO.setConnection(connection);
            itensServicoDAO.setConnection(connection);
            produtoDAO.setConnection(connection);
            servicoDAO.setConnection(connection);
            
            for(ItensServico lisItensServico : ordemServico.getItensServico()){
                itensServicoDAO.remover(lisItensServico);
            }
            
            for(ItensProduto itensproduto : ordemServico.getItensProduto()){
                Produto produto = itensproduto.getProduto();
                produto.setEstoque(produto.getEstoque() + itensproduto.getQuantidade());
                produtoDAO.alterar(produto);
                itensProdutoDAO.remover(itensproduto);
            }
            
            ordemServicoDAO.remover(ordemServico);
            connection.commit();
            carregartableviewServico();
            
        }else{
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setContentText("Escolha um Serviço na Tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    public void handleButtonAlterarServico() throws IOException, SQLException{
        if(tableViewS.getSelectionModel().selectedItemProperty().getValue() != null){ 
            
            OrdemServico ordemServico = tableViewS.getSelectionModel().selectedItemProperty().getValue();
            
            boolean buttonConfirmarClick = showFXMLTelaServicoDialog(ordemServico);
            
            if(buttonConfirmarClick){
                try {
                    connection.setAutoCommit(false);
                    
                    ordemServicoDAO.setConnection(connection);
                    itensServicoDAO.setConnection(connection);
                    itensProdutoDAO.setConnection(connection);
                    produtoDAO.setConnection(connection);
                    servicoDAO.setConnection(connection);
                               
                    for(ItensServico lisItensServico : ordemServico.getItensServico()){
                        lisItensServico.setOrdemservico(ordemServico);
                        itensServicoDAO.remover(lisItensServico);
                    }

                    for(ItensProduto itensproduto : ordemServico.getItensProduto()){
                        itensproduto.setOrdemservico(ordemServico);
                        Produto produto = itensproduto.getProduto();
                        produto.setEstoque(produto.getEstoque() + itensproduto.getQuantidade());
                        produtoDAO.alterar(produto);
                        itensProdutoDAO.remover(itensproduto);
                    }
                    
                    ordemServicoDAO.alterar(ordemServico);
                    
                    for(ItensServico lisItensServico : ordemServico.getItensServico()){
                        Servico lavagem = lisItensServico.getServico();
                        lisItensServico.setOrdemservico(ordemServicoDAO.buscarUltimoServico());
                        itensServicoDAO.inserir(lisItensServico);
                    }

                    for(ItensProduto lisOrdemServicoProdutos : ordemServico.getItensProduto()){
                        Produto produto = lisOrdemServicoProdutos.getProduto();
                        lisOrdemServicoProdutos.setOrdemservico(ordemServicoDAO.buscarUltimoServico());
                        itensProdutoDAO.inserir(lisOrdemServicoProdutos);
                        produto.setEstoque(produto.getEstoque() - lisOrdemServicoProdutos.getQuantidade());
                        produtoDAO.alterar(produto);
                    }
                   
                    connection.commit();
                    carregartableviewServico();
                } catch (SQLException ex) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex1) {
                        Logger.getLogger(FXMLTelaServicoController.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                    Logger.getLogger(FXMLTelaServicoController.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Ops...!");
            alert.setContentText("Selecione um serviço na tabela!");
            cssAlert(alert);
            alert.show();
        }
    }
    
    public boolean showFXMLTelaServicoDialog(OrdemServico ordemServico) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLTelaServicoDialogController.class.getResource("/javafxmvc/view/FXMLTelaServicoDialog.fxml"));
        ScrollPane page = (ScrollPane) loader.load();
        
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Serviço");
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
      
        
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        
        FXMLTelaServicoDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setOrdemServico(ordemServico);
        dialogStage.getIcons().add(new Image("/javafxmvc/img/Icone_1.png"));
        
        dialogStage.setFocused(true);
        
        
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
