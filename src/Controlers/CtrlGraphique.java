package Controlers;

import Entities.DatasGraph;
import Tools.ConnexionBDD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtrlGraphique
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public CtrlGraphique() {
        cnx = ConnexionBDD.getCnx();
    }

    public HashMap<Integer, Double> getDataGraph1() throws SQLException {
        HashMap<Integer, Double> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT ROUND(AVG(employe.salaireEmp), 2), employe.ageEmp\n" +
                "FROM employe\n" +
                "GROUP BY employe.ageEmp\n" +
                "ORDER BY employe.ageEmp ASC");
        rs = ps.executeQuery();
        while(rs.next()){
            data.put(rs.getInt("ageEmp"), rs.getDouble(1));
        }
        return data;
    }

    public HashMap<String, HashMap<String, Double>> getDataGraph4() throws SQLException {
        HashMap<String, HashMap<String, Double>> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT vente.nomSemestre, SUM(vente.montant), vente.nomMagasin\n" +
                "FROM vente\n" +
                "GROUP BY vente.nomSemestre, vente.nomMagasin");
        rs = ps.executeQuery();
        while(rs.next()){
            if(data.containsKey(rs.getString("nomSemestre"))){
                data.get(rs.getString("nomSemestre")).put(rs.getString("nomMagasin"), rs.getDouble(2));
            }
            else{
                HashMap<String, Double> magasin = new HashMap<>();
                magasin.put(rs.getString("nomMagasin"), rs.getDouble(2));
                data.put(rs.getString("nomSemestre"), magasin);
            }
        }
        return data;
    }

}
