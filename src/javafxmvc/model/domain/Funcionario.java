package javafxmvc.model.domain;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private int codFuncionario;
    private String nome;
    private String cpf;
    private String telefone;

    public Funcionario(){
    }
    
    public Funcionario(int cod, String nome, String cpf, String telefone) {
        this.codFuncionario = cod;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }

    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
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
