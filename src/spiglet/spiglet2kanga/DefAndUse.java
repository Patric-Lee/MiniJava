package spiglet.spiglet2kanga;


import spiglet.syntaxtree.Temp;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/6.
 */
public class DefAndUse {
    private Set<Integer> def;
    private Set<Integer> use;
    private String label;
    public void addDef(Integer t) {
        def.add(t);
    }
    public void addUse(Integer t) {
        use.add(t);
    }
    public void setLabel(String s) {
        label = s;
    }
    public DefAndUse() {
        def = new HashSet<Integer>();
        use = new HashSet<Integer>();
    }
    public Set<Integer> getUse() {
        return use;
    }
    public void addUse(DefAndUse d) {
        if(d != null)
        use.addAll(d.getUse());
    }
    public void addDef(DefAndUse d) {
        if(d != null)
        def.addAll(d.getDef());
    }
    public String getLabel() {
        return label;
    }
    public Set<Integer> getDef() {
        return def;
    }
}
