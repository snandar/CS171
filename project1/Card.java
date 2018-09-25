import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class Card {

	//Declaration of Variables
	private int number;
	private int symbol;
	private static String heart = "\u2661";
	private static String spade = "\u2660";
	private static String diamond = "\u2662";
	private static String clubs = "\u2663";

	//Deck Variables
	private static String[] cardSymbol = {heart, spade, diamond, clubs};
	private static String[] cardNumber = {"A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

    //Constructor for Card Class
	public Card(int number, int symbol) {
		this.number = number;
		this.symbol = symbol;
    }

    //Method to get number on the object card
	public String getNumber(){
		return cardNumber[number];
	}

    //Method to get symbol on object card
	public String getSymbol(){
		return cardSymbol[symbol];
	}

    //Method to get number on object card
	public String toString(){
		return cardNumber[number] + cardSymbol[symbol];
	}

    public static void buildDeck(ArrayList<Card> deck) {
	// Given an empty deck, construct a standard deck of playing cards

    	//Recollects Deck
    	if (deck.size() > 0) {
    		deck.clear();
    	}

    	//Input new 52 Cards
    	for(int i=0; i<4 ; i++) {
			for(int j=0; j<13; j++) {
				deck.add(new Card(j, i));
			}
		}
    }

    public static void initialDeal(ArrayList<Card> deck, ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
	//Purpose: Deal two cards from the deck into each of the player's hand and dealer's hand
    //Input: ArrayList of deck, playerHand, and dealerHand
    //Output: void

        //Rebuilds Deck
    	buildDeck(deck);

    	//Empties current player Hand from previous round
    	if(playerHand.size() > 0 ) {
			playerHand.clear();
			dealerHand.clear();
		}

        //Declaration of Variables
		Random random = new Random();
		int numOfCard = 52;
		int randomNumber = random.nextInt(numOfCard);
		Card tempCard;

        //Add random card from deck to player's hand
		for(int i=0 ; i<2 ; i++) {
		tempCard = deck.get(randomNumber);
		playerHand.add(tempCard);
		deck.remove(tempCard);
		numOfCard--;
		randomNumber = random.nextInt(numOfCard);
		}

        //Add random card from deck to dealer's hand
		for(int i=0 ; i<2 ; i++) {
		tempCard = deck.get(randomNumber);
		dealerHand.add(tempCard);
		deck.remove(tempCard);
		numOfCard--;
		randomNumber = random.nextInt(numOfCard);
		}

    }

    public static void dealOne(ArrayList<Card> deck, ArrayList<Card> hand){
	//Purpose: this should deal a single card from the deck to the hand
    //Input: ArrayList of deck and Hand
    //Output: void
    	Random random = new Random();
		int numOfCard = deck.size();
		int randomNumber = random.nextInt(numOfCard);   //generates a random int within deck size
		Card tempCard = deck.get(randomNumber);         //takes card at random index from deck
		hand.add(tempCard);                             //adds card to hand
		deck.remove(tempCard);                          //removes card from deck
    }

    public static boolean checkBust(ArrayList<Card> hand){
	// Purpose: Return whether a given hand's value exceeds 21
    // Input: ArrayList of cards in hand
    // Output: true if sum is greater than 21; false otherwise
		return sumCards(hand) > 21;
    }

    public static boolean dealerTurn(ArrayList<Card> deck, ArrayList<Card> hand){
	// Purpose: This should conduct the dealer's turn and
    // Input: ArrayList of deck and dealer's hand
    // Output: Return true if the dealer busts; false otherwise
        
        //Add card to dealer's hand if sum of card in hand is less than 17
		while (sumCards(hand) < 17) {
			dealOne(deck, hand);
		}

		return sumCards(hand) > 21;
    }

    public static int whoWins(ArrayList<Card> playerHand, ArrayList<Card> dealerHand){
    //Purpose: Decides who wins if neither busts
    //Input: ArrayList of playerHand and dealerHand
    //Output: integer; 2 if dealer has greater than or equal player's hand; 1 otherwise

		if (sumCards(dealerHand) >= sumCards(playerHand) ) {
			return 2; 
		}

		return 1;
    }

    public static String displayCard(ArrayList<Card> hand){
	//Purpose: Describes the card which has index 1 in the hand
    //Input: ArrayList of cards in hand
    //Output: String displaying 1 card
        
		String displayCard ="";
		Card tempCard;

        tempCard = hand.get(1);
        displayCard = tempCard.toString();

		return displayCard;
    }

    public static String displayHand(ArrayList<Card> hand){
    //Purpose: Displays all card in hand
    //Input: ArrayLlist of cards in hand
    //Output: String displaying all cards
		String displayHand = "";
		Card tempCard;

		for(int i=0; i<hand.size();i++){
			tempCard = hand.get(i);
			displayHand += tempCard.toString() +" ";
		}


		return displayHand;
    }

	private static int sumCards(ArrayList<Card> hand){
	//Purpose: to compute sum of cards in hand
    //Input: ArrayList of all cards in hand
    //Output: Integer sum of cards in hand

		int sum = 0;
        int aceCount = 0;
		Card tempCard;
		String snumber;

        //for all card in hand
		for(int i = 0; i<hand.size() ;i++) {

			tempCard = hand.get(i);
			snumber = tempCard.getNumber();

			if(snumber.equals("K") || snumber.equals("Q") || snumber.equals("J")) {
			sum += 10;
			}

            else if(snumber.equals("A")){
                sum += 11;
                aceCount++;
            }
            
            else {
                sum += Integer.parseInt(snumber);
            }
            
            //Card Ace assumes 1 or 11 depending on which is more beneficial
            if (sum>21 && aceCount !=0) {
                while(aceCount!=0 && sum>21){
                    sum -= 10;
                    aceCount--;
                }
            }
		}

		return sum; 
	}

    public static void main(String[] args) {

        //Declaration of Variables
		int playerChoice, winner;
        int betMoney = 500; //Initial bet money, all players start with $500
        int playerBet;      //Stores how much player bet each round
        
		ArrayList<Card> deck = new ArrayList<Card> ();
        
		buildDeck(deck);
		
        //Creates the Menu
        Object[] menuOptions = {"Start Game", "Exit"};
        
		playerChoice = JOptionPane.showOptionDialog(null, "Welcome to Black Jack! \nAre you ready? o(＞ω＜o)", "Blackjack", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, menuOptions, menuOptions[0]);
        
		if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.NO_OPTION))
		    System.exit(0);
        
        
        //Explains the Rule of Betting
        playerChoice = JOptionPane.showConfirmDialog(null, "1) All Players have $500 initially.\n2) Players can only bet money they have.\n3) Winning doubles the money betted.\n4) Players who lost money cannot play.\n5) All bets must be rounded to whole dollar.\n6) Player cannot play without bet.", "Betting Rules", JOptionPane.OK_CANCEL_OPTION);
        
        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
            System.exit(0);
		
        
        //Declaration of Game variables
		Object[] options = {"Hit","Stand"};
		boolean isBusted, dealerBusted;
		boolean isPlayerTurn;
		ArrayList<Card> playerHand = new ArrayList<>();
		ArrayList<Card> dealerHand = new ArrayList<>();
	
        
		do{ // Game loop
		    initialDeal(deck, playerHand, dealerHand);
		    isPlayerTurn=true;
		    isBusted=false;
		    dealerBusted=false;
            playerBet=0;
            String playerBetString="";
		    
            //If player lost all their money
            if(betMoney == 0) {
                playerChoice = JOptionPane.showConfirmDialog(null, "You have lost all your money.\nThank you for playing.\n(✿╹◡╹) ByeBye!", "Bye~", JOptionPane.DEFAULT_OPTION);
                if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.OK_OPTION))
                    System.exit(0);
            }
            
            //Loop to check if input of bet money is valid
            boolean isValid = false;
            while(!isValid) {
                try {
                    //Prompts Player to enter the amount of money they would like to bet and stores as a string
                    playerBetString = JOptionPane.showInputDialog(null, "Please enter how much you would like to bet this round","Enter Bet", JOptionPane.INFORMATION_MESSAGE);
                    
                    //if player closes
                    if(playerBetString == null) {
                        System.exit(0);
                    }
                
                    //Change playerBet into an int
                    playerBet = Integer.parseInt(playerBetString);
                    
                    //Quit while loop if input is valid
                    if(playerBet <= betMoney && playerBet>0) {
                        isValid = true;
                    }
                    
                    //Produce Error message if input negative or $0
                    else if(playerBet<1){
                        playerChoice = JOptionPane.showConfirmDialog(null, "Error: cannot bet $0 or negative dollars", "Invalid", JOptionPane.OK_CANCEL_OPTION);
                        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                            System.exit(0);
                    }
                    
                    //Produce Error message if user bets more than they have
                    else {
                        playerChoice = JOptionPane.showConfirmDialog(null, "You don't have enough money. You have: $"+betMoney+"\nPlease try again.", "Error: Poor", JOptionPane.OK_CANCEL_OPTION);
                        if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                            System.exit(0);
                    }
                }
                //Produce Error message if input is not a Integer
                catch (Exception e){
                    playerChoice = JOptionPane.showConfirmDialog(null, "Error: incorrect input.\nPlease try again.", "Error Message", JOptionPane.OK_CANCEL_OPTION);
                    if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
                        System.exit(0);
                }
            }
                
		    while(isPlayerTurn){

				// Shows the hand and prompts player to hit or stand
				playerChoice = JOptionPane.showOptionDialog(null," Dealer shows " + displayCard(dealerHand) + "\n Your hand is: " + displayHand(playerHand) + "\n What do you want to do?","Hit or Stand",
									   JOptionPane.YES_NO_OPTION,
									   JOptionPane.QUESTION_MESSAGE,
									   null,
									   options,
									   options[0]);

				if(playerChoice == JOptionPane.CLOSED_OPTION)
				    System.exit(0);
				
				else if(playerChoice == JOptionPane.YES_OPTION){
				    dealOne(deck, playerHand);
				    isBusted = checkBust(playerHand);
				    if(isBusted){
                        
						// Case: Player Busts, Player's bet is subtracted from betting Money
                        
                        betMoney -= playerBet;
                        
						playerChoice = JOptionPane.showConfirmDialog(null,"Your hand: "+displayHand(playerHand)+"\n\nPlayer has busted!\nYou lost: $"+playerBet+"\nYou have: $"+betMoney, "You lose", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
						
						isPlayerTurn=false;
				    }
				}
			    
				else{
				    isPlayerTurn=false;
				}
		    }
		    if(!isBusted){ // Continues if player hasn't busted
				dealerBusted = dealerTurn(deck, dealerHand);
				if(dealerBusted){ // Case: Dealer Busts, Betting Money increase by double the amount of player's bet.
                    
                    playerBet *=2;
                    betMoney += playerBet;
                    
				    playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\nYour hand: " + displayHand(playerHand) + "\n\nThe dealer busted.\nCongrautions!\n\nYou won : $"+playerBet+"\nYou have: $"+betMoney, "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

					if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						System.exit(0);
				}
			
			
				else{ //The Dealer did not bust.  The winner must be determined
				    winner = whoWins(playerHand, dealerHand);

				    if(winner == 1){ //Player Wins, Betting Money increase by double the amount of player's bet.
                        
                        playerBet *=2;
                        betMoney += playerBet;
                        
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\nYour hand: " + displayHand(playerHand) + "\n\nCongrautions!\n\nYou won : $"+playerBet+"\nYou have: $"+betMoney, "You Win!!!", JOptionPane.OK_CANCEL_OPTION);

						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }

				    else{ //Player Loses, Betting Money decreases by the amount of player's bet
                        betMoney -= playerBet;
                        
						playerChoice = JOptionPane.showConfirmDialog(null, "The dealer's hand: " +displayHand(dealerHand) + "\nYour hand: " + displayHand(playerHand) + "\n\nBetter luck next time!\nYou lost: $"+playerBet+"\nYou have: $"+betMoney, "You lose!!!", JOptionPane.OK_CANCEL_OPTION);
					
						if((playerChoice == JOptionPane.CLOSED_OPTION) || (playerChoice == JOptionPane.CANCEL_OPTION))
						    System.exit(0);
				    }
				}
		    }
		}while(true);
    }
}
