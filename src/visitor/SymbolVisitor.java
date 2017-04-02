package visitor;

import syntaxtree.*;
import miniJava.symbolTable.*;

import java.util.Enumeration;

/**
 * Created by lianxiang on 2017/4/1.
 */
public class SymbolVisitor extends GJDepthFirst<String, SymbolTable> {

        public String visit(NodeList n, SymbolTable argu) {
            String _ret=null;
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                e.nextElement().accept(this,argu);
                _count++;
            }
            return _ret;
        }

        public String visit(NodeListOptional n, SymbolTable argu) {
            if ( n.present() ) {
                String _ret=null;
                int _count=0;
                for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                    e.nextElement().accept(this,argu);
                    _count++;
                }
                return _ret;
            }
            else
                return null;
        }

        public String visit(NodeOptional n, SymbolTable argu) {
            if ( n.present() )
                return n.node.accept(this,argu);
            else
                return null;
        }

        public String visit(NodeSequence n, SymbolTable argu) {
            String _ret=null;
            int _count=0;
            for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
                e.nextElement().accept(this,argu);
                _count++;
            }
            return _ret;
        }

        public String visit(NodeToken n, SymbolTable argu) { return null; }

        //
        // User-generated visitor methods below
        //

        /**
         * f0 -> MainClass()
         * f1 -> ( TypeDeclaration() )*
         * f2 -> <EOF>
         */
        public String visit(Goal n, SymbolTable argu) {
           // System.out.println("Goal visited!");
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "class"
         * f1 -> Identifier()
         * f2 -> "{"
         * f3 -> "public"
         * f4 -> "static"
         * f5 -> "void"
         * f6 -> "main"
         * f7 -> "("
         * f8 -> "String"
         * f9 -> "["
         * f10 -> "]"
         * f11 -> Identifier()
         * f12 -> ")"
         * f13 -> "{"
         * f14 -> ( VarDeclaration() )*
         * f15 -> ( Statement() )*
         * f16 -> "}"
         * f17 -> "}"
         */
        public String visit(MainClass n, SymbolTable a) {
           // System.out.println("MainClass visited!");
            String _ret=null;
            ClassTable argu = new ClassTable(n.f1.f0.toString(), -1, null);
            ClassTree.addClass(argu);
            MethodTable met = new MethodTable("main", argu);
            argu.addMethods(met);
            met.addParams(new Variable(n.f11.f0.toString()));
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            n.f6.accept(this, argu);
            n.f7.accept(this, argu);
            n.f8.accept(this, argu);
            n.f9.accept(this, argu);
            n.f10.accept(this, argu);
            n.f11.accept(this, met);
            n.f12.accept(this, met);
            n.f13.accept(this, met);
            n.f14.accept(this, met);
            n.f15.accept(this, met);
            n.f16.accept(this, met);
            n.f17.accept(this, met);
          //  argu.dump();
            return _ret;
        }

        /**
         * f0 -> ClassDeclaration()
         *       | ClassExtendsDeclaration()
         */
        public String visit(TypeDeclaration n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "class"
         * f1 -> Identifier()
         * f2 -> "{"
         * f3 -> ( VarDeclaration() )*
         * f4 -> ( MethodDeclaration() )*
         * f5 -> "}"
         */
        public String visit(ClassDeclaration n, SymbolTable a) {
            String _ret=null;
            ClassTable argu = new ClassTable(n.f1.f0.toString(), -1, null);
            ClassTree.addClass(argu);
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);

         //   argu.dump();
            return _ret;
        }

        /**
         * f0 -> "class"
         * f1 -> Identifier()
         * f2 -> "extends"
         * f3 -> Identifier()
         * f4 -> "{"
         * f5 -> ( VarDeclaration() )*
         * f6 -> ( MethodDeclaration() )*
         * f7 -> "}"
         */
        public String visit(ClassExtendsDeclaration n, SymbolTable a) {
            String _ret=null;
            ClassTable argu = new ClassTable(n.f1.f0.toString(), -1, n.f3.f0.toString());
            ClassTree.addClass(argu);
            argu.setParent(n.f3.f0.toString());
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            n.f6.accept(this, argu);
            n.f7.accept(this, argu);
            //argu.dump();
            return _ret;
        }

        /**
         * f0 -> Type()
         * f1 -> Identifier()
         * f2 -> ";"
         */
        public String visit(VarDeclaration n, SymbolTable argu) {
         //   System.out.println("VarDeclaration " + n.f1.f0.toString() + " visited!");
            String _ret=null;
            Variable v = new Variable(n.f1.f0.toString());
            argu.addVariable(v);
            n.f0.accept(this, v);
            n.f1.accept(this, v);
            n.f2.accept(this, v);

            return _ret;
        }

        /**
         * f0 -> "public"
         * f1 -> Type()
         * f2 -> Identifier()
         * f3 -> "("
         * f4 -> ( FormalParameterList() )?
         * f5 -> ")"
         * f6 -> "{"
         * f7 -> ( VarDeclaration() )*
         * f8 -> ( Statement() )*
         * f9 -> "return"
         * f10 -> Expression()
         * f11 -> ";"
         * f12 -> "}"
         */
        public String visit(MethodDeclaration n, SymbolTable a) {
            String _ret=null;
            MethodTable argu = new MethodTable(n.f2.f0.toString(), a);
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            n.f6.accept(this, argu);
            n.f7.accept(this, argu);
            n.f8.accept(this, argu);
            n.f9.accept(this, argu);
            n.f10.accept(this, argu);
            n.f11.accept(this, argu);
            n.f12.accept(this, argu);

            ((ClassTable)a).addMethods(argu);
            return _ret;
        }

        /**
         * f0 -> FormalParameter()
         * f1 -> ( FormalParameterRest() )*
         */
        public String visit(FormalParameterList n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> Type()
         * f1 -> Identifier()
         */
        public String visit(FormalParameter n, SymbolTable argu) {
            String _ret=null;
            Variable v = new Variable(n.f1.f0.toString());
            n.f0.accept(this, v);
            n.f1.accept(this, argu);
            ((MethodTable)argu).addParams(v);
            return _ret;
        }

        /**
         * f0 -> ","
         * f1 -> FormalParameter()
         */
        public String visit(FormalParameterRest n, SymbolTable argu) {
            String _ret=null;

            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> ArrayType()
         *       | BooleanType()
         *       | IntegerType()
         *       | Identifier()
         */
        public String visit(Type n, SymbolTable argu) {
            String _ret=null;
            String s = n.f0.accept(this, argu);
         //   if(s == null) argu.addType("Class");
         //   else
                argu.addType(s, 0);
            return _ret;
        }

        /**
         * f0 -> "int"
         * f1 -> "["
         * f2 -> "]"
         */
        public String visit(ArrayType n, SymbolTable argu) {
            String _ret= "Array";
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "boolean"
         */
        public String visit(BooleanType n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return "Boolean";
        }

        /**
         * f0 -> "int"
         */
        public String visit(IntegerType n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return "Integer";
        }

        /**
         * f0 -> Block()
         *       | AssignmentStatement()
         *       | ArrayAssignmentStatement()
         *       | IfStatement()
         *       | WhileStatement()
         *       | PrintStatement()
         */
        public String visit(Statement n, SymbolTable argu) {
            String _ret=null;
           // n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "{"
         * f1 -> ( Statement() )*
         * f2 -> "}"
         */
        public String visit(Block n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> Identifier()
         * f1 -> "="
         * f2 -> Expression()
         * f3 -> ";"
         */
        public String visit(AssignmentStatement n, SymbolTable argu) {
            String _ret=null;
            Variable v = ((MethodTable)argu).getVariable(n.f0.toString());
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, v);
            n.f3.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> Identifier()
         * f1 -> "["
         * f2 -> Expression()
         * f3 -> "]"
         * f4 -> "="
         * f5 -> Expression()
         * f6 -> ";"
         */
        public String visit(ArrayAssignmentStatement n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            n.f6.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "if"
         * f1 -> "("
         * f2 -> Expression()
         * f3 -> ")"
         * f4 -> Statement()
         * f5 -> "else"
         * f6 -> Statement()
         */
        public String visit(IfStatement n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            n.f6.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "while"
         * f1 -> "("
         * f2 -> Expression()
         * f3 -> ")"
         * f4 -> Statement()
         */
        public String visit(WhileStatement n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "System.out.println"
         * f1 -> "("
         * f2 -> Expression()
         * f3 -> ")"
         * f4 -> ";"
         */
        public String visit(PrintStatement n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> AndExpression()
         *       | CompareExpression()
         *       | PlusExpression()
         *       | MinusExpression()
         *       | TimesExpression()
         *       | ArrayLookup()
         *       | ArrayLength()
         *       | MessageSend()
         *       | PrimaryExpression()
         */
        public String visit(Expression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "&&"
         * f2 -> PrimaryExpression()
         */
        public String visit(AndExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "<"
         * f2 -> PrimaryExpression()
         */
        public String visit(CompareExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "+"
         * f2 -> PrimaryExpression()
         */
        public String visit(PlusExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "-"
         * f2 -> PrimaryExpression()
         */
        public String visit(MinusExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "*"
         * f2 -> PrimaryExpression()
         */
        public String visit(TimesExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "["
         * f2 -> PrimaryExpression()
         * f3 -> "]"
         */
        public String visit(ArrayLookup n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "."
         * f2 -> "length"
         */
        public String visit(ArrayLength n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> PrimaryExpression()
         * f1 -> "."
         * f2 -> Identifier()
         * f3 -> "("
         * f4 -> ( ExpressionList() )?
         * f5 -> ")"
         */
        public String visit(MessageSend n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            n.f5.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> Expression()
         * f1 -> ( ExpressionRest() )*
         */
        public String visit(ExpressionList n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> ","
         * f1 -> Expression()
         */
        public String visit(ExpressionRest n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> IntegerLiteral()
         *       | TrueLiteral()
         *       | FalseLiteral()
         *       | Identifier()
         *       | ThisExpression()
         *       | ArrayAllocationExpression()
         *       | AllocationExpression()
         *       | NotExpression()
         *       | BracketExpression()
         */
        public String visit(PrimaryExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> <INTEGER_LITERAL>
         */
        public String visit(IntegerLiteral n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "true"
         */
        public String visit(TrueLiteral n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "false"
         */
        public String visit(FalseLiteral n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> <IDENTIFIER>
         */
        public String visit(Identifier n, SymbolTable argu) {
          //  System.out.println("Identifier " + n.f0.toString() +" Visited!");
            String _ret=n.f0.toString();
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "this"
         */
        public String visit(ThisExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "new"
         * f1 -> "int"
         * f2 -> "["
         * f3 -> Expression()
         * f4 -> "]"
         */
        public String visit(ArrayAllocationExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            n.f4.accept(this, argu);
            try {((Variable)argu).setLength(Integer.parseInt(n.f3.toString()));}
            catch (NumberFormatException e){}

            return _ret;
        }

        /**
         * f0 -> "new"
         * f1 -> Identifier()
         * f2 -> "("
         * f3 -> ")"
         */
        public String visit(AllocationExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            n.f3.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "!"
         * f1 -> Expression()
         */
        public String visit(NotExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            return _ret;
        }

        /**
         * f0 -> "("
         * f1 -> Expression()
         * f2 -> ")"
         */
        public String visit(BracketExpression n, SymbolTable argu) {
            String _ret=null;
            n.f0.accept(this, argu);
            n.f1.accept(this, argu);
            n.f2.accept(this, argu);
            return _ret;
        }



}
