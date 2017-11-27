# lets-play
lets-play is a small command line game developed using nothing but plain java (no other libraries)

The game is based on the "Harry Potter" theme, designed to be easily extensible for other themes

To run the game,
1. Clone this repository
2. Run "mvn clean package" from the project base directory
3. Run java -jar target/lets-play-1.0-SNAPSHOT.jar
4. Follow the instructions and enjoy the game!

Note:
1. The game works well on consoles that support ANSI escape codes. Tested on Cygwin terminal.
2. Turn up the computer volume for best experience.

#Implemented features
1. Create a character
	a. A player can select what role he wants to play in the game
	b. The player can select what spells his character should possess
2. Explore
	The player can explore the various options of the game like player and spell selection, and the game itself.
3. Gain experience through fighting
	The player gains the experience of choosing the right spells as he/she plays the game
4. Save and resume
	The player can save the game at certain points and resume later from where he/she left.

#Extensibility
1. To create a new game, one must 
	a. Register the new "Theme" with "ThemeFactory"
	b. Extend the "Game" abstract class
2. A game is provided with a "CharacterFactory" and a "CommandFactory" as specified in the "Theme" (registered in step 1)
	a. Each theme can create its own CharacterFactory by implementing the "CharacterFactory" interface
	b. Each theme can create its own CommandFactory by implementing the "CommandFactory" interface

#Save and Resume
	a. If the invocation of a Game returns "true", the current state of the game is saved by the "ConsoleGame" class.
		i. The "Theme" object is saved in the file system (user directory, tested on Windows 7). This object contains the instance
		   of the current game, and the game instance, in turn, contains the CharacterFactory and CommandFactory instances. The state 
		   of all these objects ends up getting save in the file system. For this reason, all these classes implement Serializable interface.
	b. Everytime the game is started (using the java -jar command), the CosoleGame class tries to resume a saved game (if one exists)
	   by invoking the "GameSerializer". GameSerializer looks for a file by name "gog.ser" in the user's home directory, and if found, will 
	   return an instance of "Theme". If this Theme instance is non-null, ConsoleGame will present the player with an option to resume the saved game.
		
		
		