package game.Controller;

import org.joml.Vector2f;

public interface Entity {
    void Process();

    Vector2f GetTargetPosition();
    int GetFractionID();

    boolean OutOfScope();
    //Maybe a function to verify if it is outofscope
}
