package swingy.Model.Characters;

/*
 * Defence functionality:
 *
 * warrior: Defence works as armor, reducing the incoming damage
 *
 * rogue: Defence works as evasion, giving either a complete dodge or "avoided" damage
 *
 * barbarian: maybe Defence could allow regeneration during combat?
 *
 * battlemage: Defence would work as a recharging shield after each fight(could be too op)
 */

/*
 * possible luck modifiers per class
 *
 * warrior: chance to counter when getting attacked
 *
 * rogue: chance to ignore the targets Defence when attacking
 *
 * barbarian: chance to bash your target when attacking, bash makes the enemy lose 1 turn
 *
 * battlemage: chance to cast a damaging spell when attacking?
 */


import swingy.Model.Items.Item;

public abstract class BasePlayer {
	private String name;
	private AvailableClasses role;
	private int level, exp, expToLevel;
	private int baseAttack, baseDefence, baseHp;
	private int itemAttack = 0, itemDefence = 0, itemHp = 0;
	private int totalAttack, totalDefence, totalHp, currentHp;
	private Item weapon, chest, helmet;
	private int ascendedFloors;
	private String damageMessage;


	void setName(String name) {
		this.name = name;
	}

	void setRole(AvailableClasses role) {
		this.role = role;
	}

	void setLevel(int level) {
		this.level = level;
		this.expToLevel = this.level * 1000 + (this.level - 1) * (this.level - 1) * 450;
	}

	void setExp(int exp) {
		this.exp = exp;
		while (this.exp >= this.expToLevel) {
			this.levelUp();
			this.exp -= this.expToLevel;
			this.expToLevel = this.level * 1000 + (this.level - 1) * (this.level - 1) * 450;
		}
	}

	void setBaseAttack (int baseAttack) {
		this.baseAttack = baseAttack;
	}

	void setBaseDefence (int baseDefence) {
		this.baseDefence = baseDefence;
	}

	void setBaseHp (int baseHp) {
		this.baseHp = baseHp;
	}

	void setAscendedFloors (int ascendedFloors) {
		this.ascendedFloors = ascendedFloors;
	}

	public String getName() {
		return name;
	}

