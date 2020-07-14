# UNO

## Introduction

This ist the documentation for the Uno-Project of **Group 3**, consisting of **Iro Chatziantoniou** , **Emina Jordamovic-Kuric**, **Jayanthi Dhanaraj** and **Elisabeth Kolleritsch**.

Goal of this program is to be able to play UNO (with the common rules of UNO, as described here: https://www.uno-kartenspiel.de/spielregeln/) with up to 4 real players on one computer. If there are less than 4 human players, the other players will be substituted by bots.

3 Packages: cards, game and players in org.campus02.zam.ss2020

### CARDS:

2 Classes and 2 enums:

#### Classes:

**public class Deck:** 

creates entire Deck of Cards, with Card Types and values.
Also contains for loop for playing out a wild card (4 cards are pushed 	on the deck)

**public void shuffle():** 
shuffles the deck
public ArrayList dealCards: deals cards to the players


**UnoCard:** 
creates in Constructor one card with type, value and card points


#### Enums:

**public enum Type:** Sets the card colors either Red, Green, Blue, Yellow
**public enum Value:** Sets the values of the card (0-9, drawto, skip, wild, wildfour)


### GAME:

3 Classes:

**App:** given by the Professor, contains the main loop that initializes the game.
	
**public void Run():**
initializes the game and the round. Then it picks the current player and checks if 	cards are to be picked up. When a game ends, it prints the final score.

**private void initalizeRound()**
initializes the round, starts the method dealCards()


**private void readUserInput()**
Prints the cards on hand for the player, as well as a text that asks what player 	would like to play. The input is changed to uppercase.

**private void updateState()**
Reads the user input, validates the User input, updates the state and shows the 	current print state


**Main**
creates the class App
prints the Starting text ("UNO wird nun gestartet...") and the text when the game 	ends ("UNO wird beendet...")



### UnoGame

**public UnoGame ()**
adds a new ArrayList of players, creates a new Stack

**public void addPlayer()**
checks if the Playername is already used, and if so, asks for another name. If it 	is unique, the player is added.


**public void processCard()**
checks for the type of card that is played depending on its name
if the card is allowed it will be probed further 

if it contains WILD: method allowWild() is executed,
if it contains REVERSE: method reverse() is executed,
if it contains WILDFOUR or DRAWTWO: cardsToBePickedUp turns true

**private void updatePlayedCard()**
puts the played Card on the discard pile
it also ask wether the player has more than one card left,
it the amount of cards is not one, the played card will be removed
else the Round will be completed.


**private void completeRound()**
Writes text that the round is over, congratulates the winning Player, 
prints the Players Scores.




### PLAYERS:

3 Classes:

HumanPlayer

Player: Abstract Class

Robot

