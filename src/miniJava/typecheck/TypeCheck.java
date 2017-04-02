package miniJava.typecheck;

import java.io.*;

import miniJava.symbolTable.ClassTree;
import	parser.*;
import visitor.TypeCheckVisitor;

public class TypeCheck {

	public static void main(String[] args) {
		try {
		//Open the source code
		File file = new File(args[0]);
		FileInputStream fInputStream = new FileInputStream(file);
		MiniJavaParser parser = new MiniJavaParser(fInputStream);
			System.out.println("Parsing completed!");
			syntaxtree.Goal goal = parser.Goal();
			ClassTree classTree = new ClassTree();
			classTree.buildSymbolTable(goal);
			classTree.doesFatherExist();
			classTree.canOverride();
			classTree.isInheritanceAcylic();
			TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
			typeCheckVisitor.visit(goal, null);
		}
		catch(Exception fileOpenE)
		{
			fileOpenE.printStackTrace();
		}



	}
}










