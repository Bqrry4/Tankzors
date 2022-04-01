package game.layer;

import Managers.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TileLayer extends Layer {
    int texID;

    int tileW;
    int tileH;

    int[][] TileIdMap;

    public TileLayer(int texID, int tilew, int tileH, int [][]TileIdMap)
    {
        this.texID = texID;
        this.tileW = tilew;
        this.tileH = tileH;
        this.TileIdMap = TileIdMap;
    }

    public void render()
    {
        Renderer.Instance().beginBatching();
        for(int id = 0; id < TileIdMap.length; ++id)
        {
            for(int jd = 0; jd < TileIdMap[id].length; ++jd)
            {
                if (TileIdMap[id][jd] == 0) continue;
                
                int column = TileIdMap[id][jd] % 27 - 1;
                int row = TileIdMap[id][jd] / 27;
                Renderer.Instance().DrawBatching(texID, new Vector4f(new float[]{tileW * column, tileH * row, tileW, tileH}), new Vector4f(new float[]{jd * tileW * 4, id * tileH * 4, tileW * 4, tileH * 4 }));
            }
        }
        Renderer.Instance().endBatching(0, 0, new Matrix4f());
    }

    public int GetGrid(int row, int column)
    {
        if (row >= TileIdMap.length || row < 0 || column >= TileIdMap[row].length || column < 0)
            return 0;
        return TileIdMap[row][column];
    }
}
