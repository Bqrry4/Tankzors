package exceptions;

import game.object.Entity.EnemySpawner;

public class EmptySpawner extends Exception {
    EnemySpawner spawner;

    public EmptySpawner(EnemySpawner spawner)
    {
        this.spawner = spawner;
    }

    public EnemySpawner getSpawner()
    {
        return spawner;
    }
}
