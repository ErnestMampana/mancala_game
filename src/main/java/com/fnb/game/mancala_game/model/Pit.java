/**
 * 
 */
package com.fnb.game.mancala_game.model;

import com.fnb.game.mancala_game.enums.GameStatus;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ernest Mampana
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pit {

	@Nonnull
	private Integer pitIndex;

	@Nonnull
	private Integer stoneCount;

	@Nonnull
	private Integer playerIndex;

	public Boolean isDistributable(GameStatus gameStatus) {

		return (!gameStatus.equals(GameStatus.PLAYER1_TURN) || !this.pitIndex.equals(Board.PLAYER2_HOUSE))
				&& (!gameStatus.equals(GameStatus.PLAYER2_TURN) || !this.pitIndex.equals(Board.PLAYER1_HOUSE));
	}

	public Boolean isPlayerPit(GameStatus gameStatus) {

		if (gameStatus.equals(GameStatus.PLAYER1_TURN) && this.playerIndex.equals(Player.PLAYER1_INDEX)) {
			return true;
		} else if (gameStatus.equals(GameStatus.PLAYER2_TURN) && this.playerIndex.equals(Player.PLAYER2_INDEX)) {
			return true;
		}

		return false;
	}

	public Boolean isHouse() {
		return this.pitIndex.equals(Board.PLAYER1_HOUSE) || this.pitIndex.equals(Board.PLAYER2_HOUSE);
	}

	public Integer nextPitIndex() {
		Integer index = this.pitIndex + 1;
		if (index > Board.PLAYER2_HOUSE) {
			index = 1;
		}
		return index;
	}

	public Boolean isPlayer1House() {
		return this.playerIndex.equals(Player.PLAYER1_INDEX) && this.pitIndex.equals(Board.PLAYER1_HOUSE);

	}

	public Boolean isPlayer2House() {
		return this.playerIndex.equals(Player.PLAYER2_INDEX) && this.pitIndex.equals(Board.PLAYER2_HOUSE);
	}

	public Integer getOppositePitIndex() {
		return (Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - this.getPitIndex();
	}
}
