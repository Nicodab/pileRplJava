package src;
import src.ObjEmp3D;

//import java.lang.*;

public class PileRPL {

    private ObjEmp[] stack; // pile
    private int nbObj;  // nb d'obj actuel dans la pile
    private final static int DEFAULT_MAX_SIZE = 3;

    // Constructeur avec taille maximum imposée
    public PileRPL(int maxSize){
        this.stack = new ObjEmp[maxSize];
        this.nbObj = 0;
    }

    // Constructeur avec taille maximum par défaut
    public PileRPL(){
        this(DEFAULT_MAX_SIZE);
        this.nbObj = 0;
    }

    public void add(){
        if(this.nbObj < 2){
            System.out.println("Pas assez d'objet dans la pile");
        } else {
            if ((this.stack[this.nbObj-1].getClass()) == (this.stack[this.nbObj -2].getClass())){
                if (this.stack[this.nbObj-1].getClass() == ObjEmp3D.class){
                    ObjEmp3D obj1 = this.pop3D();  
                    ObjEmp3D obj2 = this.pop3D();
                    this.push(obj1.add(obj2));    
                }
                else{
                    ObjEmp obj1 = this.pop();  
                    ObjEmp obj2 = this.pop();
                    this.push(obj1.add(obj2));
                }
            }
            else System.out.println("On ne peut pas additionner deux objets de dimension différentes");
        }
    }

    public void substract(){

        if(this.nbObj < 2){
            System.out.println("Pas assez d'objet dans la pile");
        } else {

            if ((this.stack[this.nbObj-1].getClass()) == (this.stack[this.nbObj -2].getClass())){
                if (this.stack[this.nbObj-1].getClass() == ObjEmp3D.class){
                    ObjEmp3D obj1 = this.pop3D();  
                    ObjEmp3D obj2 = this.pop3D();
                    this.push(obj1.substract(obj2));    
                }
                else{
                    ObjEmp obj1 = this.pop();  
                    ObjEmp obj2 = this.pop();
                    this.push(obj1.substract(obj2));
                }
            }
            else System.out.println("On ne peut pas soustraire deux objets de dimension différentes");

        }
    }

    public void multiply(){

        if(this.nbObj < 2){
            System.out.println("Pas assez d'objet dans la pile");
        } else {

            /*ObjEmp obj1 = this.pop();  
            ObjEmp obj2 = this.pop();  
            this.push(obj1.multiply(obj2));*/

            if ((this.stack[this.nbObj-1].getClass()) == (this.stack[this.nbObj -2].getClass())){
                if (this.stack[this.nbObj-1].getClass() == ObjEmp3D.class){
                    ObjEmp3D obj1 = this.pop3D();  
                    ObjEmp3D obj2 = this.pop3D();
                    this.push(obj1.multiply(obj2));    
                }
                else{
                    ObjEmp obj1 = this.pop();  
                    ObjEmp obj2 = this.pop();
                    this.push(obj1.multiply(obj2));
                }
            }
            else System.out.println("On ne peut pas multiplier deux objets de dimension différentes");
        }
    }

    public void divide(){

        if(this.nbObj < 2){
            System.out.println("Pas assez d'objet dans la pile");
        } else {
            if ((this.stack[this.nbObj-1].getClass()) == (this.stack[this.nbObj -2].getClass())){
                if (this.stack[this.nbObj-1].getClass() == ObjEmp3D.class){
                    ObjEmp3D obj1 = this.pop3D();  
                    ObjEmp3D obj2 = this.pop3D();
                    this.push(obj1.divide(obj2));    
                }
                else{
                    ObjEmp obj1 = this.pop();  
                    ObjEmp obj2 = this.pop();
                    this.push(obj1.divide(obj2));
                }
            }
            else System.out.println("On ne peut pas multiplier deux objets de dimension différentes");
        }
    }

    public ObjEmp pop(){
        ObjEmp obj;
        if(this.nbObj <= 0){
            System.out.println("CANNOT POP, nbOBJ is" + this.nbObj);
            obj = null;
        } else {
            obj = this.stack[this.nbObj -1];
            this.nbObj--;
        }
        return obj;
    }

    public ObjEmp3D pop3D(){
        ObjEmp3D obj;
        if(this.nbObj <= 0){
            System.out.println("CANNOT POP, nbOBJ is" + this.nbObj);
            obj = null;
        } else {
            obj = (ObjEmp3D) this.stack[this.nbObj -1];
            this.nbObj--;
        }
        return obj;
    }


    public int getSize(){
        return this.stack.length;
    }

    public int getCount(){
        return this.nbObj;
    }

    public void push(ObjEmp objEmp){

        if(this.nbObj == this.stack.length){
            System.out.println("La pile est pleine, on ne peut plus pousser d'objets.");
        }

        this.stack[this.nbObj] = objEmp;
        this.nbObj++;
        return;
    }

    /*private int getLongestLengthObjEmp(){
        int lengthLongestObj = 0;
        for(int i = 0; i < this.nbObj; i++)
            lengthLongestObj = this.stack[i].toString().length() > lengthLongestObj ? this.stack[i].toString().length() : lengthLongestObj;
        return 10;
    }*/

    // Construction de l'Affichage de la pile 
    public String toString(){
        String str = "";
        str += "\n            \n";
        str += "\n+----------+\n";
        str += "\n!          !\n";
        
        for(int i = 0; i < this.nbObj; i++){
            String strValue = "";
            strValue = this.stack[i].getA() + " " + this.stack[i].getB();
            if (this.stack[i].getClass() == ObjEmp3D.class){
                strValue += " " + ((ObjEmp3D) this.stack[i]).getC();
            }
            String stringPadding = "";
            for(int a = 0; a < 10 - strValue.length(); a++) stringPadding += " ";
            str += ("!" + strValue + stringPadding + "!\n");
        }
        str += ("+----------+");
        return str;
    }

}