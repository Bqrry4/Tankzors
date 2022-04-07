package game;

import Managers.ResourceManager;
import game.level.Scene;
import game.menu.Button;
import game.menu.Menu;
import game.menu.MenuComponent;
import game.menu.subMenu;
import gui.Text;
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
        MainWindow.BindResizeCallback(Window::framebuffer_resize);

/*
        Renderer.Instance().addMesh(new Mesh());
        Renderer.Instance().addShader(ResourceManager.Instance().LoadShader("assets/shaders/default.glsl", "default"));
        Renderer.Instance().addTexture(ResourceManager.Instance().LoadTexture("assets/map/ground.png", "ground")); //0
        Renderer.Instance().addTexture(ResourceManager.Instance().LoadTexture("assets/tanks/tanks.png", "tanks")); //1
        Renderer.Instance().addTexture(ResourceManager.Instance().LoadTexture("assets/map/walls.png", "walls")); //2*/

        ResourceManager.Instance().LoadShader("assets/shaders/default.glsl", "default");
        ResourceManager.Instance().LoadTexture("assets/map/ground.png", "ground"); //0
        ResourceManager.Instance().LoadTexture("assets/tanks/tanks.png", "tanks"); //1
        ResourceManager.Instance().LoadTexture("assets/map/walls.png", "walls");
        ResourceManager.Instance().LoadTexture("assets/tanks/33.jpg", "menu");
        ResourceManager.Instance().LoadFont("assets/gui/Baloo2-Medium.ttf", "font");

//        Renderer.Instance().InitBatch();

        Renderer.Instance().Init();


        scene = new Scene();
        scene.UpdateSize(width, height);
        scene.Load();


        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glClearColor(1f, 1f, 1f, 1.0f); //Clear screen
    }


    public void run()
    {
        //Menu instantiation
        subMenu MainMenu = new subMenu("MainMenu");

        Button Start = new Button("Start");
        subMenu Campaign = new subMenu("Campaign");
        Button Arcade = new Button("Arcade");
        subMenu Settings = new subMenu("Settings");
        Button Exit = new Button("Exit");

        MainMenu.add(Campaign);
        MainMenu.add(Arcade);
        MainMenu.add(Settings);
        MainMenu.add(Exit);

        Menu menu = new Menu(ResourceManager.Instance().GetTexture("menu"), ResourceManager.Instance().GetFont("font"));
        menu.SetMenuAttribute(MainMenu);
//        menu.setTrigger(true);


        Text text = new Text("Hello, World!", ResourceManager.Instance().GetFont("font"));
        text.setText("Hello, World!");


        //Game Loop
        while(!MainWindow.ShouldClose())
        {
            MainWindow.ProcessEvents();
            WindowTimer.Instance().Ticks();

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
//                scene.Process();
                text.render();
                Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"));
            }

            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
        Window.CloseProperties();

//        Renderer.Instance().Clear();
    }

}