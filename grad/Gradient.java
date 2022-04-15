package Autodiff.grad;

import Autodiff.autodiff.*;
import Autodiff.autodiff.Operation.Operations;

public class Gradient {
    public static Node Grad(Node n , int index){
        Operation op = n.operation();
        switch (op.op()) {
            case ADD:
                if(index==0){
                    return new Node(1.0f);
                }else{
                    return new Node(1.0f);
                }
            
            case SUB:
                if(index==0){
                    return new Node(1.0f);
                }else{
                    return new Node(-1.0f);
                }

            case MUL:
                if(index==0){
                    if(op.N1().operation()!=null || op.N1().isVariable()){
                        return op.N2();
                    }
                    return op.N1();
                }else{
                    if(op.N2().operation()!=null || op.N2().isVariable()){
                        return op.N1();
                    }
                    return op.N2();
                }
            
            case DIV:
                if(index==0){
                    return new Node(new Operation(new Node(1.0f), op.N2(), Operations.DIV));
                }else{
                    return new Node(new Operation(new Node(-1.0f), new Node(new Operation(op.N2(), new Node(2.0f), Operations.EXP)), Operations.DIV));
                }
            
            case EXP:
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return new Node(new Operation(op.N2(), new Node(new Operation(op.N1(), new Node(op.N2().value()-1.0f), Operations.EXP)), Operations.MUL));
                }
                return new Node(new Operation(n, new Node(new Operation(op.N1(), new Node(1.0f), Operations.LOG)), Operations.MUL));
        
            case LOG: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return Node.div(new Node(1.0f), op.N1());
                }
                return new Node(0.0f);

            case SIN: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return Node.cos(op.N1());
                }
                return new Node(0.0f);
            
            case COS: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return Node.sin(Node.mul(new Node(-1.0f), op.N1()));
                }
                return new Node(0.0f);

            case TAN: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return Node.div(new Node(1.0f), Node.pow(Node.cos(op.N1()), new Node(2.0f)));
                }
                return new Node(0.0f);
        
            case ASIN,ACOS: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    Node a = Node.sqrt(Node.sub(new Node(1.0f), Node.pow(op.N1(), new Node(2.0f))));
                    if(op.op()==Operations.ASIN){
                        return Node.div(new Node(1.0f), a);
                    }else{
                        return Node.div(new Node(-1.0f), a);
                    }
                }
                return new Node(0.0f);

            case ATAN: 
                if(op.N1().isVariable() || op.N1().operation()!=null){
                    return Node.div(new Node(1.0f), Node.sum(new Node(1.0f), Node.pow(op.N1(), new Node(2.0f))));
                }
                return new Node(0.0f);
            
            default:
                return null;
        }
    }

    public static Node GradwrtVar(Node end,Node varnode){
        if(end==varnode){
            return new Node(1.0f);
        }
        if(end.operation()==null && end.isVariable()){
            return new Node(0.0f);
        }
        if(end.operation()==null){
            return end;
        }
        Node dn1 = null;
        Node dn2 = null;
        if(end.operation().N1().isVariable() || end.operation().N1().operation()!=null){
            dn1 = Node.mul(Grad(end, 0),GradwrtVar(end.operation().N1(), varnode));
        }
        if(end.operation().N2().isVariable() || end.operation().N2().operation()!=null){
            dn2 = Node.mul(Grad(end, 1),GradwrtVar(end.operation().N2(), varnode));
        }
        if(dn1==null && dn2==null){
            return new Node(0.0f);
        }else
        if(dn1==null){
            return dn2;
        }else
        if(dn2==null){
            return dn1;
        }
        return Node.sum(dn1,dn2);
    }
}
