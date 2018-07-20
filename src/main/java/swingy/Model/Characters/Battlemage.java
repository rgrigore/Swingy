package swingy.Model.Characters;

import swingy.Model.Items.Item;

import java.util.Random;

class Battlemage extends BasePlayer implements Player{
	Battlemage(String name) {
		this.setName(name);
		this.setRole(AvailableClasses.Battlemage);
		this.setLevel(1);
		this.setExp(0);

		this.setBaseAttack(14);
		this.setBaseDefence(5);
		this.setBaseHp(8);
		this.setAscendedFloors(1);

		this.setWeapon(new Item(1, "Basic Spellsword", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Robe", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Hat", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	Battlemage(String name, int level, int exp, int attack, int defence, int hp, int ascendedFloors, int weaponId, int armorId, int helmetId) {
		this.setName(name);
		this.setRole(AvailableClasses.Battlemage);
		this.setLevel(level);
		this.setExp(exp);
		this.setBaseAttack(attack);
		this.setBaseDefence(defence);
		this.setBaseHp(hp);
		this.setAscendedFloors(ascendedFloors);

		this.setWeapon(new Item(1, "Basic Spellsword", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Robe", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Hat", "Helmet", 0, 0, 0, 0));

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
		if ((new Random()).nextInt(100) < 10) {
			return Effects.SPELL;
		}
		return Effects.NONE;
	}

	public int effectDamage() {
		return getBaseAttack() * 3;
	}
}
