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
        HeadlessWorkspace workspace1 = HeadlessWorkspace.newInstance();
        HeadlessWorkspace workspace2 = HeadlessWorkspace.newInstance();
        HeadlessWorkspace workspace3 = HeadlessWorkspace.newInstance();

        experiment1(postgres, workspace1);
        experiment2(postgres, workspace2);
        experiment3(postgres, workspace3);
        return;
    }

    private static void experiment1(PostgreSQL postgres, HeadlessWorkspace workspace) {
        int[] flameRates = {2, 5, 8, 11};
        try {
            workspace.open("src/main/resources/models/2_doors.nlogo");
            for (int n : flameRates) {
                for (int i = 0; i < 250; i++) {
                    workspace.command("set population 500");
                    workspace.command("set fire 10" );
                    workspace.command("set flame-rate " + n);
                    workspace.command("setup");
                    for(int j = 0; j < 400; j++){
                        workspace.command("go");
                        if((Double) workspace.report("final-ticks") > 0) break;
                    }
                    Double scape = (Double) workspace.report("scape");
                    Double death = (Double) workspace.report("death");
                    Double ticks = (Double) workspace.report("final-ticks");
                    Double firePits = (Double) workspace.report("fire");
                    Double fireStrength = (Double) workspace.report("flame-rate");
                    Double collisions = (Double) workspace.report("collisions");

                    Experiment e = new Experiment("2_doors", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(),
                            collisions.intValue(), ticks.intValue(), null, null);
                    postgres.saveExperiment(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void experiment2(PostgreSQL postgres, HeadlessWorkspace workspace) {
        int[] fires = {5, 10, 15, 20};
        try {
            workspace.open("src/main/resources/models/4_doors.nlogo");
            for (int n : fires) {

                for (int i = 0; i < 250; i++) {
                    workspace.command("set population 500");
                    workspace.command("set fire " + n);
                    workspace.command("set flame-rate 5");
                    workspace.command("setup");
                    for(int j = 0; j < 400; j++){
                        workspace.command("go");
                        if((Double) workspace.report("final-ticks") > 0) break;
                    }
                    Double scape = (Double) workspace.report("scape");
                    Double death = (Double) workspace.report("death");
                    Double ticks = (Double) workspace.report("final-ticks");
                    Double firePits = (Double) workspace.report("fire");
                    Double fireStrength = (Double) workspace.report("flame-rate");
                    Double collisions = (Double) workspace.report("collisions");

                    Experiment e = new Experiment("4_doors", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(),
                            collisions.intValue(), ticks.intValue(), null, null);
                    postgres.saveExperiment(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void experiment3(PostgreSQL postgres, HeadlessWorkspace workspace) {
        int[] fires = {5, 10, 15, 20};
        try {
            workspace.open("src/main/resources/models/4_doors_no_collisions.nlogo");
            for (int n : fires) {

                for (int i = 0; i < 250; i++) {
                    workspace.command("set population 500");
                    workspace.command("set fire " + n);
                    workspace.command("set flame-rate 5");
                    workspace.command("setup");
                    for(int j = 0; j < 400; j++){
                        workspace.command("go");
                        if((Double) workspace.report("final-ticks") > 0) break;
                    }
                    Double scape = (Double) workspace.report("scape");
                    Double death = (Double) workspace.report("death");
                    Double ticks = (Double) workspace.report("final-ticks");
                    Double firePits = (Double) workspace.report("fire");
                    Double fireStrength = (Double) workspace.report("flame-rate");
                    Double collisions = (Double) workspace.report("collisions");

                    Experiment e = new Experiment("4_doors_no_collisions", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(),
                            collisions.intValue(), ticks.intValue(), null, null);
                    postgres.saveExperiment(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
