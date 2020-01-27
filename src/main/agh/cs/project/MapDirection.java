package agh.cs.project;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString() {
        switch(this) {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            default:    /* WEST */
                return "West";
        }
    }

    public Vector2d toUnitVector() {
        switch(this) {
            case NORTH:
                return new Vector2d(0,1);
            case EAST:
                return new Vector2d(1,0);
            case SOUTH:
                return new Vector2d(0,-1);
            default:    /* WEST */
                return new Vector2d(-1,0);
        }
    }
}

