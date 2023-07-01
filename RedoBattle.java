import java.util.*;
import java.io.*;

public class RedoBattle {
	//validate input before battle set up
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length != 1) {
	        return false;
	    }
	    char c = str.charAt(0);
	    if(c < '5' && c >= '0' ) {
	    	return true;
	    }
	    return false;
  	}
	public static boolean validate(String rowI, String colI, boolean isSetUp, char[][] battleArray) {
		int rowInput = 0;
	    int colInput = 0;
	    char emptySlot = '-';
	    char occupiedSlot = '@';
	    char damagedSlot = 'X';
	    char missedSlot = 'O';
	    
	    boolean result = false;
	    //check if input are valid integers; otherwise returns error
	    if(isInteger(rowI) && isInteger(colI)) {
	    	rowInput = Integer.parseInt(rowI);
	    	colInput = Integer.parseInt(colI);
	    } else {
	    	System.out.println("Invalid coordinates. Choose different coordinates.");
	    	return false;
	    }
		char slotInfo = battleArray[rowInput][colInput];
		if(isSetUp) {
			if (Character.compare(slotInfo, emptySlot)==0){
					result= true;
				} 
				else if (Character.compare(slotInfo, occupiedSlot)==0){
					System.out.println("You already have a ship there. Choose different coordinates.");
				}
		} else {
				if (Character.compare(slotInfo, emptySlot)==0 || Character.compare(slotInfo, occupiedSlot)==0){
		    		return true;
		    	} 
				else if (Character.compare(slotInfo, damagedSlot)==0 || Character.compare(slotInfo, missedSlot)==0){
					System.out.println("You already fired on this spot. Choose different coordinates.");
				}
		}
	    return result;  	
	}
	// Use this method to print game boards to the console.
	private static void printBattleShip(char[][] player) {
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					System.out.print(player[row][column] + " ");
				}
			}
			System.out.println("");
		}
	}
	// Use this method to print boards history to the console.
	private static void printHistory(char[][] player) {
		final char occupiedSlot = '@';
		final char emptySlot = '-';
		
		System.out.print("  ");
		for (int row = -1; row < 5; row++) {
			if (row > -1) {
				System.out.print(row + " ");
			}
			for (int column = 0; column < 5; column++) {
				if (row == -1) {
					System.out.print(column + " ");
				} else {
					if (Character.compare(player[row][column],occupiedSlot)==0){
						System.out.print(emptySlot + " ");
					} else {
						System.out.print(player[row][column] + " ");
					}
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	public static char[][] setUpBoard(Scanner input, boolean isSetUp, boolean isTurn1) {
		char[][] board= new char[5][5];

		String rowI = "";
	    String colI = "";
		final char occupiedSlot = '@';
		final char emptySlot = '-';

		for (int row=0; row<5; row++){
			for (int col=0; col<5; col++){
				board[row][col]=emptySlot;
			}
		}

		System.out.printf((isTurn1? "PLAYER 1" : "PLAYER 2") + ", ENTER YOUR SHIPS’ COORDINATES.\n");
		int[] listShip={1, 2, 3, 4, 5};
		for (int shipNo : listShip) {
			do {
				System.out.printf("Enter ship %d location: \n",shipNo);
				rowI= input.next();
    			colI = input.next();
			} while (!validate(rowI, colI , isSetUp, board));
			board[Integer.parseInt(rowI)][Integer.parseInt(colI)]=occupiedSlot;
		};
		
		return board;
	}
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Battleship!");
		System.out.println("");
		int[] listShip={1, 2, 3, 4, 5};

		//create two 2d arrays for both players5
		char[][] player1= new char[5][5];
		char[][] player2= new char[5][5];
		final char emptySlot = '-';
		final char occupiedSlot = '@';
	    char damagedSlot = 'X';
	    char missedSlot = 'O';
	    boolean isSetUp = true;
	    boolean isTurn1 = true;
		
		
		//task 1:for each player, add 5 ships locations from scanner input. Check valid type (int), no duplicates then proceed.
		String rowI = "";
	    String colI = "";
		
    	
		player1 = setUpBoard(input, isSetUp, isTurn1);
		printBattleShip(player1);
		for (int i = 0; i < 100; i++) {
			System.out.println("");
		}
		isTurn1 = false;
    	isSetUp = true;
    	
		player2 = setUpBoard(input, isSetUp, isTurn1);
		printBattleShip(player2);
		for (int i = 0; i < 100; i++) {
			System.out.println("");
		}
		  	
						
		//task 2: prompt player 1 start fire
		int rowInput = 0;
	    int colInput = 0;
	    int countP1=0;
	    int countP2=0;
	    boolean completeFlag = false;
	    isSetUp = false;
	    isTurn1 = true;
	    
	    do {
			rowI = "";
	    	colI = "";
			do {
				System.out.printf((isTurn1? "Player 1" : "Player 2") + ", enter hit row/column:\n");
				rowI= input.next();
				colI = input.next();
			} while (!validate(rowI, colI, isSetUp, (isTurn1)? player2 : player1));
			rowInput = Integer.parseInt(rowI);
			colInput = Integer.parseInt(colI);
			char hitSpot = (isTurn1)? player2[rowInput][colInput] : player1[rowInput][colInput];
			switch (hitSpot) {
				case emptySlot:
					hitSpot = missedSlot;
					if (isTurn1){
						player2[rowInput][colInput]=hitSpot;
					} else {
						player1[rowInput][colInput]=hitSpot;
					}
	    			System.out.printf(((isTurn1)? "PLAYER 1" : "PLAYER 2") + " MISSED!\n");
	    			printHistory((isTurn1)? player2 : player1);
	    			break;
	    		case occupiedSlot:
	    			hitSpot = damagedSlot;
	    			if (isTurn1){
						player2[rowInput][colInput]=hitSpot;
					} else {
						player1[rowInput][colInput]=hitSpot;
					}
	    			if(isTurn1){
	    				countP1 +=1;
	    			} else {
	    				countP2 +=1;
	    			}
	    			System.out.println(((isTurn1)? "PLAYER 1 HIT PLAYER 2’s SHIP!" : "PLAYER 2 HIT PLAYER 1’s SHIP!"));

	    			printHistory((isTurn1)? player2 : player1);
	    			if (countP1 == 5 || countP2==5){
	    				completeFlag=true;
	    			}
	    			break;
			}
			isTurn1 = !isTurn1;	
					
	    } while (completeFlag == false);
	    if (countP1 == 5){
	    	System.out.println("PLAYER 1 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!");
	    	System.out.println("");
	    } else {
	    	System.out.println("PLAYER 2 WINS! YOU SUNK ALL OF YOUR OPPONENT’S SHIPS!");
	    	System.out.println("");
	    }
	    System.out.println("Final boards:");
	    System.out.println("");
	    printBattleShip(player1);
	    System.out.println("");
	    printBattleShip(player2);

    
    }
}