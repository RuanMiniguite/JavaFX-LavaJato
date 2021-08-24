package javafxmvc.model.domain;

import java.io.Serializable;

public class Produto implements Serializable {

    
    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    private int codProduto;
    private String nome;
    private int estoque;
    private double preco;
    private int qtd;

    public Produto() {
    }

    public Produto(int cod, String nome, int estoque, double preco) {
        this.codProduto = codProduto;
        this.nome = nome;
        this.estoque = estoque;
        this.preco = preco;
    }

    public int getCodProduto() {
        return codProduto;
    }

    public void setCodProduto(int codProduto) {
        this.codProduto = codProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }


    @Override
    public String toString() {
        return this.nome;
    }
    
}
