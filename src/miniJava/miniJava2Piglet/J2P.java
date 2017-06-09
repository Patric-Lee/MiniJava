package miniJava.miniJava2Piglet;

import miniJava.symbolTable.ClassTree;
import parser.MiniJavaParser;
import visitor.PigletVisitor;
import visitor.TypeCheckVisitor;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by lianxiang on 2017/5/1.
 */
public class J2P {
    public static void main(String[] args) {
        try {
            //Open the source code
            File file = new File(args[0]);
            FileInputStream fInputStream = new FileInputStream(file);

            //Parse
            MiniJavaParser parser = new MiniJavaParser(fInputStream);
            System.out.println("Parsing completed!");

            //To obtain the root node
            syntaxtree.Goal goal = parser.Goal();
            ClassTree classTree = new ClassTree();
            classTree.buildSymbolTable(goal);
            classTree.doesFatherExist();
            classTree.canOverride();
            classTree.isInheritanceAcylic();
            TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
            typeCheckVisitor.visit(goal, null);

            //Piglet
            classTree.computeDVTable();
            PigletVisitor pigletVisitor = new PigletVisitor();
            PigletStatement ps = pigletVisitor.visit(goal, null);// argu to be modified
            System.out.println(ps.getPigletCodes());
        }
        catch(Exception fileOpenE)
        {
            fileOpenE.printStackTrace();
        }
    }
}
