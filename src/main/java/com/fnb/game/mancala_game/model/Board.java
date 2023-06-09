/**
 * 
 */
package com.fnb.game.mancala_game.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.fnb.game.mancala_game.exception.MancalaException;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ernest Mampana
 *
 */
@Data
@NoArgsConstructor
public class Board {
	public static final Integer PIT_START_INDEX = 1;
	public static final Integer PIT_END_INDEX = 14;
	public static final Integer PLAYER1_HOUSE = 7;
	public static final Integer PLAYER2_HOUSE = 14;
	public static final Integer INITIAL_STONE_ON_PIT = 6;
	public static final Integer INITIAL_STONE_ON_HOUSE = 0;

	private Map<Integer, Pit> pits;

	public Board(Integer initialStoneOnPit, Player player1, Player player2) {
		this.pits = initPit(initialStoneOnPit, player1, player2);
	}


	public Integer getStoneCountByPitIndex(Integer pitIndex) {
		return getPitByPitIndex(pitIndex).getStoneCount();
	}

	public Pit getPlayerHouse(Integer playerIndex) {
		if (playerIndex.equals(Player.PLAYER1_INDEX)) {
			return pits.get(Board.PLAYER1_HOUSE);
		} else if (playerIndex.equals(Player.PLAYER2_INDEX)) {
			return pits.get(Board.PLAYER2_HOUSE);
		}

		throw new MancalaException("playerIndex is not correct");
	}

	public Pit getPitByPitIndex(Integer pitIndex) {
		return pits.get(pitIndex);
	}

	public Pit getNextPit(Pit pit) {
		return pits.get(pit.nextPitIndex());
	}

	public Pit getOppositePit(Pit pit) {
		return pits.get(pit.getOppositePitIndex());
	}

	public Integer getPlayer1PitStoneCount() {
		Integer player1PitStoneCount = 0;
		for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
			player1PitStoneCount += this.getPits().get(i).getStoneCount();
		}
		return player1PitStoneCount;
	}

	public Integer getPlayer2PitStoneCount() {
		Integer player2PitStoneCount = 0;
		for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
			player2PitStoneCount += this.getPits().get(i).getStoneCount();
		}
		return player2PitStoneCount;
	}

	private Map<Integer, Pit> initPit(Integer initialStoneOnPit, Player player1, Player player2) {

		Map<Integer, Pit> pits = new ConcurrentHashMap<>();
		for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
			Pit pit = new Pit(i, initialStoneOnPit, player1.getPlayerIndex());
			pits.put(i, pit);
		}
		Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player1.getPlayerIndex());
		pits.put(Board.PLAYER1_HOUSE, house1);

		for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
			Pit pit = new Pit(i, initialStoneOnPit, player2.getPlayerIndex());
			pits.put(i, pit);
		}
		Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player2.getPlayerIndex());
		pits.put(Board.PLAYER2_HOUSE, house2);

		return pits;
	}
}
