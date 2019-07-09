// Class Casino for CSCI 145 Project 4 Fall '17
// Modified by: Robert Zou & Mai Pham

import java.util.*;
import java.io.*;

public class Casino {
	private PrintWriter outFile;
	private Queue<Player> playerQueue = new LinkedList<Player>();
	private Roulette games[] = new Roulette[5];
	private int numberOfGames;
	private String model;
	
	public Casino() {	// Casino constructor setting up player and roulette data structures
		try {
			outFile = new PrintWriter("report.txt");
		}
		catch (FileNotFoundException e) {
			System.out.println("File " + e + " not found.");
		}
		this.numberOfGames = 0;
		this.inputGames();
		this.inputPlayers();
		// for debugging
		// while (!playerQueue.isEmpty()) {
		//	System.out.println(playerQueue.remove());
		// }
	}
	
	public void inputPlayers() {		// read players.txt into player queue
		try {
			File playerFile = new File("players.txt");
			Scanner sc = new Scanner(playerFile);
			while (sc.hasNextLine()) {
				int playerID;
				String playerName;
				int playerType = sc.nextInt();
				int playerMoney = sc.nextInt();
				if (playerType != 0) {
					playerID = sc.nextInt();
					playerName = sc.nextLine();
					playerName = playerName.substring(1);
					Player tempPlayer = new VIP(playerMoney, playerType, playerID, playerName);
					playerQueue.add(tempPlayer);
				}
				else {
					Player tempPlayer = new Player(playerMoney);
					playerQueue.add(tempPlayer);
				}
			}
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	public void inputGames() {	// read games.txt into games array
		try {
			File gameFile = new File("games.txt");
			Scanner sc = new Scanner(gameFile);
			model = sc.next();
			int numGames;
			numGames = sc.nextInt();
			numberOfGames += numGames;
			for (int i = 0; i < numGames; i++) {
				int minBet = sc.nextInt();
				int maxBet = sc.nextInt();
				int hundreds = sc.nextInt();
				int twentyfives = sc.nextInt();
				int fives = sc.nextInt();
				int ones = sc.nextInt();
				Roulette tempGame = new Roulette(model, minBet, maxBet, hundreds, twentyfives, fives, ones);
				games[i] = tempGame;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void addPlayer() {
		Scanner sc = new Scanner(System.in);
		int playerType;
		int playerMoney;
		System.out.print("Enter player type (0 - regular; 1 - VIP; 2 - SuperVIP): "); 
		playerType = sc.nextInt();
		System.out.print("Enter player money: ");
		playerMoney = sc.nextInt();
		if (playerType != 0) {
			int playerID;
			String playerName;
			System.out.print("Enter player ID: ");
			playerID = sc.nextInt();
			sc.nextLine(); // get rid of \n
			System.out.print("Enter player name: ");
			playerName = sc.nextLine();
			Player tempPlayer = new VIP(playerMoney, playerType, playerID, playerName);
			playerQueue.add(tempPlayer);
		}
		else {
			Player tempPlayer = new Player(playerMoney);
			playerQueue.add(tempPlayer);
		}		
	}
	
	public void start() {	// start the games/menu selection
		Scanner sc = new Scanner(System.in);
		int mainSelection = 0;	        // priming selection
		while (mainSelection != 3) {	// initial menu
			System.out.println("Main Menu");
			System.out.println("1. Select a game");
			System.out.println("2. Add a new player to the list");
			System.out.println("3. Quit\n");
			System.out.print("Choose an option (1-3): ");
			mainSelection = sc.nextInt();
			switch(mainSelection) {	// main menu selection
				case 1: {
					System.out.print("Select a game (1-" + numberOfGames + "): ");
					int rouletteChoice = sc.nextInt() - 1; // roulette choice mapped to index in gameArray
					// File file = new File("report.txt");
					// PrintWriter outFile = new PrintWriter("report.txt");
					// outFile = new PrintWriter ("report.txt");
			    		outFile.println("Game: " + model + (rouletteChoice+1));
			    		outFile.print("Initial ");
			    		int initialB;
			    		initialB = games[rouletteChoice].Balance(outFile);
			    		// create Roulette object
					int gameSelection = 0;
					while (gameSelection != 3) {
						System.out.println("\nMain Menu");
						System.out.println("1. Add a player to the game");
						System.out.println("2. Play one round");
						System.out.println("3. Quit\n");
						System.out.print("Option --> ");
						gameSelection = sc.nextInt();
						switch(gameSelection) {	// select roulette operations.
							case 1: {
								// add player from queue to game & remove from queue
								if (playerQueue.isEmpty()) 
									System.out.println("No players in the queue.");									
								else {
									Player addedPlayer = playerQueue.remove();
									boolean canAdd = games[rouletteChoice].addPlayer(addedPlayer);
									// if the game is full, add the player back to the queue
									if (!canAdd) {
										playerQueue.add(addedPlayer);
										System.out.println("Player could not be added. Returning to the queue");
									}
									else {
										System.out.println("Player added!");
									}
								}
								break;
							}
							case 2:	{
								games[rouletteChoice].playRound(outFile);
								if (games[rouletteChoice].getNumPlayers() == 0) {
									gameSelection = 3;
									System.out.println("No players remaining in the game.");
								}
								break;
							}
							case 3:	{
								int endingB;
								outFile.print("Ending ");
								endingB = games[rouletteChoice].Balance(outFile);
								outFile.println("The difference amount for this session: " + (endingB - initialB));
								break;
							}
							default: {
								System.out.println("Invalid option. Try again (1-3)");
								break;
							}
						}
					}
					break;
				}
				case 2: {
					this.addPlayer();
					break;
				}
				case 3: {
					// exit - no code needed
					// for each game printsummary 
					outFile.close();
					break;
				}
				default: {
					System.out.println("Invalid option. Try again (1-3)");
					break;
				}
			}
		}
	}
}
