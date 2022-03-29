package game.layer;

import Managers.Renderer;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class TileLayer extends Layer {
    int texID;

    int tileW;
    int tileH;

    int[][] map;

    public TileLayer(int tilew, int tileH, int [][]map)
    {
        this.tileW = tilew;
        this.tileH = tileH;
        this.map = map;
    }

    public void render()
    {
        Renderer.Instance().beginBatching();
        for(int id = 0; id < map.length; ++id)
        {

            for(int jd = 0; jd < map[id].length; ++jd)
            {
                int column = map[id][jd] % 26 -1;
                int row = map[id][jd] / 26;
                Renderer.Instance().DrawBatching(0, new Vector4f(new float[]{tileW * column, tileH * row, tileW, tileH}), new Vector4f(new float[]{jd * tileW * 4, id * tileH * 4, tileW * 4, tileH * 4 }));
            }
        }
        Renderer.Instance().endBatching(0, 0, new Matrix4f());
    }

}
