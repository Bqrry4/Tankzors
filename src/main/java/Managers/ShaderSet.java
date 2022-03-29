package Managers;

import renderer.Shader;

import java.util.ArrayList;
import java.util.List;

public class ShaderSet {

    private static ShaderSet instance = new ShaderSet();

    //Common shader uniforms
    public static final String textureUniform = "u_texture";
    public static final String transformUniform = "transform";

    //List of Shaders
    private final List<Shader> shaderList = new ArrayList<Shader>();

    private ShaderSet(){};

    public static ShaderSet Instance() {
        return instance;
    }

    public void addShader(Shader shader)
    {
        shaderList.add(shader);
    }

    public void useShader(int ShaderId)
    {
        shaderList.get(ShaderId).use();
    }

}
