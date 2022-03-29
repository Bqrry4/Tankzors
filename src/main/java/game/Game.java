package game;

import game.level.Scene;
import game.menu.Menu;
import renderer.Mesh;
import Managers.Renderer;
import renderer.Shader;
import renderer.Texture;

import static org.lwjgl.opengl.GL30.*;

public class Game {
    private final Window MainWindow;

    Scene scene;

    public Game(int width, int height, String title)
    {
        Window.InitProperties();

        MainWindow = new Window(width, height, title);

        MainWindow.BindKeyCallBack(InputHandler::keyUpdate);
        MainWindow.BindResizeCallback(Renderer::framebuffer_resize);

        Renderer.Instance().addMesh(new Mesh());
        Renderer.Instance().addShader(new Shader("assets/shaders/default.glsl"));
        Renderer.Instance().addShader(new Shader("assets/shaders/default2.glsl"));
        Renderer.Instance().addTexture(new Texture("assets/map/ground.png")); //0
        Renderer.Instance().addTexture(new Texture("assets/tanks/tanks.png")); //1

        Renderer.Instance().InitBatch();
        scene = new Scene();
        scene.UpdateSize(width, height);
        scene.Load();


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(1f, 1f, 1f, 1.0f); //Clear screen


    }


    public void run()
    {

        Menu menu = new Menu();




        while(!MainWindow.ShouldClose())
        {
            MainWindow.ProcessEvents();


/*            DefaultShader.SetUniform1f("u_texture", 0);
            mesh.Bind();
            tex1.Bind(0);
            mesh.render();*/


            if(menu.isTriggered())
            {
                menu.Process();
            }
            else
            {
                scene.Process();
            }

            glBindVertexArray(0);

            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
        Window.CloseProperties();
    }

}