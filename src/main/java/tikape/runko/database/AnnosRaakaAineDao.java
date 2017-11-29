/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.RaakaAine;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;


public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {
    
    private Database database;
    private AnnosDao annosDao;
    private RaakaAineDao raakaAineDao;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
        this.annosDao = new AnnosDao(database);
        this.raakaAineDao = new RaakaAineDao(database);   
    }
    
    public void saveAnnosRaakaAine(AnnosRaakaAine ara) throws SQLException {
        Connection connection = database.getConnection();
        
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (?, ?, ?, ?, ?)");
        stmt.setInt(1, ara.getRaakaAine().getId());
        stmt.setInt(2, ara.getAnnos().getId());
        stmt.setInt(3, ara.getJarjestys());
        stmt.setString(4, ara.getMaara());
        stmt.setString(5, ara.getOhje());
        stmt.executeUpdate();
        stmt.close();
    }
    
    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        
        Integer raid = rs.getInt("raaka_aine_id");
        RaakaAine ra = raakaAineDao.findOne(raid);
        
        Integer aid = rs.getInt("annos_id");
        Annos a = annosDao.findOne(aid);
        
        Integer jarjestys = rs.getInt("jarjestys");
        String maara = rs.getString("maara");
        String ohje = rs.getString("ohje");

        AnnosRaakaAine ara = new AnnosRaakaAine(id, ra, a, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return ara;
    }
    
    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            
            Integer raid = rs.getInt("raaka_aine_id");
            RaakaAine ra = raakaAineDao.findOne(raid);
        
            Integer aid = rs.getInt("annos_id");
            Annos a = annosDao.findOne(aid);
            
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            

            annosRaakaAineet.add(new AnnosRaakaAine(id, ra, a, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAineet;
    }
    
    
    public List<AnnosRaakaAine> findAllForAnnosKey(Integer key) throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE annos_id = ? ORDER BY jarjestys ASC");
        stmt.setObject(1, key);
        

        ResultSet rs = stmt.executeQuery();
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            
            Integer raid = rs.getInt("raaka_aine_id");
            RaakaAine ra = raakaAineDao.findOne(raid);
        
            Integer aid = rs.getInt("annos_id");
            Annos a = annosDao.findOne(aid);
            
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");
            

            annosRaakaAineet.add(new AnnosRaakaAine(id, ra, a, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAineet;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM AnnosRaakaAine WHERE id = ?");
        stmt.setInt(1, key);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
}
