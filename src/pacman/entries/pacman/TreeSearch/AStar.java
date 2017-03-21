package pacman.entries.pacman.TreeSearch;
import pacman.controllers.Controller;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.Constants.MOVE;
import java.util.*;

/**
 * Created by Adnan on 10/25/16.
 */

class PathClass {
    Game game;
    int score;
    ArrayList<MOVE> myMove;
    MOVE originalMoves;

    PathClass(MOVE m, int s, Game g) {
        myMove = new ArrayList<MOVE>();
        myMove.add(m);
        originalMoves = m;
        score = s;
        game = g;
    }
    PathClass(ArrayList<MOVE> routes, MOVE om, MOVE m, int s, Game g) {
        myMove = new ArrayList<MOVE>(routes);
        originalMoves = om;
        myMove.add(m);
        score = s;
        game = g;
    }
}



public class AStar extends Controller<Constants.MOVE> {
    private Controller<EnumMap<Constants.GHOST,MOVE>> ghostController = new StarterGhosts();
    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {
        int theCurrent = game.getPacmanCurrentNodeIndex();


        for(Constants.GHOST ghost : Constants.GHOST.values())
            if(game.getGhostEdibleTime(ghost)==0 && game.getGhostLairTime(ghost)==0)
                if(game.getShortestPathDistance(theCurrent,game.getGhostCurrentNodeIndex(ghost))<5)
                    return game.getNextMoveAwayFromTarget(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost), Constants.DM.PATH);

        ArrayList<PathClass> theCourse = new ArrayList<>();
        int totalPills = game.getNumberOfPills() + game.getNumberOfPowerPills();
        int numberOfPillsCollected = game.getNumberOfActivePills() + game.getNumberOfActivePowerPills();
        int pillMultiplier = 2;
        int scoreMultiplier = 1;

        MOVE theBEST = MOVE.NEUTRAL;
        MOVE [] moves =  game.getPossibleMoves(theCurrent, game.getPacmanLastMoveMade());
        for(MOVE move: moves) {
            Game copiedState = game.copy();
            copiedState.advanceGame(move, ghostController.getMove(copiedState, timeDue));
            PathClass path = new PathClass(move, copiedState.getScore()*scoreMultiplier + (totalPills - numberOfPillsCollected)*pillMultiplier, copiedState);
            theCourse.add(path);
        }

        while(!theCourse.isEmpty()) {
            if(System.currentTimeMillis() >= timeDue)
                return theBEST;

            int best = 0;
            for(int i = 1; i < theCourse.size(); i++) {
                if (theCourse.get(i).score > theCourse.get(best).score){
                    best = i;
                }
            }

            PathClass currPath = theCourse.remove(best);
            theBEST = currPath.originalMoves;
            int nextCurrent = currPath.game.getPacmanCurrentNodeIndex();
            MOVE [] nextMoves =  currPath.game.getPossibleMoves(nextCurrent, currPath.game.getPacmanLastMoveMade());
            for(MOVE move: nextMoves) {
                Game copiedState = game.copy();
                copiedState.advanceGame(move, ghostController.getMove(copiedState, timeDue));
                PathClass path;
                int eaten = copiedState.getNumberOfActivePills() + copiedState.getNumberOfActivePowerPills();
                path = new PathClass(currPath.myMove, currPath.originalMoves, move, copiedState.getScore()*scoreMultiplier + (totalPills - eaten)*pillMultiplier, copiedState);
                theCourse.add(path);
            }

        }
        return theBEST;
    }


}