package miniJava.symbolTable;

import visitor.SymbolVisitor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class ClassTree {
	public static final HashMap<String, ClassTable> classes = new HashMap<String, ClassTable>();
	public static void computeDVTable() {
		Iterator iter = classes.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();

			NewClassTable newClassTable = ((NewClassTable)entry.getValue());
			newClassTable.computeDTable(newClassTable.getDTable());
			newClassTable.computeVTable(newClassTable.getVTable());
		}
	}
	public static boolean addClass(ClassTable c) {
		if(classes.containsKey(c.getName())) {
			System.out.println(c.getName() + " has been defined.");
			return false;
		}
		classes.put(c.getName(), c);
		return true;
	}
	public static void buildSymbolTable(syntaxtree.Goal goal) {
		SymbolVisitor symbolVisitor = new SymbolVisitor();
		symbolVisitor.visit(goal, null);
		// To be written
	}
	public static ClassTable getClassTable(String name) {
		return classes.get(name);
	}
	public static void doesFatherExist() {
		Iterator iter = classes.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry m = (Map.Entry)iter.next();
			ClassTable t = (ClassTable)m.getValue();
			if(t.getParent() != null) {
				if(classes.containsKey(t.getParent())) continue;
				else {
				System.out.println(t.getName() + "'s declared parent "+ t.getParent()+" is undefined.");
				}
			}
		}
	}
	public static void canOverride() {
		Iterator iter = classes.entrySet().iterator();
		while(iter.hasNext()) {
			Map.Entry m = (Map.Entry)iter.next();
			ClassTable t = (ClassTable)m.getValue();
			t.canOverride();
		}
	}
	public static boolean isInheritanceAcylic() {
		boolean flag = true;
		HashMap<String, ClassTable> visited = new HashMap<String, ClassTable>();
		Iterator iter = classes.entrySet().iterator();
		while(iter.hasNext()) {
			ClassTable here = (ClassTable)((Map.Entry)iter.next()).getValue();
			if(visited.containsKey(here.getName())) continue;
			else {
				ClassTable tmp = here;
				visited.put(tmp.getName(), tmp);
				HashMap<String, ClassTable> checked = new HashMap<String, ClassTable>();
				checked.put(tmp.getName(), tmp);
				while(tmp.getParent() != null) {
					if(checked.containsKey(tmp.getParent())) {
						System.out.println(tmp.getParent() + " Cyclic inheritace!");
						flag =  false;
						break;
					}
					else if(classes.containsKey(tmp.getParent())){
						checked.put(tmp.getParent(), classes.get(tmp.getParent()));
						visited.put(tmp.getParent(), classes.get(tmp.getParent()));
						tmp = classes.get(tmp.getParent());
					}
					else break;//Parents undefined
				}
			}



		}
		return flag;
	}
}
