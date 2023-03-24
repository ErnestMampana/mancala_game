/**
 * 
 */
package com.fnb.game.mancala_game.rule;

import com.fnb.game.mancala_game.enums.GameStatus;
import com.fnb.game.mancala_game.exception.MancalaIllegalMoveException;
import com.fnb.game.mancala_game.model.Board;
import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;
import com.fnb.game.mancala_game.model.Player;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ernest Mampana
 *
 */
@Slf4j
public class StartPitRule extends MancalaRule {
	@Override
	public void apply(Game game, Pit startPit) {
		log.debug("check rules for the start pit {}", startPit);

		checkPlayerTurnRule(game, startPit);
		checkEmptyStartRule(startPit);
		this.next.apply(game, startPit);
	}

	private void checkPlayerTurnRule(Game game, Pit startPit) {

		if (game.getGameStatus().equals(GameStatus.INITIALIZE_GAME)) {
			GameStatus gameStatus = startPit.getPlayerIndex().equals(Player.PLAYER1_INDEX) ? GameStatus.PLAYER1_TURN
					: GameStatus.PLAYER2_TURN;
			game.setGameStatus(gameStatus);
		}

		if ((game.getGameStatus().equals(GameStatus.PLAYER1_TURN) && startPit.getPitIndex() >= Board.PLAYER1_HOUSE)
				|| (game.getGameStatus().equals(GameStatus.PLAYER2_TURN)
						&& startPit.getPitIndex() <= Board.PLAYER1_HOUSE)) {
			throw new MancalaIllegalMoveException("Incorrect pit to play");
		}
	}

	private void checkEmptyStartRule(Pit startPit) {

		if (startPit.getStoneCount() == 0) {
			throw new MancalaIllegalMoveException("Can not start from empty pit");
		}
	}
}
