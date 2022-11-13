import db.PostgreSQL;
import domain.Experiment;
import org.nlogo.headless.HeadlessWorkspace;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] argv) {

        PostgreSQL postgres = new PostgreSQL(5432, "postgres", "admin", "postgres");
        try {
            postgres.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<HeadlessWorkspace> instances = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HeadlessWorkspace workspace = HeadlessWorkspace.newInstance();
            instances.add(workspace);
        }
        instances.stream().parallel().forEach(workspace -> {
            experiment1(postgres, workspace);
            //experiment2(postgres, workspace);
            try {
                workspace.dispose();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void experiment1(PostgreSQL postgres, HeadlessWorkspace workspace) {
        int[] flameRates = {2, 5, 8, 11};
        try {
            workspace.open("src/main/resources/models/experiment1.nlogo");
            for (int n : flameRates) {
                for (int i = 0; i < 15; i++) {
                    workspace.command("set population 500");
                    workspace.command("set fire 10" );
                    workspace.command("set flame-rate " + n);
                    workspace.command("setup");
                    Double lastScapes = 0.0;
                    Double lastDeaths = 0.0;
                    ArrayList<Integer> scapes = new ArrayList<>();
                    ArrayList<Integer> deaths = new ArrayList<>();
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

                    Experiment e = new Experiment("experiment2", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(),
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
            workspace.open("src/main/resources/models/experiment1.nlogo");
            for (int n : fires) {

                for (int i = 0; i < 15; i++) {
                    workspace.command("set population 500");
                    workspace.command("set fire " + n);
                    workspace.command("set flame-rate 5");
                    workspace.command("setup");
                    Integer last = 0;
                    ArrayList<Integer> scapes = new ArrayList<>();
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

                    Experiment e = new Experiment("experiment2", firePits.intValue(), fireStrength.intValue(), scape.intValue(), death.intValue(),
                            collisions.intValue(), ticks.intValue(), null, null);
                    postgres.saveExperiment(e);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
