package auxiliar.PathFinding;

import org.joml.GeometryUtils;
import org.joml.Vector2f;
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


public class AStar implements PathFinder {
    private static final double INFINITY = 1.0 / 0.0;

    PriorityQueue<Node> openSet = new PriorityQueue<>();
    Node[][] set;
    Vector2i GridSize;


    public AStar(int[][] map, Vector2i GridSize)
    {
        this.GridSize = GridSize;

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

    @Override
    public List<Vector2i> FindPath(Vector2f from, Vector2f to)
    {

        if(from == null || to == null || from == to)
        {
            return null;
        }

        //Clear the set
        openSet.clear();
        for (Node[] nodes : set) {
            for (Node node : nodes) {

                node.visited = false;
                node.f = INFINITY;
                node.g = INFINITY;
                node.parrent = null;
            }
        }

        //Converting real coordonates into grid-like ones

        Vector2i first = new Vector2i((int) (from.x/GridSize.x), (int) (from.y/GridSize.y));
        Vector2i second = new Vector2i((int) (to.x/GridSize.x), (int) (to.y/GridSize.y));

        Node start = set[first.y][first.x];
        Node goal = set[second.y][second.x];

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

        LinkedList<Vector2i> path = null;
        Node iter = goal;

        if(iter.parrent != null)
        {
            path = new LinkedList<>();

            while(iter.parrent != null)
            {
                path.addFirst(iter.position);
                iter = iter.parrent;
            }
        }

        return path;
    }

}