package renderer;

import auxiliar.TextureRegion;

public class TextureMap {

    Texture texture;

    TextureRegion[] Indices;


    public TextureMap()
    {}

    public TextureMap(TextureRegion[] Indices)
    {
        this.Indices = Indices;
    }

    public void setIndices(TextureRegion[] Indices)
    {
        this.Indices = Indices;
    }

    public TextureRegion getRegion(int id)
    {
        return Indices[id];
    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public Texture getTexture()
    {
        return texture;
    }

}
