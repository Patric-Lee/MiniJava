package spiglet.spiglet2kanga;

import java.util.*;

/**
 * Created by lianxiang on 2017/6/6.
 */
public class InterfereGraph {
    public Set<Integer> temps;
    public HashMap<Integer, InterfereNode > tempHashMap;
    private Stack<Integer> toBeColored;
    private Vector<Integer> toBeSpilled;
    public InterfereNode getInterfereNode(Integer i) {
        return tempHashMap.get(i);
    }
    public InterfereGraph() {
        temps = new HashSet<Integer>();
        tempHashMap = new HashMap<Integer, InterfereNode>();
        toBeColored = new Stack<>();
        toBeSpilled = new Vector<>();
    }
    public void addEdge(Integer i, Integer j) {
        if(!temps.contains(i)) {
            temps.add(i);
            tempHashMap.put(i, new InterfereNode(i));
        }
        if(!temps.contains(j)) {
            temps.add(j);
            tempHashMap.put(j, new InterfereNode(j));
        }
        InterfereNode ni = tempHashMap.get(i), nj = tempHashMap.get(j);
        ni.addInterfereTemps(j);
        nj.addInterfereTemps(i);
    }
    public void preColor(Integer numColor) {
        Set<Integer> stmp = new HashSet<Integer>(temps);
        while(stmp.size() > 0) {
            Iterator<Integer> iter = stmp.iterator();
            boolean flag = false;
            while(iter.hasNext()) {
                Integer x = iter.next();
                InterfereNode nx = tempHashMap.get(x);
                if(nx.getNumIntefere() > numColor - 1) {
                    continue;
                }
                else {
                    toBeColored.add(x);
                    iter.remove();
                    Set<Integer> modify = nx.getInterfereTemps();
                    Iterator<Integer> it = modify.iterator();
                    while(it.hasNext()) {
                        tempHashMap.get(it.next()).removeInterfereTemps(x);

                    }
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                Integer x = stmp.iterator().next();
                InterfereNode nx = tempHashMap.get(x);
                toBeSpilled.add(x);
                iter.remove();
                Set<Integer> modify = nx.getInterfereTemps();
                Iterator<Integer> it = modify.iterator();
                while(it.hasNext()) {
                    tempHashMap.get(it.next()).removeInterfereTemps(x);
                }
            }
        }
    }

    public HashMap<Integer, Integer> color(Integer numColor) {
        HashMap<Integer, Integer> assign = new HashMap<Integer, Integer>();
        while(!toBeColored.isEmpty()) {
            Integer now = toBeColored.pop();
            out:for(Integer i = 0; i < numColor; ++i) {
                Iterator<Integer> iter = tempHashMap.get(now).getInterfereTemps().iterator();
                while(iter.hasNext()) {
                    Integer neigh = iter.next();
                    if(assign.get(neigh) != null && assign.get(neigh) == i)
                        continue out;
                }
                assign.put(now, i);
                break;
            }
        }
        return assign;
    }
    public Vector<Integer> getToBeSpilled() {
        return toBeSpilled;
    }

}
class InterfereNode {
    //private Integer numIntefere;
    private Integer no;
    private Set<Integer> interfereTemps;
    public Integer getNo() {
        return no;
    }
    public Set<Integer> getInterfereTemps() {
        return interfereTemps;
    }
    public InterfereNode(Integer i) {
        no = i;
        interfereTemps = new HashSet<Integer>();
    }
    public Integer getNumIntefere() {
        return interfereTemps.size();
        //return numIntefere;
    }
    public void addInterfereTemps(Integer k) {
        interfereTemps.add(k);
    }
    public void removeInterfereTemps(Integer k) {
        interfereTemps.remove(k);
    }
}
