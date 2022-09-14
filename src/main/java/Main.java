import db.PostgreSQL;
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
            workspace.open("src/main/resources/models/pps.nlogo");
            workspace.command("set population 10");
            workspace.command("setup");
            workspace.command("repeat 50 [ go ]") ;
            System.out.println(workspace.report("population"));
            workspace.dispose();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
