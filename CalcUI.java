import src.PileRPL;
import src.TelnetClientHandler;
import src.ObjEmp;
import src.ObjEmp3D;
// Pour le socket en mode remote
import java.net.*;
import java.io.*;

public class CalcUI {
    private PileRPL pile;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader inputUser;
    private BufferedReader inputReplay; // Revu des actions du fichier de log
    private BufferedReader inputLog; // Buffer d'un fichier de log à lire en flux d'entrée pour activer lemode replay.
    private PrintStream outputUser;
    private PrintStream outputLog; // log recording pour savoir qd on enregistre maispas la var ici nécessairement OU REVU de session ne serait-ce que pour avoir l'écho de ce qu'on interprète.
    private boolean logMode = false;
    private boolean replayMode = false;
    private boolean exit = false;
    
    // On peut initialiser la pile au début dans son constructeur comme ca on a déjà son argument
    public CalcUI(String [] args) {
        try{
            // Initialisation des fluxs (y'en a plusieurs)
            initStreams(args); 
            // Boucle d'éxecution en continu de l'application
            mainloop();
        }
            
        catch (Exception e){
            System.out.println("erreur");
        }
            
    }

    // Une sorte de faux main donc c'est pas static
    public void mainloop(){
            while(true){
                try {
                    // On déclare une pile ici
                    outputUser.println(pile); // Outstream textuel bufferisée pour afficher la pile ou l'état de la pile etc... ça se fait avec la méthode toString redéfinit dans la classe PileRPL
                    String cmd = inputUser.readLine(); // Lecture inputStream du User
                    // Mode "recording" ==> enregistrement des commandes dans le fichier de log indiqué
                    if (logMode == true) {
                        outputLog.println(cmd);
                        outputLog.flush();
                    }
                    if (replayMode == true) outputUser.println(cmd);//Si replay on print dans leflux de sortie les commandes
                    if (cmd.equals("exit")) return; // Pour quitter leprog sans faire un System.exit(0); qui serait trop brutal
                    cmdParser(cmd); //--> Revoir comment faire et définir les différentes commande possible du user (par exemple push, pop, exit et puis les opérateur comme mult, add, div, sub)

                }
                catch (Exception e){
                    System.out.println(e);
                }
            }
    }

