package src;

public class ObjEmp3D extends ObjEmp {
    private int c;
    public ObjEmp3D(int _a, int _b, int _c){
        super(_a, _b);
        this.c = _c;
    }

    // parser un objEmp depuis ce qui est entrée par le user
    public static ObjEmp3D parseInput(String str){
        ObjEmp3D objEmp3D = null;

        String[] numbers = str.split(" ");

        // Revoir, possiblement >= 2 ("c'était pa le bout de code auquel je m'attendais à voir")
        if(numbers.length == 3){

            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);
            int c = Integer.parseInt(numbers[2]);

            objEmp3D = new ObjEmp3D(a, b, c);
        }
        System.out.println(objEmp3D);
        return objEmp3D;
    }

    // Additionner un ObjEmp avec un autre
    public ObjEmp3D add(ObjEmp3D obj){
        ObjEmp3D newObj = new ObjEmp3D(this.a + obj.getA(), this.b + obj.getB(), this.c + obj.getC());
        return newObj; 
    }

    // Soustraire un ObjEmp avec un autre
    public ObjEmp3D substract(ObjEmp3D obj){
        ObjEmp3D newObj = new ObjEmp3D(this.a - obj.getA(), this.b - obj.getB(), this.c - obj.getC());
        return newObj; 
    }

    // Multiplier un ObjEmpl avec un autre
    public ObjEmp3D multiply(ObjEmp3D obj){
        ObjEmp3D newObj = new ObjEmp3D(this.a * obj.getA(), this.b * obj.getB(), this.c * obj.getC());
        return newObj; 
    }

    public ObjEmp3D divide(ObjEmp3D obj){
        ObjEmp3D newObj = new ObjEmp3D(this.a / obj.getA(), this.b / obj.getB(), this.c / obj.getC());
        return newObj; 
    }

    public int getC(){
        return this.c;
    }

    public void setC(int c){
        this.c = c;
    }

    public String toString(){

        return "{src.ObjEmp => a = "+this.a+", b = "+this.b+", c = "+this.c+"}";

    }
}
