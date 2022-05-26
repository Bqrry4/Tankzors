package game;

import Managers.*;
import auxiliar.TextureRegion;
import exceptions.ExitFromMenuEvent;
import exceptions.NotImplementedLevel;
import game.level.Level;
import game.level.LevelLoader;
import game.menu.Button;
import game.menu.Menu;
import game.menu.MenuComponent;
import game.menu.subMenu;
import game.object.Entity.tank.TankFactory;
import game.object.Munition.MunitionFactory;
import gui.Text;
import org.joml.Vector4f;
import renderer.TextureMap;

import static org.lwjgl.opengl.GL30.*;

//TO DO EXCEPTION PENTRU FONT


public class Game {

    public static boolean InGame = true;

    private final Window MainWindow;

    Level level;

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
        ResourceManager.Instance().LoadTexture("assets/gui/menu_back.png", "menu");
        ResourceManager.Instance().LoadTexture("assets/gui/gui_elem.png", "gui");


        ResourceManager.Instance().LoadFont("assets/gui/Baloo2-Medium.ttf", "font");


        ResourceManager.Instance().GetTexture("tanks").BindByOrder();
        ResourceManager.Instance().GetTexture("ground").BindByOrder();
        ResourceManager.Instance().GetTexture("walls").BindByOrder();
        ResourceManager.Instance().GetTexture("bullets").BindByOrder();
        ResourceManager.Instance().GetTexture("menu").BindByOrder();
        ResourceManager.Instance().GetTexture("gui").BindByOrder();


        Renderer.Instance().Init();


        level = new Level();
//        level.UpdateSize(width, height);
//        level.Load();

        //Activate blend mode
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        //Clear screen color
        glClearColor(1f, 1f, 1f, 1.0f);




    }


    public void run()
    {
        //Load info from DataBase
        DataManager.Read("assets/db/info.db");

        //TextureAtlases init
        //Initializing the textureMaps and assign those to factories
        TextureMap textureMap = new TextureMap();
        textureMap.setTexture(ResourceManager.Instance().GetTexture("tanks"));

        TextureRegion[] Indices = {
                new TextureRegion(0, 0, 384, 24, 16),
                new TextureRegion(0, 24, 384, 24,16),
                new TextureRegion(0, 48, 384, 24, 16),
                new TextureRegion(0, 72, 384, 24, 16),
                new TextureRegion(0, 96, 352, 26, 16),

                new TextureRegion(0, 122, 168, 28, 6),

                new TextureRegion(0, 150, 288, 4, 12),
                new TextureRegion(0, 154, 288, 4, 12),
                new TextureRegion(0, 158, 288, 4, 12),
        };
        textureMap.setIndices(Indices);

        TankFactory.SetTextureMap(textureMap);

        TextureMap textureMapB = new TextureMap();
        textureMapB.setTexture(ResourceManager.Instance().GetTexture("bullets"));

        TextureRegion[] IndicesB = {
                new TextureRegion(0, 0, 24, 24, 1),
                new TextureRegion(24, 0, 24, 24,1),
                new TextureRegion(48, 0, 24, 24, 1),
                new TextureRegion(72, 0, 24, 24, 1),
                new TextureRegion(96, 0, 24, 24, 1),
                new TextureRegion(120, 0, 24, 24, 1),

                new TextureRegion(0, 24, 96, 24, 4),

                new TextureRegion(0, 48, 255, 15, 17),
                new TextureRegion(0, 63, 195, 15, 13),
                new TextureRegion(0, 78, 980, 35, 28),
        };
        textureMapB.setIndices(IndicesB);

        MunitionFactory.SetTextureMap(textureMapB);

        TextureRegion region = new TextureRegion(0,0,126,16,1);



        //Menu instantiation block

        Menu menu = new Menu(ResourceManager.Instance().GetTexture("menu"));

        subMenu MainMenu = new subMenu("MainMenu");
        MainMenu.IsRoot(true);



        MenuComponent ContinueGame = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 320, 250, 40), new Text("Continue", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {

                try {
                    if(!LevelLoader.LevelLoaded)
                        LevelLoader.Load(level);
                }
                catch (NotImplementedLevel e)
                {
                    System.out.println("Invalid level ID");
                    InGame = false;
                }


                throw new ExitFromMenuEvent();
            }
        };



        MenuComponent NewGame = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 395, 250, 40), new Text("NewGame", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region )
        {
            @Override
            public void functionality() throws ExitFromMenuEvent {
                LevelLoader.LevelID = 0;
                LevelLoader.Score = 0;

                try {
                    if(!LevelLoader.LevelLoaded)
                        LevelLoader.Load(level);
                }
                catch (NotImplementedLevel e)
                {
                    InGame = false;
                    System.out.println("Invalid level ID");
                }

                throw new ExitFromMenuEvent();
            }
        };

        subMenu Options = new subMenu(menu, new Vector4f((float) Settings.getWidth() / 2 - 125, 470, 250, 40), new Text("Options", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region);

//        MenuComponent Scores = new Button(new Vector4f((float) Settings.getWidth() / 2 - 70, 470, 250, 40), new Text("Scores", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
//        {
//            @Override
//            public void functionality() throws ExitFromMenuEvent
//            {
//
//            }
//        };
        MenuComponent Exit = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 545, 250, 40), new Text("Exit", ResourceManager.Instance().GetFont("font"), new Vector4f(0.8f, 0.1f, 0f,1f)), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent
            {
                InGame = false;
                throw new ExitFromMenuEvent();
            }
        };

        MenuComponent Scale = new Button(new Vector4f((float) Settings.getWidth() / 2 - 125, 150, 250, 40), new Text("Scale", ResourceManager.Instance().GetFont("font")), ResourceManager.Instance().GetTexture("gui") ,region)
        {
            @Override
            public void functionality() throws ExitFromMenuEvent
            {

            }
        };

        Options.add(Scale);

        MainMenu.add(ContinueGame);
        MainMenu.add(NewGame);
        MainMenu.add(Options);
//        MainMenu.add(Scores);
        MainMenu.add(Exit);

        menu.SetMenuRootAttribute(MainMenu);
//        menu.setTrigger(true);
        menu.ActivateTrigger();


        //Game Loop
        while(!MainWindow.ShouldClose() && InGame)
        {
            MainWindow.ProcessEvents();
            InputHandler.Update(MainWindow.getID());
            WindowTimer.Instance().Ticks();


            if(menu.isTriggered())
            {
                menu.Process();
            }
            else
            {
                try {
                    if(!LevelLoader.LevelLoaded)
                        LevelLoader.Load(level);
                }
                catch (NotImplementedLevel e)
                {
                    InGame = false;
                    System.out.println("Invalid level ID");
                }
                level.process();
            }


            MainWindow.SwapRenderBuffers();
        }

        MainWindow.Destroy();
        Window.CloseProperties();
        ResourceManager.Instance().Free();
        Renderer.Instance().Clear();
//        //WriteBack to DataBase
        DataManager.Write("assets/db/info.db");
    }

}