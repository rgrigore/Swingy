package swingy.View;

import swingy.Model.Enemies.Enemy;
import swingy.Model.Map.Cell;

public interface IView {
	void showStartMenu();
	void showNewCharacter();
	void showLoadCharacter();
	void setMessage(String message);
	void setCombatMessage(String combatMessage);
	void showLoadingScreen();
	void showNavigationScreen();
	void showEncounterScreen();
	void showCombatScreen();
	void showLootScreen();
}
