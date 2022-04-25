package game;

import Managers.ResourceManager;
import Managers.Settings;
import com.sun.tools.javac.Main;
import game.level.Scene;
import game.menu.Button;
import game.menu.Menu;
import game.menu.subMenu;
import gui.Text;
import Managers.Renderer;

import static org.lwjgl.opengl.GL30.*;

public class Game {
    private final Window MainWindow;

    Scene scene;

    public Game(int width, int height, String title)
    {
        Window.InitProperties();

        MainWindow = new Window(width, height, title);
        MainWindow.BindKeyCallBack(InputHandler::keyUpdate);
        MainWindow.BindResizeCallback(Window::framebuffer_resize);


        ResourceManager.Instance().LoadShader("assets/shaders/default.glsl", "default");
        ResourceManager.Instance().LoadShader("assets/shaders/font.glsl", "font");

        int[] id = {0, 1, 2, 3, 4, 5, 6, 7};

        ResourceManager.Instance().GetShader("default").SetUniform("u_texture", id);


        ResourceManager.Instance().LoadTexture("assets/tanks/tanks.png", "tanks");
        ResourceManager.Instance().LoadTexture("assets/map/ground.png", "ground");
        ResourceManager.Instance().LoadTexture("assets/map/walls.png", "walls");
        ResourceManager.Instance().LoadTexture("assets/bullets.png", "bullets");
        ResourceManager.Instance().LoadTexture("assets/gui/menu_pict.png", "menu");

        ResourceManager.Instance().GetTexture("tanks").BindByOrder();
        ResourceManager.Instance().GetTexture("ground").BindByOrder();
        ResourceManager.Instance().GetTexture("walls").BindByOrder();
        ResourceManager.Instance().GetTexture("bullets").BindByOrder();
        ResourceManager.Instance().GetTexture("menu").BindByOrder();



//        ResourceManager.Instance().LoadFont("assets/gui/Baloo2-Medium.ttf", "font");


        Renderer.Instance().Init();


        scene = new Scene();
        scene.UpdateSize(width, height);
        scene.Load();

        //Activate blend mode
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Clear screen color
        glClearColor(1f, 1f, 1f, 1.0f);
    }


    public void run()
    {
        //Menu instantiation
/*        subMenu MainMenu = new subMenu("MainMenu");

        Button Start = new Button(new Vector4f((float) Settings.getWidth()/2 - 70, 50, 140, 40), new Text("Start", ResourceManager.Instance().GetFont("font")));
        Button Exit = new Button(new Vector4f((float) Settings.getWidth()/2 - 70, 150, 140, 40), new Text("EXIT", ResourceManager.Instance().GetFont("font")));

        MainMenu.add(Start);
        MainMenu.add(Exit);

        Menu menu = new Menu(ResourceManager.Instance().GetTexture("menu"));
        menu.SetMenuAttribute(MainMenu);
//        menu.setTrigger(true);*/


        //Game Loop
        while(!MainWindow.ShouldClose())
        {
            MainWindow.ProcessEvents();
            WindowTimer.Instance().Ticks();


            if(/*menu.isTriggered()*/false)
            {
//                menu.Process();
            }
            else
            {
                scene.Process();
            }

            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
        Window.CloseProperties();

        ResourceManager.Instance().Free();

      //  Renderer.Instance().Clear();
    }

}