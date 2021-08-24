package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafxmvc.model.dao.ClienteDAO;
import javafxmvc.model.dao.FuncionarioDAO;
import javafxmvc.model.dao.ServicoDAO;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.Funcionario;
import javafxmvc.model.domain.ItensServico;
import javafxmvc.model.domain.Servico;
import javafxmvc.model.domain.OrdemServico;
import javafxmvc.model.domain.ItensProduto;
import javafxmvc.model.domain.Produto;



public class FXMLTelaServicoDialogController implements Initializable {
    
    //Sessão 1
    @FXML
    private ComboBox comboBoxSCliente;
    @FXML
    private DatePicker datePickerSData;
    @FXML
    private Label tituloS;
    @FXML
    private ComboBox comboBoxSFuncionario;

    //Sessão Serviço
    @FXML
    private ComboBox comboBoxSTipoServico;
    @FXML
    private TableView<ItensServico> tableViewServico;
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnTipoServicoL;
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnqtdL;
    @FXML
    private TableColumn<ItensServico, Integer> tableColumnPrecoL;
    @FXML
    private TextField textFieldSqtdServicoL;
    @FXML
    private Button buttonMaisL;
    @FXML
    private Button buttonMenosL;
    
    
    //Sessão Produtos
    @FXML
    private ComboBox comboBoxSTipoProduto;
    @FXML
    private TableView<ItensProduto> tableViewProduto;
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnProduto;
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnQuantidadeP;
    @FXML
    private TableColumn<ItensProduto, Integer> tableColumnPrecoP;
    @FXML
    private TextField textFieldSqtdProduto;
    @FXML
    private Button buttonMaisP;
    @FXML
    private Button buttonMenosP;
    
    //botões
    @FXML
    private Button buttonCancelar;
    @FXML
    private Button buttonConfirmar;
    
    //Sessão valor
    @FXML
    private TextField textFieldSValorTotal;
    @FXML
    private TextField textFieldSValorDesconto;
    @FXML
    private TextField textFieldSValorPagar;
    @FXML
    private CheckBox CheckBoxPago;
    
    private List<Cliente> listCliente;
    private List<Produto> listProduto;
    private List<Servico> listServico;
    private List<Funcionario> listFuncionario;
    
    private ObservableList<Cliente> observableListCliente;
    private ObservableList<Produto> observableListProduto;
    private ObservableList<Servico> observableListLavagens;
    private ObservableList<Funcionario> observableListFuncionario; 
    
    private ObservableList<ItensServico> observableListItensServico;
    private ObservableList<ItensProduto> observableListItensProduto;
     
    //Banco
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    
    private final ClienteDAO clienteDAO = new ClienteDAO(); 
    private final FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ServicoDAO servicoDAO = new ServicoDAO();
    
    private Stage dialogStage;
    private boolean buttonConfirmarClick = false;
    private boolean desconto = false;
    
    private OrdemServico ordemServico;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clienteDAO.setConnection(connection);
        funcionarioDAO.setConnection(connection);
        produtoDAO.setConnection(connection);
        servicoDAO.setConnection(connection);
        
        carregarBoxCliente();
        carregarBoxLavagens();
        caregarBoxProduto();
        carregarBoxFuncionario();
        
        //Campos tabela lavagem
        tableColumnTipoServicoL.setCellValueFactory(new PropertyValueFactory<>("servico"));
        tableColumnqtdL.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnPrecoL.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        //Campo tabela produto
        tableColumnProduto.setCellValueFactory(new PropertyValueFactory<>("produto"));
        tableColumnQuantidadeP.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumnPrecoP.setCellValueFactory(new PropertyValueFactory<>("preco"));
        
        textFieldSqtdProduto.setText("1");
        textFieldSqtdServicoL.setText("1");
        
