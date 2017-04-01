package miniJava.typecheck;

import java.io.*;
import java.util.*;

public class TypeCheck {
	public static void main(String[] args) {
		try {
		//Open the source code
		File file = new File(args[0]);
		FileInputStream fInputStream = new FileInputStream(file);
		miniJava.MiniJavaParser parser = new miniJava.MiniJavaParser(fInputStream);
		}
		catch(Exception fileOpenE)
		{
			fileOpenE.printStackTrace();
		}
	}
}

abstract class SymbolTable {
	
}

enum VariableType {
	intType, boolType, classType, arrayType;
}

class Variable extends SymbolTable {
	private int line;
	private VariableType type;
	private String name;
	
	public int getLine() {
		return line;
	}
	public VariableType getType() {
		return type;
	}
	public String getName() {
		return name;
	}
	public boolean isIdentical(Variable v) {
		if(v.type == VariableType.classType && type == VariableType.classType) {
			//Check if v is the parent of this.
			To be written.
		}
		if(v.type != type) return false;
		else return true;
	}
}

class MethodTable extends SymbolTable {
	private HashMap<String, Variable> localVariables = new HashMap<String, Variable>();
	private Vector<Variable> params;
	private Variable returnType;
	
	public boolean isRepeated(Variable v) {
		return localVariables.containsKey(v.getName());
	}
	
	public boolean addParams(Variable v) {
		if(isRepeated(v)) {
			System.out.println("Line:" + v.getLine() + "The name of the parameter " + v.getName() + " is repeated.");
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

class ClassTable extends SymbolTable {
	private HashMap<String, MethodTable> methods = new HashMap<String, MethodTable>();
	private int line;
	private ClassTable parent;
	
	public int getLine() {
		return line;
	}
}

class ClassTree {
	
}