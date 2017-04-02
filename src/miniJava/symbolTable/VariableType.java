package miniJava.symbolTable;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class VariableType {
	public String type;
	public int length = 0;
	public boolean isDescendent(VariableType var) {

		if (!type.equals(var.type)) return false;
		else if (type.equals("classType") && var.type.equals("classType")) {
			if(((ClassType)this).className.equals(((ClassType)var).className)) return true;
			ClassTable ct = ClassTree.getClassTable(((ClassType) this).className);

			while (ct.getParent() != null) {
				if (ClassTree.getClassTable(ct.getParent()).getName().equals(((ClassType) var).className)) return true;
				ct = ClassTree.getClassTable(ct.getParent());
			}
			return false;
		}
		else return true;
	}
	public boolean isArray() {
		return type.equals("arrayType");
	}
	public boolean isBool() {
		return type.equals("boolType");
	}
	public boolean isInt() {
		return type.equals("intType");

	}
	public boolean isIdentical(VariableType v) {
		return type.equals(v.type);
	}
}





