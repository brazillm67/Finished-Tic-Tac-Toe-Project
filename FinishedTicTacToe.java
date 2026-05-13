package pkg1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class FinishedTicTacToe {

	static ArrayList<Integer> HumanPlacement = new ArrayList<Integer>(); /*List of Arrays containing
	the numbers correlating to spots that have already been used, in this case by the human*/
	static ArrayList<Integer> Human2Placement = new ArrayList<Integer>(); /*List of Arrays containing
	the numbers correlating to spots that have already been used, for player 2*/
	static ArrayList<Integer> MachinePlacement = new ArrayList<Integer>(); /*List of Arrays containing
	the numbers correlating spots that have already been used, in this case by the machine*/
	
	private static boolean isGameFinished(char[][] board) {
		String result = checkWinner();						/*It checks for a winner with the
		list of arrays*/	
		if (result.length() > 0) {	/*if the message returned from the winner() method
			has a length greater than zero, ie a victory message, then the board is
			printed out along with the victory method*/						/*it prints the board */				
			print(board);
			System.out.println(result);
			return true;
		}
		return false;
	}
	
	private static String checkWinner() {
		//Each list represents a winning combination
		List<Integer> topRow = Arrays.asList(1, 2, 3);
		List<Integer> midRow = Arrays.asList(4, 5, 6);
		List<Integer> bottomRow = Arrays.asList(7, 8, 9);
		List<Integer> leftColumn = Arrays.asList(1, 4,7);
		List<Integer> midColumn = Arrays.asList(2, 5, 8);
		List<Integer> rightColumn = Arrays.asList(3, 6, 9);
		List<Integer> cross1 = Arrays.asList(1, 5, 9);
		List<Integer> cross2 = Arrays.asList(3, 5, 7);
		
		List<List> victory = new ArrayList<List>(); /*a List of the previously mentioned lists, thus
		representing a full list of all winning combinations*/
		victory.add(topRow);
		victory.add(midRow);
		victory.add(bottomRow);
		victory.add(leftColumn);
		victory.add(midColumn);
		victory.add(rightColumn);
		victory.add(cross1);
		victory.add(cross2);
			
			for(List L1 : victory) { //iterates through the Lists in the ArrayList, that represent victory combinations
				if(HumanPlacement.containsAll(L1)) { /*If a victory combination is found in the array of ints
					representing the positions taken by player 1, the human wins*/
					return "Player 1 is victorious!";
				}
				else if(MachinePlacement.containsAll(L1)) { /*If a victory combination is found in the array of ints
					representing the positions taken by the machine, the machine wins*/
					return "The machine is victorious.";
				}
				else if(Human2Placement.containsAll(L1)) { /*If a victory combination is found in the array of ints
					representing the positions taken by player 2, the machine wins*/						
					return "Player 2 is victorious!";
				}
			}
				
				 if(HumanPlacement.size() + MachinePlacement.size() + Human2Placement.size() == 9) { /*
				 If the number of items in the arrays containing the placement values for 
				 HumanPlacement, MachinePlacement, and Human2Placement reaches 9, the total possible
				 number of spots that can be taken on the board, and none of the winning combinations
				 above have been found, then it's a draw
				 */
					return "It's a draw!"; 
				}
			 return ""; /*returns nothing if there is no winner, which is necessary for the checkwinner
			 since it checks if the length of the string returned from this method is greater than zero, which
			 it is if there is a winning combination found in one of the arrays, but doesn't if there isn't
			 a winning combination*/
	}
	
	private static void playerTurn (char[][] board, Scanner scan) {
		System.out.println("Player 1: Which position would you like to take (1-9)?");
		int move = 0; // Initialize with 0
	    
	    if (scan.hasNextInt()) {
	        move = scan.nextInt();
	    } else {
	        scan.next(); // Consumes the invalid character so the program doesn't crash
	    }
		
		while (HumanPlacement.contains(move) || Human2Placement.contains(move) || MachinePlacement.contains(move) || move < 1 || move > 9) {
			/*While the spot that the user inputs, move, is contained within any
			 * of the arrays that hold the spots that have been taken by Human1 or Human2
			 * or the Machine or is less than 1 or greater than 9 */
			System.out.println("Invalid input, enter a different number:");
			if (scan.hasNextInt()) {
	            move = scan.nextInt();
	        } else {
	            scan.next(); // Consumes the invalid character
	        }
		}
		HumanPlacement.add(move); //add the chosen position, 1 - 9, to the HumanPlacement array
		placeMove(board, move, 'X'); /*take this input, and make that number spot on the board
		replace the current space therein with an X */
	}
	
	private static void player2Turn(char[][] board, Scanner scan) {

		System.out.println("Player 2: Which position would you like to take (1-9)?");
		int move2 = scan.nextInt();
		
		while (HumanPlacement.contains(move2) || Human2Placement.contains(move2) || MachinePlacement.contains(move2) || move2 < 1 || move2 > 9) {
			/*While the spot that the user inputs, move, is contained within any
			 * of the arrays that hold the spots that have been taken by Human1 or Human2
			 * or the Machine or is less than 1 or greater than 9 */
			System.out.println("Invalid input, enter a different number:");
			move2 = scan.nextInt();
		}
		
		Human2Placement.add(move2); //add the chosen position, 1 - 9, to the Human2Placement array
		placeMove(board, move2, 'O'); /*take this input, and make that number spot on the board
		replace the current space therein with an O */
    }
	
	private static void computerTurn(char[][] board) {
        Random rand = new Random();
        int computerMove = rand.nextInt(9) + 1;
        
        while (HumanPlacement.contains(computerMove) || MachinePlacement.contains(computerMove) || Human2Placement.contains(computerMove)) {
            computerMove = rand.nextInt(9) + 1; /*while the number that the computer chose at random is already in an array
            keep finding random numbers, until that is not the case (the number found is not already in an array)*/
        }
        
        System.out.println("Machine chose " + computerMove);
        MachinePlacement.add(computerMove); //adding the computer move position to the board
        placeMove(board, computerMove, 'O'); //make the corresponding spot, currently holding a space, in the board array, equal to an O
    }

	
	private static void placeMove(char[][] board, int position, char symbol) {
        // Mapping 1-9 to the 5x5 board coordinates
        switch (position) {
            case 1: board[0][0] = symbol; break;
            case 2: board[0][2] = symbol; break;
            case 3: board[0][4] = symbol; break;
            case 4: board[2][0] = symbol; break;
            case 5: board[2][2] = symbol; break;
            case 6: board[2][4] = symbol; break;
            case 7: board[4][0] = symbol; break;
            case 8: board[4][2] = symbol; break;
            case 9: board[4][4] = symbol; break;
        }
    }	

	public static void print(char[][] board) {
		for(int row = 0; row < 5; row++) { 				//iterating through rows
			for(int col = 0; col < 5; col++) { 			//iterating through columns
				System.out.print(board[row][col]); 		//printing out the columns
			}
			System.out.println(); 						//printing out a new row
		}
	}

	public static void main(String[] args) {
		Scanner scan1 = new Scanner(System.in);

		char[][] board = { {' ', '|', ' ', '|', ' '}, //The array of chars that construct the board
				   {'-', '+', '-', '+', '-'},
				   {' ', '|', ' ', '|', ' '},
				   {'-', '+', '-', '+', '-'},
				   {' ', '|', ' ', '|', ' '} };
	
		print(board);  //method that prints the board out
		
		while (true) { //Loop that keeps the game going until a valid choice is made or a break
			System.out.println("Multi-Player (a): ");
			System.out.println("Single Player (b): ");
			char choice1 = scan1.next().charAt(0); //multi-player or single player choice 
			
			if(choice1 == 'A' || choice1 == 'B' || choice1 == 'a' || choice1 == 'b') { /*if one of the valid choices, lower-case or upper-case a or b
				is selected, then proceed with the rest of the code */
				System.out.println("Reading right to left, each position on the board");
				System.out.println("corresponds to the numbers 1 - 9");	
				
				while (true) { //keeps the game loop going until a winner is found, at which point it breaks
					if(choice1 == 'A' || choice1 == 'a'){ // if multi-player is chosen
						playerTurn(board, scan1); /*puts the corresponding X or O in the spot which is represented
						by the numbers 1 - 9, which is inputed by the user in scan1*/
						if (isGameFinished(board)) break; /*if the isGameFinished conditions are met, namely there is 
						a message greater than 0 returned, meaning a winning combination has been found in an array,
						either of player1, 2, or the computer, then break and the loop is over
						*/
						print(board);

						player2Turn(board, scan1);/*puts the corresponding X or O in the spot which is represented
						by the numbers 1 - 9, which is inputed by the user in scan1*/
						if (isGameFinished(board)) break; /*if the isGameFinished conditions are met, namely there is 
						a message greater than 0 returned, meaning a winning combination has been found in an array,
						either of player1, 2, or the computer, then break and the loop is over
						*/
						print(board);
					}
					else if (choice1 == 'B' || choice1 == 'b') {
						playerTurn(board, scan1); /*puts the corresponding X or O in the spot which is represented
						by the numbers 1 - 9, which is inputed by the user in scan1*/
						if (isGameFinished(board)) break; /*if the isGameFinished conditions are met, namely there is 
						a message greater than 0 returned, meaning a winning combination has been found in an array,
						either of player1, 2, or the computer, then break and the loop is over
						*/
						print(board); //uses the print method to output the board

						computerTurn(board);/*puts the corresponding X or O in the spot which is represented
						by the numbers 1 - 9, which is inputed by the user in scan1*/
						if (isGameFinished(board)) break; /*if the isGameFinished conditions are met, namely there is 
						a message greater than 0 returned, meaning a winning combination has been found in an array,
						either of player1, 2, or the computer, then break and the loop is over
						*/
						print(board); //use the print method to output the board
					}
				}
				break; // Exit main menu after game ends
			} else {
				System.out.println("Sorry, invalid output, try again.");
			}
		}
		scan1.close();	
	}
}
