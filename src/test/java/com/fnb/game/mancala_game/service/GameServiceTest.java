package com.fnb.game.mancala_game.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fnb.game.mancala_game.enums.GameStatus;
import com.fnb.game.mancala_game.model.Board;
import com.fnb.game.mancala_game.model.Game;
import com.fnb.game.mancala_game.model.Pit;
import com.fnb.game.mancala_game.model.Player;
import com.fnb.game.mancala_game.repository.GameRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceTest {

    @MockBean
    private GameRepository gameRepository;

    @Autowired
    private GameServiceInterface gameService;

    @Test
    public void shouldInitGame(){

        Player player1 = new Player(Player.PLAYER1_INDEX, "Player 1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "Player 2");

        Board board = new Board();
        board.setPits(initPit());

        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setId(UUID.randomUUID().toString());
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

        Game mockGame = gameService.initGame(6);

        Assert.assertEquals(game, mockGame);

    }


    @Test
    public void shouldPlayGame(){

        Player player1 = new Player(Player.PLAYER1_INDEX, "Player 1");
        Player player2 = new Player(Player.PLAYER2_INDEX, "Player 2");

        Board board = new Board();
        board.setPits(initPit());

        String id = UUID.randomUUID().toString();
        Game game = new Game(Board.INITIAL_STONE_ON_PIT);
        game.setGameStatus(GameStatus.INITIALIZE_GAME);
        game.setId(id);
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(board);

        BDDMockito.given(gameRepository.findById(id)).willReturn(game);

        game.setGameStatus(GameStatus.PLAYER1_TURN);
        BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

        Game mockGame =  gameService.play(game.getId(), game.getBoard().getPits().get(1).getPitIndex());

        Assert.assertEquals(GameStatus.PLAYER1_TURN, mockGame.getGameStatus());
    }


    private Map<Integer, Pit> initPit(){
        Map<Integer, Pit> pits = new HashMap<>();
        for(int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++){
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER1_INDEX);
            pits.put(i, pit);
        }
        Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER1_INDEX);
        pits.put(Board.PLAYER1_HOUSE, house1);

        for(int i= Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++){
            Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER2_INDEX);
            pits.put(i, pit);
        }
        Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER2_INDEX);
        pits.put(Board.PLAYER2_HOUSE, house2);

        return pits;
    }


}
