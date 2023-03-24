package com.fnb.game.mancala_game.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fnb.game.mancala_game.enums.GameStatus;
import com.fnb.game.mancala_game.exception.MancalaException;
import com.fnb.game.mancala_game.exception.MancalaIllegalMoveException;
import com.fnb.game.mancala_game.model.Board;
import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRuleSetterTest {

	@Autowired
	private GameRuleSetter gameRuleSetter;

	@Test
	public void shouldStartWithOwnPit() {

		Game game = new Game(6);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(1));

		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
		Assert.assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());

	}

	@Test(expected = MancalaIllegalMoveException.class)
	public void shouldNotStartWithEmptyPit() {
		Game game = new Game(6);
		Pit pit = game.getBoard().getPits().get(2);
		pit.setStoneCount(0);
		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(2));

	}

	@Test(expected = MancalaIllegalMoveException.class)
	public void shouldNotStartWithOpponentPit() {

		Game game = new Game(6);
		game.setGameStatus(GameStatus.PLAYER2_TURN);
		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(2));
	}

	@Test
	public void shouldDistributeStoneFromPlayer2PitToPlayer1Pit() {

		Game game = new Game(6);
		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(12));

		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(12));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(13));
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(14));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(1));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(2));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(3));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(4));
		Assert.assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
	}

	@Test
	public void shouldDistributeStoneFromPlayer1PitToPlayer2Pit() {

		Game game = new Game(6);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(4));

		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getStoneCountByPitIndex(7));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(10));
		Assert.assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
		Assert.assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
	}

	@Test
	public void shouldDistribute13Stone() {

		Game game = new Game(6);
		game.getBoard().getPits().get(4).setStoneCount(13);
		game.getBoard().getPits().get(10).setStoneCount(10);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(4));

		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(4));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(5));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(6));
		Assert.assertEquals(Integer.valueOf(13), game.getBoard().getStoneCountByPitIndex(7));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(8));
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getStoneCountByPitIndex(9));
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(10));
		Assert.assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
		Assert.assertEquals(Integer.valueOf(13), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
	}

	@Test
	public void shouldIncreaseHouseStoneOnOwnEmptyPit() {
		Game game = new Game(6);
		Pit pit1 = game.getBoard().getPitByPitIndex(1);
		pit1.setStoneCount(2);

		Pit pit2 = game.getBoard().getPitByPitIndex(3);
		pit2.setStoneCount(0);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(1));

		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(1));
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(3));
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getStoneCountByPitIndex(11));
		Assert.assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
		Assert.assertEquals(Integer.valueOf(7), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
		Assert.assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
	}

	@Test
	public void shouldChangeGameToPlayerTurn1() {
		Game game = new Game(6);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(1));

		Assert.assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
	}

	@Test
	public void shouldChangeGameToPlayerTurn2() {
		Game game = new Game(6);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(2));

		Assert.assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
	}

	@Test
	public void shouldChangeGameToPlayerTurn2Again() {

		Game game = new Game(6);

		Pit pit = game.getBoard().getPits().get(8);
		pit.setStoneCount(6);
		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(8));

		// then
		Assert.assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
	}

	@Test
	public void shouldGameOver() {

		Game game = new Game(6);
		for (Integer key : game.getBoard().getPits().keySet()) {
			Pit pit = game.getBoard().getPits().get(key);
			if (!pit.isHouse()) {
				pit.setStoneCount(0);
			}
		}
		game.getBoard().getPits().get(6).setStoneCount(1);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(6));

		Assert.assertEquals(GameStatus.FINISHED, game.getGameStatus());
		Assert.assertEquals(game.getWinner(), game.getPlayer1());
	}

	@Test
	public void shouldPlayer1Win() {

		Game game = new Game(6);
		for (Integer key : game.getBoard().getPits().keySet()) {
			Pit pit = game.getBoard().getPits().get(key);
			if (!pit.isHouse()) {
				pit.setStoneCount(0);
			}
		}
		Pit lastPit = game.getBoard().getPits().get(6);
		lastPit.setStoneCount(1);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(6));

		Assert.assertEquals(GameStatus.FINISHED, game.getGameStatus());
		Assert.assertEquals(game.getWinner(), game.getPlayer1());
	}

	@Test
	public void shouldPlayer2Win() {

		Game game = new Game(6);
		for (Integer key : game.getBoard().getPits().keySet()) {
			Pit pit = game.getBoard().getPits().get(key);
			if (!pit.isHouse()) {
				pit.setStoneCount(0);
			}
		}
		game.getBoard().getPits().get(13).setStoneCount(1);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(13));

		Assert.assertEquals(GameStatus.FINISHED, game.getGameStatus());
		Assert.assertEquals(game.getWinner(), game.getPlayer2());
	}

	@Test
	public void shouldDraw() {

		Game game = new Game(6);
		for (Integer key : game.getBoard().getPits().keySet()) {
			Pit pit = game.getBoard().getPits().get(key);
			if (!pit.isHouse()) {
				pit.setStoneCount(0);
			}
		}
		game.getBoard().getPits().get(6).setStoneCount(1);
		game.getBoard().getPits().get(14).setStoneCount(1);

		gameRuleSetter.play(game, game.getBoard().getPitByPitIndex(6));

		Assert.assertEquals(GameStatus.FINISHED, game.getGameStatus());
		Assert.assertEquals(game.getWinner(), null);
	}

}
