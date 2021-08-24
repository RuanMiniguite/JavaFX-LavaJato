
package javafxmvc.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxmvc.model.domain.Produto;

public class ProdutoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Produto produto) {
        String sql = "INSERT INTO produtos(nome, estoque, preco) VALUES(?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getEstoque());
            stmt.setDouble(3, produto.getPreco());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Produto produto) {
        String sql = "UPDATE produtos SET nome=?, estoque=?, preco=? WHERE codProduto=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getEstoque());
            stmt.setDouble(3, produto.getPreco());
            stmt.setInt(4, produto.getCodProduto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Produto produto) {
        String sql = "DELETE FROM produtos WHERE codProduto=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodProduto());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Produto> listar() {
        String sql = "SELECT * FROM produtos";
        List<Produto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Produto produto = new Produto();
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                produto.setNome(resultado.getString("nome"));
                produto.setEstoque(resultado.getInt("estoque"));
                produto.setPreco(resultado.getDouble("preco"));
                
                retorno.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public List<Produto> listarPorQuantidade(){
        String sql = "SELECT p.codProduto, SUM (i.quantidade) AS quantidade, p.nome FROM   produtos p, itensProduto i WHERE   p.codProduto  = i.codProduto GROUP BY p.codProduto ORDER BY quantidade DESC;";
        List<Produto> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            
            while(resultado.next()){
                Produto produto = new Produto();
                
                produto.setCodProduto(resultado.getInt("codProduto"));
                produto.setNome(resultado.getString("nome"));
                produto.setQtd(resultado.getInt("quantidade"));
                
                retorno.add(produto);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    

    public Produto buscar(Produto produto) {
        String sql = "SELECT * FROM produtos WHERE codProduto=?";
        Produto retorno = new Produto();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodProduto());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setCodProduto(resultado.getInt("codProduto"));
                retorno.setNome(resultado.getString("nome"));
                retorno.setEstoque(resultado.getInt("estoque"));
                retorno.setPreco(resultado.getDouble("preco"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }
    
    public boolean buscarUtilizados(Produto produto){
        String sql = "SELECT COUNT (i.codProduto) FROM itensProduto i INNER JOIN produtos p ON i.codProduto = p.codProduto WHERE i.codProduto =?;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getCodProduto());
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                if(resultado.getInt("count") == 0){
                    return true;
                }else{
                    return false;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       return false; 
    }

}
