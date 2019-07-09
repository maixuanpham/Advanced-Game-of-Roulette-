// Class Roulette for CSCI 145 Project 4 Fall '17
// Modified by: Robert Zou & Mai Pham

import java.util.*;
import java.io.*;

class Roulette
{
    private Player playerList[] = new Player[5];		// up to 5 players per game
    private int numPlayers; 							// number of players in the game
    //private Transaction TransList[] = new Transaction[30];	// up to 30 transactions
    protected int numTrans;							// number of transactions
    private int numRounds;							// number of rounds play
    private int money;								// house money
    private int hundredChips;						// # of 100 chips
    private int twentyfiveChips; 					// # of 25 chips
    private int fiveChips;							// # of 5 chips
    private int oneChips;							// # of 1 chips
    private int minBet;								// min bet for this roulette
    private int maxBet;								// max bet for this roulette
    private String gameID;							// game id (game type + instance num) - created through string addition
    Scanner scan = new Scanner(System.in);
    
    public Roulette(String ID, int min, int max, int hundred, int twentyfive, int five, int one) {
	    	gameID = ID;
	    	minBet = min;
	    	maxBet = max;
	    	hundredChips = hundred;
	    	twentyfiveChips = twentyfive;
	    	fiveChips = five;
	    	oneChips = one;
	    	//money = 100*hundred + 25*twentyfive + 5*five + one;
	    	numPlayers = 0;
	    	numTrans = 0;
	    	numRounds = 0;
    	}
    
    public int Balance(PrintWriter outFile) 	{
    		int balance;
    		balance = money + 100*hundredChips + 25*twentyfiveChips + 5*fiveChips + oneChips;
    		outFile.println("Balance: " + balance);
    		outFile.println("\tCash: $" + money);
    		outFile.println("\t$100 chips: " + hundredChips);
    		outFile.println("\t$25 chips: " + twentyfiveChips);
    		outFile.println("\t$5 chips: " + fiveChips);
    		outFile.println("\t$1 chips: " + oneChips);
    		outFile.println();
    		return balance;
    }
    
    public int getNumPlayers() {
    		return numPlayers;
    }
    
    public boolean addPlayer(Player incomingPlayer) {
	    	for (int i = 0; i < 5; i++) {
	    		if (playerList[i] == null || !playerList[i].isPlaying()) {
	    			playerList[i] = incomingPlayer;
	    			numPlayers++;
	    			return true;
	    		}
	    	}
	    	return false;
    }
    
    public void playRound(PrintWriter outFile) {  	
    		numRounds++;
    		String answer;
	    	for (int i = 0; i < 5; i++)	{
	    		if (playerList[i] != null && playerList[i].isPlaying())	{
	    			System.out.println(playerList[i].getName() + " have $" + playerList[i].getCash() + " cash and $" + playerList[i].totalChips() + " in chips.");
	    			if (playerList[i].getCash() >= 100) 	{
	    				System.out.print("Would you like to exchange $100 for chips? (2 x $25; 5 x $5; 25 x $1) - y/n: ");
		    			answer = scan.next();
		    			if (answer.equalsIgnoreCase("y")) {
	    					this.exchange$100();
		    				playerList[i].exchange$100();
		    				System.out.println("Exchange completed.");
		    				outFile.println("Player " + (i + 1) + " exchanges $100 for 2 $25-chip, 5 $5 chip, 25 $1-chip");
		    			}		
	    			}
	    			playerList[i].makeBet(scan, this.minBet, this.maxBet);
	    			for (int j = 0; j < playerList[i].getNumBet(); j++)	{
	    				hundredChips += (playerList[i].getHundreds(j));
		    			twentyfiveChips += (playerList[i].getTwentyFives(j));
		    			fiveChips += (playerList[i].getFives(j));
		    			oneChips += (playerList[i].getOnes(j));
	    			}
	    		}
	    	}
	    	Wheel.spin();
	    	numTrans = 0;
	    	outFile.println("\nRound " + numRounds + " (" + Wheel.getColor() + " " +Wheel.getNumber() + ")");
	    	outFile.println("Trans Player BAmount ($100 $25 $5 $1)  BType  Pay ($100 $25 $5 $1)");
	    	for (int i = 0; i < 5; i++)	{	// 5 seats at table
	    		if (playerList[i] != null && playerList[i].isPlaying()) {
	    			//numTrans++;
	    			//outFile.print("  " + numTrans + "     "+ (i+1));
	    			//outFile.print(playerList[i].payment(numTrans, i, outFile));
	    			numTrans = playerList[i].payment(numTrans, i, outFile);
	    			outFile.println();
	    			for (int j = 0; j < playerList[i].getNumBet(); j++)	{
		    			if (playerList[i].getWinAmount(j) > 0)	{
		    				hundredChips -= (playerList[i].getHundreds(j));
			    			twentyfiveChips -= (playerList[i].getTwentyFives(j));
			    			fiveChips -= (playerList[i].getFives(j));
			    			oneChips -= (playerList[i].getOnes(j));
		    			}
	    			}
	    			
	    			if(!playerList[i].playAgain(scan))	{
	    				System.out.println(playerList[i].getName() + " get " + playerList[i].cashBack() + " cash back bonus.");
	    				playerList[i] = null;
	    			}		
	    		}
	    	}
	    	outFile.println();
    }
 
    public void exchange$100()	{
	    	money += 100;
	    	twentyfiveChips -= 2;
	    	fiveChips -= 5;
	    	oneChips -= 25;
    }
}