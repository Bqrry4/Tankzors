package auxiliar.PathFinding;

import org.joml.Vector2f;
import org.joml.Vector2i;

import java.util.List;

//An abstraction for pathFinders
public interface PathFinder {

    List<Vector2i> FindPath(Vector2f from, Vector2f to);


}
