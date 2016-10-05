/**
 * Created by yudin on 04.10.2016.
 */
public class Vector {
    public double x, y, z;
    public Vector(double _x, double _y, double _z){
        x = _x;
        y = _y;
        z = _z;
    }

    public Vector _add(Vector b){
        return new Vector(x + b.x, y + b.y, z + b.z);
    }

    public Vector _mul(double n){
        return new Vector(x * n, y * n, z * n);
    }
    public void print(){
        System.out.println(x + " " + y + " " + z);
    }
}
