package tikape.runko.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void init() {
        List<String> lauseet = sqliteLauseet();

        // "try with resources" sulkee resurssin automaattisesti lopuksi
        try (Connection conn = getConnection()) {
            Statement st = conn.createStatement();

            // suoritetaan komennot
            for (String lause : lauseet) {
                System.out.println("Running command >> " + lause);
                st.executeUpdate(lause);
            }

        } catch (Throwable t) {
            // jos tietokantataulu on jo olemassa, ei komentoja suoriteta
            System.out.println("Error >> " + t.getMessage());
        }
    }

    private List<String> sqliteLauseet() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("INSERT INTO Annos (nimi) VALUES ('Aurinkolasit')");
        lista.add("INSERT INTO Annos (nimi) VALUES ('Mustikkaplorays');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Banaani');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Appelsiini');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Maito');");
        lista.add("INSERT INTO RaakaAine (nimi) VALUES ('Mustikka');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (1, 1, 1,'3 kpl','-');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (2, 1, 2,'2 kpl','-');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (3, 1, 3,'2 dl','-');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (1, 2, 1,'2 kpl','-');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (4, 2, 2,'5 dl','-');");
        lista.add("INSERT INTO AnnosRaakaAine (raaka_aine_id, annos_id, jarjestys, maara, ohje) VALUES (3, 2, 3,'2 dl','-');");

        return lista;
    }
    
    private List<String> sqliteLauseetII() {
        ArrayList<String> lista = new ArrayList<>();

        // tietokantataulujen luomiseen tarvittavat komennot suoritusjärjestyksessä
        lista.add("CREATE TABLE Annos(id integer PRIMARY KEY, nimi varchar(20));");
        lista.add("CREATE TABLE RaakaAine (id integer PRIMARY KEY, nimi varchar (20));");
        lista.add("CREATE TABLE AnnosRaakaAine (id integer PRIMARY KEY, raaka_aine_id integer, annos_id integer, jarjestys integer, maara varchar(10), ohje varchar (50), FOREIGN KEY (raaka_aine_id) REFERENCES RaakaAine (id), FOREIGN KEY (annos_id) REFERENCES Annos (id));");

        return lista;
    }
}
