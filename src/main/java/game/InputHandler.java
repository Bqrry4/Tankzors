package game;

import com.sun.jdi.PrimitiveValue;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler //
{
    private static char keyMap;

    public static void keyUpdate(long window, int key, int scancode, int action, int mods)
    {
        if(action == GLFW_PRESS) {
            switch (key) {
                case GLFW_KEY_UP:
                    keyMap = (char) (keyMap | 1);
                    break;
                case GLFW_KEY_DOWN:
                    keyMap = (char) (keyMap | 2);
                    break;
                case GLFW_KEY_LEFT:
                    keyMap = (char) (keyMap | 4);
                    break;
                case GLFW_KEY_RIGHT:
                    keyMap = (char) (keyMap | 8);
                    break;
            }
            return;
        }
        if(action == GLFW_RELEASE)
        {
            switch (key)
            {
                case GLFW_KEY_UP:
                    keyMap = (char) (keyMap & 14);
                    break;
                case GLFW_KEY_DOWN:
                    keyMap = (char) (keyMap & 13);
                    break;
                case GLFW_KEY_LEFT:
                    keyMap = (char) (keyMap & 11);
                    break;
                case GLFW_KEY_RIGHT:
                    keyMap = (char) (keyMap & 7);
                    break;
            }
        }
    }

    public static boolean keyState(int key)
    {
        switch (key)
        {
            case GLFW_KEY_UP:
                return (keyMap & 1) != 0;
            case GLFW_KEY_DOWN:
                return (keyMap & 2) != 0;
            case GLFW_KEY_LEFT:
                return (keyMap & 4) != 0;
            case GLFW_KEY_RIGHT:
                return (keyMap & 8) != 0;
        }
        return false;
    }
}
