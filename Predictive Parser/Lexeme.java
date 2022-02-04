package parser;

public class Lexeme {
    private String kind;
    private String position;
    private String value;

    public Lexeme(String kind, String position, String value){
        this.kind = kind;
        this.position = position;
        this.value = value;
    }

    public String getKind(){
        return this.kind;
    }
    public String getPosition(){
        return this.position;
    }
    public String getValue(){
        return this.value;
    }
    @Override
    public String toString(){
        return "Position: Line "+getPosition() + " " +"Kind:"+ getKind() + " " +"Value:"+ getValue()+"\n";
    }
}
