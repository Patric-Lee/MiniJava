package spiglet.spiglet2kanga;



import com.thaiopensource.xml.dtd.om.Def;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lianxiang on 2017/6/6.
 */
public class LiveInfo {
    private Set<Integer> inLive, outLive;

    public LiveInfo() {
        inLive = new HashSet<Integer>();
        outLive = new HashSet<Integer>();
    }
    public Set<Integer> getInLive() {
        return inLive;
    }
    public Set<Integer> getOutLive() {
        return outLive;
    }
    public boolean addInLive(Integer k) {
        return inLive.add(k);
    }
    public boolean calInLive(DefAndUse du) {
        Set<Integer> s = new HashSet<Integer>();
        s.addAll(outLive);
        s.removeAll(du.getDef());
        s.addAll(du.getUse());
        if(s.equals(inLive)) {
            inLive = s;
            return false;
        }
        else {
            inLive = s;
            return true;

        }

    }
    public boolean addOutLive(Integer k) {
        return outLive.add(k);
    }
    public boolean addOutLive(Set<Integer> li) {
        return outLive.addAll(li);
    }
    public void clearOutLive() {
        outLive.clear();
    }

}
