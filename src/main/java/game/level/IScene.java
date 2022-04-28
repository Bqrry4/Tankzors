package game.level;

import game.layer.Layer;
import game.object.GameObject;

public interface IScene {

    void addObject(GameObject obj);
    void addLayer(Layer lay);
}
