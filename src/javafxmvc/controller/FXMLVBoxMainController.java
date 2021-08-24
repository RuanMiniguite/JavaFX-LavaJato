package javafxmvc.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLVBoxMainController implements Initializable {

    @FXML
    private MenuItem menuCliente;
    
    @FXML
    private MenuItem menuVeiculo;
    
    @FXML
    private MenuItem menuServico;
    
    @FXML
    private MenuItem menuGrafico1;
    
    @FXML
    private MenuItem menuGrafico2;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void handleMenuCliente() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLCadastrosClientes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuProdutos() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLCadastrosProdutos.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuFuncionario() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLCadastrosFuncionarios.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    
    @FXML
    public void handleMenuGrafico1() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficoOrdemServicoMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    
    @FXML
    public void handleMenuGrafico2() throws IOException {
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLAnchorPaneGraficoItensProdutosMes.fxml"));
        anchorPane.getChildren().setAll(a);
    }
       
    @FXML
       public void handleMenuSevico() throws IOException {
           AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLTelaServico.fxml"));
           anchorPane.getChildren().setAll(a);
    }
    
    @FXML
       public void handleMenuRelatorio1() throws IOException {
           AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLTelaRelatorio.fxml"));
           anchorPane.getChildren().setAll(a);
    }

    @FXML
       public void handleMenuRelatorio2() throws IOException {
           AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLTelaRelatorio2.fxml"));
           anchorPane.getChildren().setAll(a);
    }
       
    @FXML
    public void handleMenuAlunos() throws IOException {
         AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafxmvc/view/FXMLTelaDiscente.fxml"));
         anchorPane.getChildren().setAll(a);
    }
}
