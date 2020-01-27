package agh.cs.project;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class KeyHandler extends KeyAdapter {

    private final MapSimulation mapSimulation;
    KeyHandler(JFrame frame, MapSimulation simulation) {
        frame.addKeyListener(this);
        this.mapSimulation = simulation;

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if ((key == KeyEvent.VK_LEFT) && (mapSimulation.getOrientation() != MapDirection.EAST) &&
                mapSimulation.ifCanChangeDirection()) {
            mapSimulation.updateOrientation(MapDirection.WEST);
            mapSimulation.directionChanged();
        }

        if ((key == KeyEvent.VK_RIGHT) && (mapSimulation.getOrientation() != MapDirection.WEST) &&
                mapSimulation.ifCanChangeDirection()) {
            mapSimulation.updateOrientation(MapDirection.EAST);
            mapSimulation.directionChanged();
        }

        if ((key == KeyEvent.VK_UP) && (mapSimulation.getOrientation() != MapDirection.NORTH) &&
                mapSimulation.ifCanChangeDirection()) {
            mapSimulation.updateOrientation(MapDirection.SOUTH);
            mapSimulation.directionChanged();
        }

        if ((key == KeyEvent.VK_DOWN) && (mapSimulation.getOrientation() != MapDirection.SOUTH) &&
                mapSimulation.ifCanChangeDirection()) {
            mapSimulation.updateOrientation(MapDirection.NORTH);
            mapSimulation.directionChanged();
        }
        if(!mapSimulation.isAlive() && key == KeyEvent.VK_SPACE) {
            mapSimulation.startGame();
        }
    }
}
