package src;

//import java.lang.*;

public class ObjEmp {

    protected int a;
    protected int b; // Remettre private si l'héritage en fonctionne pas

    public ObjEmp(int a, int b){

        this.a = a;
        this.b = b;
    }

    // Additionner un ObjEmp avec un autre
    public ObjEmp add(ObjEmp obj){
        ObjEmp newObj = new ObjEmp(this.a + obj.getA(), this.b + obj.getB());
        return newObj; 
    }

    // Soustraire un ObjEmp avec un autre
    public ObjEmp substract(ObjEmp obj){
        ObjEmp newObj = new ObjEmp(this.a - obj.getA(), this.b - obj.getB());
        return newObj; 
    }

    // Multiplier un ObjEmpl avec un autre
    public ObjEmp multiply(ObjEmp obj){
        ObjEmp newObj = new ObjEmp(this.a * obj.getA(), this.b * obj.getB());
        return newObj; 
    }

    // Diviser un ObjEmpl avec un autre
    public ObjEmp divide(ObjEmp obj){
        ObjEmp newObj = new ObjEmp(this.a / obj.getA(), this.b / obj.getB());
        return newObj; 
    }

    // parser un objEmp depuis ce qui est entrée par le user
    public static ObjEmp parseInput(String str){
        ObjEmp objEmp = null;

        String[] numbers = str.split(" ");

        // Revoir, possiblement >= 2 ("c'était pa le bout de code auquel je m'attendais à voir")
        if(numbers.length == 2){

            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);

            objEmp = new ObjEmp(a, b);
        }
        System.out.println(objEmp);
        return objEmp;
    }

    public int getA(){
        return this.a;
    }

    public int getB(){
        return this.b;
    }

    public void setA(int a){
        this.a = a;
    }

    public void setB(int b){
        this.b = b;
    }

    public String toString(){

        return "{src.ObjEmp => a = "+this.a+", b = "+this.b+"}";

    }


}
