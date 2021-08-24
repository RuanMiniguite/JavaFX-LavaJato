package javafxmvc.model.domain;

import java.io.Serializable;

public class ItensProduto implements Serializable{

    private OrdemServico ordemservico;
    private Produto produto;
    private int quantidade;
    private double preco;
    
    public ItensProduto(){
    }
    
    public OrdemServico getOrdemservico() {
        return ordemservico;
    }

    public void setOrdemservico(OrdemServico ordemservico) {
        this.ordemservico = ordemservico;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto Produto) {
        this.produto = Produto;
    }

    public int getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
  
}
