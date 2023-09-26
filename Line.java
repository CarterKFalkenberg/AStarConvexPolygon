public class Line {
    Vector2D v1;
    Vector2D v2;
    double m;
    double b;
    public Line(Vector2D v1, Vector2D v2){
        this.v1 = v1;
        this.v2 = v2;
        this.m = (v2.y - v1.y) / (v2.x - v1.x);
        this.b = v1.y - (m * v1.x);
    }

    public boolean intersectsWith(Line other){
        double c = 0.001;
        // get the x intersect of the projections of the line segments
        Double x = this.xIntersect(other);
        if (x == null) return false; // parallel lines

        /*
         * In order for the intersection to b evalid, the 
         * xIntersect must be a valid x for both lines
         */
        double left1 = Math.min(this.v1.x, this.v2.x);
        double right1 = Math.max(this.v1.x, this.v2.x);
        double left2 = Math.min(other.v1.x, other.v2.x);
        double right2 = Math.max(other.v1.x, other.v2.x);
        if (left1 - c <= x && right1 + c >= x
         && left2 - c <= x && right2 + c >= x){
            return true;
        }
        return false;

    }

    /*
     * Gets the x value of the intersection of two lines
     * For two lines, intersection is:
     * m1x + b1 = m2x + b2 -> x = (b2 - b1) / (m1 - m2)
     */
    public Double xIntersect(Line other){
        if (this.m - other.m == 0){
            return null;
        }
        return (other.b - this.b) / (this.m - other.m);
    }

    public boolean contains(Vector2D vertex){
        if (v1.equals(vertex) || v2.equals(vertex)){
            return true;
        }
        return false;
    }

}   
