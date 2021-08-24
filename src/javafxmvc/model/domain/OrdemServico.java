package javafxmvc.model.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class OrdemServico implements Serializable{

 
    private int codOrdemServico;
    private LocalDate data;
    private boolean pago;
    private double total;
    private double desconto;
    private Cliente cliente;
    private Funcionario funcionario;
    private List<ItensServico> itensServico;
    private List<ItensProduto> itensProduto;
    
    public OrdemServico() {
    }
    
    public OrdemServico(int codOrdemServico, LocalDate data, double total, boolean pago) {
        this.codOrdemServico = codOrdemServico;
        this.data = data;
        this.total = total;
        this.pago = pago;
    }

    public int getCodOrdemServico() {
        return codOrdemServico;
    }

    public void setCodOrdemServico(int codOrdemServico) {
        this.codOrdemServico = codOrdemServico;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }


    public boolean getPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public List<ItensServico> getItensServico() {
        return itensServico;
    }

    public void setItensServico(List<ItensServico> itensServico) {
        this.itensServico = itensServico;
    }

    public List<ItensProduto> getItensProduto() {
        return itensProduto;
    }

    public void setItensProduto(List<ItensProduto> itensProduto) {
        this.itensProduto = itensProduto;
    }
    
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

}
