/**
 * 
 */
package com.fnb.game.mancala_game.model;

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
public class Player {
	public static final Integer PLAYER1_INDEX = 1;
    public static final Integer PLAYER2_INDEX = 2;

    @Nonnull
    private Integer playerIndex;

    @Nonnull
    private String name;
}
