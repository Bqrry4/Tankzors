package game;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements Runnable {
    private long window;


    public Game(int width, int height, String title)
    {


        GLFWErrorCallback.createPrint(System.err).set();

        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, InputHandler::keyUpdate);

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1); //V-sync On

        glfwShowWindow(window);
        GL.createCapabilities();
    }

    @Override
    public void run()
    {
        while(!glfwWindowShouldClose(window))
        {
            glfwPollEvents();

            glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if(InputHandler.keyState(GLFW_KEY_UP))
                System.out.println("SS");

            glfwSwapBuffers(window);
        }

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void update()
    {

    }

    private void render()
    {

    }

}
