package game.object;

import org.joml.Vector2f;

public interface ObjectMediator {

    void notifyCurrent(GameObject obj);
    boolean notifyDesired(GameObject obj, Vector2f position);

}
