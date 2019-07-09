// Class VIP for CSCI 145 Project 2 Fall 17
// Modified by: Robert Zou & Mai Pham

class VIP extends Player
{
	final private double CASH_BACK = 0.05;			// VIP cash back %
	private int cashBack = 0;						// amount cash back
	
	private int playerType;							// player type
	private int id;									// player ID
	
	public VIP(int amount, int type, int ID, String newName)	{	
		super(amount);
		playerType = type;
		id = ID;
		this.name = newName;
	}
	
	public int getPlayerType()	{
		return playerType;
	}
	
	public int getID()	{
		return id;
	}
	
	public String getName()	{
		return name;
	} 
	
	public int cashBack()	{
		double cash = 0;
		if (playerType == 1 || playerType == 2)	{
			cash = amountBet * CASH_BACK;
			cashBack = (int)Math.round(cash);
			if (playerType == 2){	
				if(numberBet <= 20 && numberBet >= 10)
					cashBack += 20;
				else if (numberBet > 20)
					cashBack += 50;
			}
		}
		return cashBack;
	}
    
	// for debugging
    public String toString() {
    	String output = this.cash + " " + this.name + " " + this.playerType + " " + this.id;
    	return output;
    }
}
