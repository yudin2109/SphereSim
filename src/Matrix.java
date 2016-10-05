/**
 * Created by yudin on 04.10.2016.
 */
public class Matrix {
    public double x1, y1, z1, x2, y2, z2, x3, y3, z3;
    public Matrix(double _x1, double _y1, double _z1, double _x2, double _y2, double _z2, double _x3, double _y3, double _z3){
        x1 = _x1;
        y1 = _y1;
        z1 = _z1;
        x2 = _x2;
        y2 = _y2;
        z2 = _z2;
        x3 = _x3;
        y3 = _y3;
        z3 = _z3;
    }

    public Vector _mul(Vector vec){
        return new Vector(vec.x * x1 + vec.y * y1 + vec.z * z1, vec.x * x2 + vec.y * y2 + vec.z * z2, vec.x * x3 + vec.y * y3 + vec.z * z3);
    }
    public void print(){
        System.out.println(x1 + " " + y1 + " " + z1 + " " + x2 + " " + y2 + " " + z2 + " " + x3 + " " + y3 + " " + z3);
    }
}
