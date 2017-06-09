package spiglet.spiglet2kanga;

import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/8.
 */
public class KangaMultiStmt extends KangaStmt {
    private Vector<KangaStmt> multiStmt;
    public KangaMultiStmt() {
        super("", false);
        multiStmt = new Vector<>();
    }
    public void addStmt(KangaStmt s) {
        multiStmt.add(s);
        updateMaxParam(s.getMaxParam());
    }
    public Vector<KangaStmt> getMultiStmt() {
        return multiStmt;
    }
}
