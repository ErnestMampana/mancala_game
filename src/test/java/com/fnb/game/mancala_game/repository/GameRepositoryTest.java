package com.fnb.game.mancala_game.repository;



import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fnb.game.mancala_game.model.Game;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository  gameMemoryRepository;

    @Test
    public void shouldCreateGame(){

        //given
        Game game1 = gameMemoryRepository.create(6);

        //when
        Game game = gameMemoryRepository.findById(game1.getId());

        //assert
        Assert.assertNotNull(game);
        Assert.assertEquals(game1, game1);
    }
}
