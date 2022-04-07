package auxiliar;

import org.joml.Vector4i;

public class TextureRegion {

        private Vector4i region;

        public TextureRegion(int x, int y, int w, int h)
        {
            region = new Vector4i(x, y, w, h);
        }

        public TextureRegion(Vector4i region)
        {
            this.region = region;
        }

        public int x()
        {
            return region.x;
        }
        public int y()
        {
            return region.y;
        }
        public int w()
        {
            return region.z;
        }
        public int h()
        {
            return region.w;
        }
}
