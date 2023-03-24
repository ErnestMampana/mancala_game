package com.fnb.game.mancala_game;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class MancalaGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(MancalaGameApplication.class, args);


	}

}
