package javafxmvc.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Label;

public class ThreadSocket implements Runnable{

    private final List<String> listNome;
    private final Label labelNome;
    
    public ThreadSocket(Label LabelNome, List<String> listNome){
        this.labelNome = LabelNome;
        this.listNome = listNome;
    }
    
    @Override
    public void run() {
        
        try {
            int i = 0;
            
            while(i < 3){
                for (String string : listNome) {
                    Platform.runLater(() -> labelNome.setText(string)); 
                    Thread.sleep(3000);
                }
                i++;
            }
            
             Platform.runLater(() -> labelNome.setText("Obrigado!")); 
            
        } catch (InterruptedException ex) {
            Logger.getLogger(ThreadSocket.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
