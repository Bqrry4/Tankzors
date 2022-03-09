package game;

import com.sun.tools.javac.Main;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import javax.swing.*;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements Runnable {
    private Window MainWindow;

    public Game(int width, int height, String title)
    {
        Window.InitProperties();

        MainWindow = new Window(width, height, title);

        MainWindow.BindKeyCallBack(InputHandler::keyUpdate);

        GL.createCapabilities(); //Init OpenGL
    }

    @Override
    public void run()
    {

        while(!MainWindow.ShouldClose())
        {
            Update();

            Render();

            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
    }

    public void Clean()
    {
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void Update()
    {
        glfwPollEvents();
    }

    private void Render()
    {
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //Clear screen
        glClear(GL_COLOR_BUFFER_BIT);

        //Render stuff there

    }

}
