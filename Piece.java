public class Piece{
  
   private String locLetter;
   private int locNum;
   private int player;
   private boolean isKing;
   private boolean alive;

   public Piece(String letter, int num, int player){
      if(letter.compareTo("h") <= 0)
         locLetter = letter;
      if(num <= 8)
         locNum = num;
      this.player = player;
      isKing = false;
      alive = true;
   }

   public String getLetter(){
      return locLetter;
   }

   public int getNum(){
      return locNum;
   }

   public boolean getIsKing(){
      return isKing;
   }

   public int getPlayer(){
      return player;
   }

   public String toString(){
      return "player: " + player + "\nlocation: " + locLetter + locNum + "\nking: " + isKing + "\nalive: " + alive;
   }

   public void kill(){
      alive = false;
   }
  
   public boolean isAlive(){
      return alive;
   }

   public boolean equals(Piece other){
      if(other != null)
         return locLetter == other.getLetter() && locNum == other.getNum() && player == other.getPlayer() && isKing == other.getIsKing();
      return false;
   }
   
   public void updateKing(){
      if(player == 1 && locLetter.equals("h")){
         isKing = true;
      } else if (player == 2 && locLetter.equals("a")){
         isKing = true;
      }
   }

/* Main logic for moving the pieces. Recieves the letter and number coordinates for the location user wants to 
move the current piece object. Standard rules of checkers applies (can only move diagonal forwards. Kings can 
move diagonal backwards.) Returns false if move is unsuccessful.*/

   public boolean move(String moveLetter, int moveNum){
    //convert to char and setup local vars
      char pieceLetter = locLetter.charAt(0);
      char playerLetter = moveLetter.charAt(0);
      int distance = Math.abs(moveNum - locNum);
      boolean containsPiece = Main.containsPieceAt(moveLetter, moveNum);
      boolean successful = false;
      
      //logic for determining piece to jump over
      Piece jumpPiece;
      if(moveNum - locNum > 0){
         if(playerLetter > pieceLetter){
            jumpPiece = Main.search((char)(pieceLetter + 1) + "", locNum + 1);
         } else {
            jumpPiece = Main.search((char)(pieceLetter - 1) + "", locNum + 1);
         }
      } else {
         if(playerLetter > pieceLetter){
            jumpPiece = Main.search((char)(pieceLetter + 1) + "", locNum - 1);
         } else {
            jumpPiece = Main.search((char)(pieceLetter - 1) + "", locNum + 1);
         }
      }
      
      boolean canJump = jumpPiece != null && player != jumpPiece.getPlayer();
      
      //checking if piece can move to selected location. If so, update object variables. Otherwise, return false and notify user.
      if(containsPiece == false){
         if(!isKing){
            if(player == 1){
               if(pieceLetter + 1 == playerLetter && distance == 1){
                  locLetter = playerLetter + "";
                  locNum = moveNum;
                  successful = true;
               } else if(canJump && distance == 2){
                  jumpPiece.kill();
                  locLetter = playerLetter + "";
                  locNum = moveNum;
                  successful = true;
               } else {
                  System.out.println("That is an invalid move. Not a valid space to move.");
                  successful = false;
               }
            } else {
               if(pieceLetter - 1 == playerLetter && distance == 1){
                  locLetter = playerLetter + "";
                  locNum = moveNum;
                  successful = true;
               } else if(canJump && distance == 2){
                  jumpPiece.kill();
                  locLetter = playerLetter + "";
                  locNum = moveNum;
                  successful = true;
               } else {
                  System.out.println("That is an invalid move. Not a valid space to move.");
                  successful = false;
               }
            }
         } else if(distance == 1){
            locLetter = playerLetter + "";
            locNum = moveNum;
            successful = true;
         } else if(canJump && distance == 2){
            jumpPiece.kill();
            locLetter = playerLetter + "";
            locNum = moveNum;
            successful = true;
         } else {
            System.out.println("That is an invalid move. Not a valid space to move.");
            successful = false;
         }
      } else {
         System.out.println("That is an invalid move. There is a piece there.");
         successful = false;
      }
      updateKing();
      return successful;
   }
}