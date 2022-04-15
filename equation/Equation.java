package Autodiff.equation;

import java.rmi.ConnectIOException;
import java.util.ArrayList;
import java.util.Hashtable;
import Autodiff.autodiff.Node;
import Autodiff.autodiff.Operation;
import Autodiff.autodiff.Operation.Operations;

// EQU | WRTVAR | VARS | VALS

public class Equation {
    private static Hashtable<String,Float> VarvalLst;
    private static Hashtable<String,Node> varNodeLst;
    private static Node varNode;
    private static String equationString;

    public static void processInput(String input) throws Exception{
        String[] split = removeSeparator(input.split("\\|"),"|");
        if(split.length < 2){
            throw new Exception("The input doesn't contain target variable info !");
        }
        if(split.length>2){
            String[] s = split[2].split(",");
            for(String i : s){
                if(i.replaceAll("[^0-1]", "").length()!=0){
                    throw new Exception("The 3rd place is only for variables and not for floats !");
                }
            }
        }
        varNodeLst = new Hashtable<String,Node>();
        VarvalLst = getVariableDictionary(input);
        equationString = getEquation(input);
    }

    public static String[] removeSeparator(String[] arr,String separator){
        ArrayList<String> filter = new ArrayList<String>();
        for(String i : arr){
            if(i.equals(separator)){
                continue;
            }
            filter.add(i);
        }
        return filter.toArray(new String[filter.size()]);
    }

    public static Node createSingleNode(String input){
        String test = input.replaceAll("[^0-9]", "");
        if(test.length()>=1 && test.length()==input.length()){
            return new Node(Float.parseFloat(test));
        }
        test = input.replaceAll("[^A-z]", "");
        if(test.length()==1 && input.length()==1){
            if(varNodeLst.size()>=1){
                if(varNodeLst.keySet().contains(test)){
                    return varNodeLst.get(test);
                }
                return new Node(1.0f,test);
            }
            varNodeLst.put(test, new Node(1.0f,test,true));
            return varNodeLst.get(test);
        }
        for(Operations key : Operation.Ops().keySet()){
            if(input.contains(Operation.Ops().get(key))){
                String[] parts = null;
                if(key!=Operations.ADD && key!=Operations.SUB && key!=Operations.MUL && key!=Operations.DIV && key!=Operations.EXP){
                    parts = input.split(Operation.Ops().get(key));
                    return new Node(new Operation(createSingleNode(parts[1]), new Node(1.0f), key));
                }else{
                    parts = input.split("\\"+Operation.Ops().get(key));
                    return new Node(new Operation(createSingleNode(parts[0]), createSingleNode(parts[1]), key));
                }
            }
        }
        String constant = input.replaceAll("[^0-9]", "");
        String var = input.replaceAll("[^A-z]", "");
        Node varNode = null;
        if(varNodeLst.size()>=1){
            varNode = varNodeLst.get(var);
        }else{
            varNode = new Node(1.0f,var,true);
            varNodeLst.put(var, varNode);
        }
        return new Node(new Operation(varNode, new Node(Float.parseFloat(constant)), Operations.MUL));
    }

    public static Node createGraph(String[] input,int index,Node prev){
        if(input[index].equals("(") || input[index].equals(")")){
            return createGraph(input, index+1,prev);
        }
        for(Operations key : Operation.Ops().keySet()){
            if(input[index].equals(Operation.Ops().get(key))){
                if(key!=Operations.ADD && key!=Operations.SUB && key!=Operations.MUL && key!=Operations.DIV && key!=Operations.EXP){
                    // return createGraph(input, index+1, new Node(new Operation(prev, createSingleNode(input[index+1]), key)));
                    return new Node(new Operation(createGraph(input, index+1,null), new Node(1.0f), key));
                }else{
                    // return createGraph(input, index+1, new Node(new Operation(prev, createSingleNode(input[index+1]), key)));
                    return new Node(new Operation(prev, createGraph(input, index+1, null), key));
                }
            }
        }
        if(prev==null){
            prev = createSingleNode(input[index]);
        }
        if(index == input.length-1){
            return prev;
        }
        return createGraph(input,index+1,prev);
    }

    public static String getEquation(String input){
        String[] out = input.split("|");
        return out[0];
    }

    public static Node varNodes(String input){
        String var = input.split("\\|")[1].replaceAll("\\s", "");
        return varNodeLst.get(var);
    }

    public static Hashtable<String,Float> getVariableDictionary(String input){
        String[] out = removeSeparator(input.split("\\|"),"|");
        String[] vars = null;
        String[] vals = null;
        if(out.length==2){
            return null;
        }
        if(out.length>2){
            vars = out[2].replaceAll("\\s", "").split(",");
        }
        if(out.length>3){
            vals = out[3].replaceAll("\\s", "").split(",");
        }
        Hashtable<String,Float> varval = new Hashtable<String,Float>();
        // varNodeLst = new Hashtable<String,Node>();
        for(int i=0;i<vars.length;i++){
            if(vals!=null){
                if(i<vals.length){
                    varval.put(vars[i], Float.parseFloat(vals[i]));
                }else{
                    varval.put(vars[i], 1.0f);
                }
            }else{
                varval.put(vars[i],1.0f);
            }
            varNodeLst.put(vars[i], new Node(1.0f,vars[i],true));
        }
        return varval;
    }
}
