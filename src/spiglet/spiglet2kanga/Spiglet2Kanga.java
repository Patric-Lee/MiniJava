package spiglet.spiglet2kanga;

import spiglet.parser.SpigletParser;
import spiglet.syntaxtree.*;
import spiglet.visitor.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

/**
 * Created by lianxiang on 2017/6/5.
 */
public class Spiglet2Kanga {
    public static void main(String[] args) {
        try {
            //Open the source code
            File file = new File(args[0]);
            FileInputStream fInputStream = new FileInputStream(file);
            //MiniJavaParser parser = new MiniJavaParser(fInputStream);
            SpigletParser parser = new SpigletParser(fInputStream);
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
            FlowGraphVisitor flowGraphVisitor = new FlowGraphVisitor();
            flowGraphVisitor.visit(goal, null);
            //FlowGraphs.dump();
            FlowGraphs.defAndUse();
            FlowGraphs.cal();
            FlowGraphs.liveAnalyze();
            FlowGraphs.buildInterfereGraph();
            SpigletVisitor spigletVisitor = new SpigletVisitor();
            KangaStmt kg = spigletVisitor.visit(goal, FlowGraphs.getFlowGraph("MAIN"));
            System.out.println(kg.getStmt());
            FileWriter fw = new FileWriter("kangaRes.txt");
            fw.write(kg.getStmt());
            fw.close();
            /*
            FileWriter fw = new FileWriter("resu.txt");
            fw.write(sp.getStmt());
            fw.close();
            */


        }
        catch(Exception fileOpenE)
        {
            fileOpenE.printStackTrace();
        }
    }
}
