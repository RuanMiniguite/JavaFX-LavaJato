package javafxmvc.model.domain;

import java.io.Serializable;

public class Cliente implements Serializable {

    private int codCliente;
    private String nome;
    private String cpf;
    private String telefone;

    public Cliente(){
    }
    
    public Cliente(int codCliente, String nome, String cpf, String telefone) {
        this.codCliente = codCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return this.nome;
    }
    
}
