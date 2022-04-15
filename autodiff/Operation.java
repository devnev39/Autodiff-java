package Autodiff.autodiff;
import java.util.Hashtable;

public class Operation {
    public static enum Operations{
        ADD,SUB,MUL,DIV,EXP,LOG,SIN,COS,TAN,ASIN,ACOS,ATAN
    }

    public static Hashtable<Operations,String> Ops(){
        Hashtable<Operations,String> ops = new Hashtable<Operations,String>();
        ops.put(Operations.ADD, "+");
        ops.put(Operations.SUB, "-");
        ops.put(Operations.MUL, "*");
        ops.put(Operations.DIV, "/");
        ops.put(Operations.EXP, "^");
        ops.put(Operations.LOG, "ln");
        ops.put(Operations.SIN, "sin");
        ops.put(Operations.COS, "cos");
        ops.put(Operations.TAN, "tan");
        ops.put(Operations.ASIN, "asin");
        ops.put(Operations.ACOS, "acos");
        ops.put(Operations.ATAN, "atan");
        return ops;
    }

    private Node N1;
    private Node N2;
    private Operations op;

    private String Representation = null;

    public Operation(Node n1,Node n2,Operations op){
        // if((n1.operation()==null && n2.operation()==null) && (!n1.isVariable() && !n2.isVariable())){
        //     return;
        // }
        this.N1 = n1;
        this.N2 = n2;
        this.op = op;
        this.seeRepresentation();
    }

    private void seeRepresentation() {
        String s1 = "";
        String s2 = "";
        if(this.N1.operation()!=null){
            s1 = this.N1.operation().Representation;
        }else{
            s1 = this.N1.toString();
        }
        if(this.N2.operation()!=null){
            s2 = this.N2.operation().Representation;
        }else{
            s2 = this.N2.toString();
        }
        if((N1.name()==null && N2.name()==null) && (N1.value()==1.0f || N2.value()==1.0f) && this.op == Operations.MUL && (!N1.isVariable() && !N2.isVariable())){
            
            if(N1.value()==1.0f){
                this.Representation = s2;
            }else{
                this.Representation = s1;
            }
            
        }else{
            if(this.op!=Operations.ADD && this.op!=Operations.SUB && this.op!=Operations.MUL && this.op!=Operations.DIV && this.op!=Operations.EXP){
                if(s2.equals("1.0")){
                    this.Representation = "("+Ops().get(this.op) + s1+")";
                }else{
                    this.Representation = "("+s2+Ops().get(this.op) + s1+")";
                }
            }else{
                this.Representation = "("+s1 + Ops().get(this.op) + s2+")";
            }
        }
    }

    public float Compute(Hashtable<String,Float> vars){
        float val1 = 0.0f;
        float val2 = 0.0f;
        if(this.N1.operation()!=null){
            val1 = this.N1.operation().Compute(vars);
        }else{
            if(vars!=null){
                if(this.N1.isVariable()){
                    this.N1.setValue(vars.get(this.N1.toString()));
                }
            }
            val1 = this.N1.value();
        }
        if(this.N2.operation()!=null){
            val2 = this.N2.operation().Compute(vars);
        }else{
            if(vars!=null){
                if(this.N2.isVariable()){
                    this.N2.setValue(vars.get(this.N2.toString()));
                }
            }
            val2 = this.N2.value();
        }

        switch (this.op) {
            case ADD:
                return val1 + val2;

            case SUB:
                return val1 - val2;
            
            case MUL:
                return val1 * val2;
            
            case DIV:
                return val1 / val2;
            
            case EXP: 
                return (float)Math.pow(val1,val2);
            
            case LOG: 
                return (float)Math.log(val1);
            
            case SIN:
                return (float)Math.sin(val1);
            
            case COS:
                return (float)Math.cos(val1);
            
            case TAN:
                return (float)Math.tan(val1);
            
            case ASIN: 
                return (float)Math.asin(val1);
            
            case ACOS: 
                return (float)Math.acos(val1);

            case ATAN: 
                return (float)Math.atan(val1);
            
            default:
                return 0.0f;
        }
    }

    public Node N1(){
        return this.N1;
    }

    public Node N2(){
        return this.N2;
    }
    
    public Operations op(){
        return this.op;
    }

    public String toString(){
        String n1 = "";
        String n2 = "";
        if(this.N1.operation()!=null){
            n1 = this.N1.operation().toString();
        }else{
            n1 = this.N1.toString();
        }
        if(this.N2.operation()!=null){
            n2 = this.N2.operation().toString();
        }else{
            n2 = this.N2.toString();
        }
        return n1+Operation.Ops().get(this.op)+n2;
    }

    public String Representation(){
        return this.Representation;
    }
}
