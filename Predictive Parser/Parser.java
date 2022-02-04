//author: Dean Asman
/**This is the corrected version of the project after Professor Saeedloei made the correction to the body function **/
/*to test the program, place a .txt file in the same directory as the src file where another test.txt file already exists. Change the pathname on line 35 */
package parser;

import java.util.*;
import java.io.*;


public class Parser {
    static ArrayList<Lexeme> tokens = new ArrayList<Lexeme>();


    static String[] follow = {"End-of-text"}; //program -driver
    static String[] follow1 = {"end"};       //body - inside program
    static String[] follow2 = {"ID","if","while","print"}; //declarations - inside body
    static String[] follow3 = {"bool","int","ID","if","while","print"}; // declaration inside declarations{bool int U id if while print}
    static String[] follow4 = {"else","fi","if"}; //body1 conditionalStatement
    static String[] follow5 = {";","end","if","then","else","fi","od"}; //statement {; U follow statements}
    static String[] follow6 = {"then",")"}; // Expression
    static String[] follow7 = {"fi","else",}; //body2 conditionalStatement
    static String[] follow8 = {"do"}; //expr iterative
    static String[] follow9 = {"od"}; //body iterative
    static String[] follow10 = {"<","=<","=","!=",">",">=","do","then",")",";","end"}; //simpleExpr
    static String[] follow11 = {"+","-","or","<","=<","=","!=",">",">=","do","od","then",")",";","end","if","else","fi"};  //term
    static String[] follow12 = {"*","/","and","+","-","or","<","=<","=","!=",">",">=","do","od","then",")",";","end","if","else","fi"}; //factor
    static String[] follow13 = {")"}; //expr

    static String currsym;
    static int iter = 0;

