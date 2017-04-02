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


	public MethodTable(String name, SymbolTable s) {
		this.name = name;
		localVariables.putAll(((ClassTable)s).getLocalVariables());
	};
	public String getName() {
		return name;
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
				returnType = VariableType.intType;break;
			case "Array":
				returnType = VariableType.arrayType;break;
			case "Boolean":
				returnType = VariableType.boolType;break;
			//case "Class":
			//	type = VariableType.classType;
			default:
				returnType = VariableType.classType;

		}
		System.out.println(returnType);
	}
	@Override
	public boolean addVariable(Variable v) {
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + "The name of the parameter " + v.getName() + " has been used.");
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
			System.out.println("Line:" + v.getLine() + "The name of the parameter " + v.getName() + " has been used.");
			return false;
		}
		params.add(v);
		localVariables.put(v.getName(), v);
		return true;
	}

	public boolean addLocalVariables(Variable v) {
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + "The name of the variable " + v.getName() + " has been defined.");
			return false;
		}
		localVariables.put(v.getName(), v);
		return true;
	}

	/*
	 *  Use this function to check if the parameters are compatible when a function is called.
	 */
	public boolean paramsCheck(Vector<Variable> paramList) {
		if(params.size() != paramList.size()) return false; //Check if the numbers of parameters are equal
		for(int i = 0; i < params.size(); ++i) {
			if(!params.get(i).isIdentical(paramList.get(i))) return false;
		}
		return true;
	}

}