        comboBoxSCliente.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> verificarDesconto(newValue.toString()));
        
    }    
    
    public void carregarBoxCliente(){
        listCliente = clienteDAO.listar();
        observableListCliente = FXCollections.observableArrayList(listCliente);
        comboBoxSCliente.setItems(observableListCliente);
    }
    
    public void carregarBoxLavagens(){
        listServico = servicoDAO.lista();
        observableListLavagens = FXCollections.observableArrayList(listServico);
        comboBoxSTipoServico.setItems(observableListLavagens);
    }
    
    public void caregarBoxProduto(){
        listProduto = produtoDAO.listar();
        observableListProduto = FXCollections.observableArrayList(listProduto);
        comboBoxSTipoProduto.setItems(observableListProduto);
    }
    
    public void carregarBoxFuncionario(){
        listFuncionario = funcionarioDAO.listar();
        observableListFuncionario = FXCollections.observableArrayList(listFuncionario);
        comboBoxSFuncionario.setItems(observableListFuncionario);
    }

    public Stage getDialogStage(){
        return dialogStage;
    }
    
    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    
    public OrdemServico getOrdemServico(){
        return this.ordemServico;
    }
    
    public void setOrdemServico(OrdemServico ordemServico){
        this.ordemServico = ordemServico;
        
        observableListItensServico = FXCollections.observableArrayList(ordemServico.getItensServico());
        observableListItensProduto = FXCollections.observableArrayList(ordemServico.getItensProduto());
 
        this.comboBoxSCliente.setValue(ordemServico.getCliente());
        this.comboBoxSFuncionario.setValue(ordemServico.getFuncionario());
        this.datePickerSData.setValue(ordemServico.getData());
        this.tableViewServico.setItems(observableListItensServico);
        this.tableViewProduto.setItems(observableListItensProduto);
        
        this.textFieldSValorTotal.setText(String.valueOf(ordemServico.getTotal()));
        this.textFieldSValorDesconto.setText(String.valueOf(ordemServico.getDesconto()));
        this.textFieldSValorPagar.setText(String.valueOf(ordemServico.getTotal() - ordemServico.getDesconto()));
       
        this.CheckBoxPago.setSelected(ordemServico.getPago());
        
        
        //Alteranto o titulo da tela
        if(this.ordemServico.getCliente() != null){
            tituloS.setText("Atualizando Serviço");
        }else{
            tituloS.setText("Inserindo Serviço");
        }
    }
    
    public boolean isButtonConfirmarClicked(){
        return buttonConfirmarClick;
    }
    
    @FXML
    public void buttonAdicionarServico(){
        Servico lavagem;
        ItensServico itensServico = new ItensServico();
        
        if(textFieldSqtdServicoL.getLength() > 0){
            if(textFieldSqtdServicoL.getText().matches("[0-9]*")){
                if(comboBoxSTipoServico.getSelectionModel().getSelectedItem() != null){
                    if( Integer.parseInt(textFieldSqtdServicoL.getText()) > 0){
                        if(observableListItensServico.isEmpty() || verificarTabelaServico()){
                            lavagem = (Servico) comboBoxSTipoServico.getSelectionModel().getSelectedItem();
                            itensServico.setServico((Servico) comboBoxSTipoServico.getSelectionModel().getSelectedItem());
                            itensServico.setQuantidade(Integer.parseInt(textFieldSqtdServicoL.getText()));
                            itensServico.setPreco(itensServico.getServico().getPreco() * itensServico.getQuantidade());

                            ordemServico.getItensServico().add(itensServico);
                            ordemServico.setTotal(ordemServico.getTotal() + itensServico.getPreco());

                            observableListItensServico = FXCollections.observableArrayList(ordemServico.getItensServico());
                            tableViewServico.setItems(observableListItensServico); 
                            textFieldSValorTotal.setText(String.format("%.2f", ordemServico.getTotal()));

                           calcularDesconto();

                        }else{
                            mensagemErro(5);
                        }
                    }else{
                        mensagemErro(1);
                    }
                }else{
                    mensagemErro(2);
                }
            }else{
                mensagemErro(1);
            }
        }else{
            mensagemErro(7);
        }
    }
    
    @FXML
    public void buttonAdicionarProduto(){
        Produto produto;
        ItensProduto itensProduto = new ItensProduto();

        if(textFieldSqtdProduto.getLength() > 0){
            if(textFieldSqtdProduto.getText().matches("[0-9]*")){
                if(comboBoxSTipoProduto.getSelectionModel().getSelectedItem() != null){
                    if(Integer.parseInt(textFieldSqtdProduto.getText()) > 0 ){
                        produto = (Produto) comboBoxSTipoProduto.getSelectionModel().getSelectedItem();
                        if(Integer.parseInt(textFieldSqtdProduto.getText()) <= produto.getEstoque()){
                            if(observableListItensProduto.isEmpty() || verificarTabelaProduto()){
                                itensProduto.setProduto((Produto) comboBoxSTipoProduto.getSelectionModel().getSelectedItem());     
                                itensProduto.setQuantidade(Integer.parseInt(textFieldSqtdProduto.getText()));
                                itensProduto.setPreco(itensProduto.getProduto().getPreco() * itensProduto.getQuantidade());

                                ordemServico.getItensProduto().add(itensProduto);
                                ordemServico.setTotal(ordemServico.getTotal()+ itensProduto.getPreco());

                                observableListItensProduto = FXCollections.observableArrayList(ordemServico.getItensProduto());
                                tableViewProduto.setItems(observableListItensProduto);
                                textFieldSValorTotal.setText(String.format("%.2f", ordemServico.getTotal()));

                                calcularDesconto();

                            }else{
                                mensagemErro(5);
                            }
                        }else{
                            mensagemErro(3);
                        }
                    }else{
                        mensagemErro(1);
                    } 
                }else{
                    mensagemErro(2);
                }
            }else{
                mensagemErro(1);
            }
        }else{
            mensagemErro(7);
        }
    }

    @FXML
    public void buttonRemoverLavagem(){
        if(tableViewServico.getSelectionModel().selectedItemProperty().getValue() != null){
            selecionarItemTableViewLavagem(tableViewServico.getSelectionModel().selectedItemProperty().getValue());
        }else{
            mensagemErro(4);
        }     
    }
    
    public void selecionarItemTableViewLavagem(ItensServico itensServico){
        ordemServico.setTotal(ordemServico.getTotal()- itensServico.getPreco());
        tableViewServico.getSelectionModel().clearSelection();
        ordemServico.getItensServico().remove(itensServico);

        observableListItensServico = FXCollections.observableArrayList(ordemServico.getItensServico());
        tableViewServico.setItems(observableListItensServico);
        textFieldSValorTotal.setText(String.format("%.2f", ordemServico.getTotal()));
        
        calcularDesconto();
    }
    
    @FXML
    public void buttonRemoverProduto(){   
        if(tableViewProduto.getSelectionModel().selectedItemProperty().getValue() != null){
            selecionarItemTableViewProduto(tableViewProduto.getSelectionModel().selectedItemProperty().getValue());
        }else{
            mensagemErro(4);
        }
    }
  
    public void selecionarItemTableViewProduto(ItensProduto ordemServicoProdutos){
        ordemServico.setTotal(ordemServico.getTotal() - ordemServicoProdutos.getPreco());
        tableViewProduto.getSelectionModel().clearSelection();
        ordemServico.getItensProduto().remove(ordemServicoProdutos);
        
        observableListItensProduto = FXCollections.observableArrayList(ordemServico.getItensProduto());
        tableViewProduto.setItems(observableListItensProduto);
        textFieldSValorTotal.setText(String.format("%.2f", ordemServico.getTotal()));
        
        calcularDesconto();
    }
    
    
    @FXML
    public void buttonConfirmarS(){
        if(validarEntradaDeDados()){
            ordemServico.setCliente((Cliente) comboBoxSCliente.getSelectionModel().getSelectedItem());
            ordemServico.setFuncionario((Funcionario) comboBoxSFuncionario.getSelectionModel().getSelectedItem());
            ordemServico.setPago(CheckBoxPago.isSelected());
            ordemServico.setData(datePickerSData.getValue());
 
            buttonConfirmarClick = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void buttonCancelarS(){
        getDialogStage().close();   
    }
    
    public void verificarDesconto(String cliente){
        desconto = clienteDAO.verificarDesconto(cliente);
        calcularDesconto();
    }
    
    public void calcularDesconto(){
        if(desconto == true){
            textFieldSValorDesconto.setText(String.format("%.2f", ordemServico.getTotal() * 0.1));
            textFieldSValorPagar.setText(String.format("%.2f", ordemServico.getTotal() - (ordemServico.getTotal() * 0.1)));
            ordemServico.setDesconto(ordemServico.getTotal() * 0.1);
            
        }else{
            textFieldSValorDesconto.setText("0.0");
            textFieldSValorPagar.setText(String.format("%.2f", ordemServico.getTotal()));
            ordemServico.setDesconto(0);
        }
    }
    
    public boolean verificarTabelaProduto(){
        
        for (ItensProduto itensProduto : observableListItensProduto) {
            if(itensProduto.getProduto().getNome().equals(comboBoxSTipoProduto.getSelectionModel().getSelectedItem().toString())){
                return false;
            }
        }
        return true;
    }
    
    public boolean verificarTabelaServico(){
        
        for (ItensServico itensServico : observableListItensServico) {
            if(itensServico.getServico().getNome().equals(comboBoxSTipoServico.getSelectionModel().getSelectedItem().toString())){
                return false;
            }
        }
        return true;
    }
    
    private boolean validarEntradaDeDados(){
        String msgErro = "";
        
        if(comboBoxSCliente.getSelectionModel().getSelectedItem() == null){
            msgErro += "Cliente inválido!\n";
        }
        if(comboBoxSFuncionario.getSelectionModel().getSelectedItem() == null){
            msgErro += "Funcionário inválido!\n";
        }
        if(datePickerSData.getValue() == null){
            msgErro += "Data inválida!\n";
        }

        if(observableListItensServico.isEmpty()){
            msgErro += "Serviço invalido!\n";
        }
        if(observableListItensProduto.isEmpty()){
            msgErro += "Produto invalido!\n";
        }
        
        
        if(msgErro.length() == 0){
            return true;
        }else{
   
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erro");
            alert.setHeaderText("Campos inválidos!");
            alert.setContentText(msgErro);
            cssAlert(alert);
            alert.show();
            
            return false;
        }
        
    }
    
    public void mensagemErro(int x){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
             
        switch(x){
            case 1:
                alert.setHeaderText("Quantidade inválida!");
                alert.setContentText("A quantidade deve ser maior que 0!");
            break;
            
            case 2:
                alert.setHeaderText("Ops..!");
                alert.setContentText("Selecione um item no Box!");
            break;
            
            case 3:
                alert.setHeaderText("Quantidade inválida!");
                alert.setContentText("A quantidade do produto em estoque é insuficiente!");
            break;
            
            case 4:
                alert.setHeaderText("Ops...!");
                alert.setContentText("Selecione um item da tabela!");
            break;
            
            case 5:
                alert.setHeaderText("Item já inserido na tabela!");
                alert.setContentText("Remova o item da tabela e altere a sua quantidade caso queira mais de um!");
            break;
            
            case 6:
                alert.setHeaderText("Selecione um cliente!");
                alert.setContentText("Selecione um cliente para verificar se ele possui desconto!");
            break;
            
            case 7:
                alert.setHeaderText("Quantidade Invalida!");
                alert.setContentText("O campo da quantidade não pode ser vazio!");
            break;
            
        }
        cssAlert(alert);
        alert.show();
    }

    
    public void cssAlert(Alert alert){
        DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
               getClass().getResource("/javafxmvc/view/Tema_dark.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
    }
    
}