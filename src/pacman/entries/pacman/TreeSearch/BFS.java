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

//
// THE CODE FOR RANDOM ALGORHTIM IS IN
// MyPacMan
//

class NODE
{
   public NODE(Game g, MOVE m)
   {this.gameState = g; this.movement = m;}
    private Game gameState;
    private MOVE movement;
    public ArrayList<NODE> children = new ArrayList<>();
    public Game getGame()
    {
        return  gameState;
    }
    public MOVE getMovement()
    {
        return  movement;
    }
}
public class BFS extends Controller<Constants.MOVE>{
    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        NODE n = new NODE(game, MOVE.NEUTRAL);
        MOVE[] movements = game.getPossibleMoves(game.getPacmanCurrentNodeIndex());
        Queue<NODE> queue = new LinkedList<NODE>();
        queue.add(n);
        NODE n1 = queue.peek();
        int counter = 100;
        if (n1.getGame().getNumberOfActivePills() == 0 && n1.getGame().getNumberOfPowerPills()==0) {
            return n1.getMovement();
        }

        //while (!queue.isEmpty()) {

            for (MOVE move:
                    movements)
            {
                Game copyGame = game.copy();
                copyGame.advanceGame(move, new StarterGhosts().getMove());
                NODE newNode = new NODE(n1.getGame(), move);
                queue.add(newNode);
                n1.children.add(newNode);
            }

            n1 = n1.children.get(0);
            counter -= 1;

            if (counter  == 100) {}
       // }
        return null;
    }
}
