package auxiliar;

public enum Direction {
    Down(0),
    Left(1),
    Up(2),
    Right(3);

    public final int value;

    Direction(int value) {
        this.value = value;
    }

}
