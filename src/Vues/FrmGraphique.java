package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.RingPlot;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.Map;

public class FrmGraphique extends JFrame{
    private JPanel pnlGraph1;
    private JPanel pnlGraph2;
    private JPanel pnlGraph3;
    private JPanel pnlGraph4;
    private JPanel pnlRoot;
    CtrlGraphique ctrlGraphique;

    public FrmGraphique() throws SQLException, ClassNotFoundException {
        this.setTitle("Devoir graphique");
        this.setContentPane(pnlRoot);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        ConnexionBDD cnx = new ConnexionBDD();
        ctrlGraphique = new CtrlGraphique();

        //Graphique 1
        DefaultCategoryDataset data = new DefaultCategoryDataset();
        String age;
        double salaire;
        //On remplit le dataset
        for(int key: ctrlGraphique.getDataGraph1().keySet()){
            age = String.valueOf(key);
            salaire = ctrlGraphique.getDataGraph1().get(key);
            data.addValue(salaire, "", age);
        }
        //On créer le graphique
        JFreeChart chart1 = ChartFactory.createLineChart(
                "Moyenne des salaires par âge",
                "Age",
                "Salaire",
                data,
                PlotOrientation.VERTICAL, false, true, false);
        ChartPanel graph1 = new ChartPanel(chart1);
        pnlGraph1.add(graph1);
        pnlGraph1.validate();


        //Graphique 4
        data = new DefaultCategoryDataset();
        String nomSemestre;
        String nomMagasin;
        double montant;
        //On remplit le dataset
        for(String key: ctrlGraphique.getDataGraph4().keySet()){
            nomSemestre = key;
            for(String key2: ctrlGraphique.getDataGraph4().get(key).keySet()){
                nomMagasin = key2;
                montant = ctrlGraphique.getDataGraph4().get(key).get(key2);
                data.setValue(montant, nomMagasin, nomSemestre);
            }
        }
        //On créer le graphique
        JFreeChart chart4 = ChartFactory.createBarChart(
                "Montant des ventes par magasin",
                "Magasin",
                "Montant des ventes",
                data,
                PlotOrientation.VERTICAL, true, true, false
        );
        ChartPanel graph4 = new ChartPanel(chart4);
        pnlGraph4.add(graph4);
        pnlGraph4.validate();
    }
}
