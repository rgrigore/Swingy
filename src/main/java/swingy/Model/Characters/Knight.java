package swingy.Model.Characters;

import swingy.Model.Items.Item;

class Knight extends BasePlayer implements Player{
	Knight(String name) {
		this.setName(name);
		this.setRole(AvailableClasses.Knight);
		this.setLevel(1);
		this.setExp(0);

		this.setBaseAttack(8);
		this.setBaseDefence(10);
		this.setBaseHp(10);
		this.setAscendedFloors(1);

		this.setWeapon(new Item(1, "Basic Sword", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Chestplate", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Helmet", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	Knight(String name, int level, int exp, int attack, int defence, int hp, int ascendedFloors, int weaponId, int armorId, int helmetId) {
		this.setName(name);
		this.setRole(AvailableClasses.Knight);
		this.setLevel(level);
		this.setExp(exp);
		this.setBaseAttack(attack);
		this.setBaseDefence(defence);
		this.setBaseHp(hp);
		this.setAscendedFloors(ascendedFloors);

		this.setWeapon(new Item(1, "Basic Sword", "Weapon", 0, 0, 0, 0));
		this.setChest(new Item(1, "Basic Chestplate", "Chest", 0, 0, 0, 0));
		this.setHelmet(new Item(1, "Basic Helmet", "Helmet", 0, 0, 0, 0));

		this.updateTotalStats();
		this.heal(100);
	}

	void levelUp() {
		updateBaseAttack(1);
		updateBaseDefence(2);
		updateBaseHp(1);

		this.heal(100);
	}
}
