// Class Player for CSCI 145 Project 2 Fall 17
// Modified by: Robert Zou & Mai Pham

import java.io.PrintWriter;
import java.util.*;

//************************************************************************
//   Class Player represents one roulette player.
//************************************************************************
class Player		{
	protected String name;					// player name
	protected int hundredChips;				// # of 100 chips <player possession>
    protected int twentyFiveChips; 			// # of 25 chips <player possession>
    protected int fiveChips;				// # of 5 chips <player possession>
    protected int oneChips;					// # of 1 chips <player possession>
	boolean active = false;					// is customer playing or not
	protected int amountBet;				//total amount $$ of bet
	protected int numberBet	;				//number of bet
	protected int cash;						//cash in hand
	protected int []one;					// # of 1 bet
	protected int []five;					// # of 5 bet
	protected int []twentyFive;				// # of 25 bet
	protected int []hundred;				// # of 100 bet
    //protected int money;					//total of chips
    protected int bet[];					// amount of bet
    protected int numBet;					// # of bet
    protected int betType[];				// bet color
    protected int number[];					// bet number
    protected int winAmount[];				// amount win
    
    public Player (int amount)	{
	    	name = "Anonymous player";
	    	cash = amount;
	    	active = true;
	    	oneChips = 0;
	    	fiveChips = 0;
	    	twentyFiveChips = 0;
	    	hundredChips = 0;
	    	amountBet = 0;
	    	numBet = 0;
	    	one = new int[4];				
	    	five = new int[4];
	    	twentyFive = new int [4];
	    	hundred = new int [4];
	    	bet = new int[4];
	    	betType = new int[4];
	    	number = new int[4];
	    	winAmount = new int[4];
   	}
    
    public boolean isPlaying()	{
    		return active;
    }
    
    public String getName()	{
    		return name;
    }
    public int getCash()	{
    		return cash;
    }
    
    public void exchange$100()	{
	    	cash -= 100;
	    	twentyFiveChips += 2;
	    	fiveChips += 5;
	    	oneChips += 25;
    }
    
    public int totalChips()	{
    		int totalChips = 100*hundredChips + 25*twentyFiveChips + 5*fiveChips + oneChips;
    		//System.out.println("total chips :" + totalChips);
    		return totalChips;
    }
    
    public int cashBack()	{
    		return 0;
    }

    public int getNumBet()	{
    		return numBet;
    }
    public void makeBet(Scanner scan, int minBet, int maxBet)	{
    		String answer = "y";
    		numBet = 0;
    		while (answer.equalsIgnoreCase("y") && numBet < 3)
    		{
    			System.out.print("How much to bet in chips ($100, $25, $5 & $1)? ");
    			hundred[numBet] = scan.nextInt();
    			twentyFive[numBet] = scan.nextInt();
    			five[numBet] = scan.nextInt();
    			one[numBet] = scan.nextInt();
    			bet[numBet] = 100*hundred[numBet] + 25*twentyFive[numBet] + 5*five[numBet] + one[numBet];
    			System.out.println("You bet $" + bet[numBet]);
    			while (bet[numBet] < minBet || bet[numBet] > this.totalChips() || bet[numBet] > maxBet || hundred[numBet] > hundredChips || twentyFive[numBet] > twentyFiveChips || five[numBet] > fiveChips || one[numBet] > oneChips)	{
    				System.out.println("The amount is invalid.");
    				System.out.print("Please bet again ($100, $25, $5 & $1)? ");
    				hundred[numBet] = scan.nextInt();
    				twentyFive[numBet] = scan.nextInt();
    				five[numBet] = scan.nextInt();
    				one[numBet] = scan.nextInt();
    				bet[numBet] = 100*hundred[numBet] + 25*twentyFive[numBet] + 5*five[numBet] + one[numBet];
    				System.out.println("You bet $" + bet[numBet]);
    			}
    			hundredChips -= hundred[numBet];
    			twentyFiveChips -= twentyFive[numBet];
    			fiveChips -= five[numBet];
    			oneChips -= one[numBet];
    			amountBet += bet[numBet];
    			numberBet++;

    			Wheel.betOptions();
    			System.out.print("Please enter a betting option: ");
    			betType[numBet] = scan.nextInt();
    			while (betType[numBet] < 1 || betType[numBet] > 3)	{
    				System.out.print("The betting option is invalid. \nPlease enter betting option again: ");
    				betType[numBet] = scan.nextInt();
    			}
    			if (betType[numBet] == Wheel.NUMBER)		{
    				System.out.print("Please enter a number: ");
    				number[numBet] = scan.nextInt();
    				while (number[numBet] < Wheel.MIN_NUM || number[numBet] > Wheel.MAX_NUM)	{
    					System.out.print("The number is invalid. \nPlease enter a number again: ");
    					number[numBet] = scan.nextInt();
    				}
    			}
    			System.out.print("Would you like to make a one more bet? (max 3, y/n) ");
    			answer = scan.next();
    			numBet++;
    		}
    		System.out.println();
    } 		// method makeBet

