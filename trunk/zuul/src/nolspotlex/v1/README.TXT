 ---------------------------------------------------- Project NolspotLex-----------------------------------------------------------
 
 This project is base on the "World of Zuul" project, similar to the Zork game.
 This game is based on the moving of a player through different rooms. The objective is obviously to find the exit door in order
 to win the game. 
 
 Possibility of choosing the level of the game (the number of moves depends on the level)
 EASY - MEDIUM - HARD
 
 The available commands are :
 
 - GO <name of the room>
 - HELP
 - QUIT
 - USE <the item to use>
 - TAKE <the item to take>
 - BEAMER <charged/fire>
 
 To run the application just go into eclipse and then run as Java application. At the beginning you have choice 
 - Start a new game (press 1)
 - Quit (press 0)
 
 Then you have to choose your level :
 - Easy (press 0)
 - Medium (press 1)
 - Hard (press 2)
 
 -----------------------------------------------------------------------------------------------------------------------------------
 ----------------------------------------------------------------------------------------------------------------------------------
 
  Many aspects of the game could be developed like :
 
 - The teleport room : a room is chosen in the map. When the user get to it he'll be teleported to another random room. This make
 the game harder for the players.
 
 - The beamer : a beamer is a wearable teleporter the user can charge to memorize the current room, and use it when he wants to
 
 - The trap door : a randomly chosen room which make the player falling into another secret room. By the way, the player loses 
 three moves and go back to the beginning room.
 
 - Characters : they give you some indications concerning the map.
 
 - The lock doors : the player has to find the key to open the door in order to get outside.
 
 
 	PLEASE NOTICE THAT NEARLY ALL QUESTIONS HAVE BEEN ANSWERED (from 7.42 to 7.48 / excluded 7.49)
 	
 	
  ----------------------------------------------------------------------------------------------------------------------------------
  ----------------------------------------------------------------------------------------------------------------------------------
  
  
  
  DEVELOPMENT : 
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  
  * CLASSES / ENUM
  ----------------
  
  - Game												: Both
  - Room												: Both
  - Item (Genericity starting from useKey and takeKey)	: Nolan Potier
  - Door												: Nolan Potier
  - Character 											: Nolan Potier
  - Trap												: Alexandre Boursier
  - GoCommand,QuitCommand,HelpCommand,UseCommand, etc.	: Alexandre Boursier
  - Type (enum of rooms)	 							: Alexandre Boursier
  - Player												: Alexandre Boursier
  
  * METHODS AND STUFF
  -------------------
  
  - Altering createRoom, processCommand,  				: Both
  - addRoom 											: Both
  - Beamer (beamer)                       				: Nolan Potier
  - Random teleport (goRandomRoom) 						: Nolan Potier
  - Doors in game (setRoomsDoors,createDoors)			: Nolan Potier
  - Items in game (addItemsToRooms)						: Nolan Potier
  - Altering CommandWord, CommandWords					: Alexandre Boursier
  - Moves limit (chooseLevel, countMove, get', set') 	: Alexandre Boursier
  - Main (Main class) 									: Alexandre Boursier
  - Key (takeKey, useKey) 								: Alexandre Boursier

  * TESTS
  --------
  
  - Character 											: Nolan Potier
  - Trap												: Alexandre Boursier
  - Game												: Alexandre Boursier
  
  
  () = methods

********************************************************************************************************************************
********************************************************************************************************************************
  
  IF YOU WANT TO PLAY THE GAME BY DISCOVERING IT YOURSELF DON'T READ UNDER THIS LINE
  
  
  -
  --
  ---
  ----
  -----
  
  
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  
  SOLUTIONS ! 
  
   TIPS :
  - The best way to win : 
    go west /take key /go east /go south /go south /go west /use key /go south (8 moves)
  
  - The trap door is randomly chosen so be aware you might do many other moves if you fall into it
  
  
  MAP :     DELIVERY ROOM (key is there)  -  BEDROOM (start) -       INTENSE CARE ROOM
                                                |                         |
                                             CORRIDOR        -       TOILETS (random teleporter)
               PARKING   					    |
                  |                             |
              RECEPTION          -          WAITING_ROOM     -       RESTAURANT
  				  |
     			  | (door is locked)
  	   		  OUTSIDE (win) 
  	   		  
  	   		  
  	   ____________________________________________________________________________________________________
  	   IF YOU FALL INTO THE TRAP :
  	   
  	   	 BEDROOM
  			|	(go up)  
  	    WAY_TO_GO
  	   	 	|   (go up)
  	    TRAP_ROOM (you start from there if you fall)
  	   ____________________________________________________________________________________________________
  	   		  
  	   		  
  	   	legend : "/" and "-" are doors
  
  ********************************************************************************************************************************
  ********************************************************************************************************************************
  