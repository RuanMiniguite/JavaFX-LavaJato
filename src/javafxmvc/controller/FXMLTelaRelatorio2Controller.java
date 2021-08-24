package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafxmvc.model.dao.ProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;
import javafxmvc.model.domain.Produto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLTelaRelatorio2Controller implements Initializable {
    
    @FXML
    private Button buttonImprimirRelatorio2;
    @FXML
    private TableView<Produto> tableViewRelatorio2;
    @FXML
    private TableColumn<Produto, Integer> tableColumnCodigo;
    @FXML
    private TableColumn<Produto, String> tableColumnDescricao;
    @FXML
    private TableColumn<Produto, Integer> tableColumnSaida;
    
    
    private List<Produto> listProdutos;
    private ObservableList<Produto> observableListProdutos;
    
    
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produtoDAO.setConnection(connection);
        
        listarProdutos();
    }
    
    public void listarProdutos() {
            
        tableColumnCodigo.setCellValueFactory(new PropertyValueFactory<>("codProduto"));
        tableColumnDescricao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumnSaida.setCellValueFactory(new PropertyValueFactory<>("qtd"));
            
        listProdutos = produtoDAO.listarPorQuantidade();

        observableListProdutos = FXCollections.observableArrayList(listProdutos);
        tableViewRelatorio2.setItems(observableListProdutos);
        
    }
    
     public void handleImprimir2() throws JRException{
        URL url = getClass().getResource("/javafxmvc/relatorios/lavaJato2.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }
}    
   
