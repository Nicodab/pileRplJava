public class Calculatrice {
  double haut;  // ? 
  Calculatrice suite ; // ?

  Calculatrice (double elt, Calculatrice old) {
    haut = elt ;
    suite = old ;
  }

  private static void error (String msg) {
    System.err.println (msg) ;
    System.exit (2) ;
  }

  static Calculatrice push (Calculatrice pile, double elt) {
    // empile un reel
    return new Calculatrice (elt, pile) ;
  }

  static double top (Calculatrice pile) {
    // haut de pile
    if (pile == null)
      error ("Top sur pile vide") ;
    return pile.haut ;
  }

  static Calculatrice pop (Calculatrice pile) {
    // depile
    if (pile == null)
      error ("Pop sur pile vide") ;
    return pile.suite ;
  }

  static String toString (Calculatrice pile) {
    // impression du contenu de la pile
    // version iterative
    String out = "";
    int rang = 0;

    while (pile != null) {
      out = rang + ": " + pile.haut + "\n" + out;
      pile = pile.suite;
      rang++;
    }
    return out ;
  }

  /*static Calculatrice neg (Calculatrice pile) {
    // negation du dernier element de la pile
  }

  static Calculatrice add (Calculatrice pile) {
    // addition des deux derniers elements de la pile
  }

  static Calculatrice sub (Calculatrice pile) {
    // soustraction des deux derniers elements de la pile
  }

  static Calculatrice mul (Calculatrice pile) {
    // multiplication des deux derniers elements de la pile
  }

  static Calculatrice div (Calculatrice pile) {
    // division des deux derniers elements de la pile
  }
  */

  public static String readline () throws java.io.IOException {
    // Cette methode attend que l'utilisateur tape des donnees,
    // terminees par "enter" et en fait une chaine de caracteres.
    String ligne = "" ;
    for ( ; ; ) { // boucle infinie
      int r = System.in.read () ;
      if (r == -1)   return "q" ;   // fin de fichier
      char c = (char) r ;
      if (c == '\n') return ligne ; // fin de ligne
      ligne += c ;
    }
  }

  // Attention l'interface user ne se code pas totalement ds le main
  // rq: si on fait ts ds le main on ne pourra pas dans un 2nd temps instancier d'autre objet --> Il faudra que ce soit ds la même boucle ce squi instancie les différents objets.
  // rq: on peut faire aussi pas de main --> donc calcUI (voir tout en bas l'ex de code)
  // CalcUI c'est la classe interface user.
  // Un obj avec inputUser et outputUser
  // Après on a une boucle principale et tant que ça tourne on affiche l'état de la pile,  on parse les commandes
  public static void main (String[] args) throws java.io.IOException {
    Calculatrice p = null ;
    for ( ; ; ) { // boucle infinie --> ATTENTION c'ets pas une boucle infini, c'est 
      System.out.println ("Commandes : q NEG + - / * un_nombre") ;
      String commande = readline () ;
      if (commande.equals("q")) System.exit (0) ;
      // Remettre tout ça une fois que les fonctions sont codées
      //else if (commande.equals("NEG")) p = neg (p) ;
      //else if (commande.equals("+")) p = add (p) ;
      //else if (commande.equals("-")) p = sub (p) ;
      //else if (commande.equals("*")) p = mul (p) ;
      //else if (commande.equals("/")) p = div (p) ;
      else {
        double x = Double.valueOf (commande) . doubleValue() ;
        p = push (p, x) ;
      }
      System.out.print (toString (p)) ;
    }
  }
}

// On instancie aucune obj avec un main --> Un obj ca va etre manipulées et eventuellement ça va etre instancier dans le main
// Mais avec cet ex "Il faudra avoir 2 main au lieu d'un"
//une fois que la calc est lancée elle fonctionne tt le temps de la meme manière
// On réunit le fonctionnement dans un obj
/*public class FooRPL{
  public static void main(String [] args){
    new CaclUI(args)
  }
}*/