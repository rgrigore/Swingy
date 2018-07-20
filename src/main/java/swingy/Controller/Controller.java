package swingy.Controller;

import swingy.Model.Characters.AvailableClasses;
import swingy.Model.Characters.BasePlayer;
import swingy.Model.Characters.Player;
import swingy.Model.Database.Database;
import swingy.Model.Database.PlayerData;
import swingy.Model.Enemies.Enemy;
import swingy.Model.Items.Item;
import swingy.Model.Map.Cell;
import swingy.Model.Map.Direction;
import swingy.Model.Map.Map;
import swingy.View.Console;
import swingy.View.Gui;
import swingy.View.IView;

import java.util.List;
import java.util.Random;

public class Controller {
	private boolean run = true;
	private CombatController combatController = new CombatController(this);
	private GamePhase gamePhase = GamePhase.MAIN_MENU;
	private Console console = new Console(this);
	private Gui gui = new Gui(this);
	private IView iView;
	private Map map = new Map();
	private Player player = null;

	public Controller setView(String mode) throws Exception {
		switch (mode.toLowerCase()) {
			case "console":
				this.iView = console.setActive(true);
				gui.setActive(false);
				break;
			case "gui":
				this.iView = gui.setActive(true);
				console.setActive(false);
				break;
			default: throw new Exception("Unknown view type");
		}
		return this;
	}

	IView getView() {
		return iView;
	}

	public void initialize() {
		iView.showLoadingScreen();
		Database.getInstance();
		while (run) {
			switch (gamePhase) {
				case MAIN_MENU: iView.showStartMenu(); break;
				case NEW_CHARACTER: iView.showNewCharacter(); break;
				case LOAD_CHARACTER: iView.showLoadCharacter(); break;
				case LOAD_GAME: iView.showLoadingScreen(); this.loadGame(); break;
				case NAVIGATION: iView.showNavigationScreen(); break;
				case ENCOUNTER: iView.showEncounterScreen(); break;
				case COMBAT: combatController.fight(player, map.getEnemy()); break;
				case LOOT: iView.showLootScreen(); break;
				case DEFEAT: defeat(); break;
			}
		}
	}

	public void setGamePhase(GamePhase phase) {
		this.gamePhase = phase;
	}

	public GamePhase getGamePhase() {
		return gamePhase;
	}

	public void newPlayer(String name, AvailableClasses role) {
		if (role == AvailableClasses.NONE) {
			iView.setMessage("Please select a class.");
		} else if(name.length() == 0) {
			iView.setMessage("Character name can not be empty.");
		} else if (!isUniquePlayer(name)) {
			iView.setMessage("Character name has already been taken.");
		} else {
			this.player = new BasePlayer.Builder().setName(name).setRole(role).build();
			Database.getInstance().newPlayer(this.player);
			this.setGamePhase(GamePhase.LOAD_GAME);
		}
	}

	private boolean isUniquePlayer (String name) {
		for (PlayerData playerData : Database.getInstance().getPlayers()) {
			if (playerData.getName().equalsIgnoreCase(name)) {
				return false;
			}
		}
		return true;
	}

	public void loadPlayer(String name) {
		PlayerData toLoad = null;
		for (PlayerData playerData : Database.getInstance().getPlayers()) {
			if (playerData.getName().equalsIgnoreCase(name)) {
				toLoad = playerData;
				break;
			}
		}
		if (toLoad != null) {
			this.player = Database.getInstance().loadPlayer(toLoad);
		} else {
			iView.setMessage("Player not found.");
		}
	}

	public void resetPlayer() {
		this.player = Database.getInstance().resetPlayer();
	}

	public void deletePlayer(String name) {
		PlayerData toDelete = null;
		for (PlayerData playerData : Database.getInstance().getPlayers()) {
			if (playerData.getName().equalsIgnoreCase(name)) {
				toDelete = playerData;
				break;
			}
		}
		if (toDelete != null) {
			Database.getInstance().deletePlayer(toDelete);
			if (toDelete.getName().equalsIgnoreCase(player.getName())) {
				this.player = null;
			}
		} else {
			iView.setMessage("Player not found.");
		}
	}

	public List<PlayerData> getCharacters() {
		return Database.getInstance().getPlayers();
	}

	public Player getPlayer() {
		return this.player;
	}

	public Enemy getEnemy() {
		return map.getEnemy();
	}

	public Cell[][] getMap() {
		return map.getMap("fog");
	}


	private void loadGame() {
		if (this.player != null) {
			map.generate(player.getLevel(), player.getAscendedFloors());
			this.setGamePhase(GamePhase.NAVIGATION);
		} else {
			iView.setMessage("Select a player first!");
			setGamePhase(GamePhase.LOAD_CHARACTER);
		}
	}


	public void move(Direction direction) {
		switch (map.check(direction)) {
			case EMPTY: map.move(direction); break;
			case WALL: iView.setMessage("There appears to be a wall in the way."); break;
			case BOSS:
			case MOB:
				map.move(direction);
				setGamePhase(GamePhase.ENCOUNTER);
				break;
			case EXIT:
				player.ascend();
				Database.getInstance().savePlayer(this.player);
				map.generate(player.getLevel(), player.getAscendedFloors());
				break;
			case PLAYER: iView.setMessage("You appear to have met another player....HOW?"); break;
			default: iView.setMessage("U wot mate?");
		}
	}

	public void flee() {
		if ((new Random()).nextInt(2) == 0) {
			iView.setMessage("You managed to flee your enemy.");
			combatController.resetCombat();
			setGamePhase(GamePhase.NAVIGATION);
		} else {
			iView.setMessage("You were unable to flee.");
			combatController.failedFlee();
			setGamePhase(GamePhase.COMBAT);
		}
	}

	public void combatFlee() {
		if ((new Random()).nextInt(2) == 0) {
			iView.setMessage("You managed to flee your enemy.");
			combatController.resetCombat();
			setGamePhase(GamePhase.NAVIGATION);
		} else {
			combatController.skipPlayer();
			iView.setMessage("You were unable to flee.");
			combatController.continueCombat();
		}
	}

	public void continueCombat() {
		combatController.continueCombat();
	}

	void finishedCombat() {
		if (combatController.checkVictory()) {
			player.gainExp(map.getEnemy().getExp());
			map.deleteEnemy();
			iView.setMessage("You have won!");
			if (combatController.checkLootDrop()) {
				gamePhase = GamePhase.LOOT;
			} else {
				gamePhase = GamePhase.NAVIGATION;
				combatController.resetCombat();
			}
		} else {
			iView.setMessage("You have lost!");
			gamePhase = GamePhase.DEFEAT;
		}
	}

	private void defeat() {
		gamePhase = GamePhase.MAIN_MENU;
		deletePlayer(player.getName());
		player = null;
	}

	public Item getLoot() {
		return combatController.getLoot();
	}

	public void equipLoot() {
		player.equipItem(combatController.getLoot());
		combatController.resetCombat();
		gamePhase = GamePhase.NAVIGATION;
	}

	public void discardLoot() {
		combatController.resetCombat();
		gamePhase = GamePhase.NAVIGATION;
	}

	public void quit() {
		this.run = false;
		Database.getInstance().close();
		System.exit(0);
	}
}
