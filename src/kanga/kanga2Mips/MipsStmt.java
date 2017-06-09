package kanga.kanga2Mips;
import kanga.syntaxtree.*;
/**
 * Created by lianxiang on 2017/6/3.
 */
public class MipsStmt {
    private String stmt;
    private Node n;
    public String getStmt() {
        return stmt;
    }
    public Node getNode() {
        return n;
    }
    public MipsStmt(String s, Node n) {
        stmt = s;
        this.n = n;
    }
    public MipsStmt(String s) {
        stmt = s;
    }
    public void append(String s) {
        stmt += s;
    }
    public void append(MipsStmt m) {
        if(m != null)
            stmt += m.getStmt();
    }
    public boolean isConstant() {
        if(n != null)
            return n instanceof IntegerLiteral;
        else return false;
    }
    public boolean isReg() {
        if(n != null)
            return n instanceof Reg;
        else return false;
    }
    public boolean isLabel() {
        if(n != null)
            return n instanceof Label;
        else return false;
    }

    public boolean isOperator() {
        if(n!=null)
            return n instanceof BinOp;
        else return false;
    }

}
