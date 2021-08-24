/*
INSERIR:   
ALTERAR:    
REMOVER:    
LISTAR:     ok
BUSCAR:     
*/
package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.OrdemServico;
import javafxmvc.model.domain.ItensProduto;
import javafxmvc.model.domain.Produto;

public class ItensProdutoDAO {
    
    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(ItensProduto ordemServicoProduto){
          String sql = "INSERT INTO itensProduto (codOrdemServico, codProduto, quantidade, preco) VALUES(?,?,?,?)";
          try {
              PreparedStatement stmt = connection.prepareStatement(sql);
              stmt.setInt(1, ordemServicoProduto.getOrdemservico().getCodOrdemServico());
              stmt.setInt(2, ordemServicoProduto.getProduto().getCodProduto());
              stmt.setInt(3, ordemServicoProduto.getQuantidade());
              stmt.setDouble(4, ordemServicoProduto.getPreco());
              stmt.execute();
              return true;
        } catch (SQLException ex) {
              Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
              return false;
        }
    }

    public boolean alterar(ItensProduto ordemServicoProduto){
        String sql = "UPDATE itensProduto SET codOrdemServico=?, codProduto=?, quantidade=?, preco=? WHERE codOrdemServico=? AND codProduto=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServicoProduto.getOrdemservico().getCodOrdemServico());
            stmt.setInt(2, ordemServicoProduto.getProduto().getCodProduto());
            stmt.setInt(3, ordemServicoProduto.getQuantidade());
            stmt.setDouble(4, ordemServicoProduto.getPreco());
            stmt.setInt(5, ordemServicoProduto.getOrdemservico().getCodOrdemServico());
            stmt.setInt(6, ordemServicoProduto.getProduto().getCodProduto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean remover(ItensProduto itensproduto){
        String sql = "DELETE FROM itensProduto WHERE codOrdemServico=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, itensproduto.getOrdemservico().getCodOrdemServico());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
   
    //listar
    public List<ItensProduto> listar(){
        String sql = "SELECT * FROM codOrdemServico";
        List<ItensProduto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                ItensProduto ordemServicoProdutos = new ItensProduto();
                Produto produto = new Produto();
                OrdemServico ordemServico = new OrdemServico();
                
                ordemServicoProdutos.setQuantidade(resultado.getInt("quantidade"));
                ordemServicoProdutos.setPreco(resultado.getDouble("preco"));
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                ordemServico.setCodOrdemServico(resultado.getInt("codOrdemServico"));
                  
                //Dados Produto
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                //Dados ordem de servico
                OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAO();
                ordemServicoDAO.setConnection(connection);
                ordemServico = ordemServicoDAO.buscar(ordemServico);
                
                
                ordemServicoProdutos.setProduto(produto);
                ordemServicoProdutos.setOrdemservico(ordemServico);
                
                retorno.add(ordemServicoProdutos);  
            } 
        } catch (SQLException ex) {
            Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }  
        return retorno;
    }
    
    public List<ItensProduto> listarPorServicoProduto(OrdemServico ordemServico){
        String sql = "SELECT * FROM itensProduto WHERE codOrdemServico=?";
        List<ItensProduto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, ordemServico.getCodOrdemServico());
            ResultSet resultado = stmt.executeQuery();
            while(resultado.next()){
                ItensProduto itensProduto = new ItensProduto();
                Produto produto = new Produto();
                
                itensProduto.setQuantidade(resultado.getInt("quantidade"));
                itensProduto.setPreco(resultado.getDouble("preco"));
                produto.setCodProduto(resultado.getInt("codProduto"));
                
                //Dados Produto
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.setConnection(connection);
                produto = produtoDAO.buscar(produto);
                
                itensProduto.setProduto(produto);
                itensProduto.setOrdemservico(ordemServico);
                
//                System.out.println("nome: " + itensProduto.getProduto());
//                System.out.println("qtd: " + itensProduto.getQuantidade());
//                System.out.println("p: " + itensProduto.getPreco());
//                System.out.println("--------------------------------------------");
                
                retorno.add(itensProduto);  
            }
        } catch (SQLException ex) {
            Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    

    public Map<String, ArrayList> listarQtdProdutosPorMes(String ano) {
        String sql = "SELECT DISTINCT pr.nome, osp.quantidade, ano, mes FROM produtos as pr INNER JOIN itensProduto as osp ON pr.codProduto = osp.codProduto INNER JOIN ordemServico as os ON osp.codOrdemServico = os.codOrdemServico, EXTRACT(year FROM data) as ano, EXTRACT (month FROM data) as mes WHERE ano ='"+ ano +"' --GROUP BY ano, mes --ORDER BY ano, mes";
        Map<String, ArrayList> retorno = new HashMap();
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
        
            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                if (!retorno.containsKey(resultado.getString("nome")))
                {
                    linha.add(resultado.getInt("mes"));
                    linha.add(resultado.getInt("quantidade"));
                    retorno.put(resultado.getString("nome"), linha);
                }else{
                    ArrayList linhaNova = retorno.get(resultado.getString("nome"));
                    linhaNova.add(resultado.getInt("mes"));
                    linhaNova.add(resultado.getInt("quantidade"));
                }
            }
            return retorno;
        } catch (SQLException ex) {
            Logger.getLogger(ItensProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
}
