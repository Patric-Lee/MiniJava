package piglet.piglet2spiglet;


import piglet.parser.PigletParser;
import piglet.syntaxtree.*;
import piglet.visitor.SpigletVisitor;

import java.io.*;

/**
 * Created by lianxiang on 2017/5/30.
 */
public class Piglet2Spiglet {
    public static void main(String[] args) {
        try {
            //Open the source code
            File file = new File(args[0]);
            FileInputStream fInputStream = new FileInputStream(file);
            //MiniJavaParser parser = new MiniJavaParser(fInputStream);
            PigletParser parser = new PigletParser(fInputStream);
            System.out.println("Parsing completed!");
            Goal goal = parser.Goal();
            /*
            ClassTree classTree = new ClassTree();
            classTree.buildSymbolTable(goal);
            classTree.doesFatherExist();
            classTree.canOverride();
            classTree.isInheritanceAcylic();
            TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor();
            typeCheckVisitor.visit(goal, null);
            */
            SpigletVisitor spigletVisitor = new SpigletVisitor();
            SpigletStmt sp = spigletVisitor.visit(goal, null);
            System.out.println(sp.getStmt());
            FileWriter fw = new FileWriter("resu.txt");
            fw.write(sp.getStmt());
            fw.close();


        }
        catch(Exception fileOpenE)
        {
            fileOpenE.printStackTrace();
        }
    }
}
