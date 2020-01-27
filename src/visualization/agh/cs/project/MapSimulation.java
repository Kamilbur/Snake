package agh.cs.project;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyListener;

class MapSimulation extends KeyAdapter implements ActionListener, KeyListener {
        /* Value chosen using trial and error method to make frame and panel fit together. */
    static final int ALIGNMENT_WIDTH  = 15;
    static final int ALIGNMENT_HEIGHT = 10;  // toolbar size
    private static final int TIMER_DELAY = 250;

    private final Map map;
    final JFrame frame;
    private MapRender MapRender;
    private final Timer timer;
    private MapDirection orientation;
    private boolean canChangeDirection = true;
    private boolean isAlive = true;


    MapSimulation(Map map) {

        this.map = map;
        timer = new Timer(TIMER_DELAY, this);

        frame = new JFrame("Snake PO");
        frame.setSize(map.getMapSize() * map.getObjectSize() - ALIGNMENT_WIDTH,
                map.getMapSize() * map.getObjectSize() + ALIGNMENT_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(Color.black);
        MapRender = new MapRender(map, this);
        MapRender.setSize(new Dimension(1, 1));
        frame.add(MapRender);
        this.orientation = MapDirection.EAST;
        new KeyHandler(frame, this);
    }

    void startGame(){
        if(!isAlive) {
            map.restart();
            isAlive = true;
            frame.remove(MapRender);
            MapRender = new MapRender(map, this);
            MapRender.setSize(new Dimension(1, 1));
            frame.add(MapRender);
            this.orientation = MapDirection.EAST;
        }
        map.spawnObstacles();
        map.spawnSnake();
        map.updateApple();
        MapRender.loadImages();
        MapRender.repaint();
        timer.start();
    }

    void updateOrientation(MapDirection otherOrientation) { this.orientation = otherOrientation; }

    MapDirection getOrientation() { return orientation; }

    void directionChanged() { canChangeDirection = false; }

    boolean ifCanChangeDirection() { return canChangeDirection; }

    @Override

    public void actionPerformed(ActionEvent e) {
        map.move(orientation);
        canChangeDirection = true;
        if(!(isAlive = map.isNotColliding())) {
            System.out.println("End");
            timer.stop();
        }
        map.eating();
        MapRender.repaint();
    }

    boolean isAlive() { return isAlive;}
}
