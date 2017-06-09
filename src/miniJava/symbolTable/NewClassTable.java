package miniJava.symbolTable;
import miniJava.miniJava2Piglet.*;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by lianxiang on 2017/5/1.
 */
public class NewClassTable extends ClassTable {
    private DTable dTable;
    private VTable vTable;
    public NewClassTable(String s, int line, String parent) {
        /*this.line = line;
        name = s;
        this.parent = parent;*/
        super(s, line, parent);

        // To be modified
        dTable = new DTable();
        vTable = new VTable();
    }
    public DTable getDTable() {
        return dTable;
    }
    public VTable getVTable() {
        return vTable;
    }
    public void computeVTable(VTable v) {
        if(this.getParent() != null) {
            NewClassTable n = (NewClassTable) (ClassTree.getClassTable(this.getParent()));
            n.computeVTable(v);
        }
        Map map = localVariables;
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            //entry.getKey();
            //entry.getValue();
            v.addVariable((String)entry.getKey());
        }
    }
    public void computeDTable(DTable d) {
        if(this.getParent() != null) {
            NewClassTable n = (NewClassTable) (ClassTree.getClassTable(this.getParent()));
            n.computeDTable(d);
        }
        Map map = methods;
        Iterator iter = map.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            //entry.getKey();
            //entry.getValue();

            d.addMethod((String)entry.getKey());
        }
    }
}
