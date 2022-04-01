package game;

public class WindowTimer {
    //Need to be adjusted if ill use multiple windows...

    //Singleton part
    private static WindowTimer instance = new WindowTimer();
    private WindowTimer(){};
    public static WindowTimer Instance() {
        return instance;
    }

    long last_time = System.nanoTime();
    double delta_time;


    public void Ticks()
    {
        //Calculating the delta-time frame interval in seconds
        long current_time = System.nanoTime();
        delta_time = (current_time - last_time) / 1000000000.0; //Convert to seconds
        last_time = current_time;
    }

    public double GetDt()
    {
        return delta_time;
    }



}
