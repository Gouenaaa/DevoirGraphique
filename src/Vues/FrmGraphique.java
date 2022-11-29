package Vues;

import Controlers.CtrlGraphique;
import Entities.DatasGraph;
import Tools.ConnexionBDD;
import org.jfree.chart.*;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.*;
import org.jfree.chart.util.Rotation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultKeyedValues2DDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYZDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
        this.setSize(1200, 900);

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
        CategoryPlot categoryPlot = chart1.getCategoryPlot();
        CategoryAxis domainAxis = categoryPlot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        ChartPanel graph1 = new ChartPanel(chart1);
        pnlGraph1.add(graph1);
        pnlGraph1.validate();

        //Graphique 2
        data = new DefaultCategoryDataset();
        String tranche;
        String sexe;
        int nb;
        //On remplie le dataset
        for(String key: ctrlGraphique.getDataGraph2().keySet()){
            sexe = key;
            for(String key2: ctrlGraphique.getDataGraph2().get(key).keySet()){
                tranche = key2;
                nb = ctrlGraphique.getDataGraph2().get(key).get(key2);
                data.setValue(nb, sexe, tranche);
            }
        }
        //Les données ne sont pas bonnes il faut inverser les clés dans la hashmap
        //On créer le graphique
        JFreeChart chart2 = ChartFactory.createStackedBarChart(
                "Pyramide des ages",
                "Homme/Femmes",
                "Age",
                data,
                PlotOrientation.HORIZONTAL, false, false, false
        );
        ChartPanel graph2 = new ChartPanel(chart2);
        pnlGraph2.add(graph2);
        pnlGraph2.validate();



        //Graphique 3
        DefaultPieDataset data2 = new DefaultPieDataset();
        double pourcentage;
        //On remplit le dataset
        for(String key: ctrlGraphique.getDataGraph3().keySet()){
            sexe = key;
            pourcentage = ctrlGraphique.getDataGraph3().get(key);
            data2.setValue(sexe, pourcentage);
        }
        //On créer le graphique
        JFreeChart chart3 = ChartFactory.createRingChart(
                "Pourcentage de femmes et d'hommes",
                data2, false, true, false
        );
        //On change la couleur
        RingPlot ringPlot = (RingPlot) chart3.getPlot();
        ringPlot.setSectionPaint("Homme", new Color(0, 255, 0));
        ringPlot.setSectionPaint("Femme", new Color(200, 100, 0));
        ringPlot.setStartAngle(0);
        //On affiche le graphique
        ChartPanel graph3 = new ChartPanel(chart3);
        pnlGraph3.add(graph3);
        pnlGraph3.validate();
        pnlGraph3.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                ringPlot.setStartAngle(ringPlot.getStartAngle() + 1);
                ChartPanel graph3 = new ChartPanel(chart3);
                pnlGraph3.add(graph3);
                pnlGraph3.validate();
            }
        });


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
