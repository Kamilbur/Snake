package agh.cs.project;

import java.util.*;

class Map {
    private final Random RANDOM = new Random();

        /*
         *
         */
    private final int mapSize;
    private final int startLength;
    private final int numOfStartingObstacles;
    private final int snakeGrowth;
    private final int objectSize;
    private final int maxJoints;
    private int numOfJoints;

    private ArrayList<Vector2d> snakeJoints;
    private Vector2d applePosition;

    private final Vector2d mapLowerBottomCorner;
    private final Vector2d mapRightUpperCorner;
    private boolean alive = true;
    private HashMap<Vector2d, Vector2d> obstacles;


    Map(int[] parameters) {
        this.mapSize = parameters[0];
        this.startLength = parameters[1];
        this.numOfStartingObstacles = parameters[2];
        this.snakeGrowth = parameters[3];
        this.objectSize = parameters[4];

        this.mapLowerBottomCorner = new Vector2d(0,0);
        this.mapRightUpperCorner = new Vector2d(mapSize - 1, mapSize - 1);
        this.maxJoints = mapSize * mapSize - numOfStartingObstacles;
        this.snakeJoints = new ArrayList<>();
        this.obstacles = new HashMap<>(numOfStartingObstacles + 1);
        this.numOfJoints = startLength;
    }

    void spawnSnake() {
            /* Set jointPosition to the position of the first element */
        Vector2d jointPosition = new Vector2d((mapSize + startLength - 1) / 2, (mapSize - 1) / 2);
        Vector2d step = MapDirection.WEST.toUnitVector();

        for (int ii = 0; ii < startLength; ii++) {
            snakeJoints.add(jointPosition);
            jointPosition = jointPosition.add(step);
        }
    }

    boolean isNotColliding() {
        Vector2d head = snakeJoints.get(0);

        if (obstacles.get(head) != null || !head.precedes(mapRightUpperCorner) ||
                !head.follows(mapLowerBottomCorner)) {

            this.alive = false;
        }
        else {
            for (int ii = numOfJoints - 1; ii >= 3; ii--) {
                if (snakeJoints.get(ii).equals(head)) {
                    alive = false;
                    break;
                }
            }
        }

        return alive;
    }

    void spawnObstacles() {
        int counter = 0;

        for (int ii = 0; ii < numOfStartingObstacles; ii++) {
            while (counter < maxJoints) {
                int x = RANDOM.nextInt(mapSize - 1);
                int y = RANDOM.nextInt(mapSize - 1);
                Vector2d obstaclePos = new Vector2d(x,y);

                if (!obstacles.containsKey(obstaclePos) && obstaclePos.getY() != (mapSize - 1) / 2 ) {
                    obstacles.put(obstaclePos, obstaclePos);
                    break;
                } else counter++;
            }
        }

    }

    void move(MapDirection orientation) {
        for (int ii = numOfJoints - 1; ii > 0; ii--) {
            snakeJoints.set(ii, snakeJoints.get(ii - 1));
        }
        snakeJoints.set(0, snakeJoints.get(0).add(orientation.toUnitVector()));
    }

    void eating() {
        if (applePosition.equals(snakeJoints.get(0))) {
            for (int i = 0; i < snakeGrowth; i++) {
                int x = snakeJoints.get(numOfJoints - 1).getX();
                int y = snakeJoints.get(numOfJoints - 1).getY();
                snakeJoints.add(new Vector2d(x,y));
                numOfJoints++;
            }
            updateApple();
        }
    }

    void updateApple()
    {
        int counter = 0;

        while(counter < 4 * maxJoints) {
            int x = RANDOM.nextInt(mapSize - 1);
            int y = RANDOM.nextInt(mapSize - 1);
            Vector2d applePos = new Vector2d(x,y);

            if (!snakeJoints.contains(applePos) && !obstacles.containsKey(applePos)) {
                applePosition = applePos;
                return;
            }
            else counter++;
        }

    }

    void restart() {
        this.snakeJoints = new ArrayList<>();
        this.obstacles = new HashMap<>(this.numOfStartingObstacles + 1);
        this.numOfJoints = startLength;
        this.alive = true;
    }

    boolean isAlive() { return alive; }

    ArrayList<Vector2d> getSnakeJoints() { return snakeJoints; }

    Vector2d getApplePosition() { return applePosition; }

    ArrayList<Vector2d> getObstacles() { return new ArrayList<>(obstacles.keySet()); }

    int getMapSize() { return this.mapSize; }

    int getObjectSize() { return this.objectSize; }

    int getNumOfJoints() { return this.numOfJoints; }
}
