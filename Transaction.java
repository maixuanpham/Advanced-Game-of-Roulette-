
public class Transaction 
{
	private int playerNum;			
	private int one;
	private int five;
	private int twentyFive;
	private int hundred;
	private int bet;
	private int pay;
	
	public Transaction(int player, int hundred, int twentyFive, int five, int one, int pay)
	{
		playerNum = player;
		this.one = one;
		this.five = five;
		this.twentyFive = twentyFive;
		this.hundred = hundred;
		this.pay = pay;
		
	}
	public int getAmountBet()
	{
		bet = (hundred * 100) + (twentyFive * 25) + (five * 5) + (one * 1);
		return bet;
	}
	
	public void setPay(int amount)
	{
		pay = amount;
	}
	
	public String payOut()
	{
		int amount = pay;
		int h =0, tF=0, f=0, o=0;
		h /= amount;
    	amount %= 100;
    	tF /= amount;
    	amount %= 25;
    	f /= amount;
    	amount %= 5;
    	o = amount;
    	String result = h + " " + tF + " " + f + " " + o;
    	return result;
	}
	
	
}
