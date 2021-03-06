//
// Generated by JTB 1.3.2
//

package spiglet.visitor;
import spiglet.spiglet2kanga.KangaStmt;
import spiglet.spiglet2kanga.*;
import spiglet.syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class SpigletVisitor extends GJDepthFirst<KangaStmt,FlowGraph>  {
    //
    // Auto class visitors--probably don't need to be overridden.
    //
    public KangaStmt visit(NodeList n, FlowGraph argu) {
        KangaStmt _ret= new KangaStmt("", false);
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            _ret.append(e.nextElement().accept(this,argu));
            _count++;
        }
        return _ret;
    }

    public KangaStmt visit(NodeListOptional n, FlowGraph argu) {
        if ( n.present() ) {
            KangaMultiStmt _ret= new KangaMultiStmt();
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                _ret.addStmt(e.nextElement().accept(this,argu));
                _count++;
            }
            return _ret;
        }
        else
            return null;
    }

    public KangaStmt visit(NodeOptional n, FlowGraph argu) {
        if ( n.present() )
            return n.node.accept(this,argu);
        else
            return null;
    }

    public KangaStmt visit(NodeSequence n, FlowGraph argu) {
        KangaStmt _ret= new KangaStmt("", false);
        int _count=0;
        for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            _ret.append(e.nextElement().accept(this,argu));
            _count++;
        }
        return _ret;
    }

    public KangaStmt visit(NodeToken n, FlowGraph argu) { return new KangaStmt(n.tokenImage, false); }

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
    public KangaStmt visit(Goal n, FlowGraph argu) {
        KangaStmt _ret= new KangaStmt("MAIN[0][", false);

        n.f0.accept(this, argu);
        KangaStmt kg = n.f1.accept(this, argu);
        _ret.append(argu.getSpilledNum() + 10 + ((kg.getMaxParam() > 4)?(kg.getMaxParam() - 4):0)  + "]");
        _ret.append("[" + kg.getMaxParam() + "]\n");
        _ret.append(kg);
        n.f2.accept(this, argu);
        _ret.append("END\n");
        _ret.append(n.f3.accept(this, argu));
        n.f4.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> ( ( Label() )? Stmt() )*
     */
    public KangaStmt visit(StmtList n, FlowGraph argu) {
        // Global Label?
        KangaStmt _ret=    n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Label()
     * f1 -> "["
     * f2 -> IntegerLiteral()
     * f3 -> "]"
     * f4 -> StmtExp()
     */
    public KangaStmt visit(Procedure n, FlowGraph argu) {
        argu = FlowGraphs.getFlowGraph(n.f0.f0.tokenImage);
        KangaStmt _ret= new KangaStmt("", false);
        _ret.append(n.f0.accept(this, argu));
        _ret.append(n.f1.accept(this, argu));
        _ret.append(n.f2.accept(this, argu));
        _ret.append(n.f3.accept(this, argu));

        KangaStmt tmp = n.f4.accept(this, argu);
        _ret.append("[" + (argu.getSpilledNum() + 18 + ((tmp.getMaxParam() > 4)? (tmp.getMaxParam() - 4): 0))+ "]");
        _ret.append("[" + tmp.getMaxParam() + "]\n");
        String sk = "";

        for(int i = 0; i < 8; ++i) {
            sk += "ASTORE SPILLEDARG " + (i + argu.getSpilledNum()) + " s" + i + "\n";
        }
        for(int i = 0; i < Integer.parseInt(n.f2.f0.tokenImage); ++i) {
            if(i < 4) {
                if (argu.getReg(i).isSpilled()) {
                    sk += "ASTORE " + argu.getReg(i).getStmt() + " a" + i + "\n";
                } else {
                    sk += "MOVE " + argu.getReg(i).getStmt() + " a" + i + "\n";
                }
            }
            else {
                if (argu.getReg(i).isSpilled()) {
                    sk += "ALOAD v1 SPILLEDARG " + (i - 4) + "\n";
                    sk += "ASTORE " + argu.getReg(i).getStmt() + " v1"  + "\n";

                } else {
                    sk += "ALOAD " + argu.getReg(i).getStmt() + " SPILLEDARG " + (i - 4) + "\n";
                }
            }
        }
        _ret.append(sk);
        _ret.append(tmp);
        sk = "";
        for(int i = 0; i < 8; ++i) {
            sk += "ALOAD " + "s" + i + " SPILLEDARG " + (i + argu.getSpilledNum()) + "\n";
        }
        _ret.append(sk);
        _ret.append("END\n");
        _ret.clearParam();
        _ret.updateMaxParam(Integer.parseInt(n.f2.f0.tokenImage));
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
    public KangaStmt visit(Stmt n, FlowGraph argu) {
        KangaStmt _ret=    n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "NOOP"
     */
    public KangaStmt visit(NoOpStmt n, FlowGraph argu) {
        KangaStmt _ret=        n.f0.accept(this, argu);
        _ret.append("\n");
        return _ret;
    }

    /**
     * f0 -> "ERROR"
     */
    public KangaStmt visit(ErrorStmt n, FlowGraph argu) {
        KangaStmt _ret=    n.f0.accept(this, argu);
        _ret.append("\n");
        return _ret;
    }

    /**
     * f0 -> "CJUMP"
     * f1 -> Temp()
     * f2 -> Label()
     */
    public KangaStmt visit(CJumpStmt n, FlowGraph argu) {
        KangaStmt _ret=        n.f0.accept(this, argu);
        _ret.append(" ");
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.addPre("ALOAD a0 " + tmp.getStmt() + "\n");
            _ret.append("a0");
        }
        else
            _ret.append(tmp);
        _ret.append(" ");

        _ret.append(n.f2.accept(this, argu));
        _ret.append("\n");
        return _ret;
    }

    /**
     * f0 -> "JUMP"
     * f1 -> Label()
     */
    public KangaStmt visit(JumpStmt n, FlowGraph argu) {
        KangaStmt _ret=     n.f0.accept(this, argu);
        _ret.append(" ");
        _ret.append( n.f1.accept(this, argu));
        _ret.append("\n");
        return _ret;
    }

    /**
     * f0 -> "HSTORE"
     * f1 -> Temp()
     * f2 -> IntegerLiteral()
     * f3 -> Temp()
     */
    public KangaStmt visit(HStoreStmt n, FlowGraph argu) {
        KangaStmt _ret=   n.f0.accept(this, argu);
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.addPre("ALOAD a0 " + tmp.getStmt() + "\n");
            _ret.append(" a0");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
        }
        _ret.append( " ");
        _ret.append(n.f2.accept(this, argu));
        _ret.append(" ");
        KangaStmt tmp2 = n.f3.accept(this, argu);
        if(tmp2.isSpilled()) {
            _ret.addPre("ALOAD a1 " + tmp.getStmt() + "\n");
            _ret.append(" a1");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp2);
        }
        _ret.append("\n");

        return _ret;
    }

    /**
     * f0 -> "HLOAD"
     * f1 -> Temp()
     * f2 -> Temp()
     * f3 -> IntegerLiteral()
     */
    public KangaStmt visit(HLoadStmt n, FlowGraph argu) {
        KangaStmt _ret=   n.f0.accept(this, argu);
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {

            _ret.append(" a1");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
        }
        KangaStmt tmp2 = n.f2.accept(this, argu);
        if(tmp2.isSpilled()) {
            _ret.addPre("ALOAD a0 " + tmp.getStmt() + "\n");
            _ret.append(" a0");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp2);
        }
        _ret.append(" ");

        _ret.append(n.f3.accept(this, argu));
        _ret.append("\n");
        if(tmp.isSpilled()) {
            _ret.append("ASTORE " + tmp.getStmt() + " a1\n");
        }
        return _ret;
    }

    /**
     * f0 -> "MOVE"
     * f1 -> Temp()
     * f2 -> Exp()
     */
    public KangaStmt visit(MoveStmt n, FlowGraph argu) {
        KangaStmt _ret=   n.f0.accept(this, argu);

        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.append(" a0 ");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
            _ret.append(" ");
        }
        if(n.f2.f0.choice instanceof  Call) {
            KangaStmt kk = n.f2.accept(this, argu);
            _ret.addPre(kk.getStmt());
            _ret.updateMaxParam(kk.getMaxParam());
            _ret.append("v0\n");
        }
        else {
            KangaStmt uu = n.f2.accept(this, argu);
            if(uu.isSpilled()) {
                _ret.setPre("ALOAD a1 " + uu.getStmt() + "\n");
                _ret.append(" a1");
            }
            else {
                _ret.append(uu);
            }
            _ret.addPre();
            _ret.append("\n");
        }
        if(tmp.isSpilled()) {
            _ret.append("ASTORE " + tmp.getStmt() + " a0\n");
        }
        return _ret;
    }

    /**
     * f0 -> "PRINT"
     * f1 -> SimpleExp()
     */
    public KangaStmt visit(PrintStmt n, FlowGraph argu) {
        KangaStmt _ret=     n.f0.accept(this, argu);
        _ret.append(" ");
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.addPre("ALOAD a0 " + tmp.getStmt() + "\n");
            _ret.append(" a0\n");
        }
        else {
            _ret.append(tmp);
            _ret.append("\n");
        }

        return _ret;
    }

    /**
     * f0 -> Call()
     *       | HAllocate()
     *       | BinOp()
     *       | SimpleExp()
     */
    public KangaStmt visit(Exp n, FlowGraph argu) {
        KangaStmt _ret=        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "BEGIN"
     * f1 -> StmtList()
     * f2 -> "RETURN"
     * f3 -> SimpleExp()
     * f4 -> "END"
     */
    public KangaStmt visit(StmtExp n, FlowGraph argu) {
        KangaStmt _ret= new KangaStmt("", false);
        //n.f0.accept(this, argu);
        _ret.append(n.f1.accept(this, argu));
        n.f2.accept(this, argu);
        KangaStmt tmp = n.f3.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.append("ALOAD v0 " + tmp.getStmt() + "\n");
        }
        else {
            _ret.append("MOVE v0 " + tmp.getStmt() + "\n");
        }
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
    public KangaStmt visit(Call n, FlowGraph argu) {
        String sk = "";
        for(int i = 0; i < 10; ++i) {
            sk += "ASTORE SPILLEDARG " + ((argu.getName().equals("MAIN")?0:8) + argu.getSpilledNum() + i) + " t" + i + "\n";
        }

        KangaStmt _ret= new KangaStmt( "CALL ", false);
        n.f0.accept(this, argu);

        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {

            _ret.setPre("ALOAD v1 " + tmp.getStmt() + "\n");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
            _ret.append(" ");
        }
        _ret.append("\n");
        n.f2.accept(this, argu);
        KangaMultiStmt t = (KangaMultiStmt)(n.f3.accept(this, argu));
        for(int i = 0; i < t.getMultiStmt().size(); ++i) {
            KangaStmt par = t.getMultiStmt().elementAt(i);
            if(i < 4) {
                if(par.isSpilled()) {
                    _ret.setPre("ALOAD v1 " + par.getStmt() + "\n" + "MOVE a" + i + " v1\n");
                }
                else
                    _ret.setPre("MOVE a" + (i) + " " + par.getStmt() + "\n");
            }
            else {
                if(par.isSpilled()) {
                    _ret.setPre("ALOAD v1 " + par.getStmt() + "\n" + "PASSARG " + (i - 3) + " v1\n");
                }
                else
                    _ret.setPre("PASSARG " + (i - 3) + " " + par.getStmt() + "\n");
            }
        }
        n.f4.accept(this, argu);
        _ret.setPre(sk);
        sk = "";
        for(int i = 0; i < 10; ++i) {
            sk += "ALOAD " + " t" + i + " SPILLEDARG " + ((argu.getName().equals("MAIN")?0:8) + argu.getSpilledNum() + i) + "\n";
        }
        _ret.addPre();
        _ret.append(sk);
        _ret.updateMaxParam(t.getMultiStmt().size());
        return _ret;
    }

    /**
     * f0 -> "HALLOCATE"
     * f1 -> SimpleExp()
     */
    public KangaStmt visit(HAllocate n, FlowGraph argu) {
        KangaStmt _ret=    new KangaStmt("HALLOCATE ", false);
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.append(" a1 ");
            _ret.setPre("ALOAD a1 " + tmp.getStmt() + "\n");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
           // _ret.append(" ");
        }


        return _ret;
    }

    /**
     * f0 -> Operator()
     * f1 -> Temp()
     * f2 -> SimpleExp()
     */
    public KangaStmt visit(BinOp n, FlowGraph argu) {
        KangaStmt _ret=  n.f0.accept(this, argu);
        KangaStmt tmp = n.f1.accept(this, argu);
        if(tmp.isSpilled()) {
            _ret.append(" a1 ");
            _ret.setPre("ALOAD a1 " + tmp.getStmt() + "\n");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp);
            _ret.append(" ");
        }
        KangaStmt tmp2 = n.f2.accept(this, argu);
        if(tmp2.isSpilled()) {
            _ret.append(" a2 ");
            _ret.setPre("ALOAD a2 " + tmp.getStmt() + "\n");
        }
        else {
            _ret.append(" ");
            _ret.append(tmp2);
            _ret.append(" ");
        }
        return _ret;
    }

    /**
     * f0 -> "LT"
     *       | "PLUS"
     *       | "MINUS"
     *       | "TIMES"
     */
    public KangaStmt visit(Operator n, FlowGraph argu) {
        KangaStmt _ret=        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> Temp()
     *       | IntegerLiteral()
     *       | Label()
     */
    public KangaStmt visit(SimpleExp n, FlowGraph argu) {
        KangaStmt _ret=        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> "TEMP"
     * f1 -> IntegerLiteral()
     */
    public KangaStmt visit(Temp n, FlowGraph argu) {
        KangaStmt _ret= argu.getReg(Integer.parseInt(n.f1.f0.tokenImage));

        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <INTEGER_LITERAL>
     */
    public KangaStmt visit(IntegerLiteral n, FlowGraph argu) {
        KangaStmt _ret= new KangaStmt(n.f0.tokenImage, false);
        n.f0.accept(this, argu);
        return _ret;
    }

    /**
     * f0 -> <IDENTIFIER>
     */
    public KangaStmt visit(Label n, FlowGraph argu) {
        KangaStmt _ret;
        if(argu.isStmtLabel(n.f0.tokenImage))
            _ret = new KangaStmt(argu.getName() + "_" + n.f0.tokenImage + " ", false);
        else
            _ret = new KangaStmt(n.f0.tokenImage, false);

        n.f0.accept(this, argu);
        return _ret;
    }

}
