package piglet.piglet2spiglet;

/**
 * Created by lianxiang on 2017/5/31.
 */
public class SpigletStmt {
    private String stmt;
    private boolean simple;
    private Integer tmp;
    public SpigletStmt(String s) {
        stmt = s;
        simple = false;
        tmp = -1;
    }
    public String getStmt() {
        return stmt;
    }
    public void setTmp(Integer s) {
        tmp = s;
    }
    public Integer getTmp() {
        return tmp;
    }
    public boolean isTemp() {
        //null?
        return stmt.startsWith("TEMP");
    }
    public void appendStmt(String s) {
        stmt += s;
    }
    public void appendStmt(SpigletStmt s) {
        if(s == null) return;
        if(s instanceof SpigletMultiStmt) {
            for(int i = 0; i < ((SpigletMultiStmt) s).getStmts().size(); i++) {
                stmt += ((SpigletMultiStmt) s).getStmts().elementAt(i).getStmt();
            }
        }
        else
           stmt += s.getStmt();

    }

    public void flipSimple() {
        simple = true;
    }
    public boolean isSimple() {
        return simple;
    }
}
