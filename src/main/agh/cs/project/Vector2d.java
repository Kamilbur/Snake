package agh.cs.project;

import java.util.Objects;

public class Vector2d {
    private final int x;
    private final int y;

    /*
     *      CONSTRUCTOR
     */
    Vector2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /*
     *      IMPORTANT METHODS
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }

    /*
     *      GETTERS
     */
    int getX() { return this.x; }

    int getY() {
        return this.y;
    }

    /*
     *      BASIC METHODS
     */
    boolean precedes(Vector2d other) {
        return this.x < other.x && this.y < other.y;
    }

    boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;
        Vector2d that = (Vector2d) other;
        return this.x == that.x && this.y == that.y;
    }

}
