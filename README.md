# UNO

## Introduction

This ist the documentation for the Uno-Project of **Group 3**, consisting of **Iro Chatziantoniou** , **Emina Jordamovic-Kuric**, **Jayanthi Dhanaraj** and **Elisabeth Kolleritsch**.

Goal of this program is to be able to play UNO (with the common rules of UNO, as described here: https://www.uno-kartenspiel.de/spielregeln/) with up to 4 real players on one computer. If there are less than 4 human players, the other players will be substituted by bots.

3 Packages: cards, game and players in org.campus02.zam.ss2020

### CARDS:

2 Classes and 2 enums:

#### Classes:

**public class Deck:** 

public Deck ()
creates entire Deck of Cards, with Card Types and values.
Also contains for loop for playing out a wild card (4 cards are pushed on the deck)

**public void shuffle():** 
shuffles the deck
public ArrayList dealCards: deals cards to the players


**UnoCard:** 
creates in Constructor one card with type, value and card points


#### Enums:

**public enum Type:** Sets the card colors either Red, Green, Blue, Yellow
**public enum Value:** Sets the values of the card (0-9, drawto, skip, wild, wildfour)

### EXCEPTIONS:

**PlayerAlreadyExistsException :** This exception is thrown and handled when two human players choose the same name at the start of the game. 

### GAME:

3 Classes:

**App:** given by the Professor, contains the main loop that initializes the game.
	
**public void Run():**\
initializes the game and the round. Then it picks the current player and checks if 	cards are to be picked up. When a game ends, it prints the final score.

**private void initializeGame()**\
creates Players

**private void initalizeRound()**\
initializes the round, starts the method dealCards()


**private void readUserInput()**\
Prints the cards on hand for the player, as well as a text that asks what player would like to play. The input is changed to uppercase.

**private void updateState()**\
Reads the user input, validates the User input, updates the state and shows the current print state


**private void roundEnded()**
When the round ends it checks if one of the player has enough points for the game to end; if so, it ends the game.

**private void gameEnded()**\
prints "This game has ended!" and the player Scores

**private void printFinal Score()**\
prints the final Scores of the Players


### Main
creates the class App
prints the Starting text ("UNO wird nun gestartet...") and the text when the game 	ends ("UNO wird beendet...")



### UnoGame

This class manages the entire game.  All the rules of the game are validated here. This class has access to all other classes within the UNO project.


**public UnoGame ()**\
adds a new ArrayList of players, creates a new Stack

**public void addPlayer()**\
checks if the Playername is already used, and if so, asks for another name. If it is unique, the player is added.

**public void createNewDeck**\
creates a new Deck.

**public void processCard()**\
checks for the type of card that is played depending on its name \if the card is allowed it will be probed further 

if it contains WILD: method allowWild() is executed,\
if it contains REVERSE: method reverse() is executed,\
if it contains WILDFOUR or DRAWTWO: cardsToBePickedUp turns true

**private void updatePlayedCard()**\
puts the played Card on the discard pile
it also ask wether the player has more than one card left,
it the amount of cards is not one, the played card will be removed
else the Round will be completed.


**private void completeRound()**\
Writes text that the round is over, congratulates the winning Player, 
prints the Players Scores.

**private void allowWild()**\
Ask the player what color he or she would like to change to, checks if the user input is valid

**public boolean isAllowed()**\
checks if the card played is allowed based on the type and the value of the card

**private boolean isWildAllowed()**\
checks if the current player is allowed to play a wild card

**private boolean validateWild()**\
When the open card is a wild card, this method checks to see if the card played by the player is valid.


**public void pickUpCards()**\
gives penalty to the player if the card on the discardPile is a Drawtwo or a Wildplus4
prints the open card

**public void reverse()**\
reverses the Player ArrayList

**public void renewDeckPile()**\
renews the deck pile if the size of the deck pile is less or equal to 4. 
The top card gets popped of and is pushed to the new, shuffled deck pile

**public void dealCards()**\
deals seven cards to the players.


**public void penalty()**\


**public void completePlayers()**\
checks if the size of the players is 4, if not, it creates and adds a Robot

**public void printPlayerScores()**\
prints the scores


**public ArrayList<UnoCard> combineHandsFromAllPlayers()**\
combines the Hands of all Players

**public boolean checkUno()**\
This method checks if the player is allowed to say Uno at this pint. No penalty for incorrect call

**public void skip()**\
lets the player skip a turn
prints out a message

**public void printHelp()**\
offers help and explanation

**public void drawCard()**\
draw a card, plays drawn card if possible, if not it explains you cannot play it and prints the new hand of cards

**public void createPlayers()**
creates the Players, asks for a name and puts them in a random order. When STOP is put in, creates the rest of the players as Robots

**public boolean isCard()**\
checks if the input made by the user is a valid card



### PLAYERS:

3 Classes:

**Player:** This is an abstract class and holds some prewritten code that the HumanPlayer class and the Robot class inherit.  There is  also a playCard abstract method that is implemented by the HumanPlayer and Robot classes

**HumanPlayer :** This class extends the Player class and implements the playCard method for a human player



**Robot :** This class extends the Player class and implements the playCard method for a robot

