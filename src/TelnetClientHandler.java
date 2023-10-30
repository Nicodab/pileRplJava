package src;
import java.io.*;
import java.net.*;

import src.ObjEmp;
import src.ObjEmp3D;

public class TelnetClientHandler extends Thread {
    private Socket clientSocket;
    private BufferedReader inputUser;
    private PrintStream outputUser;
    private PileRPL pile;

    public TelnetClientHandler(Socket socket, PileRPL pile) {
        clientSocket = socket;
        this.pile = pile;
        try {
            inputUser = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outputUser = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                outputUser.println(pile);
                String cmd = inputUser.readLine();
                if (cmd.length() > 0)   cmdParser(cmd);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void cmdParser(String cmd){
        if (cmd.contains("push")){
            String argsInCommand = cmd.split(" ", 2)[1]; //2 pour séparer just en 2 morceau le tableau
            String [] separatedArgsInCommand = argsInCommand.split(" ");
            if (separatedArgsInCommand.length == 3){
                ObjEmp3D newElem = ObjEmp3D.parseInput(argsInCommand);
                System.out.println("ObjEmp3D crée");
                pile.push(newElem);
                System.out.println("ObjEmp3D Ajouté à la pile");
            }
            else{
                ObjEmp newElem = ObjEmp.parseInput(argsInCommand);
                outputUser.println("ObjEmp crée");
                pile.push(newElem);
                outputUser.println("ObjEmp Ajouté à la pile");
            }
            
        }
        else if (cmd.contains("pop")){
            pile.pop();
        }
        else if (cmd.contains("add")){
            pile.add();
        }
        else if (cmd.contains("sub")){
            pile.substract();
        }
        else if (cmd.contains("mult")){
            pile.multiply();
        }
        else if (cmd.contains("div")){
            pile.divide();
        }
        // Exit ==> on ferme la socket
        else if (cmd.contains("exit")){
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else outputUser.println("regarder l'aide avec l'option -h");
    }
}