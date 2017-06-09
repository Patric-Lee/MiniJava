package kanga.kanga2Mips;

import kanga.parser.KangaParser;
import kanga.syntaxtree.*;
import kanga.visitor.KangaVisitor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

/**
 * Created by lianxiang on 2017/6/3.
 */
public class Kanga2Mips {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(args[0]));
            KangaParser kangaParser = new KangaParser(fileInputStream);
            System.out.println("Parsing completed!");
            Goal goal = kangaParser.Goal();
            KangaVisitor kv = new KangaVisitor();
            MipsStmt ms = kv.visit(goal, null);
            System.out.println(ms.getStmt());
            FileWriter fw = new FileWriter("mipsRes.txt");
            fw.write(ms.getStmt());
            fw.close();
        }
        catch(Exception fileOpenE) {
            fileOpenE.printStackTrace();
        }
    }
}
