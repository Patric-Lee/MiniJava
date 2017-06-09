package miniJava.miniJava2Piglet;


import java.util.*;
/**
 * Created by lianxiang on 2017/4/24.
 */
public class DTable {
    private Hashtable<String, String> methodSignature;
    private Vector<String> methodList;
    private Hashtable<String, Integer> methodOffset;
    public DTable() {
        methodList = new Vector<String>();
        methodSignature = new Hashtable<String, String>();
        methodOffset = new Hashtable<String, Integer>();
    }
    public DTable(DTable ancestor) {
        methodSignature = ancestor.methodSignature;
        methodList = ancestor.methodList;
        methodOffset = ancestor.methodOffset;
    }
    public Vector<String> getMethodList() {
        return methodList;
    }
    public void addMethod(String method) {
        methodOffset.put(method, 4 * methodList.size());
        methodList.add(method);
    }
    public Integer getOffset(String m) {
        return methodOffset.get(m);
    }
    public Integer size() {
        return methodList.size();
    }

}
