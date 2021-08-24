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
import javafxmvc.model.domain.Funcionario;

public class FXMLCadastrosFuncionariosDialogController implements Initializable {
    
    @FXML
    private Label labelFuncionarioNome;
    
    @FXML
    private Label labelFuncionarioCpf;
    
    @FXML
    private Label labelFuncionarioTelefone;
    
    @FXML
    private TextField textFieldFuncionarioNome;
    
    @FXML
    private TextField textFieldFuncionarioCpf;
    
    @FXML
    private TextField textFieldFuncionarioTelefone;
    
    @FXML
    private Button buttonCancelar;
    
    @FXML
    private Button buttonConfirmar;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Funcionario funcionario;
    
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

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        
        this.textFieldFuncionarioNome.setText(funcionario.getNome());
        this.textFieldFuncionarioCpf.setText(funcionario.getCpf());
        this.textFieldFuncionarioTelefone.setText(funcionario.getTelefone()); 
    }
    
    @FXML
    public void handleButtonConfirmar() {
        
        if (validarEntradaDeDados()) {
            funcionario.setNome(textFieldFuncionarioNome.getText().trim());
            funcionario.setCpf(textFieldFuncionarioCpf.getText().trim());
            funcionario.setTelefone(textFieldFuncionarioTelefone.getText().trim());

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
        
        if (validarNome(textFieldFuncionarioNome.getText())) {
            errorMessage += "Nome inválido!\n";
        }
        if (validarCpf(textFieldFuncionarioCpf.getText())) {
            errorMessage += "CPF inválido!\n";
        }
        if (validarTelefone(textFieldFuncionarioTelefone.getText())) {
            errorMessage += "Telefone inválido\n";
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
        boolean invalido = false;
        for (char c : nome.toCharArray()) {
            if (invalido = !Character.isLetter(c) && !Character.isSpace(c)) {
                break;
            }
        }
        return invalido;
    }
    
    private boolean validarCpf (String cpf) {
        
        if (cpf == null || cpf.trim().length() < 11) {
            return true;
        }
        boolean invalido = false;
        for (char c : cpf.toCharArray()) {
            if (invalido = Character.isLetter(c)) {
                break;
            }
        }
        return invalido;
    }
    
    private boolean validarTelefone (String telefone) {
        
        if (telefone == null || telefone.trim().length() < 8) {
            return true;
        }
        boolean invalido = false;
        for (char c : telefone.toCharArray()) {
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

