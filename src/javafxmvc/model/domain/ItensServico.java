
package javafxmvc.model.domain;

import java.io.Serializable;

public class ItensServico implements Serializable{
    
    private int quantidade;
    private double preco;
    private OrdemServico ordemservico;
    private Servico servico;
    
    
    public ItensServico(){
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

    public OrdemServico getOrdemservico() {
        return ordemservico;
    }

    public void setOrdemservico(OrdemServico ordemservico) {
        this.ordemservico = ordemservico;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
