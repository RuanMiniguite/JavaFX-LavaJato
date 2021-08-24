package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Cliente;
import javafxmvc.model.domain.Funcionario;
import javafxmvc.model.domain.ItensServico;
import javafxmvc.model.domain.OrdemServico;
import javafxmvc.model.domain.ItensProduto;

public class OrdemServicoDAO {
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public boolean inserir(OrdemServico ordemServico){
          String sql = "INSERT INTO ordemServico(codCliente, codFuncionario, data, pago, desconto, total) VALUES(?,?,?,?,?,?)";
          try {
              PreparedStatement stmt = connection.prepareStatement(sql);
              stmt.setInt(1, ordemServico.getCliente().getCodCliente());
              stmt.setInt(2, ordemServico.getFuncionario().getCodFuncionario());
              stmt.setDate(3, Date.valueOf(ordemServico.getData()));
              stmt.setBoolean(4, ordemServico.getPago());
              stmt.setDouble(5, ordemServico.getDesconto());
              stmt.setDouble(6, ordemServico.getTotal());
              stmt.execute();
              return true;
        } catch (SQLException ex) {
              Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
              return false;
        }
    }
    
    public boolean alterar(OrdemServico ordemServico){
        String sql = "UPDATE ordemServico SET codCliente=?, codFuncionario=?, data=?, pago=?, desconto=?, total=? WHERE codOrdemServico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, ordemServico.getCliente().getCodCliente());
            stmt.setInt(2, ordemServico.getFuncionario().getCodFuncionario());
            stmt.setDate(3, Date.valueOf(ordemServico.getData()));
            stmt.setBoolean(4, ordemServico.getPago());
            stmt.setDouble(5, ordemServico.getDesconto());
            stmt.setDouble(6, ordemServico.getTotal());
           
            stmt.setInt(7, ordemServico.getCodOrdemServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServico.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(OrdemServico ordemServico){
        String sql = "DELETE FROM ordemServico WHERE codOrdemServico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServico.getCodOrdemServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServico.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
     
    public List<OrdemServico> listar(){
        
        String sql = "SELECT * FROM ordemServico;";
        List<OrdemServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                OrdemServico ordemServico = new OrdemServico();
                Cliente cliente = new Cliente();
                Funcionario funcionario = new Funcionario();
                
                List<ItensServico> itensServico = new ArrayList();
                List<ItensProduto> itensProduto = new ArrayList();
                
                ordemServico.setCodOrdemServico(resultado.getInt("codOrdemServico"));
                ordemServico.setData(resultado.getDate("data").toLocalDate());
                ordemServico.setTotal(resultado.getDouble("total"));
                ordemServico.setDesconto(resultado.getDouble("desconto"));
                ordemServico.setPago(resultado.getBoolean("pago"));
                cliente.setCodCliente(resultado.getInt("codCliente"));
                funcionario.setCodFuncionario(resultado.getInt("codFuncionario"));
                
                //Dados do Cliente
                ClienteDAO clienteDAO = new ClienteDAO();
                clienteDAO.setConnection(connection);
                cliente = clienteDAO.buscar(cliente);
                
                //Dados do Funcionario
                FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
                funcionarioDAO.setConnection(connection);
                funcionario = funcionarioDAO.buscar(funcionario);
                
                //Dados do ItensServico
                ItensServicoDAO itensServicoDAO = new ItensServicoDAO();
                itensServicoDAO.setConnection(connection);
                itensServico = itensServicoDAO.listarPorServico(ordemServico);
                
                //Dados do ItensProduto
                ItensProdutoDAO ordemServicoProdutosDAO = new ItensProdutoDAO();
                ordemServicoProdutosDAO.setConnection(connection);
                itensProduto = ordemServicoProdutosDAO.listarPorServicoProduto(ordemServico);
                
                ordemServico.setCliente(cliente);
                ordemServico.setFuncionario(funcionario);
                ordemServico.setItensServico(itensServico);
                ordemServico.setItensProduto(itensProduto);
                retorno.add(ordemServico);
                              
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    } 

    public OrdemServico buscar(OrdemServico ordemServico){
        String sql = "SELECT * FROM ordemServico WHERE codOrdemServico=?";
        OrdemServico retorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServico.getCodOrdemServico());
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                Cliente cliente = new Cliente();
                Funcionario funcionario = new Funcionario();
                ordemServico.setCodOrdemServico(resultado.getInt("codOrdemServico"));
                ordemServico.setData(resultado.getDate("data").toLocalDate());
                ordemServico.setTotal(resultado.getDouble("total"));
                ordemServico.setDesconto(resultado.getDouble("desconto"));
                ordemServico.setPago(resultado.getBoolean("pago"));
                cliente.setCodCliente(resultado.getInt("codCliente"));
                funcionario.setCodFuncionario(resultado.getInt("codFuncionario"));
                ordemServico.setCliente(cliente);
                retorno = ordemServico;
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServico.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public OrdemServico buscarUltimoServico(){
        String sql = "SELECT max(codOrdemServico) FROM ordemServico";
        OrdemServico retorno = new OrdemServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            if(resultado.next()){
                retorno.setCodOrdemServico(resultado.getInt("max"));
                return retorno;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public Map<Integer, ArrayList> listarQtdOrdemServicoMes() {
        String sql = "SELECT COUNT(codOrdemServico), EXTRACT(year from data) AS ano, EXTRACT(month from data) AS mes FROM ordemServico GROUP BY ano, mes ORDER BY ano, mes";
        Map<Integer, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getInt("ano"))){
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("count"));
                    retorno.put(resultado.getInt("ano"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getInt("ano"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("count"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(OrdemServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
}
