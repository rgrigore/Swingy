package swingy.View;

import swingy.Controller.Controller;
import swingy.Controller.GamePhase;
import swingy.Model.Characters.AvailableClasses;
import swingy.Model.Database.PlayerData;
import swingy.Model.Enemies.Enemy;
import swingy.Model.Map.Cell;
import swingy.Model.Map.Direction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements IView {
	private Controller controller;
	private GamePhase previousPhase = null;
	private String message = null;
	private String combatMessage = null;
	private AvailableClasses characterRole = AvailableClasses.NONE;


	private JFrame window = new JFrame("Swingy");
	private JPanel container;

	private JPanel panelMainMenu;
	private JButton btnNewCharacter;
	private JButton btnLoadCharacter;
	private JButton btnQuit;

	private JPanel panelNewCharacter;
	private JTextField fieldName;
	private JComboBox<AvailableClasses> boxNCClass;
	private JButton btnNCCreate;
	private JButton btnNCBack;
	private JLabel labelNC;

	private JPanel panelLoadCharacter;
	private JButton btnLCLoad;
	private JButton btnLCBack;
	private JButton btnLCDelete;
	private JComboBox<String> heroList;
	private JLabel labelLC;
	private boolean listen;

	private JPanel panelLoading;
	private JButton btnMode;
	private JButton btnNCMode;
	private JButton btnLCMode;
	private JPanel panelNavigation;
	private JButton btnNMBack;
	private JPanel panelNButtons;
	private JPanel panelNMovement;
	private JPanel panelNCombat;
	private JButton btnNMMode;
	private JButton btnNMSouth;
	private JButton btnNMNorth;
	private JButton btnNMEast;
	private JButton btnNMWest;
	private JButton btnNCStrike;
	private JTextPane fieldLog;
	private JPanel panelNEncounter;
	private JPanel panelNLoot;
	private JProgressBar expBar;
	private JPanel panelNCharacter;
	private JLabel labelHealth;
	private JLabel labelAttack;
	private JLabel labelDefence;
	private JLabel labelName;
	private JLabel labelRole;
	private JLabel labelLevel;
	private JTextPane fieldMap;
	private JButton btnNEFlee;
	private JButton btnNEFight;
	private JLabel labelHero1;
	private JLabel labelHero2;
	private JButton btnNCFlee;
	private JLabel labelMapLevel;
	private JButton btnNLEquip;
	private JButton btnNLDiscard;
	private JLabel labelHelmStats;
	private JLabel labelWeaponStats;
	private JLabel labelChestStats;
	private JLabel labelMM;
	private JButton btnNMSwitch;
	private JButton btnNCSwitch;

	public Gui(Controller _controller) {
		this.controller = _controller;

		this.window.setPreferredSize(new Dimension(1200, 800));
		this.window.add(this.container);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnNewCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setGamePhase(GamePhase.NEW_CHARACTER);
			}
		});

		btnLoadCharacter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setGamePhase(GamePhase.LOAD_CHARACTER);
			}
		});

		btnNCBack.addActionListener(new ActionBackMM());

		btnQuit.addActionListener(new ActionQuit());

		btnNCCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.newPlayer(fieldName.getText(), characterRole);
				previousPhase = GamePhase.LOAD_GAME;
			}
		});

		boxNCClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				characterRole = ((AvailableClasses) ((JComboBox) e.getSource()).getSelectedItem());
			}
		});
		boxNCClass.addItem(AvailableClasses.Knight);
		boxNCClass.addItem(AvailableClasses.Barbarian);
		boxNCClass.addItem(AvailableClasses.Rogue);
		boxNCClass.addItem(AvailableClasses.Battlemage);

		heroList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listen) {
					controller.loadPlayer(((String) ((JComboBox) e.getSource()).getSelectedItem()));
					displaySelectedHero();
				}
			}
		});

		btnLCLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setGamePhase(GamePhase.LOAD_GAME);
				displaySelectedHero();
			}
		});

		btnLCDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (controller.getPlayer() != null) {
					controller.deletePlayer(controller.getPlayer().getName());
				}
				previousPhase = GamePhase.LOAD_GAME;
			}
		});

		btnMode.addActionListener(new ActionMode());

		btnNCMode.addActionListener(new ActionMode());

		btnLCMode.addActionListener(new ActionMode());

		btnLCBack.addActionListener(new ActionBackMM());

		btnNMBack.addActionListener(new ActionBackMM());

		btnNMMode.addActionListener(new ActionMode());

		btnNMNorth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.move(Direction.NORTH);
				updateMap();
			}
		});

		btnNMSouth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.move(Direction.SOUTH);
				updateMap();
			}
		});

		btnNMWest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.move(Direction.WEST);
				updateMap();
			}
		});

		btnNMEast.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.move(Direction.EAST);
				updateMap();
			}
		});

		btnNEFight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.setGamePhase(GamePhase.COMBAT);
			}
		});
		btnNEFlee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.flee();
			}
		});

		btnNCStrike.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.continueCombat();
			}
		});
		btnNCFlee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.combatFlee();
			}
		});

		btnNLEquip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.equipLoot();
			}
		});
		btnNLDiscard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.discardLoot();
			}
		});

		this.window.pack();
	}

	public Gui setActive(boolean state) {
		this.window.setVisible(state);
		return this;
	}


	public void setMessage(String message) {
		this.message = message;
	}

	public void setCombatMessage(String combatMessage) {
		this.combatMessage = combatMessage;
	}


	private void updateHeroList() {
		while (heroList.getItemCount() > 0) {
			heroList.removeItemAt(0);
		}

		for (PlayerData player : controller.getCharacters()) {
			heroList.addItem(player.getName());
		}
	}

	private void displaySelectedHero() {
		labelHero1.setText("---");
		labelHero2.setText("---");
		if (controller.getPlayer() != null) {
			labelHero1.setText(controller.getPlayer().getName() + "  Class: " + controller.getPlayer().getRole() + "  Lv: " + controller.getPlayer().getLevel());
			labelHero2.setText("Attack: " + controller.getPlayer().getTotalAttack() + "  Defence: " + controller.getPlayer().getTotalDefence() + "  Health: " + controller.getPlayer().getTotalHp());
		}
	}

	private void updatePlayer() {
		expBar.setMaximum(controller.getPlayer().getExpToLevel());
		expBar.setValue(controller.getPlayer().getExp());
		expBar.setString("Exp: " + controller.getPlayer().getExp() + " / " + controller.getPlayer().getExpToLevel());
		labelName.setText(controller.getPlayer().getName());
		labelRole.setText(controller.getPlayer().getRole().toString());
		labelLevel.setText("Lv: " + controller.getPlayer().getLevel());
		labelHealth.setText(controller.getPlayer().getCurrentHp() + " / " + controller.getPlayer().getTotalHp() + " (" + controller.getPlayer().getBaseHp() + ")");
		labelAttack.setText(String.valueOf(controller.getPlayer().getTotalAttack()) + " (" + controller.getPlayer().getBaseAttack() + ")");
		labelDefence.setText(String.valueOf(controller.getPlayer().getTotalDefence())+ " (" + controller.getPlayer().getBaseDefence() + ")");
		String itemStats = "";
		if (controller.getPlayer().getWeapon().getAttack() > 0) {
			itemStats = itemStats.concat("A: " + controller.getPlayer().getWeapon().getAttack() + " ");
		}
		if (controller.getPlayer().getWeapon().getDefence() > 0) {
			itemStats = itemStats.concat("D: " + controller.getPlayer().getWeapon().getDefence() + " ");
		}
		if (controller.getPlayer().getWeapon().getHp() > 0) {
			itemStats = itemStats.concat("H: " + controller.getPlayer().getWeapon().getHp() + " ");
		}
		labelWeaponStats.setText(itemStats);

		itemStats = "";
		if (controller.getPlayer().getChest().getAttack() > 0) {
			itemStats = itemStats.concat("A: " + controller.getPlayer().getChest().getAttack() + " ");
		}
		if (controller.getPlayer().getChest().getDefence() > 0) {
			itemStats = itemStats.concat("D: " + controller.getPlayer().getChest().getDefence() + " ");
		}
		if (controller.getPlayer().getChest().getHp() > 0) {
			itemStats = itemStats.concat("H: " + controller.getPlayer().getChest().getHp() + " ");
		}
		labelChestStats.setText(itemStats);

		itemStats = "";
		if (controller.getPlayer().getHelmet().getAttack() > 0) {
			itemStats = itemStats.concat("A: " + controller.getPlayer().getHelmet().getAttack() + " ");
		}
		if (controller.getPlayer().getHelmet().getDefence() > 0) {
			itemStats = itemStats.concat("D: " + controller.getPlayer().getHelmet().getDefence() + " ");
		}
		if (controller.getPlayer().getHelmet().getHp() > 0) {
			itemStats = itemStats.concat("H: " + controller.getPlayer().getHelmet().getHp() + " ");
		}
		labelHelmStats.setText(itemStats);
	}

	private void updateMap() {
		String border = "";
		String map;
		for (int i = 0; i < controller.getMap()[0].length; i++) {
			border = border.concat("--");
		}

		map = "/-" + border + "\\\n";
		for (Cell[] line : controller.getMap()) {
			map = map.concat("| ");
			for (Cell cell : line) {
				switch (cell) {
					case PLAYER: map = map.concat("@ "); break;
					case EMPTY: map = map.concat(". "); break;
					case FOG: map = map.concat("~ "); break;
					case WALL: map = map.concat("# "); break;
					case MOB: map = map.concat("! "); break;
					case EXIT: map = map.concat("^ "); break;
					default: map = map.concat("? ");
				}
			}
			map = map.concat("|\n");
		}
		fieldMap.setText(map + "\\-" + border + "/");
		labelMapLevel.setText("Level: " + controller.getPlayer().getAscendedFloors());
	}

	private void updateLog(String message) {
		if (message != null) {
			try {
				fieldLog.getStyledDocument().insertString(fieldLog.getStyledDocument().getLength(), message + "\n", null);
				fieldLog.setCaretPosition(fieldLog.getStyledDocument().getLength());
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	public void showStartMenu() {
		if (this.previousPhase != GamePhase.MAIN_MENU) {
			this.previousPhase = GamePhase.MAIN_MENU;
			fieldLog.setText("");
			if (this.message != null) {
				labelMM.setText(this.message);
				this.message = null;
			} else {
				labelMM.setText("   ");
			}
			((CardLayout)this.container.getLayout()).show(this.container, "Main Menu");
		}
	}

	public void showNewCharacter() {
		if (this.previousPhase != GamePhase.NEW_CHARACTER) {
			this.previousPhase = GamePhase.NEW_CHARACTER;
			if (this.message != null) {
				labelNC.setText(this.message);
				this.message = null;
			} else {
				labelNC.setText("   ");
			}
			((CardLayout)this.container.getLayout()).show(this.container, "New Character");
		}
	}

	public void showLoadCharacter() {
		if (this.previousPhase != GamePhase.LOAD_CHARACTER) {
			this.previousPhase = GamePhase.LOAD_CHARACTER;
			this.listen = false;
			updateHeroList();
			this.listen = true;
			controller.loadPlayer(this.heroList.getItemAt(0));
			displaySelectedHero();
			if (this.message != null) {
				labelLC.setText(this.message);
				this.message = null;
			} else {
				labelLC.setText("   ");
			}
			((CardLayout)this.container.getLayout()).show(this.container, "Load Character");
		}
	}

	public void showLoadingScreen() {
		((CardLayout)this.container.getLayout()).show(this.container, "Loading");
	}

	public void showNavigationScreen() {
		if (this.previousPhase != GamePhase.NAVIGATION) {
			this.previousPhase = GamePhase.NAVIGATION;
			updateMap();
			((CardLayout) this.container.getLayout()).show(this.container, "Navigation");
			((CardLayout) this.panelNButtons.getLayout()).show(this.panelNButtons, "Movement");
		}
		updateLog(this.message);
		this.message = null;
		updatePlayer();
	}

	public void showEncounterScreen() {
		if (this.previousPhase != GamePhase.ENCOUNTER) {
			this.previousPhase = GamePhase.ENCOUNTER;
			message = "You encountered a Lv." + controller.getEnemy().getLevel() + " " + controller.getEnemy().getType().toString() + ". Attack: " + controller.getEnemy().getAttack() + "  Defence: " + controller.getEnemy().getDefence() + "  Health: " + controller.getEnemy().getHp();
			updateLog(this.message);
			this.message = null;
			((CardLayout) this.container.getLayout()).show(this.container, "Navigation");
			((CardLayout) this.panelNButtons.getLayout()).show(this.panelNButtons, "Encounter");
		}
		updatePlayer();
	}

	public void showCombatScreen() {
		this.previousPhase = GamePhase.COMBAT;
		updateLog(this.message);
		this.message = null;
		updateLog(this.combatMessage);
		this.combatMessage = null;
		updatePlayer();
		((CardLayout) this.container.getLayout()).show(this.container, "Navigation");
		((CardLayout) this.panelNButtons.getLayout()).show(this.panelNButtons, "Combat");
	}

	public void showLootScreen() {
		if (this.previousPhase != GamePhase.LOOT) {
			this.previousPhase = GamePhase.LOOT;
			updateLog(this.message);
			this.message = null;

			updateLog("You got a new item: " + controller.getLoot().getName());
			if (controller.getLoot().getAttack() > 0) {
				updateLog(" Attack: " + controller.getLoot().getAttack());
			}
			if (controller.getLoot().getDefence() > 0) {
				updateLog(" Defence: " + controller.getLoot().getDefence());
			}
			if (controller.getLoot().getHp() > 0) {
				updateLog(" Health: " + controller.getLoot().getHp());
			}

			((CardLayout) this.container.getLayout()).show(this.container, "Navigation");
			((CardLayout) this.panelNButtons.getLayout()).show(this.panelNButtons, "Loot");
		}
	}


	private class ActionMode implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				controller.setView("console");
				previousPhase = GamePhase.LOAD_GAME;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private class ActionBackMM implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			controller.setGamePhase(GamePhase.MAIN_MENU);
		}
	}

	private class ActionQuit implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			controller.quit();
		}
	}
}
