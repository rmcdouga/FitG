package io.github.rmcdouga.fitg.tui4jviewer.view;

import com.williamcallahan.tui4j.compat.bubbletea.Command;
import com.williamcallahan.tui4j.compat.bubbletea.Message;
import com.williamcallahan.tui4j.compat.bubbletea.Model;
import com.williamcallahan.tui4j.compat.bubbletea.UpdateResult;
import com.williamcallahan.tui4j.compat.bubbletea.message.KeyPressMessage;
import com.williamcallahan.tui4j.compat.bubbletea.message.QuitMessage;

public class MainView  implements Model {

	@Override
	public Command init() {
		// Don't need to do anything special on init.
		return null;
	}

	@Override
	public UpdateResult<? extends Model> update(Message msg) {
        if (msg instanceof KeyPressMessage keyPressMessage) {
            return switch (keyPressMessage.key()) {
                case "q", "Q" -> UpdateResult.from(this, QuitMessage::new);
                default -> UpdateResult.from(this);
            };
        }

        // do nothing for other messages
        return UpdateResult.from(this);
	}

	@Override
	public String view() {
		return "This is a static TUI4J view. (placeholder)";
	}

}
