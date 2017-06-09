package spiglet.spiglet2kanga;

import spiglet.syntaxtree.Stmt;

import java.util.Vector;

/**
 * Created by lianxiang on 2017/6/5.
 */
public class FlowGraphNode {
    private Stmt stmt;
    private Vector<FlowGraphNode> successor;
    private Vector<FlowGraphNode> pre;
    private String label;
    private Integer num;
    private FlowGraph flowGraph;
    private DefAndUse defAndUse;
    private LiveInfo liveInfo;
    public LiveInfo getLiveInfo() {
        return liveInfo;
    }
    public void getOutLive() {

    }
    public boolean updateLiveInfo() {
        //liveInfo.clearOutLive();
        for(int i = 0; i < successor.size(); ++i) {

            liveInfo.addOutLive(successor.elementAt(i).getLiveInfo().getInLive());
        }
        return liveInfo.calInLive(defAndUse);
    }
    public Stmt getStmt() {
        return stmt;
    }
    public void setDefAndUse(DefAndUse d) {
        defAndUse = d;
    }

    public FlowGraphNode(Stmt stmt, FlowGraph fl) {
        this.stmt = stmt;
        liveInfo = new LiveInfo();
        successor = new Vector<FlowGraphNode>();
        pre = new Vector<FlowGraphNode>();
        label = fl.getTmp();
        flowGraph = fl;
    }

    public void setNum(Integer n) {
        num = n;
    }
    public String getLabel() {
        return label;
    }
    public void addPre(FlowGraphNode flowGraphNode) {
        pre.add(flowGraphNode);
    }
    public void addSuc(FlowGraphNode flowGraphNode) {
        successor.add(flowGraphNode);
    }
    public DefAndUse getDefAndUse() {
        return defAndUse;
    }
}
