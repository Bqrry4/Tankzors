package game.object;

import Managers.Renderer;
import game.InputHandler;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import renderer.Mesh;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends GameObjects{

    private Mesh hitbox;

    private final Matrix4f mx = new Matrix4f();

    public Player(int x, int y, int w, int h)
    {
/*        hitbox = new Mesh(x, y, w, h);*/

    }
    @Override
    public void update() {


        if(InputHandler.keyState(GLFW_KEY_UP))
        {
            mx.translate(0, 0.05f, 0);
        }

        if(InputHandler.keyState(GLFW_KEY_DOWN))
        {
            mx.translate(0, -0.05f, 0);
        }

        if(InputHandler.keyState(GLFW_KEY_LEFT))
        {
            mx.translate(-0.05f, 0, 0);
        }

        if(InputHandler.keyState(GLFW_KEY_RIGHT))
        {
            mx.translate(0.05f, 0, 0);
        }
    }

    @Override
    public void render() {
/*        Renderer.Instance().DrawObject(0, 0, hitbox, mx);*/

        Renderer.Instance().beginBatching();
        Renderer.Instance().DrawBatching(0, new Vector4f(new float[]{50 , 50, 250, 250}), new Vector4f(new float[]{50 , 50, 250, 250}));
        Renderer.Instance().endBatching(0, 0, mx);

/*        shader.SetUniform("transform", mx);

        shader.SetUniform("u_texture", 0);
        mesh.Bind();
        texture.Bind(0);
        mesh.render();*/
    }

    public void setWidth(float value)
    {
        mx.set(0, 0, value);
    }

    public void setHeigth(float value)
    {
        mx.set(1, 1, value);
    }

}
