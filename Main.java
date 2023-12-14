//CHECKERS

import java.util.Scanner;

public class Main {

   private static Piece[] pieces;
   private static boolean running = false;
   private static Piece currentPiece = null;
  
   public static void main(String[] args) {
      Scanner input = new Scanner(System.in);
      String letterChoice;
      int numChoice;

      start();
      System.out.println("Welcome to checkers!");
      System.out.println();

     while(running){
       int winner = 0;
       for(int currentTurn = 1; currentTurn <= 2; currentTurn++){
          //prompt the user for the piece they want to move
          System.out.println("It is currently player " + currentTurn + "'s turn.");
          System.out.println("Please choose the piece you want to move, player " + currentTurn);
          do{
            show();
            input = new Scanner(System.in);
            System.out.println("Letter (only enter ONE character): ");
            letterChoice = input.nextLine();
            System.out.println("Number: ");
            numChoice = input.nextInt();
            currentPiece = search(letterChoice, numChoice);
            if(currentPiece == null || currentPiece.getPlayer() != currentTurn)
                System.out.println("Not a valid peice! Please enter a valid piece.");
          } while(currentPiece == null || currentPiece.getPlayer() != currentTurn);
    
          //Prompt the user for the location they want to move.
         
          String moveLetter;
          do{
             input = new Scanner(System.in);
             System.out.println();
             System.out.println("Where would you like to move the piece at " + currentPiece.getLetter() + currentPiece.getNum());
             System.out.println("Letter (only enter ONE character): ");
             moveLetter = input.nextLine();
             System.out.println("Number: ");
          } while(currentPiece.move(moveLetter, input.nextInt()) == false);
          currentPiece = null;
          winner = checkEndGame();
          if(winner > 0)
            break;
         //printPieces();
       }
       
       //Check if the game is over.
       if(winner == 1){
          System.out.println("Game over! Player 1 wins!");
       } else if(winner == 2){
          System.out.println("Game over! Player 2 wins!");
       }
     }
     show();
     input.close();
   }
   
   public static void start(){
     //Setting up Piece locations.
     pieces = new Piece[] {new Piece("a", 2, 1), new Piece("a", 4, 1), new Piece("a", 6, 1), new Piece("a", 8, 1), new Piece("b", 1, 1), 
     new Piece("b", 3, 1), new Piece("b", 5, 1), new Piece("b", 7, 1), new Piece("c", 2, 1), new Piece("c", 4, 1), new Piece("c", 6, 1), 
     new Piece("c", 8, 1), new Piece("f", 1, 2), new Piece("f", 3, 2), new Piece("f", 5, 2), new Piece("f", 7, 2), new Piece("g", 2, 2), 
     new Piece("g", 4, 2), new Piece("g", 6, 2), new Piece("g", 8, 2), new Piece("h", 1, 2), new Piece("h", 3, 2), new Piece("h", 5, 2), 
     new Piece("h", 7, 2)};
    
     System.out.println("Setup complete! Starting...");
      
     running = true;
   }

//Searches the list of available pieces in the list. If it is found, return that object. Otherwise, return null.
   public static Piece search(String pieceLetter, int pieceNum){
     Piece foundPiece = null;
     for(int i = 0; i < pieces.length; i++){
       if(pieces[i].getLetter().equals(pieceLetter) && pieces[i].getNum() == pieceNum && pieces[i].isAlive()){
         foundPiece = pieces[i];
         break;
       }
     }
     return foundPiece;
   }
   
   public static Piece search(Piece other){
     Piece foundPiece = null;
     for(int i = 0; i < pieces.length; i++){
       if(pieces[i].equals(other)){
         foundPiece = pieces[i];
         break;
       }
     }
     return foundPiece;
   }
   
//Searches through the list to determine if there is a piece at that location. Return true if there is a piece at that location, else return false.
   public static boolean containsPieceAt(String letter, int num){
     boolean contains = false;
     for(int i = 0; i < pieces.length; i++){
       if(pieces[i].getNum() == num && pieces[i].getLetter().equals(letter) && pieces[i].isAlive()){
         contains = true;
         break;
       }
     }
     return contains;
   }
   
   public static void printPieces(){
      for(int i = 0; i < pieces.length; i++){
         System.out.println(pieces[i]);
         System.out.println();
      }
   }
   
   //Prints the locations of the pieces in the list.
   public static void show(){
   //initialize String for each row
   String a = "        ";
   String b = "        ";
   String c = "        ";
   String d = "        ";
   String e = "        ";
   String f = "        ";
   String g = "        ";
   String h = "        ";
   
   //Iterate through the list.
     for(int i = 0; i < pieces.length; i++){
       Piece currentPiece = pieces[i];
       char currentLetter = currentPiece.getLetter().charAt(0);
       String display = "";
       
       //Logic for what kind of piece to display.
       if(currentPiece.isAlive()){
          if(currentPiece.getPlayer() == 1){
             if(currentPiece.getIsKing()){
                display = "A";
             } else {
                display = "a";
             }
          } else if(currentPiece.getPlayer() == 2){
             if(currentPiece.getIsKing()){
                display = "B";
             } else {
                display = "b";
             }
          }
       }
       
       //Add the piece to their respective row and location.
       switch(currentLetter){
          case 'a':
             a = a.substring(0, currentPiece.getNum()) + display + a.substring(currentPiece.getNum(), a.length());
             break;
          case 'b':
             b = b.substring(0, currentPiece.getNum()) + display + b.substring(currentPiece.getNum(), b.length());
             break;
          case 'c':
             c = c.substring(0, currentPiece.getNum()) + display + c.substring(currentPiece.getNum(), c.length());
             break;
          case 'd':
             d = d.substring(0, currentPiece.getNum()) + display + d.substring(currentPiece.getNum(), d.length());
             break;
          case 'e':
             e = e.substring(0, currentPiece.getNum()) + display + e.substring(currentPiece.getNum(), e.length());
             break;
          case 'f':
             f = f.substring(0, currentPiece.getNum()) + display + f.substring(currentPiece.getNum(), f.length());
             break;
          case 'g':
             g = g.substring(0, currentPiece.getNum()) + display + g.substring(currentPiece.getNum(), g.length());
             break;
          case 'h':
             h = h.substring(0, currentPiece.getNum()) + display + h.substring(currentPiece.getNum(), h.length());
             break;
       }
     }
     
     //Print each row
     System.out.println(a);
     System.out.println(b);
     System.out.println(c);
     System.out.println(d);
     System.out.println(e);
     System.out.println(f);
     System.out.println(g);
     System.out.println(h);
   }
   
   public static int checkEndGame(){
       boolean hasP1 = false;
       boolean hasP2 = false;
       for(int i = 0; i < pieces.length; i++){
          if(pieces[i].getPlayer() == 1 && pieces[i].isAlive())
             hasP1 = true;
          if(pieces[i].getPlayer() == 2 && pieces[i].isAlive())
             hasP2 = true;
          if(hasP1 && hasP2)
             break;
       }
      if(!hasP2){
         running = false;
         return 1;
      } else if (!hasP1){
         running = false;
         return 2;
      } else {
         return 0;
      }
   }
}  