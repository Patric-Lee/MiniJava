package miniJava.miniJava2Piglet;

import miniJava.symbolTable.VariableType;

/**
 * Created by lianxiang on 2017/4/24.
 */
public class PigletStatement {
    private String pigletCodes = "";
    private String returnValue;
    private VariableType returnType;
    private String baseAddress;
    public String getPigletCodes() {
        return pigletCodes;
    }
    public void appendCodes(String s) {
        pigletCodes += s;
    }
    public void setReturnValue(String s) {
        returnValue = s;
    }
    public String getReturnValue() {
        return returnValue;
    }
    public void appendCodes(PigletStatement p) {
        if(p != null)
            pigletCodes += p.getPigletCodes();
    }
    public void setReturnType(VariableType v) {
        returnType = v;
    }
    public VariableType getReturnType() {
        return returnType;
    }
}
