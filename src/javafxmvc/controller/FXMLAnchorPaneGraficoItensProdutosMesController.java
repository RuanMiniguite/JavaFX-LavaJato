package javafxmvc.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafxmvc.model.dao.ItensProdutoDAO;
import javafxmvc.model.database.Database;
import javafxmvc.model.database.DatabaseFactory;


public class FXMLAnchorPaneGraficoItensProdutosMesController implements Initializable {

    @FXML
    private ComboBox comboBoxGAno;
    private ObservableList<String> observableListAnos;
    
    
    @FXML
    private LineChart<String, Integer> lineChart2;
    @FXML
    private CategoryAxis categoryAxis;
    @FXML
    private NumberAxis numberAxis;
    
    
    private ObservableList<String> observableListMeses = FXCollections.observableArrayList();
    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final ItensProdutoDAO ordemServicoProdutosDAO = new ItensProdutoDAO();
    private String ano;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carregarBoxAno();
        ano = "2021";
        
        comboBoxGAno.getSelectionModel().select("2021");
        comboBoxGAno.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> carregarAno(newValue.toString()));
        setGrafico();
  
    }

    public String retornaNomeMes(int mes) {
        switch (mes) {
            case 1:
                return "Jan";
            case 2:
                return "Fev";
            case 3:
                return "Mar";
            case 4:
                return "Abr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Ago";
            case 9:
                return "Set";
            case 10:
                return "Out";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
            default:
                return "";
        }
    }
    
    public void carregarBoxAno(){
        
        String[] arrayAnos = {"2025", "2024", "2023", "2022", "2021", "2020","2019", "2018"};

        observableListAnos = FXCollections.observableArrayList(arrayAnos);
        comboBoxGAno.setItems(observableListAnos);
    }
    
    public void carregarAno(String x){
        if(ano != null){
            ano = x;
        } else {
            ano = "2021";
        }
       
    }
    
    public void setGrafico(){
        
        lineChart2.getData().clear();
        observableListMeses.clear();
        
        // Obtém an array com nomes dos meses em Inglês.
        String[] arrayMeses = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};
        // Converte o array em uma lista e adiciona em nossa ObservableList de meses.
        observableListMeses.addAll(Arrays.asList(arrayMeses));
        // Associa os nomes de mês como categorias para o eixo horizontal.
        categoryAxis.setCategories(observableListMeses);
        ordemServicoProdutosDAO.setConnection(connection);
        Map<String, ArrayList> dados = ordemServicoProdutosDAO.listarQtdProdutosPorMes(ano);
        for (Map.Entry<String, ArrayList> dadosItem : dados.entrySet()) {
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            series.setName(dadosItem.getKey().toString());
            for (int i = 0; i < dadosItem.getValue().size(); i = i + 2) {
                String mes;
                Integer quantidade;
                mes = retornaNomeMes((int) dadosItem.getValue().get(i));
                quantidade = (Integer) dadosItem.getValue().get(i + 1);
                series.getData().add(new XYChart.Data<>(mes, quantidade));
            }
            lineChart2.getData().add(series);
        }
    
    }

}