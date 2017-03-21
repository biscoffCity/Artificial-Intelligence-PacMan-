package pacman.entries.pacman.TreeSearch;
import java.util.*;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.MOVE;




/**
 * Created by Adnan on 10/24/16.
 */
public class Random extends Controller<Constants.MOVE> {
    ///RANDOM ALGO IS IN MyPacMAN

    @Override
    public MOVE getMove(Game game, long timeDue) {

           // MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex(),game.getPacmanLastMoveMade());
            //return moves[new Random().nextInt(moves.length)];
        return null;

    }
}

