package game;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFWErrorCallback;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class Window {
    private final long window;

    public Window(int width, int height, String title)
    {
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); //V-sync On

        glfwShowWindow(window);
    }

    public void Destroy()
    {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    public static void InitProperties()
    {
        GLFWErrorCallback.createPrint(System.err).set(); //Set error callback to standart error output

        if ( !glfwInit() )  //Initialize GLFW thread
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
    }


    public void BindKeyCallBack(@Nullable org.lwjgl.glfw.GLFWKeyCallbackI cbfun )
    {
        glfwSetKeyCallback(window, cbfun);
    }

    public void SwapRenderBuffers()
    {
        glfwSwapBuffers(window);
    }

    public boolean ShouldClose()
    {
        return glfwWindowShouldClose(window);
    }
}