    public static void main(String[] args) throws IOException{
        String text="";
// puts whole file into string
        try {
            File fileName = new File("test.txt");
            try (Scanner reader = new Scanner(fileName)) {
                while (reader.hasNextLine()) {
                    String input = reader.nextLine();

                    text += input;
                    text +='\n';
                }
                text += "ç"; //adds this symbol as "end of file" symbol
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
        }
        int i = 0;
        int state = 0;
        int line = 1;
        int charr =0;
        boolean txtfile = true;
        char c = getNext(text,i); // gets first character from string


        while(txtfile){ // while not the end of file, process the lexemes
            switch(state){ // start by checking the kind of character then proceed throughout the switch statements as the switch changes states
                case 0:
                    if (c == '<'){
                        state = 1;
                    }
                    else if(c == '>'){
                        state = 6;
                    }
                    else if (c == '='){
                        state = 5;
                    }
                    else if( Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z').contains(c) ){
                        state = 9;
                    }
                    else if( Arrays.asList('0','1','2','3','4','5','6','7','8','9').contains(c)){
                        state = 10;
                    }
                    else if(c == '-' || c == '+' || c == '*'){
                        state = 11;
                    }
                    else if(c =='!'){
                        state = 12;
                    }
                    else if(c == '/'){
                        state = 13;
                    }
                    else if(Arrays.asList('(',')',';').contains(c)){
                        state = 11;
                    }
                    else if (c ==':'){
                        state = 14;
                    }
                    else if(c == ' '){
                        i++;
                        c = getNext(text,i);
                        charr++;
                        state = 0;
                    }
                    else if (c == '\n'){
                        state = 15;
                    }
                    else if(c =='ç'){
                        tokens.add(new Lexeme("End-of-Text"," "," "));
                        txtfile = false;
                    }
                    else{
                        charr++;
                        System.out.println("Error - character does not belong to the grammar. Line "+ line +" char "+charr);
                        i++;
                        c = getNext(text,i);
                        charr++;
                        state = 0;
                        //txtfile = false;

                    }
                    break;
// end of case 0
                case 1: //handles '<'
                    i++;
                    c = getNext(text,i);
                    charr++;
                    if(c == '='){
                        state = 2;
                    }
                    else if(c == '>'){
                        state = 3;
                    }
                    else{
                        state = 4;
                    }
                    break;

                case 2: //handles '<='
                    tokens.add(new Lexeme("<=", line +" char "+charr," "));
                    state = 0;
                    break;
                case 3: //handles '<>'
                    tokens.add(new Lexeme("<>", line +" char "+charr," "));
                    state = 0;
                    break;
                case 4: //handles '<'
                    tokens.add(new Lexeme("<", line +" char "+charr," "));
                    state = 0;
                    break;
                case 5: //handles '='
                    tokens.add(new Lexeme("=", line +" char "+charr," "));
                    i++;
                    c = getNext(text,i);
                    charr++;
                    state = 0;
                    break;
                case 6: //handles '>'
                    i++;
                    c = getNext(text,i);
                    charr++;
                    if (c == '='){
                        state = 7;
                    }
                    else {
                        state = 8;
                    }
                case 7: //handles '>='
                    tokens.add(new Lexeme(">=", line +" char "+charr," "));
                    i++;
                    c = getNext(text,i);
                    state = 0;
                    break;
                case 8: //handles '>'
                    tokens.add(new Lexeme(">", line +" char "+charr," "));
                    state = 0;
                    break;
                case 9: //handles identifiers/keywords
                    String s = String.valueOf(c);
                    ++i;
                    c = getNext(text,i);
                    charr++;
                    while( ( Arrays.asList('0','1','2','3','4','5','6','7','8','9').contains(c)) || (Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z').contains(c) )){
                        s += String.valueOf(c);
                        ++i;
                        c = getNext(text,i);
                        charr++;
                    }
                    //state = 0;
                    // checks for keywords
                    if((Arrays.asList("if","false","while","int","program","bool","true","then","else","do","print","not","and","fi","od","end").contains(s))){
                        tokens.add(new Lexeme(s, line +" char "+(charr-s.length()+1)," "));
                    }
                    else {
                        tokens.add(new Lexeme("ID", line +" char "+(charr-s.length()+1),s));
                    }
                    state = 0;

                    break;
                case 10: // processes numbers
                    String num = String.valueOf(c);
                    i++;
                    c = getNext(text,i);
                    ++charr;
                    if(Arrays.asList('0','1','2','3','4','5','6','7','8','9').contains(c)){
                        while(Arrays.asList('0','1','2','3','4','5','6','7','8','9','.').contains(c)){
                            num += String.valueOf(c);
                            i++;
                            c = getNext(text,i);
                            ++charr;
                        }
                        tokens.add(new Lexeme("NUM", line +" char "+charr,num));
                        state = 0;
                    }
                    else
                        tokens.add(new Lexeme("NUM", line +" char "+charr,num));
                    state = 0;
                    break;
                case 11: // handles -|+|*
                    charr++;
                    tokens.add(new Lexeme(String.valueOf(c), line +" char "+charr," "));
                    i++;
                    c = getNext(text,i);
                    state = 0;
                    break;
                case 12: //handles '!'
                    i++;
                    c = getNext(text,i);
                    ++charr;
                    if(c == '='){
                        tokens.add(new Lexeme("!=", line +" char "+charr," "));
                    }
                    else {
                        tokens.add(new Lexeme("!", line +" char "+charr," "));
                    }
                    i++;
                    c = getNext(text,i);
                    state = 0;
                    break;
                case 13: // skips over comments|handles '/'
                    i++;
                    c = getNext(text,i);
                    ++charr;
                    if(c == '/'){
                        while(c != '\n'){
                            i++;
                            c = getNext(text,i);
                        }
                        state = 15;
                    }
                    else{
                        tokens.add(new Lexeme("/", line +" char "+charr," "));
                    }
                    state = 0;
                    break;
                case 14: //handles ':'
                    i++;
                    c = getNext(text,i);
                    ++charr;
                    if (c == '='){
                        tokens.add(new Lexeme(":=", line +" char "+charr," "));
                    }else{
                        tokens.add(new Lexeme(":", line +" char "+charr," "));
                    }
                    i++;
                    c = getNext(text,i);
                    state = 0;
                    break;
                case 15: // increments line number and resets char position
                    line +=1;
                    i++;
                    c = getNext(text,i);
                    charr = 0;
                    state = 0;
                    break;
                default:// if something not in the grammar is found, prints an error
                    System.out.println("Error - character does not belong to the grammar.");
                    break;
            }
        }
        //prints out the information of each token
        //for(int x=0; x<tokens.size(); x++){
        //    System.out.println(tokens.get(x));
        //}
        next(iter);
        program(follow);

    }
    // gets the next character
    public static char getNext(String s, int index) throws FileNotFoundException, IOException{
        ArrayList<Character> chars = new ArrayList<>();
        for (char ch : s.toCharArray()) {
            chars.add(ch);
        }
        return chars.get(index);
    }
    public static void next(int a){currsym = tokens.get(a).getKind();}

    public static void accept(String sym){
        if(currsym.equals("end")) {
            System.out.println("True - Input file was successfully parsed");
        }
        else if(currsym.equals(sym)){
            iter++;
            next(iter);
        }
        else{
            System.out.println("Accept: "+currsym+" Error "+tokens.get(iter).getPosition()+" "+tokens.get(iter).getValue());
            System.out.println("expected: "+ sym);
        }
    }

    public static void program(String[] follow){
        accept("program");
        accept("ID");
        accept(":");
        body(follow1);
        accept("end");
    }

    public static void body(String[] follow){
        if(Arrays.asList("bool","int").contains(currsym)){
            declarations(follow2);
        }
            statements(follow);
    }

    public static void declarations(String[] follow){
        declaration(follow3);
        while(Arrays.asList("bool","int").contains(currsym)){
            declaration(follow3);
        }
        expected(follow3);
    }

    public static void declaration(String[] follow){
        assert Arrays.asList("bool","int").contains(currsym);
        iter++;
        next(iter);
        accept("ID");
        accept(";");
    }

    public static void statements(String[] follow){
        statement(follow5);
        while(currsym.equals(";")){
            iter++;
            next(iter);
            statement(follow5);
        }
        expected(follow5);
    }

    public static void statement(String[] follow){
        if(currsym.equals("ID")){
            assignmentStatement(follow);
        }
        else if(currsym.equals("if")){
            conditionalStatement(follow);
        }
        else if(currsym.equals("while")){
            iterativeStatement(follow);
        }
        else if(currsym.equals("print")){
            printStatement(follow);
        }
        else{
            System.out.println("Statement error: Saw "+ currsym + " but expected ID|if|while|print");
        }
    }

    public static void assignmentStatement(String[] follow){
        assert (currsym.equals("ID"));
        accept("ID");
        accept(":=");
        expression(follow);
    }

    public static void conditionalStatement(String[] follow){
        assert (currsym.equals("if"));
        accept("if");
        expression(follow6);
        accept("then");
        body(follow4);
        if(currsym.equals("else")){
            iter++;
            next(iter);
            body(follow7);
        }
        accept("fi");
    }
    public static void iterativeStatement(String[] follow){
        assert (currsym.equals("while"));
        accept("while");
        expression(follow8);
        accept("do");
        body(follow9);
        accept("od");
    }

    public static void printStatement(String[] follow){
        assert (currsym.equals("print"));
        accept("print");
        expression(follow);
    }

    public static void expression(String[] follow){
        simpleExpr(follow10);
        if(Arrays.asList("<","=<","=","!=",">=",">").contains(currsym)){
            iter++;
            next(iter);
            simpleExpr(follow);
        }
    }

    public static void simpleExpr(String[] follow){
        term(follow11);
        while(Arrays.asList("+","-","or").contains(currsym)){
            iter++;
            next(iter);
            term(follow11);
        }
        expected(follow11);
    }

    public static void term(String[] follow){
        factor(follow12);
        while(Arrays.asList("*","/","and").contains(currsym)){
            iter++;
            next(iter);
            factor(follow12);
        }
        expected(follow12);
    }

    public static void factor(String[] follow){
        String[] expec = {"true","false","NUM","ID", "("};
        if(Arrays.asList("-","not").contains(currsym)){
            iter++;
            next(iter);
        }
        if(Arrays.asList("false","true","NUM").contains(currsym)){
            literal(follow);
        }
        else if(currsym.equals("ID")){
            iter++;
            next(iter);
        }
        else if(currsym.equals("(")){
            iter++;
            next(iter);
            expression(follow13);
            accept(")");
        }
        else{
            expected(expec);
        }
    }

    public static void literal(String[] follow){
        assert(Arrays.asList("false","true","NUM").contains(currsym));
        if(currsym.equals("NUM")){
            iter++;
            next(iter);
        }
        else{
            booleanLiteral(follow);
        }
    }

    public static void booleanLiteral(String[] follow){
        assert(Arrays.asList("true","false").contains(currsym));
        iter++;
        next(iter);
    }

    public static void expected(String [] follow){
        //If the current symbol is in the list expected dont return anything, else it will return an error.
        boolean a = true;
        for (int i = 0; i < follow.length; i++) {
            if(currsym.equals(follow[i])){
                a = false;
            }
        }
        if(a){
            System.out.println("I see "+currsym+" but I expect to see "+java.util.Arrays.toString(follow));
            //System.out.println(currsym+" Error"+tokens.get(iter).getPosition()+tokens.get(iter).getValue());
        }

    }

}
