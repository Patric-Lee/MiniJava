package spiglet.spiglet2kanga;

/**
 * Created by lianxiang on 2017/6/8.
 */
public class KangaStmt {
    private String stmt;
    private String pre;
    private boolean spilled;
    public String getStmt() {
        return stmt;
    }
    public boolean isSpilled() {
        return spilled;
    }
    public Integer getMaxParam() {
        return maxParam;
    }
    public void append(KangaStmt k) {
        if(k != null) {
            if(k instanceof KangaMultiStmt) {
                KangaMultiStmt kg = (KangaMultiStmt)k;
                for(int i = 0; i < kg.getMultiStmt().size(); ++i) {
                    stmt += kg.getMultiStmt().elementAt(i).getStmt();
                    pre += kg.getMultiStmt().elementAt(i).getPre();
                    updateMaxParam(kg.getMultiStmt().elementAt(i).getMaxParam());
                }
            }
            else {
                stmt += k.getStmt();
                pre += k.getPre();
                updateMaxParam(k.getMaxParam());
            }
        }
    }
    public void append(KangaMultiStmt k) {
        for(int i = 0; i < k.getMultiStmt().size(); ++i) {
            stmt += k.getMultiStmt().elementAt(i).getStmt();
            pre += k.getMultiStmt().elementAt(i).getPre();
            updateMaxParam(k.getMultiStmt().elementAt(i).getMaxParam());
        }
    }
    public void clearParam() {
        maxParam = 0;
    }
    public void setPre(String s) {
        pre = s + pre;
    }
    public void addPre(String s) {
        stmt = s + stmt;
    }
    public void addPre() {
        stmt = pre + stmt;
        pre = "";
    }
    public String getPre() {
        return pre;
    }
    public void append(String s) {
        stmt += s;
    }
    public KangaStmt(String s, boolean b) {
        stmt = s;
        pre = "";
        spilled = b;
        maxParam = 0;
    }
    private Integer maxParam;
    public void updateMaxParam(Integer i) {
        if(i > maxParam)
            maxParam = i;
    }
}
