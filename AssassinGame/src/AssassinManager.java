
//Andrew  Yu
//CSE 143 BS TA:Raymond Webster Berry
//Homework 3 
//AssassinManager manages of game of assassin which tells
//you the state of the game including who's alive and dead as well 
//as at the end if someone has won the game 
import java.util.*;

//Pre: if names List is empty or null throw IllegalArgumentAcception 
//Post: Constructs an assassin manager object using a given list of 
//names, forming a killRing
//Parameters: List<String> names, a list of names to be put into the killRing
public class AssassinManager {

	// killRing is a field of type AssassinNode
	// that represents the front of the killRing (first node)
	private AssassinNode killRing;
	// graveyard is a field of type AssassinNode
	// that represents the front of the graveyard (first node)
	private AssassinNode graveyard;

	public AssassinManager(List<String> names) {
		if (names == null || names.isEmpty()) {
			throw new IllegalArgumentException();
		}
		AssassinNode playerList = new AssassinNode(names.get(names.size() - 1));
		killRing = playerList;
		for (int i = names.size() - 2; i > -1; i--) {
			killRing = new AssassinNode(names.get(i), killRing);
		}
	}

	// Post: prints the names of all people in the killRing one per a line
	public void printKillRing() {
		AssassinNode currentPlayer = killRing;
		while (currentPlayer.next != null) {
			System.out.println("    " + currentPlayer.name + " is stalking " + currentPlayer.next.name);
			currentPlayer = currentPlayer.next;
		}
		System.out.println("    " + currentPlayer.name + " is stalking " + killRing.name);
	}

	// Post: prints the names of all people in the graveyard one per a line in a
	// similar format
	public void printGraveyard() {
		AssassinNode currentPlayer = graveyard;
		while (currentPlayer != null) {
			System.out.println("    " + currentPlayer.name + " was killed by " + currentPlayer.killer);
			currentPlayer = currentPlayer.next;
		}
	}

	// Post: returns true if the given name is in the killRing
	// returns false if the given name is not in the kill Ring
	// Parameters: String name - the name to be checked if it's in the ring or not
	public boolean killRingContains(String name) {
		return nodeContains(name, killRing);
	}

	// Post: Determines whether a certain string is within any of a certain list's
	// nodes
	// returns the determination (true if contained, false if not)
	// Parameters: String name - the string being checked to see if it's contained
	// AssassinNode node - the node being checked to see if the string is in it or
	// not
	private boolean nodeContains(String name, AssassinNode node) {
		AssassinNode currentPlayer = node;
		while (currentPlayer != null) {
			if (currentPlayer.name.equalsIgnoreCase(name)) {
				return true;
			}
			currentPlayer = currentPlayer.next;
		}
		return false;
	}

	// Post: returns true if the given name is in the graveyard
	// returns false if the given name is not in the graveyard
	// Parameters: String name - the name to be checked if it's in the graveyard or
	// not
	public boolean graveyardContains(String name) {
		return nodeContains(name, graveyard);
	}

	// Post: returns true if there is only one person left in the kill ring
	// returns false if there are more than one player left in the killring
	public boolean gameOver() {
		return killRing.next == null;
	}

	// Post: returns the name of the last player in the killRing if
	// there is only one player, returns null if the game over method
	// is not true
	public String winner() {
		if (gameOver() != true) {
			return null;
		}
		return killRing.name;
	}

	// Pre: if the given name contained in the killRing Throw
	// IllegalArgumentException
	// if if the game is over throw an illegalStateException
	// Post: method to "kill" someone with
	// the given name (ignored case)
	// by removing them from their location in killRing
	// and adding them to the front of the graveyard
	public void kill(String name) {
		if (!killRingContains(name)) {
			throw new IllegalArgumentException();
		}
		if (gameOver()) {
			throw new IllegalStateException();
		}
		AssassinNode currentPlayer = killRing;
		AssassinNode killerNextNode = null;
		if (currentPlayer.name.equalsIgnoreCase(name)) {
			// if 1st element is killed
			String killer = getKillerFromEnd();
			killRing = currentPlayer.next;
			currentPlayer.next = graveyard;
			graveyard = currentPlayer;
			graveyard.killer = killer;
		} else {
			while (currentPlayer != null && currentPlayer.next != null) {
				if (currentPlayer.next.name.equalsIgnoreCase(name)) {
					killerNextNode = currentPlayer.next.next;
					currentPlayer.next.next = graveyard;
					currentPlayer.next.killer = currentPlayer.name;
					graveyard = currentPlayer.next;
					currentPlayer.next = killerNextNode;
				}
				currentPlayer = currentPlayer.next;
			}
		}
	}

	// Post: Returns a string that represents the person
	// in the last element of the list (for when first person is killed)
	private String getKillerFromEnd() {
		AssassinNode currentPlayer = killRing;
		while (currentPlayer != null) {
			if (currentPlayer.next == null) {
				return currentPlayer.name;
			}
			currentPlayer = currentPlayer.next;
		}
		return null;
	}
}
