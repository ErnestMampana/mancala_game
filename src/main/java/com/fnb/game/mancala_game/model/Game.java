/**
 * 
 */
package com.fnb.game.mancala_game.model;

import com.fnb.game.mancala_game.enums.GameStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ernest Mampana
 *
 */
@Data
@NoArgsConstructor
public class Game {
	private String id;
	private Board board;
	private Player player1;
	private Player player2;
	private Player winner;
	private GameStatus gameStatus;


	public Game(Integer initialStoneOnPit) {
		this.player1 = new Player(Player.PLAYER1_INDEX, "player1");
		this.player2 = new Player(Player.PLAYER2_INDEX, "player2");
		this.board = new Board(initialStoneOnPit, player1, player2);
		this.gameStatus = GameStatus.INITIALIZE_GAME;
	}

}
