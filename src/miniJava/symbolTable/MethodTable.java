package miniJava.symbolTable;

import org.jetbrains.annotations.TestOnly;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class MethodTable extends SymbolTable {
	private HashMap<String, Variable> localVariables = new HashMap<String, Variable>();
	private String name;
	private Vector<Variable> params = new Vector<Variable>();
	private VariableType returnType;
	private ClassTable classTable;

	public ClassTable getClassTable() {
		return classTable;
	}
	public MethodTable(String name, SymbolTable s) {
		this.name = name;
		classTable = (ClassTable)s;
		localVariables.putAll(((ClassTable)s).getLocalVariables());
	};
	public Vector<Variable> getParams() {
		return params;
	}
	public VariableType getReturnType() {
		return returnType;
	}
	public String getName() {
		return name;
	}
	public Variable getVariable(String s) {
		return localVariables.get(s);
	}
	public boolean canOverride(MethodTable m) {
		return paramsCheck(m.getParams()) && (m.getReturnType() == returnType);
	}
	public Variable isDefined(String s) {
		if(localVariables.get(s) != null) {
			return localVariables.get(s);
		}
		else{
			ClassTable tmp = classTable;
			while(tmp.getParent() != null) {
				tmp = ClassTree.getClassTable(tmp.getParent());
				if(tmp.getVariable(s) != null)
					return tmp.getVariable(s);
			}
			return null;
		}

	}
	@TestOnly
	public void dump() {
		System.out.println(name);
		System.out.println(returnType);
		System.out.println("	Parameters:");
		for(Variable v : params) {
			System.out.println("	" + v.getType() + " " + v.getName());
		}
		System.out.println("	Local variables:");
		Iterator i = localVariables.entrySet().iterator();
		while(i.hasNext()) {
			Map.Entry m = (Map.Entry)i.next();
			Variable v = (Variable)m.getValue();
			System.out.println("	" + v.getType() + " " + v.getName());
		}
	}

	@Override
	public void addType(String s) {

		switch(s) {
			case "Integer":
				returnType = new IntType();break;
			case "Array":
				returnType = new ArraysType();break;
			case "Boolean":
				returnType = new BoolType();break;
			//case "Class":
			//	type = VariableType.classType;
			default:
				returnType = new ClassType(s);

		}
		System.out.println(returnType);
	}
	@Override
	public boolean addVariable(Variable v) {
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + "Variable " + v.getName() + " has been defined.");
			return false;
		}
		localVariables.put(v.getName(), v);
		return true;
	}
	public boolean isRepeated(Variable v) {
		return localVariables.containsKey(v.getName());
	}

	public boolean addParams(Variable v) {
		if(v == null) return false;
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + " Variable " + v.getName() + " has been defined.");
			return false;
		}
		params.add(v);
		localVariables.put(v.getName(), v);
		return true;
	}

	public boolean addLocalVariables(Variable v) {
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + "Variable " + v.getName() + " has been defined.");
			return false;
		}
		localVariables.put(v.getName(), v);
		return true;
	}

	/*
	 *  Use this function to check if the parameters are compatible when a function is called.
	 */
	public boolean paramsCheck(Vector<Variable> paramList) {
		if(params == null && paramList == null) return true;
		else if(params != null && paramList != null) {
			if (params.size() != paramList.size()) return false; //Check if the numbers of parameters are equal
			for (int i = 0; i < params.size(); ++i) {
				if (!params.get(i).getType().isIdentical(paramList.get(i).getType())) return false;
			}
			return true;
		}
		else return false;
	}

}
