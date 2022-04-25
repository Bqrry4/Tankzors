package game.object.Entity.tank;

import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

class Node implements Comparable<Node>
{
    boolean visited = false;

    boolean obstacle = false;

    Vector2i position = new Vector2i();

    List<Node> neighbours = new ArrayList<>();


    Node parrent = null;
    double f, g;


    @Override
    public int compareTo(Node o) {
        return (g < o.g) ? (-1) : (1);
    }

}


public class AStar {
    private static final double INFINITY = 1.0 / 0.0;


  /*  List<Integer> open = new ArrayList<>();
    List<Integer> closed = new ArrayList<>();

    int ROW, COL;

    public void Search(int[][] grid, Vector2i src, Vector2i dst)
    {
        ROW = grid.length;
        COL = grid[0].length;

        if(!isValid(src.x, src.y))
        {
            System.out.println("Source invalid");
            return;
        }

        if(!isValid(dst.x, dst.y))
        {
            System.out.println("Destination invalid");
            return;
        }

    }

    boolean isValid(int row, int col)
    {
        return (row >= 0) && (row< ROW) && (col >= 0) && (col < COL);
    }
*/

    PriorityQueue<Node> openSet = new PriorityQueue<Node>();

    Node[][] set;
    public AStar(int[][] map) {
        set = new Node[map.length][map[0].length];
        for (int y = 0; y < map.length; ++y)
        {
            set[y] = new Node[map[y].length];
            for(int x = 0; x < map[y].length; ++x)
            {
                set[y][x] = new Node();
                set[y][x].position.x = x;
                set[y][x].position.y = y;
                set[y][x].parrent = null;
                set[y][x].visited = false;
                set[y][x].obstacle = !(map[y][x] == 0);
            }
        }

        for (int y = 0; y < set.length; ++y)
        {
            for(int x = 0; x < set[y].length; ++x)
            {

                if(y>0)
                    set[y][x].neighbours.add(set[y-1][x]);
                if(y<set.length-1)
                    set[y][x].neighbours.add(set[y+1][x]);
                if(x>0)
                    set[y][x].neighbours.add(set[y][x-1]);
                if(x<set[y].length-1)
                    set[y][x].neighbours.add(set[y][x+1]);

            }
        }

    }

    double heuristic(Node a, Node b)
    {
        return Math.sqrt((a.position.x - b.position.x) * (a.position.x - b.position.x) + (a.position.y - b.position.y) * (a.position.y - b.position.y));
    }

    public List<Vector2i> solve(Vector2i from, Vector2i to)
    {

        if(from.x == to.x && from.y == to.y)
        {
            return null;
        }

        for (int y = 0; y < set.length; ++y) {
            for (int x = 0; x < set[y].length; ++x) {

                set[y][x].visited = false;
                set[y][x].f = INFINITY;
                set[y][x].g = INFINITY;
                set[y][x].parrent = null;
            }
        }

        Node start = set[from.y][from.x];
        Node goal = set[to.y][to.x];

        openSet.clear();

        start.f = 0;
        start.g = heuristic(start, goal);

        openSet.add(start);

        while (!openSet.isEmpty())
        {
            while(!openSet.isEmpty() && openSet.peek().visited)
            {
                openSet.poll();
            }

            if(openSet.isEmpty()) break;

            Node current = openSet.peek();
            current.visited = true;

            for(Node neighbour : current.neighbours)
            {
                if(!neighbour.visited && !neighbour.obstacle)
                {
                    openSet.add(neighbour);
                }

                double fgoal = current.f + heuristic(current, neighbour);

                if(fgoal < neighbour.f)
                {
                    neighbour.parrent = current;
                    neighbour.f = fgoal;

                    neighbour.g = neighbour.f + heuristic(neighbour, goal);
                }
            }

        }

        List<Vector2i> path = new LinkedList<>();

        Node iter = goal;
        while(iter.parrent != null)
        {
            path.add(0, iter.position);
            iter = iter.parrent;
        }

        return path;
    }

}