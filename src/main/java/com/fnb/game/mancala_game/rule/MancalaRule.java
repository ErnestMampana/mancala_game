/**
 * 
 */
package com.fnb.game.mancala_game.rule;

import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;

/**
 * @author Ernest Mampana
 *
 */
public abstract class MancalaRule {
	protected MancalaRule next;
    public abstract void apply(Game game, Pit currentPit);

    public MancalaRule setNext(MancalaRule next) {
        this.next = next;
        return next;
    }
}
