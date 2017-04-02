package miniJava.symbolTable;

import java.util.Vector;

/**
 * Created by lianxiang on 2017/4/3.
 */
public class VarsType extends VariableType {
    public Vector<VariableType> types = new Vector<VariableType>();
    public void addTypes(VariableType v) {
        if(v instanceof VarsType) {

                types.addAll(((VarsType)v).types);

        }

        else types.add(v);
    }
}
