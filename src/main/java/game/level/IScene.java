package game.level;

import game.level.layer.Layer;
import game.object.GameObject;
import game.object.ObjectMediator;

public interface IScene {

    void addObject(GameObject obj);
    void addToBuffer(GameObject obj);
    void addLayer(Layer lay);

    ObjectMediator getMediator();
}
