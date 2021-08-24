package javafxmvc.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxmvc.model.domain.Produto;

public class FXMLCadastrosProdutosDialogController implements Initializable {

    @FXML
    private Label labelProdutoNome;
    
    @FXML
    private Label labelProdutoEstoque;
    
    @FXML
    private Label labelProdutoPreco;
    
    @FXML
    private TextField textFieldProdutoNome;
    
    @FXML
    private TextField textFieldProdutoEstoque;
    
    @FXML
    private TextField textFieldProdutoPreco;
    
    @FXML
    private Button buttonCancelar;
    
    @FXML
    private Button buttonConfirmar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Produto produto;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }  
    
    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    public void setButtonConfirmarClicked(boolean buttonConfirmarClicked) {
        this.buttonConfirmarClicked = buttonConfirmarClicked;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
        
        this.textFieldProdutoNome.setText(produto.getNome());
        this.textFieldProdutoEstoque.setText(String.valueOf(produto.getEstoque()));
        this.textFieldProdutoPreco.setText(String.valueOf(produto.getPreco())); 
    }
    
    @FXML
    public void handleButtonConfirmar() {
        
        if (validarEntradaDeDados()) {
            produto.setNome(textFieldProdutoNome.getText().trim());
            produto.setEstoque(Integer.parseInt(textFieldProdutoEstoque.getText().trim()));
            produto.setPreco(Double.parseDouble(textFieldProdutoPreco.getText().trim()));

            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }
    
    @FXML
    public void handleButtonCancelar() {
        dialogStage.close();
    }
    
    private boolean validarEntradaDeDados() {
        String errorMessage = "";
        
        if (textFieldProdutoNome.getText() == null || textFieldProdutoNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (validarEstoque(textFieldProdutoEstoque.getText())) {
            errorMessage += "Quantidade de estoque inválida!\n";
        }
        if (validarPreco(textFieldProdutoPreco.getText())) {
            errorMessage += "Preço inválido!\n";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Existem campos inválidos");
            alert.setContentText(errorMessage);
            cssAlert(alert);
            alert.show();
            return false;
        }
    }  
    
    private boolean validarNome (String nome) {
        if (nome == null || nome.trim().length() < 2) {
            return true;
        }
        return false;
    }
    
    private boolean validarEstoque (String estoque) {
        
        if (estoque == null || estoque.trim().length() == 0) {
            return true;
        }
        boolean invalido = false;
        for (char c : estoque.toCharArray()) {
            if (invalido = !Character.isDigit(c)) {
                break;
            }
        }
        return invalido;
    }
    
    private boolean validarPreco (String preco) {
        
        if (preco == null || preco.trim().length() == 0) {
            return true;
        }
        boolean invalido = false;
        for (char c : preco.toCharArray()) {
            if (invalido = Character.isLetter(c)) {
                break;
            }
        }
        return invalido;
    } 
    
    public void cssAlert(Alert alert){
        DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
               getClass().getResource("/javafxmvc/view/Tema_dark.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
    }
    
}