    // Initialisationdes connexions locales ou distantes en fonction des options
    public void initStreams(String [] args){
        String argument = args[0]; // ex: user:local
        String pileSize = args[1]; // ex: 3
        String recordOrReplay = ""; // rec/rep=./chemin/vers/ficher après user:local pour indiquer le mode recording
        if (args.length == 3) recordOrReplay = args[2]; // le 2eme arg peut-être soit record soit replay
        try{

            if (argument.equals("user:local") && (recordOrReplay.equals("")) ) {
                pile = new PileRPL(Integer.valueOf(pileSize)); //Init de la pile
                initFullLocal(logMode, ""); // On met rien dans le filename car pas d'argument recordOrReplay
            } 
            // Connecter l'utilisateur en mode replay local.
            else if ((argument.equals("user:local")) && (recordOrReplay.contains("rep="))) {
                pile = new PileRPL(Integer.valueOf(pileSize)); //Init de la pile
                String filepath = "";
                
                replayMode = true;
                filepath = recordOrReplay.split("=")[1];
                initFullReplayLocale(filepath);
            }
            // Connecter l'utilisateur en mode recording local 
            else if ((argument.equals("user:local")) && (recordOrReplay.contains("rec="))) {
                pile = new PileRPL(Integer.valueOf(pileSize));
                logMode = true;
                String logFile = recordOrReplay.split("=")[1];
                initFullLocal(logMode, logFile);
            } 
                // Connexions distantes
            else if (argument.equals("user:remote")) {
                pile = new PileRPL(Integer.valueOf(pileSize));
                // Remote record
                if (recordOrReplay.contains("rec=")){
                    logMode = true;
                    String logFile = recordOrReplay.split("=")[1];
                    initFullRemote(logMode, logFile);
                }
                // Remote Replay 
                else if (recordOrReplay.contains("rep=")){
                    replayMode = true;
                    String filepath = recordOrReplay.split("=")[1];
                    initReplayNetwork(filepath);
                }
                // Remote local
                else initFullRemote(logMode, "");
            }  
            else {
                System.out.println("Argument non reconnu : " + argument);
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    // Initialisation du initFullLocal avec booleen logMode et le nomdefichier de sortie
    public void initFullLocal(boolean logMode, String filename) throws Exception {
        inputUser = new BufferedReader(new InputStreamReader(System.in));
        outputUser = System.out;
        //Si c'est en mode log on ouvre un fichier et les flux sortants seront redirigés dedans
        if (logMode){
            outputLog = new PrintStream(new FileOutputStream(filename, true), true, "UTF-8");
        }
    }
    // Initialisation du initFullRemote avec booleen logMode et le nom defichier de sortie
    public void initFullRemote(boolean logMode, String filename) throws Exception{
        serverSocket = new ServerSocket(12345);
        try {
            System.out.println("Serveur Telnet en attente de connexions...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                TelnetClientHandler clientHandler = new TelnetClientHandler(clientSocket, new PileRPL());
                clientHandler.start(); // Démarrer un nouveau thread pour ce client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Initialisation du initFullReplayLocal avec le nom defichier d'entrée
    public void initFullReplayLocale(String filename){
        try {
			inputUser = new BufferedReader(new FileReader(filename));
            System.out.println(filename);
            outputUser = System.out;
		} catch( FileNotFoundException exc ) {
			System.out.println( "le fichier n'existe pas" );
			System.exit( 1 );
		}
    }
    public void initReplayNetwork(String filename) throws Exception{
        serverSocket = new ServerSocket(12345);
        System.out.println("En attente de connexions distantes sur le port 12345...");
        clientSocket = serverSocket.accept();
        System.out.println("Client connecté.");
        outputUser = new PrintStream(clientSocket.getOutputStream());
        try{
            inputUser = new BufferedReader(new FileReader(filename));
        }
        catch (FileNotFoundException e){
            System.out.println( "le fichier n'existe pas" );
			System.exit( 1 );
        }
    }

    public void cmdParser(String cmd){
        if (cmd.contains("push")){
            String argsInCommand = cmd.split(" ", 2)[1]; //2 pour séparer just en 2 morceau le tableau
            String [] separatedArgsInCommand = argsInCommand.split(" ");
            // Vecteur 3D à pousser dans la pile
            if (separatedArgsInCommand.length == 3){
                ObjEmp3D newElem = ObjEmp3D.parseInput(argsInCommand);
                System.out.println("ObjEmp3D crée");
                pile.push(newElem);
                System.out.println("ObjEmp3D Ajouté à la pile");
            }
            // Vecteur 2D à pousser dans la pile
            else{
                ObjEmp newElem = ObjEmp.parseInput(argsInCommand);
                System.out.println("ObjEmp crée");
                pile.push(newElem);
                System.out.println("ObjEmp Ajouté à la pile");
            }
            
        }
        //Retirer ledernière objet poussé dans la pile
        else if (cmd.contains("pop")){
            pile.pop();
        }
        //Additionner les 2 derniers objets de la pile
        else if (cmd.contains("add")){
            pile.add();
        }
        //Soustraire les 2 derniers objets de la pile
        else if (cmd.contains("sub")){
            pile.substract();
        }
        //Multiplier les 2 derniers objets de la pile
        else if (cmd.contains("mult")){
            pile.multiply();
        }
        //Diviser les 2 derniers objets de la pile
        else if (cmd.contains("div")){
            pile.divide();
        }
        // Quitter le programme
        else if (cmd.contains("exit")){
            System.exit( 1 );
        }
        else System.out.println("regarder l'aide avec l'option -h");
    }
}