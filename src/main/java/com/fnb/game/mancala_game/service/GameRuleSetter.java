/**
 * 
 */
package com.fnb.game.mancala_game.service;

import org.springframework.stereotype.Component;

import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;
import com.fnb.game.mancala_game.rule.DistributePitStoneRule;
import com.fnb.game.mancala_game.rule.EndPitRule;
import com.fnb.game.mancala_game.rule.GameOver;
import com.fnb.game.mancala_game.rule.MancalaRule;
import com.fnb.game.mancala_game.rule.StartPitRule;

/**
 * @author Ernest Mampana
 *
 */
@Component
public class GameRuleSetter {
	private final MancalaRule chain;

	public GameRuleSetter() {
		this.chain = new StartPitRule();
		chain.setNext(new DistributePitStoneRule()).setNext(new EndPitRule()).setNext(new GameOver());
	}

	public void play(Game game, Pit pit) {
		this.chain.apply(game, pit);
	}
}
