/**
 * 
 */
package com.fnb.game.mancala_game.service;

import org.springframework.stereotype.Service;

import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;
import com.fnb.game.mancala_game.repository.GameRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ernest Mampana
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameServiceInterface {

	private final GameRepository gameRepository;
	private final GameRuleSetter gameRuleSetter;

	@Override
	public Game initGame(Integer initialPitStoneCount) {
		return gameRepository.create(initialPitStoneCount);
	}

	@Override
	public Game play(String gameId, Integer pitIndex) {
		log.debug("gameId {}, pitIndex {}", gameId, pitIndex);

		Game game = gameRepository.findById(gameId);
		Pit pit = game.getBoard().getPitByPitIndex(pitIndex);

		gameRuleSetter.play(game, pit);
		return game;
	}

}
