package pacman.entries.pacman.TreeSearch;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
import java.util.*;

/**
 * Created by Adnan on 10/19/16.
 */
public class DFS extends Controller<Constants.MOVE> {
    @Override
    public MOVE getMove(Game game, long timeDue) {
        int best = 0;
        MOVE bestmove = MOVE.NEUTRAL;
        MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex());
        for ( MOVE move: moves) {
            Game copyGame = game.copy();
            copyGame.advanceGame(move, new StarterGhosts().getMove());
            int value = 0;
            int counter = 0;
            value = dfsRecursive (copyGame, timeDue, counter, 0);
            if (value > best) {
                best = value;
                bestmove = move;
            }
        }
        return bestmove;
    }
    public int dfsRecursive(Game gameState, long timeDue, int counter, int limit) {
        limit++;
        int best = 0;
        int value= gameState.getScore();
        if (counter >= 99) { best = value; return best;}
        if (gameState.getPacmanNumberOfLivesRemaining() == 0) return -100;
        if (gameState.getNumberOfActivePills()==0) {
            value = gameState.getScore();
            return value;
        }
        MOVE[] moves = gameState.getPossibleMoves(gameState.getPacmanCurrentNodeIndex(), gameState.getPacmanLastMoveMade());
        for ( MOVE move: moves) {
            Game copyGame = gameState.copy();
            copyGame.advanceGame(move, new StarterGhosts().getMove(copyGame, timeDue));

                value =  dfsRecursive(copyGame, timeDue, counter+1, limit);
                if (value > best) best = value;
        }
        return best;
    }
}


