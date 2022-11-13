package db;

import domain.Experiment;

import java.sql.*;

public class PostgreSQL {
    Connection instance;

    public PostgreSQL(int port, String user, String password, String dbName) {
        try {
            Class.forName("org.postgresql.Driver");
            instance = DriverManager
                    .getConnection(String.format("jdbc:postgresql://localhost:%s/%s", port, dbName),
                            user, password);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void createTables() throws SQLException {
        Statement stmt = instance.createStatement();
        stmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS experimentBoxPlot
                    (ID SERIAL PRIMARY KEY ,
                     NAME TEXT NOT NULL,
                     FIRE_PITS INT,
                     FIRE_STRENGTH INT,
                     POPULATION_ALIVE INT,
                     POPULATION_DEAD INT,
                     COLLISIONS INT,
                     DURATION INT,
                     SCAPES  integer[],
                     DEATHS  integer[])
                """);
        stmt.close();
    }

    public void saveExperiment(Experiment e) throws SQLException {

        PreparedStatement pstmt = instance.prepareStatement("INSERT INTO experimentBoxPlot(NAME, FIRE_PITS, FIRE_STRENGTH, POPULATION_ALIVE, POPULATION_DEAD, COLLISIONS, DURATION, SCAPES, DEATHS)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        Array scapes = instance.createArrayOf("int",e.scapes);
        Array deaths = instance.createArrayOf("int",e.deaths);
        pstmt.setString(1, e.name);
        pstmt.setInt(2, e.firePits);
        pstmt.setInt(3, e.fireStrength);
        pstmt.setInt(4, e.populationAlive);
        pstmt.setInt(5, e.populationDead);
        pstmt.setInt(6, e.collisions);
        pstmt.setInt(7, e.duration);
        pstmt.setArray(8, scapes);
        pstmt.setArray(9, deaths);
        pstmt.executeUpdate();
        pstmt.close();
    }
}
