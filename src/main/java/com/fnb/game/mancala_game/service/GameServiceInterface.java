/**
 * 
 */
package com.fnb.game.mancala_game.service;

import com.fnb.game.mancala_game.model.Game;

/**
 * @author Ernest Mampana
 *
 */
public interface GameServiceInterface {
	
	Game initGame(Integer pitNumber);

    Game play(String gameId, Integer pitId);
}
