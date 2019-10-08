package control;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import model.BoardModel;
import model.Dice;
import model.cards.Card;
import model.player.Player;

public class GameController {
	
	private List<ControllerObserver> observers = new ArrayList<>();
	private BoardModel bm;
	private Random randomValue;

	private String name1; 
	private String name2;
	private Stack<Card> selectedCard;
	
	
	private static GameController instance;
	
	private GameController() {
		bm = new BoardModel();
		randomValue = new Random();
		selectedCard = new Stack();
	}
	
	public synchronized static GameController getInstance(){
		if(instance == null) {
			instance = new GameController();
			return instance;
		}else {
			return instance; 
		}
	}
	
	public void attach(ControllerObserver obs) {
		observers.add(obs);
	}
	public void detach(ControllerObserver obs) {
		observers.remove(obs);
	}
	
	
	/* ----------- DICES -----------*/	
	public void addDices() {
		for (int i = 0; i < 7; i++) {
			Dice newDice = createNewDice();
			bm.addDice(newDice);
		}
		notifyAddedDices(bm.getDiceList());
	}
	
	public void rollDices() {
		for (Dice dice : bm.getDiceList()) {
			dice.setValue(randomValue.nextInt(6));
			notifyRolledDices(dice.getValue());
		}
			
	}
	
	public void removeDice(int index) {
		bm.removeDice(index);
		notifyRemovedDice(index);
	}
	
	public Dice createNewDice() {
		Dice d = new Dice();
		return d;
	}
	/* -----------------------------*/
	
	
	
	/* ----------- 	PLAYERS -----------*/
	public void addPlayers(String name1, String name2) {
		Player p1 = new Player(name1);
		bm.addPlayer(p1);
		Player p2 = new Player(name2);
		bm.addPlayer(p2);
				
		notifyAddedPlayers(p1.getName(), p2.getName());
	}
	
	/* --------------------------------*/
	
	
	
	/* ----------- CARDS -----------*/
	public Card getCard(int index) {
		return bm.getCard(index);
	}
	
	public void addToSelectedCard(Card c) {
		selectedCard.push(c);
	}
	
	public void removeSelectedCard() {
		selectedCard.pop();
	}

	public void addCards() {
		
	}

	public void createCards() {
		
	}
	/* -----------------------------*/
	
	
	
	public void notifyRolledDices(String value) {
		for (ControllerObserver cO : observers) {
			cO.rolledDices(value);
		}
	}
	
	public void notifyAddedDices(List<Dice> dices) {
		for (ControllerObserver cO : observers) {
			cO.addedDices(dices);
		}
	}
	
	public void notifyRemovedDice(int index) {
		for (ControllerObserver cO : observers) {
			cO.removedDice(index);
		}
	}
	
	public void notifyAddedPlayers(String n1, String n2) {
		for (ControllerObserver cO : observers) {
			cO.addedPlayers(n1, n2);
			
		}
	}

	
	//teste:
	public void showDices() {
		int i = 1;
		for (Dice d : bm.getDiceList()) {
			System.out.println(i++ + " " + d.getValue());
		}
		
	}
	
	
}

