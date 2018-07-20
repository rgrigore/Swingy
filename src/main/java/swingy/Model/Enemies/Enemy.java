package swingy.Model.Enemies;

import swingy.Model.Characters.Effects;

public class Enemy {
	private int level, exp, attack, defence, hp, currentHp;
	private EnemyTypes type;
	private int x, y;
	private String damageMessage;

	public Enemy(int attack, int defence, int hp, int level, int exp, EnemyTypes type, int coordX, int coordY) {
		this.attack = attack;
		this.defence = defence;
		this.hp = hp;
		this.level = level;
		this.exp = exp;
		this.type = type;
		this.currentHp = this.hp;
		this.x = coordX;
		this.y = coordY;
	}

	public EnemyTypes getType() {
		return type;
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

	public int getHp() {
		return hp;
	}

	public int getCurrentHp() {
		return currentHp;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean collision(int x, int y) {
		return (this.x == x && this.y == y);
	}

	public int damage() {
		return this.attack;
	}

	public void defend(int damage, int effectDamage, Effects effect) {
		int damageTaken;
		int damageMitigated;

		switch (effect) {
			case TRUE:
				damageTaken = damage;
				this.damageMessage = this.type + " was dealt " + damageTaken + " true damage.";
				break;
			case SPELL:
				damageTaken = Math.round(damage * damage / (damage + this.defence));
				damageMitigated = damage - damageTaken;
				this.damageMessage = this.type + " was dealt " + damageTaken + " damage";
				if (damageMitigated > 0) {
					this.damageMessage += " (" + damageMitigated + " mitigated)";
				}
				this.damageMessage += ", a fireball hit it for an additional " + effectDamage + " unblockable damage.";
				break;
			case STUN:
				damageTaken = damage / 2;
				this.damageMessage = this.type + " was dealt " + damageTaken + " damage and was also stunned.";
				break;
			case NONE:
				damageTaken = Math.round(damage * damage / (damage + this.defence));
				damageMitigated = damage - damageTaken;
				this.damageMessage = this.type + " was dealt " + damageTaken + " damage.";
				if (damageMitigated > 0) {
					this.damageMessage += " (" + damageMitigated + " mitigated).";
				}
				break;
			default:
				damageTaken = 0;
				this.damageMessage = "Something went wrong and you ended up just looking around confused. Sorry!";
		}
		this.currentHp -= damageTaken;
	}

	public String damageMessage() {
		return this.damageMessage;
	}
}
