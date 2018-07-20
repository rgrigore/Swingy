package swingy.Model.Characters;

import swingy.Model.Items.Item;

public interface Player {
	String getName();
	AvailableClasses getRole();
	int getLevel();
	int getExp();
	int getExpToLevel();
	int getBaseAttack();
	int getBaseDefence();
	int getBaseHp();
	int getTotalAttack();
	int getTotalDefence();
	int getTotalHp();
	int getCurrentHp();
	int getAscendedFloors();
	Item getWeapon();
	Item getChest();
	Item getHelmet();
	void ascend();
	void gainExp(int value);
	int damage();
	Effects attackType();
	int effectDamage();
	void defend(int damage);
	String getDamageMessage();
	void equipItem(Item item);
}
