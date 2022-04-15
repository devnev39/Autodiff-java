package Autodiff;

import java.util.Hashtable;

import Autodiff.autodiff.*;
import Autodiff.autodiff.Operation.Operations;
import Autodiff.equation.Equation;
import Autodiff.grad.Gradient;

public class Main {
    public static void main(String[] args) {
        // (5x^2 + 7x)*(2x)
        Node var_x = new Node(1.0f,"x",true);
        Node var_y = new Node(1.0f,"y",true);
        Node var_u = new Node(1.0f,"u",true);
        Node var_z = new Node(1.0f,"z",true);
        // Node a1 = Node.pow(var_x, new Node(2.0f));
        // Node a2 = Node.mul(new Node(5.0f), a1);
        // Node a3 = Node.mul(new Node(7.0f), var_x);
        // Node a4 = Node.sum(a2, a3);
        // Node f = Node.mul(a4, Node.mul(new Node(2.0f), var_x));

        // //Node f = Node.mul(Node.pow(var_x, new Node(2.0f)), Node.pow(new Node(7.0f), var_x));
        // System.out.println(f.operation().Representation());
        // Node g = Gradient.GradwrtVar(f, var_x);
        // System.out.println(g.operation().Representation());
        // System.out.println(g.Evaluate(null));
        
        // Node a1 = Node.pow(var_x,new Node(3.0f));
        // Node a2 = Node.mul(var_x, new Node(4.0f));
        // Node a3 = Node.mul(a2, var_y);
        // Node a4 = Node.mul(a3, var_z);
        // Node a5 = Node.mul(var_z, var_y);
        // Node a6 = Node.mul(a5, var_u);
        // Node a7 = Node.sum(a1, a4);
        // Node f = Node.sum(a7,a6);

        // System.out.println(f.operation().Representation());
        // // System.out.println(f.Evaluate(new Hashtable<String,Float>(){{
        // //     put("x",3.0f);
        // //     put("y",3.0f);
        // //     put("z",3.0f);
        // //     put("u",3.0f);
        // // }}));

        // System.out.println(f.printGraph());
        // System.out.println(f.Evaluate(null));
        // Node g = Gradient.GradwrtVar(f, var_x);
        // System.out.println(g.operation().Representation());
        // //System.out.println(g.printGraph());
        //System.out.println(f.Evaluate(null));
        String eq = "( x^4 + 4x ) | x";
        // // // printArray(eq.split("\\|"));
        try {
            Equation.processInput(eq);
            String e = eq.split("\\|")[0];
            Node n = Equation.createGraph(e.split("\\s"), 0, null);
            System.out.println(n.operation().Representation());
            System.out.println(n.Evaluate(null));
            System.out.println(n.printGraph());
            System.out.println(Gradient.GradwrtVar(n, Equation.varNodes(eq)).operation().Representation());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // String vars = Equation.removeSeparator(eq.split(" | "), "|")[0];
        // System.out.println(vars);
        // if(vars.contains(Operation.Ops().get(Operations.LOG))){
        //     System.out.println(true);
        // }
        // printArray(vars);
        // String e = "2/xy";
        // String[] sp = e.split("/");
        // printArray(sp);
    }   
    
    public static void printArray(String[] arr){
        for(String i : arr){
            System.out.println(i);
        }
    }
}
