package swingy.Model.Characters;

import swingy.Model.Items.Item;

import java.util.Random;

class Barbarian extends BasePlayer implements Player{
	Barbarian(String name) {
		this.setName(name);
		this.setRole(AvailableClasses.Barbarian);
		this.setLevel(1);
		this.setExp(0);

		this.setBaseAttack(7);
		this.setBaseDefence(7);
		this.setBaseHp(15);
		this.setAscendedFloors(1);

		this.setWeapon(new Item(1,"Basic Waraxe", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Belt", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Skull", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	Barbarian(String name, int level, int exp, int attack, int defence, int hp, int ascendedFloors, int weaponId, int armorId, int helmetId) {
		this.setName(name);
		this.setRole(AvailableClasses.Barbarian);
		this.setLevel(level);
		this.setExp(exp);
		this.setBaseAttack(attack);
		this.setBaseDefence(defence);
		this.setBaseHp(hp);
		this.setAscendedFloors(ascendedFloors);

		this.setWeapon(new Item(1,"Basic Waraxe", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Belt", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Skull", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	void levelUp() {
		updateBaseAttack(1);
		updateBaseDefence(1);
		updateBaseHp(2);

		this.heal(100);
	}

	public Effects attackType() {
		if ((new Random()).nextInt(100) < 10) {
			return Effects.STUN;
		}
		return Effects.NONE;
	}
}
