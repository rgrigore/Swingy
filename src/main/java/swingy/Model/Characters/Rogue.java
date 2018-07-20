package swingy.Model.Characters;

import swingy.Model.Items.Item;

import java.util.Random;

class Rogue extends BasePlayer implements Player{
	Rogue(String name) {
		this.setName(name);
		this.setRole(AvailableClasses.Rogue);
		this.setLevel(1);
		this.setExp(0);

		this.setBaseAttack(15);
		this.setBaseDefence(8);
		this.setBaseHp(9);
		this.setAscendedFloors(1);

		this.setWeapon(new Item(1, "Basic Dagger", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Tunic", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Mask", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	Rogue(String name, int level, int exp, int attack, int defence, int hp, int ascendedFloors, int weaponId, int armorId, int helmetId) {
		this.setName(name);
		this.setRole(AvailableClasses.Rogue);
		this.setLevel(level);
		this.setExp(exp);
		this.setBaseAttack(attack);
		this.setBaseDefence(defence);
		this.setBaseHp(hp);
		this.setAscendedFloors(ascendedFloors);

		this.setWeapon(new Item(1, "Basic Dagger", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Tunic", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Mask", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	void levelUp() {
		updateBaseAttack(2);
		updateBaseDefence(1);
		updateBaseHp(1);

		this.heal(100);
	}

	public Effects attackType() {
		if ((new Random()).nextInt(100) < 30) {
			return Effects.TRUE;
		}
		return Effects.NONE;
	}
}