    public int getWinAmount(int numBet)	{
    		return winAmount[numBet];
    }
    public int getHundreds(int numBet) {
    	return hundred[numBet];
    }
    public int getTwentyFives(int numBet) {
    	return twentyFive[numBet];
    }
    public int getFives(int numBet) {
    	return five[numBet];
    }
    public int getOnes(int numBet) {
    	return one[numBet];
    }
    
    public int payment(int numTrans, int playerSeat, PrintWriter outFile)	{	
    		//int chips;
    		//int hChips, tFChips, fChips, oChips;
    		int amount;
    		String output = "";
	    	String bType = "";
    		for (int i = 0; i < numBet; i++)	{
    			numTrans++;
    			output += "  " + numTrans + "     "+ (playerSeat+1);
    			output += "      " + bet[i];
    			output += "    (" + "   " + hundred[i] + "   " + twentyFive[i] + "  " + five[i] + "  " + one[i] +")   ";
	    	    winAmount[i] = Wheel.payoff(bet[i], betType[i], number[i]);
	    	    amount = winAmount[i];
	    	    System.out.println(name + " won $" + amount);
	    	    if (betType[i] == 1)
	    	    	bType = "B";
	    	    if (betType[i] == 2)
	    	    	bType = "R";
	    	    if (betType[i] == 3)
	    	    	bType = "N(" + number[i] + ")";
	    	    output += bType + "   " + amount + "  (";
	    	    
	    	    // convert money to chips
	    	    hundred[i] = (amount/100);
	    	    hundredChips += hundred[i];
	    	    amount %= 100;
	    	    twentyFive[i] = (amount/25);
	    	    twentyFiveChips += twentyFive[i];
	    	    amount %= 25;
	    	    five[i]= (amount/5);
	    	    fiveChips += five[i]; 
	    	    amount %= 5;	
	    	    one[i] = amount;
	    	    oneChips += one[i];
	    	    output += "   " + hundred[i] + "   " + twentyFive[i] + "  " + five[i] + "  " + one[i] +")";
	    	    // reset for next line
	    	    outFile.println(output);
	    	    output = "";
	    	    bType = "";
	    	    //hChips = 0;
	    	    //tFChips =0;
	    	    //fChips = 0;
	    	    //oChips = 0;
    		}
    		return numTrans;
    }
    /*
    public void displayStatus()
    {
    		System.out.println("The total amount player win/lose is: " + winLose);
    }*/
    
    public boolean playAgain(Scanner scan)	{
      	String answer;
      	System.out.print ("Play again [y/n]? ");
      	answer = scan.next();
      	if (!answer.equalsIgnoreCase("y"))
      		active = false;
      	return (answer.equals("y") || answer.equals("Y"));
    }  // method playAgain
    
    // for debugging
    public String toString() {
    	String output = this.cash + " " + this.name + " ";
    	return output;
    }
}
