package renderer;

import auxiliar.TextureRegion;
import renderer.Texture;

public class TextureMap extends Texture {

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
        this.texId = texture.texId;
        this.width = texture.width;
        this.height = texture.height;
    }


    public void UseTextureAtlas()
    {
        
    }

}
