package miniJava.symbolTable;

/**
 * Created by lianxiang on 2017/4/2.
 */


public class ClassType extends VariableType {
    public String className;
    public ClassType(String v) {
        type = "classType";
        className = v;
    }
}