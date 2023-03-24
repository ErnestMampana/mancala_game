/**
 * 
 */
package com.fnb.game.mancala_game.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fnb.game.mancala_game.exception.MancalaException;
import com.fnb.game.mancala_game.model.Game;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Ernest Mampana
 *
 */
@Slf4j
@Component
public class GameRepository {

	private static final Map<String, Game> gameMap = new ConcurrentHashMap<>();

	public Game create(Integer initialPitStoneCount) {
		String id = UUID.randomUUID().toString();
		Game game = new Game(initialPitStoneCount);
		game.setId(id);
		gameMap.put(id, game);
		return gameMap.get(id);
	}

	public Game findById(String id) {
		Game game = gameMap.get(id);
		if (game == null) {
			throw new MancalaException("Game is not found for the id: " + id);
		}
		return game;
	}

}
