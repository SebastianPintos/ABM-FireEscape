import db.PostgreSQL;
import domain.Experiment;
import org.nlogo.headless.HeadlessWorkspace;

import java.sql.SQLException;

public class Main {
    public static void main(String[] argv) throws InterruptedException {
        int[] flameRates = {2, 5, 8, 11};
        int[] fires = {5, 10, 15, 20};
        PostgreSQL postgres = new PostgreSQL(5432, "postgres", "admin", "postgres");
        try {
            postgres.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HeadlessWorkspace workspace1 = HeadlessWorkspace.newInstance();
        HeadlessWorkspace workspace2 = HeadlessWorkspace.newInstance();
        HeadlessWorkspace workspace3 = HeadlessWorkspace.newInstance();

        experiment1(postgres, workspace1, fires, new int[]{5});
        experiment1(postgres, workspace1, new int[]{10}, flameRates);

        experiment2(postgres, workspace2, fires, new int[]{5});
        experiment2(postgres, workspace2, new int[]{10}, flameRates);

        experiment3(postgres, workspace3, fires, new int[]{5});
        experiment3(postgres, workspace3, new int[]{10}, flameRates);

        workspace1.dispose();
        workspace2.dispose();
        workspace3.dispose();
    }

    private static void experiment1(PostgreSQL postgres, HeadlessWorkspace workspace, int[] fires, int[] flameRates) {
        try {
            workspace.open("src/main/resources/models/2_doors.nlogo");
            for (int n : fires) {
                for (int k : flameRates) {
                    for (int i = 0; i < 200; i++) {
                        workspace.command("set population 500");
                        workspace.command("set fire " + n);
                        workspace.command("set flame-rate " + k);
                        workspace.command("setup");
                        for (int j = 0; j < 400; j++) {
                            workspace.command("go");
                            if ((Double) workspace.report("final-ticks") > 0) break;
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void experiment2(PostgreSQL postgres, HeadlessWorkspace workspace, int[] fires, int[] flameRates) {
        try {
            workspace.open("src/main/resources/models/4_doors_no_collisions.nlogo");
            for (int n : fires) {
                for (int k : flameRates) {
                    for (int i = 0; i < 200; i++) {
                        workspace.command("set population 500");
                        workspace.command("set fire " + n);
                        workspace.command("set flame-rate " + k);
                        workspace.command("setup");
                        for (int j = 0; j < 400; j++) {
                            workspace.command("go");
                            if ((Double) workspace.report("final-ticks") > 0) break;
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void experiment3(PostgreSQL postgres, HeadlessWorkspace workspace, int[] fires, int[] flameRates) {
        try {
            workspace.open("src/main/resources/models/4_doors.nlogo");
            for (int n : fires) {
                for (int k : flameRates) {
                    for (int i = 0; i < 200; i++) {
                        workspace.command("set population 500");
                        workspace.command("set fire " + n);
                        workspace.command("set flame-rate " + k);
                        workspace.command("setup");
                        for (int j = 0; j < 400; j++) {
                            workspace.command("go");
                            if ((Double) workspace.report("final-ticks") > 0) break;
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
