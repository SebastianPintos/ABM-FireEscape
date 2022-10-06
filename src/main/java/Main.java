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
                HeadlessWorkspace.newInstance();
        int[] flameRates = {2, 5, 8, 11};

        try {
            workspace.open("src/main/resources/models/experiment1.nlogo");
            for (int n : flameRates) {
                for (int i = 0; i < 1000; i++) {
                    workspace.command("set population 200");
                    workspace.command("set fire 10");
                    workspace.command("set flame-rate " + n);
                    workspace.command("setup");
                    workspace.command("repeat 400 [ go ]");
                    Double scape = (Double) workspace.report("scape");
                    Double death = (Double) workspace.report("death");
                    Double ticks = (Double) workspace.report("final-ticks");
                    Double firePits = (Double) workspace.report("fire");
                    Double fireStrength = (Double) workspace.report("flame-rate");

                    Experiment e = new Experiment("experiment1", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(), 0, ticks.intValue());
                    postgres.saveExperiment(e);
                }
            }
            workspace.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
