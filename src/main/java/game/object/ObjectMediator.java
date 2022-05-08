package game.object;

import org.joml.Vector2f;

public interface ObjectMediator {

    void notify(GameObject obj, Vector2f position);

}
