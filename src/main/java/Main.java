import db.PostgreSQL;
import domain.Experiment;
import org.nlogo.headless.HeadlessWorkspace;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) {

        PostgreSQL postgres = new PostgreSQL(5432, "postgres", "admin", "postgres");
        try {
            postgres.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HeadlessWorkspace workspace =
                HeadlessWorkspace.newInstance() ;
        try {
            workspace.open("src/main/resources/models/experiment1.nlogo");
            for(int i = 0; i < 100; i++) {
                workspace.command("set population 200");
                workspace.command("set fire 10");
                workspace.command("set flame-rate 6");
                workspace.command("setup");
                workspace.command("repeat 1000 [ go ]") ;
                Double scape = (Double) workspace.report("scape");
                Double death = (Double) workspace.report("death");
                Double ticks = (Double) workspace.report("final-ticks");

                Experiment e = new Experiment("experiment1", scape.intValue(), death.intValue(), 0, ticks.intValue());
                postgres.saveExperiment(e);
            }
            workspace.dispose();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
