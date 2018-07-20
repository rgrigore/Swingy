package swingy.View;

import swingy.Controller.Controller;
import swingy.Controller.GamePhase;
import swingy.Model.Characters.AvailableClasses;
import swingy.Model.Database.PlayerData;
import swingy.Model.Enemies.Enemy;
import swingy.Model.Items.Item;
import swingy.Model.Map.Cell;
import swingy.Model.Map.Direction;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;


public class Console implements IView {
	private Controller controller;
	private boolean isActive;
	private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private String input;
	private String message = null;
	private String combatMessage = null;


	@Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
	private String characterName = "";
	private AvailableClasses characterRole = AvailableClasses.NONE;


	public Console(Controller controller) {
		this.controller = controller;
	}

	public Console setActive(boolean state) {
		this.isActive = state;
		return this;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCombatMessage(String combatMessage) {
		this.combatMessage = combatMessage;
	}

	public void showStartMenu() {
		clearScreen();
		System.out.println("Main menu:");
		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}
		System.out.print("\nAvailable inputs:\n 1) New character  2) Load character  3) Quit\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.MAIN_MENU) {
			this.sortInput();
		}
	}

	public void showNewCharacter() {
		clearScreen();
		System.out.println("New character:");
		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}
		System.out.println("\nName: " + characterName + "\nClass: " + characterRole.toString());
		System.out.print("\nAvailable inputs:\n 1) Name  2) Class  3) Finish  4) Back  5) Quit\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.NEW_CHARACTER) {
			this.sortInput();
		}
	}

	public void showLoadCharacter() {
		clearScreen();
		System.out.println("Load character:");

		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}

		if (controller.getPlayer() != null) {
			System.out.println("\nCurrently selected character:\n" +
					controller.getPlayer().getName() + " " + controller.getPlayer().getRole() + "  Level: " + controller.getPlayer().getLevel() + "\n" +
					"Attack: " + controller.getPlayer().getTotalAttack() + "  Defence: " + controller.getPlayer().getTotalDefence() + "  Hp: " + controller.getPlayer().getTotalHp());
		}

		System.out.print("\nAvailable inputs:\n 1) Select  2) Delete  3) Load  4) Back  5) Quit\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.LOAD_CHARACTER) {
			this.sortInput();
		}
	}

	public void showLoadingScreen() {
		clearScreen();
		System.out.println("Loading, please wait");
	}

	public void showNavigationScreen() {//Cell[][] map) {
		clearScreen();

		System.out.print("/-");
		for (int i = 0; i < controller.getMap()[0].length; i++) {
			System.out.print("--");
		}
		System.out.println("\\");
		for (Cell[] row : controller.getMap()) {
			System.out.print("| ");
			for (Cell cell : row) {
				switch (cell) {
					case PLAYER: System.out.print("@ "); break;
					case EMPTY: System.out.print(". "); break;
					case FOG: System.out.print("  "); break;
					case WALL: System.out.print("# "); break;
					case MOB: System.out.print("! "); break;
					case EXIT: System.out.print("^ "); break;
					default: System.out.print("? ");
				}
			}
			System.out.println("|");
		}
		System.out.print("\\-");
		for (int i = 0; i < controller.getMap()[0].length; i++) {
			System.out.print("--");
		}
		System.out.println("/\n");

		System.out.println(controller.getPlayer().getName() + " " + controller.getPlayer().getRole() + "  Level: " + controller.getPlayer().getLevel() + "\n" +
				"Attack: " + controller.getPlayer().getTotalAttack() + "  Defence: " + controller.getPlayer().getTotalDefence() + "  Hp: " + controller.getPlayer().getCurrentHp() + " / " + controller.getPlayer().getTotalHp() + "\n" +
				"Exp: " + controller.getPlayer().getExp() + " / " + controller.getPlayer().getExpToLevel());

		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}

		System.out.print("\nAvailable inputs:\n" +
				"Directions:\n W) North  A) West  S) South  D) East\n" +
				"Commands:\n 1) Back  2) Quit\n\n_> ");

		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.NAVIGATION) {
			this.sortInput();
		}
	}

	public void showEncounterScreen() {
		clearScreen();
		System.out.println("You encountered an enemy!\n" +
				controller.getEnemy().getType() + "  Lv: " + controller.getEnemy().getLevel() + "\n" +
				"Attack: " + controller.getEnemy().getAttack() + "  Defence: " + controller.getEnemy().getDefence() + "  Hp: " + controller.getEnemy().getHp());
		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}
		System.out.print("\nWhat will you do?\n 1) Attack  2) Flee\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.ENCOUNTER) {
			this.sortInput();
		}
	}

	public void showCombatScreen() {
		clearScreen();
		System.out.println("The flame of combat rages!\n");

		System.out.println(controller.getPlayer().getName() + "			" + controller.getEnemy().getType() + "\n" +
				controller.getPlayer().getCurrentHp() + "/" + controller.getPlayer().getTotalHp() + "			" + controller.getEnemy().getCurrentHp() + "/" + controller.getEnemy().getHp());

		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}

		if (combatMessage != null) {
			System.out.println("\n" + this.combatMessage + "\n");
			this.combatMessage = null;
		}

		System.out.print("\nPress \"ENTER\" to continue or type \"RUN\" to attempt an escape...\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.COMBAT) {
			this.sortInput();
		}
	}

	public void showLootScreen() {
		clearScreen();
		System.out.println("You got a new item:");
		if (message != null) {
			System.out.println("\n" + message);
			message = null;
		}

		System.out.print(controller.getLoot().getName());
		if (controller.getLoot().getAttack() > 0) {
			System.out.print(" Attack: " + controller.getLoot().getAttack());
		}
		if (controller.getLoot().getDefence() > 0) {
			System.out.print(" Defence: " + controller.getLoot().getDefence());
		}
		if (controller.getLoot().getHp() > 0) {
			System.out.print(" Health: " + controller.getLoot().getHp());
		}
		System.out.print(".\nCurrently equipped: ");
		Item equippedItem;
		switch (controller.getLoot().getType()) {
			case "Weapon": equippedItem = controller.getPlayer().getWeapon(); break;
			case "Chest": equippedItem = controller.getPlayer().getChest(); break;
			case "Helmet": equippedItem = controller.getPlayer().getHelmet(); break;
			default: equippedItem = new Item(1, "???", "???", 1, 0, 0, 0);
		}
		System.out.print(equippedItem.getName());
		if (equippedItem.getAttack() > 0) {
			System.out.print(" Attack: " + equippedItem.getAttack());
		}
		if (equippedItem.getDefence() > 0) {
			System.out.print(" Defence: " + equippedItem.getDefence());
		}
		if (equippedItem.getHp() > 0) {
			System.out.print(" Health: " + equippedItem.getHp());
		}
		System.out.println(".");

		System.out.print("\nAvailable inputs:\n 1) Equip  2) Discard\n\n_> ");
		this.getInput();
		if (this.isActive && controller.getGamePhase() == GamePhase.LOOT) {
			this.sortInput();
		}
	}

	private void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
		//System.out.println("clear__________");
	}


	private void getInput() {
		try {
			input = reader.readLine();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
			System.exit(1);
		}
	}

	private void sortInput() {
		if (this.input.equalsIgnoreCase("guimode")) {
			try {
				clearScreen();
				controller.setView("gui");
			} catch (Exception ex) {
				this.setMessage(ex.getMessage());
			}
		} else {
			switch (controller.getGamePhase()) {
				case MAIN_MENU:
					inputMainMenu();
					break;
				case NEW_CHARACTER:
					inputNewCharacter();
					break;
				case LOAD_CHARACTER:
					inputLoadCharacter();
					break;
				case NAVIGATION:
					inputNavigation();
					break;
				case ENCOUNTER:
					inputEncounter();
					break;
				case COMBAT:
					inputCombat();
					break;
				case LOOT:
					inputLoot();
					break;
			}
		}
	}


	private void inputMainMenu() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "new character":
				controller.setGamePhase(GamePhase.NEW_CHARACTER);
				break;
			case "2":
			case "load character":
				controller.setGamePhase(GamePhase.LOAD_CHARACTER);
				break;
			case "3":
			case "quit":
				clearScreen();
				System.out.println("exiting");
				controller.quit();
				break;
			default:
				clearScreen();
				System.out.println("Unknown input, please try again");
		}
	}


	private void inputNewCharacter() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "name":
				clearScreen();
				System.out.print("Input a character name\n\n_> ");
				getCharacterName();
				break;
			case "2":
			case "class":
				clearScreen();
				System.out.println("Input a character class.");
				getCharacterRole();
				break;
			case "3":
			case "finish":
				controller.newPlayer(characterName, characterRole);
				break;
			case "4":
			case "back":
				System.out.println("going back");
				controller.setGamePhase(GamePhase.MAIN_MENU);
				break;
			case "5":
			case "quit":
				clearScreen();
				System.out.println("exiting");
				controller.quit();
				break;
			default:
				setMessage("Unknown input, please try again");
		}
	}

	private void getCharacterName() {
		getInput();

		this.characterName = input;

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<Console>> constraintViolations = validator.validate(this);

		if (constraintViolations.size() > 0) {
			clearScreen();
			for (ConstraintViolation<Console> violation : constraintViolations) {
				System.out.println(violation.getMessage());
			}
			System.out.print( "\n_> ");
			getCharacterName();
		}
	}

	private void getCharacterRole() {
		System.out.print("\nAvailable classes are:\n 1) Barbarian 2) Battlemage  3) Knight  4) Rogue\n\nOr go back with:\n 5) Back\n\n_> ");
		getInput();
		switch (this.input.toLowerCase()) {
			case "1":
			case "barbarian":
				characterRole = AvailableClasses.Barbarian;
				break;
			case "2":
			case "battlemage":
				characterRole = AvailableClasses.Battlemage;
				break;
			case "3":
			case "knight":
				characterRole = AvailableClasses.Knight;
				break;
			case "4":
			case "rogue":
				characterRole = AvailableClasses.Rogue;
				break;
			case "5":
			case "back":
				break;
			default:
				clearScreen();
				System.out.println("Unknown class, please try again.");
				getCharacterRole();
		}
	}


	private void inputLoadCharacter() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "select":
				getLoadCharacter();
				break;
			case "2":
			case "delete":
				getDeleteCharacter();
				break;
			case "3":
			case "load":
				controller.setGamePhase(GamePhase.LOAD_GAME);
				break;
			case "4":
			case "back":
				System.out.println("going back");
				controller.setGamePhase(GamePhase.MAIN_MENU);
				break;
			case "5":
			case "quit":
				clearScreen();
				System.out.println("exiting");
				controller.quit();
				break;
			default:
				setMessage("Unknown input, please try again");
		}
	}

	private void getLoadCharacter() {
		clearScreen();

		System.out.println("Available characters:");
		for (PlayerData playerData : controller.getCharacters()) {
			System.out.println(playerData.getName() + " - " + playerData.getRole());
		}

		System.out.print("\nPlease write the name of the character u wish to select:\n\n_> ");
		getInput();
		controller.loadPlayer(input);
	}

	private void getDeleteCharacter() {
		clearScreen();

		System.out.println("Available characters:");
		for (PlayerData playerData : controller.getCharacters()) {
			System.out.println(playerData.getName() + " - " + playerData.getRole());
		}

		System.out.print("\nPlease write the name of the character u wish to delete:\n\n_> ");
		getInput();
		controller.deletePlayer(input);
	}


	private void inputNavigation() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "back":
				controller.setGamePhase(GamePhase.MAIN_MENU);
				controller.resetPlayer();
				break;
			case "2":
			case "quit":
				clearScreen();
				System.out.println("exiting");
				controller.quit();
			case "w":
			case "north":
				controller.move(Direction.NORTH);
				break;
			case "a":
			case "west":
				controller.move(Direction.WEST);
				break;
			case "s":
			case "south":
				controller.move(Direction.SOUTH);
				break;
			case "d":
			case "east":
				controller.move(Direction.EAST);
				break;
			default:
				setMessage("Unknown input, please try again");
		}
	}


	private void inputEncounter() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "damage":
				controller.setGamePhase(GamePhase.COMBAT);
				break;
			case "2":
			case "flee":
				controller.flee();
				break;
			default:
				setMessage("Unknown input, please try again");
		}
	}

	private void inputCombat() {
		switch (this.input.toLowerCase()) {
			case "run": controller.combatFlee(); break;
			default: System.out.println("default"); controller.continueCombat();
		}
	}

	private void inputLoot() {
		switch (this.input.toLowerCase()) {
			case "1":
			case "equip":
				controller.equipLoot();
				break;
			case "2":
			case "discard":
				controller.discardLoot();
				break;
			default:
				setMessage("Unknown input, please try again");
		}
	}
}
