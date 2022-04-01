package game;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private final long window;

    public static void InitProperties()
    {
        GLFWErrorCallback.createPrint(System.err).set(); //Set error callback to standart error output

        if ( !glfwInit() )  //Initialize GLFW thread
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    }

    public static void CloseProperties()
    {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public Window(int width, int height, String title)
    {
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); //V-sync On

        glfwShowWindow(window);

        GL.createCapabilities();
    }

    public void Destroy()
    {
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }


    public void BindKeyCallBack(org.lwjgl.glfw.GLFWKeyCallbackI cbfun )
    {
        glfwSetKeyCallback(window, cbfun);
    }

    public void BindResizeCallback(org.lwjgl.glfw.GLFWFramebufferSizeCallbackI cbfun)
    {
        glfwSetFramebufferSizeCallback(window, cbfun);
    }

    public void ProcessEvents()
    {
        glfwPollEvents();
    }

    public void SwapRenderBuffers()
    {
        //Swap buffer to window and clear
        glfwSwapBuffers(window);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    public boolean ShouldClose()
    {
        return glfwWindowShouldClose(window);
    }

    public static void framebuffer_resize(long window, int width, int height)
    {
        glViewport(0, 0, width, height);
    }
}