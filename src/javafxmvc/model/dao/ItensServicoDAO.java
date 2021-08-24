package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.ItensServico;
import javafxmvc.model.domain.Servico;
import javafxmvc.model.domain.OrdemServico;


public class ItensServicoDAO {
    
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    
    public boolean inserir(ItensServico itensServico){
        String sql = "INSERT INTO itensServico(quantidade, preco, codOrdemServico, codServico) VALUES(?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensServico.getQuantidade());
            stmt.setDouble(2, itensServico.getPreco());
            stmt.setInt(3, itensServico.getOrdemservico().getCodOrdemServico());
            stmt.setInt(4, itensServico.getServico().getCodServico());
            
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean alterar(ItensServico itensServico){
        String sql = "UPDATE itensServico SET codOrdemServico=?, codServico=?, quantidade=?, preco=? WHERE codOrdemServico=? AND codServico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensServico.getOrdemservico().getCodOrdemServico());
            stmt.setInt(2, itensServico.getServico().getCodServico());
            stmt.setInt(3, itensServico.getQuantidade());
            stmt.setDouble(4, itensServico.getPreco());
            stmt.setInt(5, itensServico.getOrdemservico().getCodOrdemServico());
            stmt.setInt(6, itensServico.getServico().getCodServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(ItensServico itensServico){
        String sql = "DELETE FROM  itensServico WHERE codOrdemServico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensServico.getOrdemservico().getCodOrdemServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<ItensServico> listar(){
        String sql = "SELECT * FROM itensServico";
        List<ItensServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                ItensServico itensServico = new ItensServico();
                Servico servico = new Servico();
                OrdemServico ordemServico = new OrdemServico();
                
                itensServico.setPreco(resultado.getDouble("preco"));
                itensServico.setQuantidade(resultado.getInt("quantidade"));
                
                servico.setCodServico(resultado.getInt("codServico"));
                ordemServico.setCodOrdemServico(resultado.getInt("codOrdemServico"));
                
                //Dados da lavagens
                ServicoDAO lavagensDAO = new ServicoDAO();
                lavagensDAO.setConnection(connection);
                servico = lavagensDAO.buscar(servico);
                
                
                //Dados da ordem de servico
                OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
                ordemServicoDAO.setConnection(connection);
                ordemServico = ordemServicoDAO.buscar(ordemServico);
                
                itensServico.setServico(servico);
                itensServico.setOrdemservico(ordemServico);
                
                retorno.add(itensServico);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ItensServico> listarPorServico(OrdemServico ordemServico){
        String sql = "SELECT * FROM itensServico WHERE codOrdemServico=?";
        List<ItensServico> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServico.getCodOrdemServico());
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()){
                ItensServico itensServico = new ItensServico();
                Servico servico = new Servico();
                OrdemServico o = new OrdemServico();

                itensServico.setPreco(resultado.getDouble("preco"));
                itensServico.setQuantidade(resultado.getInt("quantidade"));

                servico.setCodServico(resultado.getInt("codServico"));
                o.setCodOrdemServico(resultado.getInt("codOrdemServico"));

                //Dados da lavagens
                ServicoDAO lavagensDAO = new ServicoDAO();
                lavagensDAO.setConnection(connection);
                servico = lavagensDAO.buscar(servico);

                //Dados da ordem de servico
                OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
                ordemServicoDAO.setConnection(connection);
                ordemServico = ordemServicoDAO.buscar(o);

                itensServico.setServico(servico);
                itensServico.setOrdemservico(ordemServico);

                retorno.add(itensServico);
            }

        } catch (SQLException ex) {
           Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public ItensServico buscar(ItensServico itensServico){
        String sql = "SELECT * FROM itensServico WHERE codOrdemServico=? AND codServico=?";
        ItensServico retorno = new ItensServico();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensServico.getOrdemservico().getCodOrdemServico());
            stmt.setInt(2, itensServico.getServico().getCodServico());
            ResultSet resultado = stmt.executeQuery();
            if(resultado.next()){
                Servico servico = new Servico();
                OrdemServico ordemServico = new OrdemServico();

                itensServico.setPreco(resultado.getDouble("preco"));
                itensServico.setQuantidade(resultado.getInt("quantidade"));

                servico.setCodServico(resultado.getInt("codServico"));
                ordemServico.setCodOrdemServico(resultado.getInt("codSrdemServico"));

                //Dados da lavagens
                ServicoDAO lavagensDAO = new ServicoDAO();
                lavagensDAO.setConnection(connection);
                servico = lavagensDAO.buscar(servico);


                //Dados da ordem de servico
                OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
                ordemServicoDAO.setConnection(connection);
                ordemServico = ordemServicoDAO.buscar(ordemServico);

                itensServico.setServico(servico);
                itensServico.setOrdemservico(ordemServico);

                retorno = itensServico;    
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<ItensServico> listarPorServicoServicos(OrdemServico ordemServico){
        String sql = "SELECT * FROM itensServico WHERE codOrdemServico=?";
        List<ItensServico> retorno = new ArrayList<>();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServico.getCodOrdemServico());
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                ItensServico itensServico = new ItensServico();
                Servico servico = new Servico();
                
                itensServico.setQuantidade(resultado.getInt("quantidade"));
                itensServico.setPreco(resultado.getDouble("preco"));
                servico.setCodServico(resultado.getInt("codServico"));
                
                //Dados Servico
                ServicoDAO servicoDAO = new ServicoDAO();
                servicoDAO.setConnection(connection);
                servico = servicoDAO.buscar(servico);
                
                itensServico.setServico(servico);
                itensServico.setOrdemservico(ordemServico);
                
//                System.out.println("nome: " + itensServico.getServico());
//                System.out.println("qtd: " + itensServico.getQuantidade());
//                System.out.println("p: " + itensServico.getPreco());
//                System.out.println("--------------------------------------------");
                
                retorno.add(itensServico);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensServicoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

}
