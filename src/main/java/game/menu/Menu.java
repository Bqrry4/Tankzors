package game.menu;

import renderer.Shader;

public class Menu {
    private boolean isActive; //Active or not
    MenuComponent component;

    public void Process()
    {
        Update();

        Render();
    }

    public boolean isTriggered()
    {
        return isActive;
    }

    private void Update()
    {

    }

    private void Render()
    {


    }


}
