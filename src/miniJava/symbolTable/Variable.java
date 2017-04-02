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
				type = new IntType();break;
			case "Array":
				type = new ArraysType();break;
			case "Boolean":
				type = new BoolType();break;
			//case "Class":
			//	type = VariableType.classType;
			default:
				type = new ClassType(s);

		}
		System.out.println(type);
	}
	public String getName() {
		return name;
	}
	/*public boolean isIdentical(Variable v) {
		if(v.type == VariableType.classType && type == VariableType.classType) {
			//Check if v is the parent of this.
			//To be written.
			return v.isParent(this);
		}
		if(v.type != type) return false;
		else return true;
	}
	public boolean isIdentical(VariableType v) {
		if(v == VariableType.classType && type == VariableType.classType) {
			//Check if v is the parent of this.
			//To be written.
			return v.isParent(this);
		}
		if(v != type) return false;
		else return true;
	}
	public boolean isParent(Variable v) {
		//To be written
		ClassTable here = classTree.getClassTable(this.getName());
		return (here == classTree.getClassTable(v.getName()));
	}*/
}
