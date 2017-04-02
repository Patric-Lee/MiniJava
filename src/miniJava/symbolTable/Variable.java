package miniJava.symbolTable;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class Variable extends SymbolTable {
	private int line;
	private VariableType type;
	private String name;
	private ClassTree classTree;

	public Variable(String s) {
		name = s;
	}
	public int getLine() {
		return line;
	}
	public VariableType getType() {
		return type;
	}
	public void addType(String s) {
		switch(s) {
			case "Integer":
				type = VariableType.intType;break;
			case "Array":
				type = VariableType.arrayType;break;
			case "Boolean":
				type = VariableType.boolType;break;
			//case "Class":
			//	type = VariableType.classType;
			default:
				type = VariableType.classType;

		}
		System.out.println(type);
	}
	public String getName() {
		return name;
	}
	public boolean isIdentical(Variable v) {
		if(v.type == VariableType.classType && type == VariableType.classType) {
			//Check if v is the parent of this.
			//To be written.
			return v.isParent(this);
		}
		if(v.type != type) return false;
		else return true;
	}
	public boolean isParent(Variable v) {
		//To be written
		ClassTable here = classTree.getClassTable(this.getName());
		return (here == classTree.getClassTable(v.getName()));
	}
}
