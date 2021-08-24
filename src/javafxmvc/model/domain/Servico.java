package javafxmvc.model.domain;

import java.io.Serializable;

public class Servico implements Serializable{
  
    private int codServico;
    private String nome;
    private float preco;

    public Servico(){
    }
    
    public Servico(int codServico, String nome_lavagem, float preco_lavagem){
        this.codServico = codServico;
        this.nome = nome;
        this.preco = preco;
 
    }
    
    public int getCodServico() {
        return codServico;
    }

    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }

 
    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public float getPreco() {
        return preco;
    }

 
    public void setPreco(float preco) {
        this.preco = preco;
    }
    
    @Override
    public String toString(){
        return this.nome;
    }
    
}

