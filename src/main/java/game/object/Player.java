package game.object;

import renderer.Mesh;
import renderer.Shader;
import renderer.Texture;

public class Player extends GameObjects{

    public Player(Mesh mesh, Texture texture)
    {
        this.mesh = mesh;
        this.texture = texture;
    }
    @Override
    public void update() {

    }

    @Override
    public void render(Shader shader) {

        shader.SetUniform1f("u_texture", 0);
        mesh.Bind();
        texture.Bind(0);
        mesh.render();

    }
}
