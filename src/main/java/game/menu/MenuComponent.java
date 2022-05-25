package game.menu;

import exceptions.ExitFromMenuEvent;

//Composite design pattern
public interface MenuComponent {
    void show() throws ExitFromMenuEvent;
    void Select(boolean value);
    void functionality() throws ExitFromMenuEvent;
    void IsRoot(boolean value);

}

