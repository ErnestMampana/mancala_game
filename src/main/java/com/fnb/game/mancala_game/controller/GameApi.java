/**
 * 
 */
package com.fnb.game.mancala_game.controller;

import com.fnb.game.mancala_game.model.Game;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.fnb.game.mancala_game.model.Board;
import com.fnb.game.mancala_game.service.GameServiceImpl;
import com.fnb.game.mancala_game.exception.MancalaException;
import com.fnb.game.mancala_game.exception.MancalaIllegalMoveException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author Ernest Mampana
 *
 */
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/mancala")
public class GameApi {
	private final GameServiceImpl gameService;
    

    @PostMapping( "/games")
    public ResponseEntity initBoard(@RequestParam(name = "stone", defaultValue = "6", required = false) Integer numberOfStone){
        log.info("======================= : initializing the game");
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.initGame(numberOfStone));
    }


    @PutMapping("/games/{gameId}/pits/{pitIndex}")
    public ResponseEntity play(@PathVariable String gameId, @PathVariable Integer pitIndex){
        log.debug("From game {}, player {} is moving stone from pit {}",gameId, pitIndex);

        if(pitIndex > Board.PIT_END_INDEX || pitIndex < Board.PIT_START_INDEX){
            throw new MancalaException("Incorrect pit index");
        }

        if(pitIndex.equals(Board.PLAYER1_HOUSE) || pitIndex.equals(Board.PLAYER2_HOUSE)){
            throw new MancalaIllegalMoveException("House stone is not allow to distribute");
        }

        return ResponseEntity.ok().body(gameService.play(gameId, pitIndex));
    }


}
