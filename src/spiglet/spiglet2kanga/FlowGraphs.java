package spiglet.spiglet2kanga;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/5.
 */
public class FlowGraphs {
    private static HashMap<String, FlowGraph> flowGraphHashMap = new HashMap<String, FlowGraph>();
    public static void putFlowGraph(String s, FlowGraph flowGraph) {
        flowGraphHashMap.put(s, flowGraph);
    }
    public static FlowGraph getFlowGraph(String s) {
        return flowGraphHashMap.get(s);
    }
    public static void dump() {
        Iterator iter = flowGraphHashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FlowGraph fl = (FlowGraph)entry.getValue();
            //fl.dump();
        }
    }

    public static void defAndUse() {
        Iterator iter = flowGraphHashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FlowGraph fl = (FlowGraph)entry.getValue();
            //fl.dump();
            fl.defAndUse();
        }
    }
    public static void cal() {
        Iterator iter = flowGraphHashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FlowGraph fl = (FlowGraph)entry.getValue();
            //fl.dump();
            fl.calSuccessor();
        }
    }
    public static void liveAnalyze() {
        Iterator iter = flowGraphHashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FlowGraph fl = (FlowGraph)entry.getValue();
            //fl.dump();
            fl.liveAnalysis();
        }
    }
    public static void buildInterfereGraph() {
        Iterator iter = flowGraphHashMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            FlowGraph fl = (FlowGraph)entry.getValue();
            //fl.dump();
            fl.buildInterfereGraph();
        }
    }
}
