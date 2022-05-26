package Managers;

import com.sun.jdi.PrimitiveValue;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;

public class InputHandler //
{
    //Binded only to one window

    private static boolean[] keyStateMap = new boolean[8];
    private static boolean[] keyActionMap = new boolean[8];


    public static void Update(long window)
    {
        keyActionMap[0] = (glfwGetKey(window, GLFW_KEY_UP) == GLFW_RELEASE && keyStateMap[0]);
        keyActionMap[1] = (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_RELEASE && keyStateMap[1]);
        keyActionMap[2] = (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_RELEASE && keyStateMap[2]);
        keyActionMap[3] = (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_RELEASE && keyStateMap[3]);
        keyActionMap[4] = (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_RELEASE && keyStateMap[4]);
        keyActionMap[5] = (glfwGetKey(window, GLFW_KEY_ENTER) == GLFW_RELEASE && keyStateMap[5]);
        keyActionMap[6] = (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_RELEASE && keyStateMap[6]);


        keyStateMap[0] = (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS);
        keyStateMap[1] = (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS);
        keyStateMap[2] = (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS);
        keyStateMap[3] = (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS);
        keyStateMap[4] = (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS);
        keyStateMap[5] = (glfwGetKey(window, GLFW_KEY_ENTER) == GLFW_PRESS);
        keyStateMap[6] = (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS);

    }


   public static void keyUpdate(long window, int key, int scancode, int action, int mods)
   {

//       switch (key) {
//           case GLFW_KEY_UP:
//               keyStateMap[0] = !(action == GLFW_RELEASE);
//               break;
//           case GLFW_KEY_DOWN:
//               keyStateMap[1] = !(action == GLFW_RELEASE);
//               break;
//           case GLFW_KEY_LEFT:
//               keyStateMap[2] = !(action == GLFW_RELEASE);
//               break;
//           case GLFW_KEY_RIGHT:
//               keyStateMap[3] = !(action == GLFW_RELEASE);
//               break;
//           case GLFW_KEY_SPACE:
//               keyStateMap[4] = !(action == GLFW_RELEASE);
//               break;
//       }
   }

    public static boolean keyState(int key)
    {
        switch (key)
        {
            case GLFW_KEY_UP:
                return keyStateMap[0];
            case GLFW_KEY_DOWN:
                return keyStateMap[1];
            case GLFW_KEY_LEFT:
                return keyStateMap[2];
            case GLFW_KEY_RIGHT:
                return keyStateMap[3];
            case GLFW_KEY_SPACE:
                return keyStateMap[4];
            case GLFW_KEY_ENTER:
                return keyStateMap[5];
            case GLFW_KEY_ESCAPE:
                return keyStateMap[6];
        }
        return false;
    }

    public static boolean keyAction(int key)
    {
        switch (key)
        {
            case GLFW_KEY_UP:
                return keyActionMap[0];
            case GLFW_KEY_DOWN:
                return keyActionMap[1];
            case GLFW_KEY_LEFT:
                return keyActionMap[2];
            case GLFW_KEY_RIGHT:
                return keyActionMap[3];
            case GLFW_KEY_SPACE:
                return keyActionMap[4];
            case GLFW_KEY_ENTER:
                return keyActionMap[5];
            case GLFW_KEY_ESCAPE:
                return keyActionMap[6];
        }
        return false;
    }
}
