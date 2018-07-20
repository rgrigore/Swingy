package swingy.Model.Database;

import swingy.Model.Characters.AvailableClasses;
import swingy.Model.Characters.BasePlayer;
import swingy.Model.Characters.Player;
import swingy.Model.Items.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Database {
	private static Database ourInstance = new Database();
	public static Database getInstance() {
		return ourInstance;
	}

	private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("main-persistence-unit");
	private EntityManager entityManager = entityManagerFactory.createEntityManager();

	private List<PlayerData> players;
	private List<WeaponData> weapons;
	private List<ArmorData> armors;
	private List<HelmetData> helmets;

	private PlayerData currentPlayer;


	private Database() {
		loadPlayers();
		loadWeapons();
		loadArmors();
		loadHelmets();
	}

	private void loadPlayers() {
		this.players = entityManager.createQuery("SELECT e FROM PlayerData e", PlayerData.class).getResultList();
	}

	private void loadWeapons() {
		this.weapons = entityManager.createQuery("SELECT e FROM WeaponData e", WeaponData.class).getResultList();
	}

	private void loadArmors() {
		this.armors = entityManager.createQuery("SELECT e FROM ArmorData e", ArmorData.class).getResultList();
	}

	private void loadHelmets() {
		this.helmets = entityManager.createQuery("SELECT e FROM HelmetData e", HelmetData.class).getResultList();
	}


	public List<PlayerData> getPlayers() {
		return players;
	}

	public List<WeaponData> getWeapons() {
		return weapons;
	}

	public List<ArmorData> getArmors() {
		return armors;
	}

	public List<HelmetData> getHelmets() {
		return helmets;
	}


	public void newPlayer(Player player) {
		this.currentPlayer = new PlayerData();

		this.currentPlayer.name = player.getName();
		this.currentPlayer.role = player.getRole().toString();
		this.currentPlayer.level = player.getLevel();
		this.currentPlayer.exp = player.getExp();
		this.currentPlayer.attack = player.getBaseAttack();
		this.currentPlayer.defence = player.getBaseDefence();
		this.currentPlayer.maxHp = player.getBaseHp();
		this.currentPlayer.ascendedFloors = player.getAscendedFloors();

		entityManager.getTransaction().begin();
		entityManager.persist(this.currentPlayer);
		entityManager.getTransaction().commit();

		loadPlayers();
	}

	public Player loadPlayer(PlayerData playerData) {
		this.currentPlayer = playerData;
		Player player = new BasePlayer.Builder().load().setName(playerData.getName()).setRole(AvailableClasses.valueOf(playerData.getRole())).setLevel(playerData.getLevel()).setExp(playerData.getExp()).setAttack(playerData.getAttack()).setDefence(playerData.getDefence()).setHp(playerData.getMaxHp()).setAscendedFloors(playerData.getAscendedFloors()).build(); // Todo Add the equipment

		// Temporary solution
		player.equipItem(new Item(1, playerData.getWeaponName(), "Weapon", 1, playerData.getWeaponAttack(), playerData.getWeaponDefence(), playerData.getWeaponHealth()));
		player.equipItem(new Item(1, playerData.getChestName(), "Chest", 1, playerData.getChestAttack(), playerData.getChestDefence(), playerData.getChestHealth()));
		player.equipItem(new Item(1, playerData.getHelmetName(), "Helmet", 1, playerData.getHelmetAttack(), playerData.getHelmetDefence(), playerData.getHelmetHealth()));
		return player;
	}

	public Player resetPlayer() {
		return new BasePlayer.Builder().load().setName(this.currentPlayer.getName()).setRole(AvailableClasses.valueOf(this.currentPlayer.getRole())).setLevel(this.currentPlayer.getLevel()).setExp(this.currentPlayer.getExp()).setAttack(this.currentPlayer.getAttack()).setDefence(this.currentPlayer.getDefence()).setHp(this.currentPlayer.getMaxHp()).setAscendedFloors(this.currentPlayer.getAscendedFloors()).build(); // Todo Add the equipment
	}

	public void savePlayer(Player player) {
		this.currentPlayer.level = player.getLevel();
		this.currentPlayer.exp = player.getExp();
		this.currentPlayer.attack = player.getBaseAttack();
		this.currentPlayer.defence = player.getBaseDefence();
		this.currentPlayer.maxHp = player.getBaseHp();
		this.currentPlayer.ascendedFloors = player.getAscendedFloors();


		// Temporary
		this.currentPlayer.weaponName = player.getWeapon().getName();
		this.currentPlayer.chestName = player.getChest().getName();
		this.currentPlayer.helmetName = player.getHelmet().getName();

		this.currentPlayer.weaponAttack = player.getWeapon().getAttack();
		this.currentPlayer.weaponDefence = player.getWeapon().getDefence();
		this.currentPlayer.weaponHealth = player.getWeapon().getHp();

		this.currentPlayer.chestAttack = player.getChest().getAttack();
		this.currentPlayer.chestDefence = player.getChest().getDefence();
		this.currentPlayer.chestHealth = player.getChest().getHp();

		this.currentPlayer.helmetAttack = player.getHelmet().getAttack();
		this.currentPlayer.helmetDefence = player.getHelmet().getDefence();
		this.currentPlayer.helmetHealth = player.getHelmet().getHp();



		entityManager.getTransaction().begin();
		entityManager.getTransaction().commit();

		loadPlayers();
	}

	public void deletePlayer(PlayerData player) {
		entityManager.remove(entityManager.find(PlayerData.class, player.getId()));
		entityManager.getTransaction().begin();
		entityManager.getTransaction().commit();
		loadPlayers();
	}


	public Item loadWeapon(int weaponId) {
		// Todo
		return new Item(1,"placeholder", "weapon", 0, 0,0,0);
	}

	public Item loadArmor(int armorId) {
		// Todo
		return new Item(1, "placeholder", "armor", 0, 0,0,0);
	}

	public Item loadHelmet(int helmetId) {
		// Todo
		return new Item(1, "placeholder", "helmet", 0, 0,0,0);
	}

	public void close() {
		this.entityManager.close();
		this.entityManagerFactory.close();
	}
}
