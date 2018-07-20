package swingy.Model.Items;


public class Item {
	private int id, attack, defence, hp, rarity;
	private String name, type;

	// TODO Instead of specifying all the values for the item, only use the name and take the params from a DB

	public Item(int id, String name, String type, int rarity, int attack, int defence, int hp) {
		this.id = id;
		this.attack = attack;
		this.defence = defence;
		this.hp = hp;
		this.name = name;
		this.type = type;
		this.rarity = rarity;
	}

	public int getId() {
		return id;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefence() {
		return defence;
	}

	public int getHp() {
		return hp;
	}

	public int getRarity() {
		return rarity;
	}

	public String getName() {
		return name;
	}

	public String getType () {
		return type;
	}
}
