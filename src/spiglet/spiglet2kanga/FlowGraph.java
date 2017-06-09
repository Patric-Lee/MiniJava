package spiglet.spiglet2kanga;

import spiglet.syntaxtree.CJumpStmt;
import spiglet.syntaxtree.ErrorStmt;
import spiglet.syntaxtree.JumpStmt;
import spiglet.visitor.DUVisitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/5.
 */
public class FlowGraph {
    private Vector<FlowGraphNode> flowGraphNodeVector;
    private HashMap<String, FlowGraphNode> labelStmt;
    private String name;
    private String tmp;
    private LiveInfo exitLiveInfo;
    private InterfereGraph interfereGraph;
    private HashMap<Integer, Integer> assigned;
    private Integer numSpilledParam;
    public void buildInterfereGraph() {
        for(int i = 0; i < flowGraphNodeVector.size(); ++i) {
            LiveInfo lf = flowGraphNodeVector.elementAt(i).getLiveInfo();
            Set<Integer> inSet = lf.getInLive();
            Iterator iter1 = inSet.iterator();
            while(iter1.hasNext()) {
                Integer p = (Integer)(iter1.next());
                Iterator iter2 = inSet.iterator();
                while(iter2.hasNext()) {
                    Integer q = (Integer)(iter2.next());
                    if(p != q)
                        interfereGraph.addEdge(p, q);
                }
            }
            iter1 = lf.getOutLive().iterator();
            while(iter1.hasNext()) {
                Integer p = (Integer)(iter1.next());
                if(p == 224)
                    System.out.println("a");;
                Set<Integer> defs = flowGraphNodeVector.elementAt(i).getDefAndUse().getDef();
                Iterator<Integer> iter2 = defs.iterator();
                while(iter2.hasNext()) {
                    Integer q = iter2.next();
                    if (!p.equals(q)) {
                        interfereGraph.addEdge(p, q);
                    }
                }
            }
        }
        interfereGraph.preColor(18);
        assigned = interfereGraph.color(18);

    }
    public Integer getAssigned(Integer i) {
        return assigned.get(i);
    }
    public Integer getSpilled(Integer i) {
        return interfereGraph.getToBeSpilled().indexOf(i) + numSpilledParam ;
    }
    public Integer getSpilledNum() {
        return interfereGraph.getToBeSpilled().size() + numSpilledParam;
    }
    private Vector<String> regs = new Vector<String>();
    public String iToReg(Integer i) {
        return regs.elementAt(i);
    }
    public KangaStmt getReg(Integer i) {
        Integer v = getAssigned(i);
        if(v != null) {
            return new KangaStmt(iToReg(v), false);
        }
        v = getSpilled(i);
        if(v !=  numSpilledParam - 1) {
            return new KangaStmt("SPILLEDARG " + v, true);
        }
        return new KangaStmt( "v1", false);
    }
    public boolean isStmtLabel(String s) {
        return labelStmt.get(s) != null;
    }
    public void calSuccessor() {
        for(int i = 0; i < flowGraphNodeVector.size(); ++i) {
            FlowGraphNode fl = flowGraphNodeVector.elementAt(i);
            if(fl.getStmt().f0.choice instanceof JumpStmt) {
                fl.addSuc(labelStmt.get(fl.getDefAndUse().getLabel()));
            }
            else if(fl.getStmt().f0.choice instanceof CJumpStmt) {
                fl.addSuc(labelStmt.get(fl.getDefAndUse().getLabel()));
                if(i + 1 < flowGraphNodeVector.size() )
                    fl.addSuc(flowGraphNodeVector.elementAt(i + 1));

            }
            else if(!(fl.getStmt().f0.choice instanceof ErrorStmt)) {
                if(i + 1 < flowGraphNodeVector.size() )
                    fl.addSuc(flowGraphNodeVector.elementAt(i + 1));
            }

        }
    }
    public void defAndUse() {
        DUVisitor duVisitor = new DUVisitor();
        for(int i = 0; i < flowGraphNodeVector.size(); ++i) {
            FlowGraphNode fn = flowGraphNodeVector.elementAt(i);
            duVisitor.visit(fn.getStmt(), fn);
        }
    }
    public void setTmp(String s) {
        tmp = s;
    }
    public String getName() {
        return name;
    }
    public void addNode(FlowGraphNode flowGraphNode) {
        flowGraphNode.setNum(flowGraphNodeVector.size());
        flowGraphNodeVector.add(flowGraphNode);
        if(flowGraphNode.getLabel()!= null)
            labelStmt.put(flowGraphNode.getLabel(), flowGraphNode);
    }
    public String getTmp() {
        return tmp;
    }
    public FlowGraph(String s, Integer ii) {
        name = s;
        numSpilledParam = ii - 4;
        if(numSpilledParam < 0) numSpilledParam = 0;
        interfereGraph = new InterfereGraph();
        flowGraphNodeVector = new Vector<FlowGraphNode>();
        labelStmt = new HashMap<>();
        FlowGraphs.putFlowGraph(s, this);
        exitLiveInfo = new LiveInfo();
        for(int i = 0; i < 8; ++i) {
            regs.add("s" + i);
        }
        for(int i = 0; i < 10; ++i) {
            regs.add("t" + i);
        }
    }
    public void setExitLiveInfo(Integer k) {
        exitLiveInfo.addInLive(k);
    }
    public void liveAnalysis() {

        boolean flag = true;
        while(flag) {
            flowGraphNodeVector.elementAt(flowGraphNodeVector.size() - 1).getLiveInfo().addOutLive(exitLiveInfo.getInLive());
            flag = false;
            for (int i = flowGraphNodeVector.size() - 1; i >= 0; --i) {
                FlowGraphNode fn = flowGraphNodeVector.elementAt(i);
                if(i < flowGraphNodeVector.size() - 1)
                    fn.getLiveInfo().clearOutLive();
                flag = fn.updateLiveInfo() || flag;

            }
        }

        for (int i = 0; i < flowGraphNodeVector.size(); ++i) {
            FlowGraphNode fn = flowGraphNodeVector.elementAt(i);
            Set<Integer> s = fn.getLiveInfo().getInLive();
            System.out.println(fn.getStmt().toString());
            if(fn.getLabel() != null)
                System.out.println("????????????????????????????????????????" + fn.getLabel());

            Iterator iter = s.iterator();
            while(iter.hasNext()) {
                Integer k = (Integer)(iter.next());
                System.out.println(k);
            }
        }
        System.out.println("######");
    }
}
