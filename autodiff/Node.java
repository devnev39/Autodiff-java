package Autodiff.autodiff;

import java.util.Hashtable;

import Autodiff.autodiff.Operation.Operations;

public class Node {
    // Node can have const value or operation
    // const value -> value set
    // operation -> compute -> value set
    // representation
    private float value;
    private Operation operation;
    private String name = null;
    private boolean isVariable = false;

    // private static int counter = 0;

    public Node(float value,String name){
        this.value = value;
        this.name = name;
        this.operation = null;
        // counter += 1z
    }

    public Node(float value,String name,boolean isVariable){
        this.value = value;
        this.name = name;
        this.operation = null;
        this.isVariable = true;
        // counter z= 1;
    }

    public Node(Operation op,String name){
        this.operation = op;
        this.name = name;
        // counter += 1;
    }

    public Node(float value){
        this.value = value;
        this.operation = null;
        // this.name = this.getClass().getSimpleName()+Node.counter;
        // counter += 1;
    }

    public Node(Operation op){
        this.operation = op;
        // this.name = this.getClass().getName()+Node.counter;
        // counter += 1;
    }

    public float value(){
        return this.value;
    }

    public void setValue(float value){
        if(this.isVariable){
            this.value = value;
        }
    }

    public Operation operation(){
        return this.operation;
    }

    public boolean isVariable(){
        return this.isVariable;
    }

    public String printGraph(){
        if(this.operation==null){
            return this.toString();
        }else{
            return "("+this.operation.N1().printGraph()+" "+Operation.Ops().get(this.operation.op())+" "+this.operation.N2().printGraph()+"--->"+this.operation.Representation()+")\n";
        }
    }

    public float Evaluate(Hashtable<String,Float> vars){
        return this.operation.Compute(vars);
    }

    public String toString(){
        if(this.name!=null){
            return this.name;
        }else{
            return ""+this.value;
        }
    }

    public String name(){
        return this.name;
    }

    public static Node sum(Node n1,Node n2){
        return new Node(new Operation(n1, n2, Operations.ADD));
    }
    public static Node sub(Node n1,Node n2){
        return new Node(new Operation(n1, n2, Operations.SUB));
    }
    public static Node div(Node n1,Node n2){
        return new Node(new Operation(n1, n2, Operations.DIV));
    }
    public static Node mul(Node n1,Node n2){
        return new Node(new Operation(n1, n2, Operations.MUL));
    }
    public static Node pow(Node n1,Node n2){
        return new Node(new Operation(n1, n2, Operations.EXP));
    }
    public static Node log(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.LOG));
    }
    public static Node sin(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.SIN));
    }
    public static Node cos(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.COS));
    }
    public static Node tan(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.TAN));
    }
    public static Node asin(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.ASIN));
    }
    public static Node acos(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.ACOS));
    }
    public static Node atan(Node n1){
        return new Node(new Operation(n1, new Node(1.0f), Operations.ATAN));
    }
    public static Node sqrt(Node n1) {
        return new Node(new Operation(n1, new Node(2.0f), Operations.EXP));
    }
}