	public AvailableClasses getRole() {
		return role;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int getExpToLevel() {
		return expToLevel;
	}

	public int getBaseAttack () {
		return baseAttack;
	}

	public int getBaseDefence () {
		return baseDefence;
	}

	public int getBaseHp () {
		return baseHp;
	}

	public int getTotalAttack () {
		return totalAttack;
	}

	public int getTotalDefence () {
		return totalDefence;
	}

	public int getTotalHp () {
		return totalHp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getAscendedFloors() {
		return ascendedFloors;
	}


	public Item getWeapon() {
		return weapon;
	}

	public Item getChest() {
		return chest;
	}

	public Item getHelmet() {
		return helmet;
	}

	void updateBaseAttack (int value) {
		this.baseAttack += value;
		updateTotalAttack();
	}

	void updateBaseDefence (int value) {
		this.baseDefence += value;
		updateTotalDefence();
	}

	void updateBaseHp (int value) {
		this.baseHp += value;
		updateTotalHp();
	}


	private void updateItemAttack(int value) {
		this.itemAttack += value;
		updateTotalAttack();
	}

	private void updateItemDefence(int value) {
		this.itemDefence += value;
		updateTotalDefence();
	}

	private void updateItemHp(int value) {
		this.itemHp += value;
		updateTotalHp();
	}


	void updateTotalStats() {
		updateTotalAttack();
		updateTotalDefence();
		updateTotalHp();
	}

	private void updateTotalAttack() {
		this.totalAttack = this.baseAttack + this.itemAttack;
	}

	private void updateTotalDefence() {
		this.totalDefence = this.baseDefence + this.itemDefence;
	}

	private void updateTotalHp() {
		this.totalHp = this.baseHp + this.itemHp;
	}


	public void equipItem(Item item) {
		switch (item.getType()) {
			case "Weapon":
				updateItemAttack(item.getAttack() - this.weapon.getAttack());
				updateItemDefence(item.getDefence() - this.weapon.getDefence());
				updateItemHp(item.getHp() - this.weapon.getHp());
				this.weapon = item;
				break;
			case "Chest":
				updateItemAttack(item.getAttack() - this.chest.getAttack());
				updateItemDefence(item.getDefence() - this.chest.getDefence());
				updateItemHp(item.getHp() - this.chest.getHp());
				this.chest = item;
				break;
			case "Helmet":
				updateItemAttack(item.getAttack() - this.helmet.getAttack());
				updateItemDefence(item.getDefence() - this.helmet.getDefence());
				updateItemHp(item.getHp() - this.helmet.getHp());
				this.helmet = item;
				break;
		}
	}

	void setWeapon(Item item) {
		this.itemAttack += item.getAttack();
		this.itemDefence += item.getDefence();
		this.itemHp += item.getHp();
		this.weapon = item;
	}

	void setChest(Item item) {
		this.itemAttack += item.getAttack();
		this.itemDefence += item.getDefence();
		this.itemHp += item.getHp();
		this.chest = item;
	}

	void setHelmet(Item item) {
		this.itemAttack += item.getAttack();
		this.itemDefence += item.getDefence();
		this.itemHp += item.getHp();
		this.helmet = item;
	}


	public void ascend() {
		this.ascendedFloors++;
	}

	// TODO Balance this!!!!
	abstract void levelUp();

	public void gainExp (int exp) {
		this.exp += exp;
		while (this.exp >= this.expToLevel) {
			this.level++;
			this.levelUp();
			this.exp -= this.expToLevel;
			this.expToLevel = this.level * 1000 + (this.level - 1) * (this.level - 1) * 450;
		}
	}

	public void heal(int value) {
		this.currentHp += this.totalHp / 100.0 * value;
		if (this.currentHp > this.totalHp) {
			this.currentHp = this.totalHp;
		}
	}

	public int damage() { // Todo Balance this per class
		return getTotalAttack();
	}

	public Effects attackType() {
		return Effects.NONE;
	}

	public int effectDamage() {
		return 0;
	}

	public void defend(int damage) { // Todo Make it custom per class
		int damageTaken = Math.round(damage * damage / (damage + getTotalDefence()));
		int damageMitigated = damage - damageTaken;
		damageMessage = "You were dealt " + damageTaken + " damage";
		if (damageMitigated > 0) {
			this.damageMessage += " (" + damageMitigated + " mitigated)";
		}
		currentHp -= damageTaken;
	}

	public String getDamageMessage() {
		return this.damageMessage;
	}

	public static class Builder {
		private String name = "Jeremy";
		private AvailableClasses role = AvailableClasses.NONE;
		private int level = 1, exp = 0, attack = 1, defence = 1, hp = 1, ascendedFloors = 1, weaponId = 0, armorId = 0, helmetId = 0;
		private Boolean existing = false;

		public Player build() {
			if (existing) {
				switch (this.role) {
					case Barbarian: return new Barbarian(this.name, this.level, this.exp, this.attack, this.defence, this.hp, this.ascendedFloors, this.weaponId, this.armorId, this.helmetId);
					case Battlemage: return new Battlemage(this.name, this.level, this.exp, this.attack, this.defence, this.hp, this.ascendedFloors, this.weaponId, this.armorId, this.helmetId);
					case Knight: return new Knight(this.name, this.level, this.exp, this.attack, this.defence, this.hp, this.ascendedFloors, this.weaponId, this.armorId, this.helmetId);
					case Rogue: return new Rogue(this.name, this.level, this.exp, this.attack, this.defence, this.hp, this.ascendedFloors, this.weaponId, this.armorId, this.helmetId);
				}
			} else {
				switch (this.role) {
					case Barbarian: System.out.println("Barbarian"); return new Barbarian(this.name);
					case Battlemage: return new Battlemage(this.name);
					case Knight: return new Knight(this.name);
					case Rogue: return new Rogue(this.name);
				}
			}
			System.out.println("Something went horribly wrong!");
			System.exit(1);
			return null;
		}

		public Builder load() {
			this.existing = true;
			return this;
		}

		public Builder setName(String name) {
			this.name = name;
			return this;
		}

		public Builder setRole(AvailableClasses role) {
			this.role = role;
			return this;
		}

		public Builder setLevel(int level) {
			this.level = level;
			return this;
		}

		public Builder setExp(int exp) {
			this.exp = exp;
			return this;
		}

		public Builder setAttack(int attack) {
			this.attack = attack;
			return this;
		}

		public Builder setDefence(int defence) {
			this.defence = defence;
			return this;
		}

		public Builder setHp(int hp) {
			this.hp = hp;
			return this;
		}

		public Builder setAscendedFloors (int ascendedFloors) {
			this.ascendedFloors = ascendedFloors;
			return this;
		}

		public Builder setWeapon(int weaponId) {
			this.weaponId = weaponId;
			return this;
		}

		public Builder setArmor(int armorId) {
			this.armorId = armorId;
			return this;
		}

		public Builder setHelmet(int helmetId) {
			this.helmetId = helmetId;
			return this;
		}
	}
}
