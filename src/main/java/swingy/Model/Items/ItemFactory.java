package swingy.Model.Items;

import swingy.Model.Characters.AvailableClasses;

import java.util.Random;

abstract public class ItemFactory {
	public static Item newRandomItem(int level, AvailableClasses role) {
		String type = "???";
		String name = "???";
		int attack = 0, defence = 0, health = 0;

		switch ((new Random()).nextInt(3)) {
			case 0:
				type = "Weapon";
				switch (role) {
					case Rogue:
						name = "Dagger";
						attack = level * 3 / 2;
						defence = level / 2;
						break;
					case Knight:
						name = "Sword";
						attack = level / 3 * 2;
						defence = level;
						break;
					case Barbarian:
						name = "Waraxe";
						attack = level / 3 * 2;
						health = level;
						break;
					case Battlemage:
						name = "Spellsword";
						attack = level * 2;
						break;
					default:
						name = "???";
				}
				break;
			case 1:
				type = "Chest";
				switch (role) {
					case Rogue:
						name = "Tunic";
						attack = (int)(level * 0.25);
						defence = level;
						break;
					case Knight:
						name = "Chestplate";
						defence = level * 2;
						health = (int)(level * 0.75);
						break;
					case Barbarian:
						name = "Belt";
						defence = (int)(level * 0.25);
						health = level * 2;
						break;
					case Battlemage:
						name = "Robe";
						attack = level;
						defence = level;
						break;
					default:
						name = "???";
				}
				break;
			case 2:
				type = "Helmet";
				switch (role) {
					case Rogue:
						name = "Mask";
						attack = (int)(level * 0.25);
						defence = (int)(level * 0.25);
						health = level;
						break;
					case Knight:
						name = "Helmet";
						defence = level / 2;
						health = level;
						break;
					case Barbarian:
						name = "Skull";
						attack = (int)(level * 0.25);
						health = level * 3;
						break;
					case Battlemage:
						name = "Hat";
						attack = (int)(level * 0.5);
						health = level / 2;
						break;
					default:
						name = "???";
				}
				break;
		}
		return new Item(1, name, type, 1, attack, defence, health);
	}
}
