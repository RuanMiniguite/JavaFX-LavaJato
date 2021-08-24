package javafxmvc.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


public class FXMLTelaDiscenteController implements Initializable {
    
    private List<String> listNome;
    
    @FXML
    private Label LabelNome;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        int grupo = 6;
        String ip = "34.125.46.96";
        int porta = 12345;
 
        try (Socket socketin = new Socket(ip, porta)){
            
            DataOutputStream saida = new DataOutputStream(socketin.getOutputStream());
            saida.writeInt(grupo);
            
            ObjectInputStream entrada = new ObjectInputStream(socketin.getInputStream());
            listNome = (List<String>) entrada.readObject();
            
            listNome.forEach((string) -> {
                System.out.println(string);
            });
                              
            ThreadSocket threadzin = new ThreadSocket(LabelNome,listNome);
            Thread thread = new Thread(threadzin);
            thread.setDaemon(true);
            thread.start();
            
            entrada.close();
            saida.close();
            socketin.close();
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLTelaDiscenteController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex){
            Logger.getLogger(FXMLTelaDiscenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    
    
}