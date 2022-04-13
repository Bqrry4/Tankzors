package game.layer;

import Managers.Renderer;
import Managers.ResourceManager;
import org.joml.Vector4f;
import renderer.Texture;
import renderer.VAOStandart;

public class TileLayer extends Layer {
    Texture tex;

    int tileW;
    int tileH;

    int[][] TileIdMap;

    public TileLayer(Texture tex, int tilew, int tileH, int [][]TileIdMap)
    {
        this.tex = tex;
        this.tileW = tilew;
        this.tileH = tileH;
        this.TileIdMap = TileIdMap;
    }

    public void render()
    {
        for(int id = 0; id < TileIdMap.length; ++id)
        {
            for(int jd = 0; jd < TileIdMap[id].length; ++jd)
            {
                if (TileIdMap[id][jd] == 0) continue;
                
                int column = TileIdMap[id][jd] % 27 - 1;
                int row = TileIdMap[id][jd] / 27;
                Renderer.Instance().Draw(tex, new Vector4f(new float[]{tileW * column, tileH * row, tileW, tileH}), new Vector4f(new float[]{jd * tileW * 4, id * tileH * 4, tileW * 4, tileH * 4 }));
            }
        }
        tex.Bind(0);
        Renderer.Instance().Present(ResourceManager.Instance().GetShader("default"), 0);
    }

    public int GetGrid(int row, int column)
    {
        if (row >= TileIdMap.length || row < 0 || column >= TileIdMap[row].length || column < 0)
            return 0;
        return TileIdMap[row][column];
    }
}
