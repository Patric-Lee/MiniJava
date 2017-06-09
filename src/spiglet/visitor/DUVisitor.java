//
// Generated by JTB 1.3.2
//

package spiglet.visitor;
import spiglet.spiglet2kanga.DefAndUse;
import spiglet.spiglet2kanga.FlowGraphNode;
import spiglet.syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class DUVisitor extends GJDepthFirst<DefAndUse,FlowGraphNode>  {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    public DefAndUse visit(NodeList n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
        }
        return _ret;
    }

    public DefAndUse visit(NodeListOptional n, FlowGraphNode argu) {
        if ( n.present() ) {
            DefAndUse _ret= new DefAndUse();
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                DefAndUse tmp = e.nextElement().accept(this,argu);
                _ret.addDef(tmp);
                _ret.addUse(tmp);
                _count++;
            }
            return _ret;
        }
        else
            return null;
    }

    public DefAndUse visit(NodeOptional n, FlowGraphNode argu) {
        if ( n.present() )
            return n.node.accept(this,argu);
        else
            return null;
    }

    public DefAndUse visit(NodeSequence n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
        }
        return _ret;
    }

    public DefAndUse visit(NodeToken n, FlowGraphNode argu) { return null; }

    //
    // User-generated visitor methods below
    //

    /**
     * f0 -> "MAIN"
     * f1 -> StmtList()
     * f2 -> "END"
     * f3 -> ( Procedure() )*
     * f4 -> <EOF>
     */
    public DefAndUse visit(Goal n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    public DefAndUse visit(StmtList n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    public DefAndUse visit(Procedure n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> NoOpStmt()
     *       | ErrorStmt()
     *       | CJumpStmt()
     *       | JumpStmt()
     *       | HStoreStmt()
     *       | HLoadStmt()
     *       | MoveStmt()
     *       | PrintStmt()
     */
    public DefAndUse visit(Stmt n, FlowGraphNode argu) {
        DefAndUse _ret=   n.f0.accept(this, argu);
        argu.setDefAndUse(_ret);
        return _ret;
    }

    /**
     * f0 -> "NOOP"
     */
    public DefAndUse visit(NoOpStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "ERROR"
     */
    public DefAndUse visit(ErrorStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    public DefAndUse visit(CJumpStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        _ret.addUse(Integer.parseInt(n.f1.f1.f0.tokenImage));
        _ret.setLabel(n.f2.f0.tokenImage);
        return _ret;
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    public DefAndUse visit(JumpStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        _ret.setLabel(n.f1.f0.tokenImage);
        return _ret;
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    public DefAndUse visit(HStoreStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        _ret.addUse(Integer.parseInt(n.f1.f1.f0.tokenImage));
        _ret.addUse(Integer.parseInt(n.f3.f1.f0.tokenImage));
        return _ret;
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    public DefAndUse visit(HLoadStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        _ret.addDef(Integer.parseInt(n.f1.f1.f0.tokenImage));
        _ret.addUse(Integer.parseInt(n.f2.f1.f0.tokenImage));;
        return _ret;
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    public DefAndUse visit(MoveStmt n, FlowGraphNode argu) {
        DefAndUse _ret= new DefAndUse();
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        DefAndUse tmp = n.f2.accept(this, argu);
        _ret.addUse(tmp);
        _ret.addDef(Integer.parseInt(n.f1.f1.f0.tokenImage));

        return _ret;
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    public DefAndUse visit(PrintStmt n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Call()
     *       | HAllocate()
     *       | BinOp()
     *       | SimpleExp()
     */
    public DefAndUse visit(Exp n, FlowGraphNode argu) {
        DefAndUse _ret=        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    public DefAndUse visit(StmtExp n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        n.f2.accept(this, argu);
        n.f3.accept(this, argu);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "CALL"
     * f1 -> SimpleExp()
     * f2 -> "("
     * f3 -> ( Temp() )*
     * f4 -> ")"
     */
    public DefAndUse visit(Call n, FlowGraphNode argu) {
        DefAndUse _ret=  new DefAndUse();
        n.f0.accept(this, argu);
        _ret.addUse(n.f1.accept(this, argu));
        n.f2.accept(this, argu);
        DefAndUse tmp = n.f3.accept(this, argu);
        _ret.addUse(tmp);
        _ret.addDef(tmp);
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    public DefAndUse visit(HAllocate n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    public DefAndUse visit(BinOp n, FlowGraphNode argu) {
        DefAndUse _ret= null;
        n.f0.accept(this, argu);
        _ret = n.f1.accept(this, argu);
        _ret.addUse(n.f2.accept(this, argu));
        return _ret;
    }

    /**
     * f0 -> "LT"
     *       | "PLUS"
     *       | "MINUS"
     *       | "TIMES"
     */
    public DefAndUse visit(Operator n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Temp()
     *       | IntegerLiteral()
     *       | Label()
     */
    public DefAndUse visit(SimpleExp n, FlowGraphNode argu) {
        DefAndUse _ret=
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    public DefAndUse visit(Temp n, FlowGraphNode argu) {
        DefAndUse _ret = new DefAndUse();
        _ret.addUse(Integer.parseInt(n.f1.f0.tokenImage));
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public DefAndUse visit(IntegerLiteral n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public DefAndUse visit(Label n, FlowGraphNode argu) {
        DefAndUse _ret=null;
        n.f0.accept(this, argu);
        return _ret;
    }

}
