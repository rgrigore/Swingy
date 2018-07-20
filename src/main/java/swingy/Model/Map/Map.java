package swingy.Model.Map;

import swingy.Model.Enemies.Enemy;
import swingy.Model.Enemies.EnemyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
	private int size, difficulty, seed, playerX, playerY;
	private Cell[][] map, fog;
	private List<Enemy> enemies = new ArrayList<>();
	private Enemy enemy;

	public void generate(int size, int difficulty) {
		this.size = (size - 1) * 5 + 10 - (size % 2) + 2;
		this.difficulty = difficulty;
		this.playerX = this.size / 2;
		this.playerY = this.size / 2;

		this.map = new Cell[this.size][this.size];
		this.fog = new Cell[this.size][this.size];
		for (int y = 0; y < map.length; y++){
			for (int x = 0; x < map[y].length; x++) {
				this.fog[y][x] = Cell.FOG;
				if (y == 0 || y == this.size - 1 || x == 0 || x == this.size - 1) {
					map[y][x] = Cell.EXIT;
					fog[y][x] = Cell.EXIT;
				} else {
					this.map[y][x] = Cell.WALL;
				}
			}
		}

		generateMaze();
		generateMobs();
		clearFog();
	}

	private void generateMaze() {
		int y = playerY, x = playerX;
		ArrayList<int[]> walls = new ArrayList<>();

		walls.add(new int[] {playerY, playerX});

		while (this.map[y][x] != Cell.EXIT) {

			this.map[y][x] = Cell.EMPTY;

			int random = (new Random()).nextInt(4);

			switch (random) {
				case 0:
					if (y - 1 >= 0 && (this.map[y - 1][x] == Cell.WALL || this.map[y - 1][x] == Cell.EXIT)) {
						walls.add(new int[]{y - 1, x});
						break;
					}
				case 1:
					if (y + 1 < this.size && (this.map[y + 1][x] == Cell.WALL || this.map[y + 1][x] == Cell.EXIT)) {
						walls.add(new int[]{y + 1, x});
						break;
					}
				case 2:
					if (x - 1 >= 0 && (this.map[y][x - 1] == Cell.WALL || this.map[y][x - 1] == Cell.EXIT)) {
						walls.add(new int[]{y, x - 1});
						break;
					}
				case 3:
					if (x + 1 < this.size && (this.map[y][x + 1] == Cell.WALL || this.map[y][x + 1] == Cell.EXIT)) {
						walls.add(new int[]{y, x + 1});
						break;
					}
				default:
					if (y - 1 >= 0 && (this.map[y - 1][x] == Cell.WALL || this.map[y - 1][x] == Cell.EXIT)) {
						walls.add(new int[]{y - 1, x});
					} else if (y + 1 < this.size && (this.map[y + 1][x] == Cell.WALL || this.map[y + 1][x] == Cell.EXIT)) {
						walls.add(new int[]{y + 1, x});
					} else if (x - 1 >= 0 && (this.map[y][x - 1] == Cell.WALL || this.map[y][x - 1] == Cell.EXIT)) {
						walls.add(new int[]{y, x - 1});
					}
			}


			int index = (new Random()).nextInt(walls.size());
			y = walls.get(index)[0];
			x = walls.get(index)[1];

			if (walls.size() > 25) {
				walls.remove(index);
			}
		}
	}


	private void generateMobs() {
		Random random = new Random();

		for (int y = 1; y < this.size - 1; y++) {
			for (int x = 1; x < this.size - 1; x++) {
				if (this.map[y][x] == Cell.EMPTY && y != playerY && x != playerX) {
					if (random.nextInt(5) == 1) {
						this.map[y][x] = Cell.MOB;
						enemies.add(EnemyFactory.newRandomEnemy(this.difficulty, this.size, x, y));
					}
				}
			}
		}

	}

	private void clearFog() {
		clearCell(playerY, playerX);
		clearCell(playerY - 1, playerX);
		clearCell(playerY + 1, playerX);
		clearCell(playerY, playerX - 1);
		clearCell(playerY, playerX + 1);
	}

	private void clearCell(int y, int x) {
		if (y == playerY && x == playerX && (map[y][x] == Cell.EMPTY || map[y][x] == Cell.MOB)) {
			fog[y][x] = Cell.EMPTY;
		} else if (map[y][x] == Cell.WALL || map[y][x] == Cell.MOB) {
			fog[y][x] = map[y][x];
		}
	}

	public Cell check(Direction direction) {
		int x = playerX, y = playerY;

		switch (direction) {
			case NORTH: y--; break;
			case EAST: x++; break;
			case SOUTH: y++; break;
			case WEST: x--; break;
		}

		switch (this.map[y][x]) {
			case MOB:
			case BOSS:
				for (Enemy enemy : enemies) {
					if (enemy.collision(x, y)) {
						this.enemy = enemy;
						break;
					}
				}
				break;
		}
		return this.map[y][x];
	}

	public void move(Direction direction) {
		switch (direction) {
			case NORTH: playerY--; break;
			case EAST: playerX++; break;
			case SOUTH: playerY++; break;
			case WEST: playerX--; break;
		}
		clearFog();
	}

	public Cell[][] getMap(String type) {
		Cell[][] map = this.map;
		switch (type) {
			case "map": map = this.map; break;
			case "fog": map = this.fog; break;
		}

		Cell[][] tempMap;
		int startX = playerX, startY = playerY;
		int	endX = playerX, endY = playerY;
		int border = 10;

		while (startX > 0 && playerX - startX < border) {
			startX--;
		}
		while (startY > 0 && playerY - startY < border) {
			startY--;
		}

		while (endX < map[0].length - 1 && endX - playerX < border) {
			endX++;
		}
		while (endY < map.length - 1 && endY - playerY < border) {
			endY++;
		}


		if (playerX - startX < 10) {
			while (endX < map[0].length - 1 && endX - playerX < border * 2 - (playerX - startX)) {
				endX++;
			}
		}
		if (playerY - startY < 10) {
			while (endY < map.length - 1 && endY - playerY < border * 2 - (playerY - startY)) {
				endY++;
			}
		}

		if (endX - playerX < 10) {
			while (startX > 0 && playerX - startX < border * 2 - (endX - playerX)) {
				startX--;
			}
		}
		if (endY - playerY < 10) {
			while (startY > 0 && playerY - startY < border * 2 - (endY - playerY)) {
				startY--;
			}
		}

		tempMap = new Cell[endY - startY + 1][endX - startX + 1];
		int y = startY, x;
		for (int _y = 0; _y < tempMap.length; _y++) {
			x = startX;
			for (int _x = 0; _x < tempMap[_y].length; _x++) {
				tempMap[_y][_x] = map[y][x++];
			}
			y++;
		}
		tempMap[playerY - startY][playerX - startX] = Cell.PLAYER;

		/*
		Cell[][] tempMap = new Cell[this.size][this.size];

		for (int y = 0; y < this.map.length; y++) {
			for (int x = 0; x < this.map[y].length; x++) {
				tempMap[y][x] = this.map[y][x];
			}
		}
		tempMap[playerY][playerX] = Cell.PLAYER;
		*/

		return tempMap;
	}

	public Enemy getEnemy() {
		return this.enemy;
	}

	public void deleteEnemy() {
		this.map[enemy.getY()][enemy.getX()] = Cell.EMPTY;
		this.fog[enemy.getY()][enemy.getX()] = Cell.EMPTY;
		this.enemies.remove(this.enemy);
	}
}
