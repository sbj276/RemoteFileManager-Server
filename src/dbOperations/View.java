/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbOperations;

/**
 *
 * @author Vinay
 */
public class View {
    final String logName;
    public View(String logName){
        this.logName=logName;
    }
    public String viewAll(){
        String str="";
        //read all from database
        String s[]={"f1.docx","f2.pptx"};
        for(String si:s){
            str=str+"<"+si;
        }
        str=str.substring(1);
        System.out.println(str);
        return str;
    }
}
