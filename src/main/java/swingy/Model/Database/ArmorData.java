package swingy.Model.Database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
class ArmorData {
	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	String name;
	int rarity, attack, defence, hp;
}
