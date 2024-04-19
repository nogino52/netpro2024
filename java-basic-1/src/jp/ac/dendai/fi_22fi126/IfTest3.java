package jp.ac.dendai.fi_22fi126;
//xが7,8,9ながbignumber
//xが4,5,6ならmiddle number
//xが1,2,3ならsmall numberを表示するプログラムを書け
//xがそれ以外(10や-1なら)ならout of scopeと表示するプログラムをかけ

public class IfTest3 {
    public static void main(String[] args) { 
        int x=Integer.parseInt(args[0]);
        /*以下を改造する。**/

        if(x<1 || 9<x)
        {
            System.out.println(x+" is Out of Scope");
            return;
        }

        if(x<4)
            System.out.println(x+" is Small Number");
        else if(x<7)
            System.out.println(x+" is Middle Number");
        else
            System.out.println(x+" is Big Number");
    }//main end
}//class end

