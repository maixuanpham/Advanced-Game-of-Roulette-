// Class Wheel for CSCI 145 Project 4 Fall '17
// Modified by: Robert Zou & Mai Pham

//************************************************************************
//   Class Wheel represents a roulette wheel and its operations.  Its
//   data and methods are static because there is only one wheel.
//************************************************************************

class Wheel
{
    // public name constants -- accessible to others
    public final static int BLACK     =  0;			// even numbers
    public final static int RED       =  1;			// odd numbers
    public final static int GREEN     =  2;			// 00 OR 0
    public final static int NUMBER    =  3;			// number bet
    public final static int MIN_NUM   =  1;			// smallest number to bet
    public final static int MAX_NUM   = 36;			// largest number to bet

    // private name constants -- internal use only
    private final static int MAX_POSITIONS = 38;		// number of positions on wheel
    private final static int NUMBER_PAYOFF = 35;		// payoff for number bet
    private final static int COLOR_PAYOFF  = 2;		// payoff for color bet

    // private variables -- internal use only
    private static int ballPosition;					// 00, 0, 1 .. 10
    private static int color;						// GREEN, RED, OR BLACK


    //=====================================================================
    //  Presents welcome message
    //=====================================================================
    public static void welcomeMessage()
    {
      	System.out.println("Welcome to a simple version of roulette game.");
      	System.out.println("You can place a bet on black, red, or a number.");
      	System.out.println("A color bet is paid " + COLOR_PAYOFF + " the bet amount.");
      	System.out.println("A number bet is paid " + NUMBER_PAYOFF + " the bet amount.");
      	System.out.println("You can bet on a number from " + MIN_NUM + " to " + MAX_NUM + ".");
      	System.out.println("Gamble responsibly.  Have fun and good luck!\n");
    }


    //=====================================================================
    //  Presents betting options
    //=====================================================================
    public static void betOptions()	{
      	System.out.println("Betting Options:");
      	System.out.println("    1. Bet on black (even numbers)");
      	System.out.println("    2. Bet on red (odd numbers)");
      	System.out.println("    3. Bet on a number between " + MIN_NUM +
      			" and " + MAX_NUM);
    }
    
    
    public static void spin()	{
    		ballPosition = (int)(Math.random()*(MAX_POSITIONS));
    	
    		if (ballPosition == 0 || ballPosition == (MAX_NUM+1))		{	
    			if (ballPosition == (MAX_NUM+1))
    				System.out.println("The number is: 00");
    			else
    				System.out.println("The number is: " + ballPosition);
    			color = GREEN;
    			System.out.println("The color is: Green");
    		}
    		else 	{	
    			System.out.println("The number is: " + ballPosition);
    			if (ballPosition % 2 == BLACK)	{
    				color = BLACK;
    				System.out.println("The color is: Black");
    			}
    			else if (ballPosition % 2 == RED){	
    				color = RED;
    				System.out.println("The color is: Red");
    			}
    		}
    }
    
    public static int getNumber()	{
    		return ballPosition;
    }
    
    public static String getColor()	{
		String output = "";
		if (color == GREEN)
			output = "Green";
		if (color == BLACK)
			output = "Black";
		if (color == RED)
			output = "Red";
    		return output;
    }
    
    public static int payoff(int bet, int betType, int number)	{
    		int payoff = 0;
    		if (betType == NUMBER && number == ballPosition)
    			payoff = bet * NUMBER_PAYOFF;
    		else if (betType == 1 && color == BLACK || betType == 2 && color == RED)
    			payoff = bet * COLOR_PAYOFF;
    		return payoff;
    } 
}

