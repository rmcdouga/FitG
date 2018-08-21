package com.rogers.rmcdouga.fitg.actiondeck;

import java.util.Optional;

import org.teavm.flavour.templates.BindTemplate;
import org.teavm.flavour.widgets.ApplicationTemplate;

import com.rogers.rmcdouga.fitg.basegame.Action.EnvironType;
import com.rogers.rmcdouga.fitg.basegame.Game;

@BindTemplate("templates/client.html")
public class Client extends ApplicationTemplate {
    private String userName = "";
    private Game game = new Game();

    public static void main(String[] args) {
        Client client = new Client();
        client.bind("application-content");
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    private String Action = "";

	/**
	 * @return the action
	 */
	public String getAction() {
		return Action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		Action = action;
	}
    
	public String drawAction() {
		Optional<com.rogers.rmcdouga.fitg.basegame.Action> drawnAction = game.actionDeck().draw();
		if(drawnAction.isPresent()) {
			return drawnAction.get().getResultDescription(EnvironType.URBAN).getAsPlainString();
		}
		return "End of Deck";
	}
    
	public String resetAction() {
		game.actionDeck().reset();
		return "Deck has been reset.";
	}
    
}
