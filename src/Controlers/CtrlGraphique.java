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

    public HashMap<String, HashMap<String, Integer>> getDataGraph2() throws SQLException {
        HashMap<String, HashMap<String, Integer>> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT COUNT(employe.numEmp), employe.ageEmp, employe.sexe\n" +
                "FROM employe\n" +
                "GROUP BY employe.sexe, employe.ageEmp\n" +
                "ORDER BY employe.ageEmp");
        rs = ps.executeQuery();
        while(rs.next()){
            if(rs.getString("sexe").compareTo("Homme") == 0){
                if(data.containsKey("Homme")){
                    if(rs.getInt("ageEmp") < 20){
                        if(data.get("Homme").containsKey("< 20")){
                            data.get("Homme").put("< 20", rs.getInt(1)-data.get("Homme").get("< 20"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("< 20", 0-rs.getInt(1));
                            data.put("Homme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 30){
                        if(data.get("Homme").containsKey("20 - 29")){
                            data.get("Homme").put("20 - 29", rs.getInt(1)-data.get("Homme").get("20 - 29"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("20 - 29", 0-rs.getInt(1));
                            data.put("Homme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 40){
                        if(data.get("Homme").containsKey("30 - 39")){
                            data.get("Homme").put("30 - 39", rs.getInt(1)-data.get("Homme").get("30 - 39"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("30 - 39", 0-rs.getInt(1));
                            data.put("Homme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 50){
                        if(data.get("Homme").containsKey("40 - 49")){
                            data.get("Homme").put("40 - 49", rs.getInt(1)-data.get("Homme").get("40 - 49"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("40 - 49", 0-rs.getInt(1));
                            data.put("Homme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") >= 50){
                        if(data.get("Homme").containsKey("50+")){
                            data.get("Homme").put("50+", rs.getInt(1)-data.get("Homme").get("50+"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("50+", 0-rs.getInt(1));
                            data.put("Homme", tranche);
                        }
                    }
                }
                else{
                    HashMap<String, Integer> truc = new HashMap<>();
                    truc.put("< 20", 0);
                    data.put("Homme", truc);
                }
            }
            else{
                if(data.containsKey("Femme")){
                    if(rs.getInt("ageEmp") < 20){
                        if(data.get("Femme").containsKey("< 20")){
                            data.get("Femme").put("< 20", rs.getInt(1)+data.get("Femme").get("< 20"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("< 20", rs.getInt(1));
                            data.put("Femme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 30){
                        if(data.get("Femme").containsKey("20 - 29")){
                            data.get("Femme").put("20 - 29", rs.getInt(1)+data.get("Femme").get("20 - 29"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("20 - 29", rs.getInt(1));
                            data.put("Femme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 40){
                        if(data.get("Femme").containsKey("30 - 39")){
                            data.get("Femme").put("30 - 39", rs.getInt(1)+data.get("Femme").get("30 - 39"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("30 - 39", rs.getInt(1));
                            data.put("Femme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") < 50){
                        if(data.get("Femme").containsKey("40 - 49")){
                            data.get("Femme").put("40 - 49", rs.getInt(1)+data.get("Femme").get("40 - 49"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("40 - 49", rs.getInt(1));
                            data.put("Femme", tranche);
                        }
                    } else if(rs.getInt("ageEmp") >= 50){
                        if(data.get("Femme").containsKey("50+")){
                            data.get("Femme").put("50+", rs.getInt(1)+data.get("Femme").get("50+"));
                        }
                        else{
                            HashMap<String, Integer> tranche = new HashMap<>();
                            tranche.put("50+", rs.getInt(1));
                            data.put("Femme", tranche);
                        }
                    }
                }
                else{
                    HashMap<String, Integer> truc = new HashMap<>();
                    truc.put("< 20", 0);
                    data.put("Femme", truc);
                }
            }
        }
        return data;
    }

    public HashMap<String, Double> getDataGraph3() throws SQLException {
        HashMap<String, Double> data = new HashMap<>();
        ps = cnx.prepareStatement("SELECT SUM((SELECT COUNT(employe.numEmp) FROM employe WHERE employe.sexe = \"Homme\") / (SELECT COUNT(employe.numEmp) FROM employe) * 100)");
        rs = ps.executeQuery();
        if(rs.next()){
            data.put("Homme", rs.getDouble(1));
            data.put("Femme", 100-rs.getDouble(1));
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
