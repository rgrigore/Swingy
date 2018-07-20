package swingy.Model.Enemies;

import java.util.Random;

abstract public class EnemyFactory {

	public static Enemy newRandomEnemy(int difficulty, int mapSize, int locationX, int locationY) {

		Random randomizer = new Random();
		int typeRoll = randomizer.nextInt(100) + 1;
		int distanceFromCenter = (int)Math.round(Math.sqrt(Math.pow(mapSize / 2 - locationX, 2) + Math.pow(mapSize / 2 - locationY, 2)));

		if (distanceFromCenter <= mapSize / 8) {

			// Easy mobs
			if (typeRoll <= 20) {
				return newZombie(difficulty + typeRoll / 51, locationX, locationY);
			} else if (typeRoll <= 40) {
				return newSkeleton(difficulty + typeRoll / 51, locationX, locationY);
			} else if (typeRoll <= 60) {
				return newWolf(difficulty + typeRoll / 51, locationX, locationY);
			} else if (typeRoll <= 80) {
				return newBanshee(difficulty + typeRoll / 51, locationX, locationY);
			} else {
				return newDrake(difficulty + typeRoll / 51, locationX, locationY);
			}

		} else if (distanceFromCenter <= mapSize / 4) {

			if (randomizer.nextInt(3) == 0) {
				// Hard mobs
				if (typeRoll <= 20) {
					return newGhoul(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 40) {
					return newBoneguard(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 60) {
					return newCerberus(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 80) {
					return newWraith(difficulty + typeRoll / 34, locationX, locationY);
				} else {
					return newDragon(difficulty + typeRoll / 34, locationX, locationY);
				}

			} else {

				// Easy mobs
				if (typeRoll <= 20) {
					return newZombie(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 40) {
					return newSkeleton(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 60) {
					return newWolf(difficulty + typeRoll / 34, locationX, locationY);
				} else if (typeRoll <= 80) {
					return newBanshee(difficulty + typeRoll / 34, locationX, locationY);
				} else {
					return newDrake(difficulty + typeRoll / 34, locationX, locationY);
				}
			}

		} else if (distanceFromCenter <= mapSize * 3 / 8) {

			if (randomizer.nextInt(3) == 0) {

				// Easy mobs
				if (typeRoll <= 20) {
					return newZombie(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 40) {
					return newSkeleton(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 60) {
					return newWolf(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 80) {
					return newBanshee(difficulty + typeRoll / 26, locationX, locationY);
				} else {
					return newDrake(difficulty + typeRoll / 26, locationX, locationY);
				}

			} else {

				// Hard mobs
				if (typeRoll <= 20) {
					return newGhoul(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 40) {
					return newBoneguard(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 60) {
					return newCerberus(difficulty + typeRoll / 26, locationX, locationY);
				} else if (typeRoll <= 80) {
					return newWraith(difficulty + typeRoll / 26, locationX, locationY);
				} else {
					return newDragon(difficulty + typeRoll / 26, locationX, locationY);
				}

			}

		} else if (distanceFromCenter < mapSize / 2) {

			// Hard mobs
			if (typeRoll <= 20) {
				return newGhoul(difficulty + typeRoll / 19, locationX, locationY);
			} else if (typeRoll <= 40) {
				return newBoneguard(difficulty + typeRoll / 19, locationX, locationY);
			} else if (typeRoll <= 60) {
				return newCerberus(difficulty + typeRoll / 19, locationX, locationY);
			} else if (typeRoll <= 80) {
				return newWraith(difficulty + typeRoll / 19, locationX, locationY);
			} else {
				return newDragon(difficulty + typeRoll / 19, locationX, locationY);
			}

		} else {
			// Kill him!
			return newDragon(difficulty + 10, locationX, locationY);
		}

	}


	public static Enemy newZombie(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Zombie, locationX,locationY);
	}

	public static Enemy newSkeleton(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Skeleton, locationX,locationY);
	}

	public static Enemy newWolf(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Wolf, locationX,locationY);
	}

	public static Enemy newBanshee(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Banshee, locationX,locationY);
	}

	public static Enemy newDrake(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Drake, locationX,locationY);
	}

	public static Enemy newGhoul(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Ghoul, locationX,locationY);
	}

	public static Enemy newBoneguard(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Boneguard, locationX,locationY);
	}

	public static Enemy newCerberus(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Cerberus, locationX,locationY);
	}

	public static Enemy newWraith(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Wraith, locationX,locationY);
	}

	public static Enemy newDragon(int difficulty, int locationX, int locationY) {
		//
		int exp = (difficulty * 1000 + (difficulty - 1) / 2 * 450) / (difficulty * 5);
		return new Enemy(difficulty, (int)(difficulty * 0.6), difficulty * 2, difficulty, exp, EnemyTypes.Dragon, locationX,locationY);
	}
}
