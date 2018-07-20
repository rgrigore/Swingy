package swingy.Controller;

import swingy.Model.Characters.Effects;
import swingy.Model.Characters.Player;
import swingy.Model.Enemies.Enemy;
import swingy.Model.Items.Item;
import swingy.Model.Items.ItemFactory;

import java.util.Random;

public class CombatController {
	private Controller controller;
	private Item loot = null;
	private boolean lootDrop;
	private boolean skipPlayer = false, skipEnemy = false;

	private boolean nextRound = true;
	private boolean playerTurn = true;

	private boolean victory = true;

	CombatController(Controller controller) {
		this.controller = controller;
	}


	void fight(Player player, Enemy enemy) {
		if (nextRound) {
			nextRound = false;
			if (checkFight(player, enemy)) {
				rollSpoils();
				controller.finishedCombat();
			} else if (playerTurn) {
				playerAttack(player, enemy);
				playerTurn = false;
			} else {
				enemyAttack(player, enemy);
				playerTurn = true;
			}
			controller.getView().showCombatScreen();
		}
	}

	private void playerAttack(Player player, Enemy enemy) {
		if (!skipPlayer) {
			Effects effect = player.attackType();
			if (effect == Effects.STUN) {
				this.skipEnemy();
			}
			enemy.defend(player.damage(), player.effectDamage(), effect);
			controller.getView().setCombatMessage(enemy.damageMessage());

		} else {
			controller.getView().setCombatMessage("You are stunned!");
			skipPlayer = false;
		}
	}

	private void enemyAttack(Player player, Enemy enemy) {
		if (!skipEnemy) {
			player.defend(enemy.damage());
			controller.getView().setCombatMessage(player.getDamageMessage());
			//controller.getView().setCombatMessage("The enemy looks at you with tears in its eyes!");
		} else {
			controller.getView().setCombatMessage("Your enemy is stunned!");
			skipEnemy = false;
		}
	}

	private boolean checkFight(Player player, Enemy enemy) {
		if (player.getCurrentHp() <= 0) {
			victory = false;
			return true;
		}
		return enemy.getCurrentHp() <= 0;
	}

	void failedFlee() {
		playerTurn = false;
	}

	private void rollSpoils() {
		if ((new Random()).nextInt(2) == 0) {
			lootDrop = true;
			loot = ItemFactory.newRandomItem(controller.getPlayer().getAscendedFloors(), controller.getPlayer().getRole());
		}
	}

	void continueCombat() {
		nextRound = true;
	}

	void resetCombat() {
		skipPlayer = false;
		skipEnemy = false;
		lootDrop = false;
		loot = null;
		nextRound = true;
		playerTurn = true;
	}

	public void skipPlayer() {
		skipPlayer = true;
	}

	public void skipEnemy() {
		skipEnemy = true;
	}

	public boolean checkVictory() {
		return victory;
	}

	public boolean checkLootDrop () {
		return lootDrop;
	}

	Item getLoot() {
		return this.loot;
	}
}
