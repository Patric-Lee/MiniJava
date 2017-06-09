package piglet.piglet2spiglet;

import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/2.
 */
public class SpigletMultiStmt extends SpigletStmt {
    private Vector<SpigletStmt> stmts;
    public Vector<SpigletStmt> getStmts() {
        return stmts;
    }
    public SpigletMultiStmt(){
        super("");
        stmts = new Vector<SpigletStmt>();

    }

    public void pushStmt(SpigletStmt s) {
        stmts.add(s);
    }
}
