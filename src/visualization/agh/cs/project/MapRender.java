package agh.cs.project;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class MapRender extends JPanel {

    private static final Color BLACK = new Color(0,0,0);
    private static final Color DARK_RED = new Color(50, 11, 0);
    private static final Color RED = new Color(194, 68, 21);
    private static final Color BLUE = new Color(43, 200, 10);

    private final Map map;
    private final MapSimulation simulation;

    private static final String APPLE_SOURCE_PATH = "Resources\\apple.png";
    private static final String HEAD_NORTH_SOURCE_PATH = "Resources\\snakeHeadNorth.png";
    private static final String HEAD_SOUTH_SOURCE_PATH = "Resources\\snakeHeadSouth.png";
    private static final String HEAD_EAST_SOURCE_PATH = "Resources\\snakeHeadEast.png";
    private static final String HEAD_WEST_SOURCE_PATH = "Resources\\snakeHeadWest.png";
    private Image apple;
    private Image headNorth;
    private Image headSouth;
    private Image headEast;
    private Image headWest;
    private ArrayList<Vector2d> snakeJoints;
    private final int objectSize;

    MapRender(Map map, MapSimulation simulation) {
        this.map = map;
        this.simulation = simulation;
        this.objectSize = map.getObjectSize();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if(map.isAlive()) {
            snakeJoints = map.getSnakeJoints();
            ArrayList<Vector2d> obstacles = map.getObstacles();
            super.paintComponent(g);
            this.setSize(simulation.frame.getWidth(), simulation.frame.getHeight() - MapSimulation.ALIGNMENT_HEIGHT);
            this.setLocation( 0, 0);
            int width = this.getWidth();
            int height = this.getHeight();

            g.setColor(BLACK);
            g.fillRect(0, 0, width, height);

            g.setColor(DARK_RED);
            g.drawImage(apple, map.getApplePosition().getX() * objectSize, map.getApplePosition().getY() * objectSize,
                    objectSize, objectSize, this);

            drawHead(g, simulation.getOrientation());

            g.setColor(RED);

            for (int ii = 1; ii < map.getNumOfJoints(); ii++) {
                g.fillOval(snakeJoints.get(ii).getX() * map.getObjectSize() + map.getObjectSize() / 4,
                        snakeJoints.get(ii).getY() * map.getObjectSize() + map.getObjectSize() / 4,
                        map.getObjectSize() / 2, map.getObjectSize() / 2);
            }

            g.setColor(BLUE);


            for (Vector2d obstacle: obstacles) {
                g.fillOval(obstacle.getX() * map.getObjectSize(), obstacle.getY() * map.getObjectSize(),
                        map.getObjectSize(), map.getObjectSize());
            }

        }
        else {
            int fontSize = 30;

            String msg = "Game Over. Your score: " + map.getNumOfJoints();
            String msgRestart = "Click space to restart";
            Font small = new Font("Helvetica", Font.BOLD, fontSize);
            FontMetrics metrics = getFontMetrics(small);
            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (map.getMapSize() * objectSize - metrics.stringWidth(msg) - MapSimulation.ALIGNMENT_WIDTH) / 2,
                    map.getMapSize() * objectSize / 2 - 40);
            g.drawString(msgRestart, (map.getMapSize() * objectSize - metrics.stringWidth(msgRestart) - MapSimulation.ALIGNMENT_WIDTH) / 2,
                    map.getMapSize() * objectSize / 2);
        }
    }

    private void drawHead(Graphics g, MapDirection orientation)
    {
        if(orientation == MapDirection.WEST)
        {
            g.drawImage(headWest, snakeJoints.get(0).getX() * objectSize,
                    snakeJoints.get(0).getY() * objectSize, map.getObjectSize(), map.getObjectSize(), this);
        }
        else if(orientation == MapDirection.EAST)
        {
            g.drawImage(headEast, snakeJoints.get(0).getX() * objectSize,
                    snakeJoints.get(0).getY() * objectSize, map.getObjectSize(), map.getObjectSize(), this);
        }
        else if(orientation == MapDirection.SOUTH)
        {
            g.drawImage(headNorth, snakeJoints.get(0).getX() * objectSize,
                    snakeJoints.get(0).getY() * objectSize, map.getObjectSize(), map.getObjectSize(), this);
        }
        else if(orientation == MapDirection.NORTH)
        {
            g.drawImage(headSouth, snakeJoints.get(0).getX() * objectSize,
                    snakeJoints.get(0).getY() * objectSize, map.getObjectSize(), map.getObjectSize(),  this);
        }


    }

    void loadImages() {

        ImageIcon appleIcon = new ImageIcon(APPLE_SOURCE_PATH);
        apple = appleIcon.getImage();

        ImageIcon headIcon = new ImageIcon(HEAD_NORTH_SOURCE_PATH);
        headNorth = headIcon.getImage();
        headIcon = new ImageIcon(HEAD_SOUTH_SOURCE_PATH);
        headSouth = headIcon.getImage();
        headIcon = new ImageIcon(HEAD_EAST_SOURCE_PATH);
        headEast = headIcon.getImage();
        headIcon = new ImageIcon(HEAD_WEST_SOURCE_PATH);
        headWest = headIcon.getImage();
    }
}

