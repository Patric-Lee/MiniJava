package miniJava.miniJava2Piglet;

import java.util.*;

/**
 * Created by lianxiang on 2017/4/24.
 */
public class VTable {
    private int base = 4;
    private Hashtable<String, Integer> variableOffset;
    private Vector<String> variables;
    private Vector<Integer> offsets;
    private void setBase(int offset) {
        base += offset;
    }
    public VTable() {
        variables = new Vector<String>();
        variableOffset = new Hashtable<String, Integer>();
    }
    public VTable(VTable ancestor) {
        variables = ancestor.variables;
        variableOffset = ancestor.variableOffset;
    }
    public Vector<String> getVariables() {
        return variables;
    }
    public int getVariableOffset(String var) {
        for(int j = variables.size(); j > 0; j--) {
            if(variables.get(j).equals(var)) {
                return offsets.get(j);
            }
        }

        // Should not be here;
        return variableOffset.get(var);
    }
    public void addVariable(String var) {
        variableOffset.put(var, base + 4 * variables.size());
        offsets.add(base + 4 * variables.size());
        variables.add(var);


    }
}
