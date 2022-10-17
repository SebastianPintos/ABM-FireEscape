package db;

import domain.Experiment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
                    CREATE TABLE IF NOT EXISTS experiments
                    (ID SERIAL PRIMARY KEY ,
                     NAME TEXT NOT NULL,
                     FIRE_PITS INT,
                     FIRE_STRENGTH INT,
                     POPULATION_ALIVE INT,
                     POPULATION_DEAD INT,
                     COLLISIONS INT,
                     DURATION INT)
                """);
        stmt.close();
    }

    public void saveExperiment(Experiment e) throws SQLException {
        Statement stmt = instance.createStatement();
        stmt.executeUpdate(
                "INSERT INTO experiments(NAME, FIRE_PITS, FIRE_STRENGTH, POPULATION_ALIVE, POPULATION_DEAD, COLLISIONS, DURATION)" +
                        "VALUES ('"  + e.name + "','"  + e.firePits + "','"  + e.fireStrength + "', '"  + e.populationAlive + "', '"  + e.populationDead + "', '"  + e.collisions + "', '"  + e.duration + "')");
        stmt.close();
    }
}
