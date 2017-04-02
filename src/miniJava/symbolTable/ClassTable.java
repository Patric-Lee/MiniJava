package miniJava.symbolTable;



import org.jetbrains.annotations.TestOnly;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class ClassTable extends SymbolTable {
	private HashMap<String, MethodTable> methods = new HashMap<String, MethodTable>();
	private HashMap<String, Variable> localVariables = new HashMap<String, Variable>();
	private int line;
	private String parent;
	private String name;

	public ClassTable(String s, int line, String parent) {
		this.line = line;
		name = s;
		this.parent = parent;
	}
	public boolean addMethods(MethodTable m) {
		if(methods.containsKey(m.getName())) {
			System.out.println("The function has been defined!");
			return false;
		}
		methods.put(m.getName(), m);
		return true;
	}
	public boolean doesMethodExist(String m) {
		return methods.containsKey(m);
	}
	public boolean isMethodDefined(String m) {
		if(methods.containsKey(m)) return true;
		ClassTable tmp = this;
		while(tmp.getParent() != null) {
			tmp = ClassTree.getClassTable(tmp.getParent());
			if(tmp.doesMethodExist(m)) return true;
		}
		return false;
	}
	public MethodTable getMethod(String s) {
		 MethodTable mt = methods.get(s);
		 if(mt != null) return mt;
		 else {
			 ClassTable tmp = this;
			 while(tmp.getParent() != null) {
				 tmp = ClassTree.getClassTable(tmp.getParent());
				 mt = tmp.getMethod(s);
				 if(mt != null) return mt;
			 }
			 return null;
		 }
	}
	public void canOverride() {
		//HashMap<String, ClassTable> visited = new HashMap<String, ClassTable>();
		//Iterator iter = ClassTree.classes.entrySet().iterator();
		//while(iter.hasNext()) {
		//	ClassTable here = (ClassTable) ((Map.Entry) iter.next()).getValue();
		//	if (visited.containsKey(here.getName())) continue;
		//	else {
				ClassTable tmp = this;
		//		visited.put(tmp.getName(), tmp);
				HashMap<String, ClassTable> checked = new HashMap<String, ClassTable>();
				checked.put(tmp.getName(), tmp);
				while (tmp.getParent() != null) {
					if (checked.containsKey(tmp.getParent())) {
						//System.out.println(tmp.getParent() + " Cyclic inheritace!");
						//flag = false;
						break;
					} else if (ClassTree.classes.containsKey(tmp.getParent())) {
						checked.put(tmp.getParent(), ClassTree.classes.get(tmp.getParent()));
		//				visited.put(tmp.getParent(), ClassTree.classes.get(tmp.getParent()));

						Iterator it = this.methods.entrySet().iterator();
						while(it.hasNext()) {
							MethodTable mt = (MethodTable)((Map.Entry) it.next()).getValue();
							if(ClassTree.classes.get(tmp.getParent()).doesMethodExist(mt.getName())) {
								if(!mt.canOverride(ClassTree.classes.get(tmp.getParent()).getMethod(mt.getName())))
									System.out.println(name + ":" + mt.getName() +" has been defined with distinct parameters in "+ tmp.getParent());
							}
						}


						tmp = ClassTree.classes.get(tmp.getParent());
					} else break;//Parents undefined
				}


	}
	@Override
	public boolean addVariable(Variable v) {
		if(localVariables.containsKey(v.getName())) {
			System.out.println(v.getName() + " has been defined.");
			return false;
		}
		localVariables.put(v.getName(), v);
		return true;
	}

	public Map<String, Variable> getLocalVariables() {
		return localVariables;
	}
	public int getLine() {
		return line;
	}
	public Variable getVariable(String s) {
		return localVariables.get(s);
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String s) {
		parent = s;
	}
	public String getName() {
		return name;
	}

	@TestOnly
	public void dump() {
		System.out.println(name);
		System.out.println(parent);
		System.out.println("Methods:");

		Iterator i = methods.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry m = (Map.Entry)i.next();
			((MethodTable)m.getValue()).dump();

		}
		System.out.println("Local variables:");
		Iterator j = localVariables.entrySet().iterator();
		while(j.hasNext()) {
			Map.Entry m = (Map.Entry)j.next();
			Variable v = (Variable)m.getValue();
			System.out.println(v.getType() + " " + v.getName());

		}
		System.out.println("******");
	}
}
