package game.object.Entity;

import auxiliar.Direction;
import exceptions.EmptySpawner;
import game.object.Entity.tank.Tank;
import game.object.Entity.tank.TankFactory;
import org.joml.Vector2f;

public class EnemySpawner {

    int count = 0;
    Vector2f position;
    String type;
    int HP;
    String shelltype;

    Tank spawned;

    public EnemySpawner(int count, String type, Vector2f position, int HP, String shelltype)
    {
        this.count = count;
        this.position = position;
        this.type = type;
        this.HP = HP;
        this.shelltype = shelltype;
    }

    public Tank TryToSpawn() throws EmptySpawner {
        if(spawned != null && count <= 0 && !spawned.ShouldExist())
            throw new EmptySpawner(this);

        if(spawned == null || (!spawned.ShouldExist() && count > 0))
        {
            spawned = (Tank) TankFactory.SpawnTank(type, Direction.Down, position, 2, HP, 0, shelltype);
            count--;
            return spawned;
        }
        return null;
    }




}
