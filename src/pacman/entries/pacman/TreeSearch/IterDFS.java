package pacman.entries.pacman.TreeSearch;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
import pacman.Executor;
import pacman.entries.pacman.TreeSearch.DFS;

import java.util.*;

/**
 * Created by Adnan on 10/25/16.
 */

public class IterDFS extends Controller<Constants.MOVE> {
    @Override

    public Constants.MOVE getMove(Game game, long timeDue) {
        int limit = 0;
        int best = 0;
        MOVE bestMove = MOVE.NEUTRAL;
        int depth = 0;
        for (MOVE m : game.getPossibleMoves(game.getPacmanCurrentNodeIndex(), game.getPacmanLastMoveMade())) {
            Game g = game.copy();
            g.advanceGame(m, Executor.getMove(g, timeDue));
            int value = 0;
            //int value = dfsRecursive(g, timeDue, depth, limit);
            //this above line of code ins't working even though I imprted it, but that's my logic
            if (value > best) {
                best = value;
                bestMove = m;
            }
        }
        limit++;
        return bestMove;

    }


}