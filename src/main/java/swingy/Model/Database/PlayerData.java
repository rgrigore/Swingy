package swingy.Model.Database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class PlayerData {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;

	String name, role;
	int level, exp, attack, defence, maxHp, ascendedFloors;

	String weaponName, chestName, helmetName;
	int weaponAttack, weaponDefence, weaponHealth, chestAttack, chestDefence, chestHealth, helmetAttack, helmetDefence, helmetHealth;


	public long getId() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public int getLevel() {
		return level;
	}

	public int getExp() {
		return exp;
	}

	public int getAttack() {
		return attack;
	}

	public int getDefence() {
		return defence;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getAscendedFloors() {
		return ascendedFloors;
	}


	public String getWeaponName() {
		return weaponName;
	}

	public String getChestName() {
		return chestName;
	}

	public String getHelmetName() {
		return helmetName;
	}

	public int getWeaponAttack() {
		return weaponAttack;
	}

	public int getWeaponDefence() {
		return weaponDefence;
	}

	public int getWeaponHealth() {
		return weaponHealth;
	}

	public int getChestAttack() {
		return chestAttack;
	}

	public int getChestDefence() {
		return chestDefence;
	}

	public int getChestHealth() {
		return chestHealth;
	}

	public int getHelmetAttack() {
		return helmetAttack;
	}

	public int getHelmetDefence() {
		return helmetDefence;
	}

	public int getHelmetHealth() {
		return helmetHealth;
	}
}
