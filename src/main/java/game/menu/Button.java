package game.menu;

public class Button implements MenuComponent {
    String label;

    public Button(String label)
    {
        this.label = label;
    }

    @Override
    public void show()
    {

    }

    @Override
    public void DoIfSelected()
    {
        //Nothing, override at instantiation
    }
}