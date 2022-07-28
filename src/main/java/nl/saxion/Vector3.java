package nl.saxion;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Vector3 {
    public float x;
    public float y;
    public float z;

    public static Vector3 zero() {
        return new Vector3(0, 0, 0);
    }

    public static Vector3 negativeInfinity() {
        return new Vector3(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
    }

    public static Vector3 positiveInfinity() {
        return new Vector3(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
    }

    public static Vector3 add(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public static Vector3 sub(Vector3 v1, Vector3 v2) {
        return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public static float distance(Vector3 v1, Vector3 v2) {
        return (float) Math.sqrt(Math.pow(v1.x - v2.x, 2) + Math.pow(v1.y - v2.y, 2) + Math.pow(v1.z - v2.z, 2));
    }

    /**
     * @param v1 vector 1 start position
     * @param v2 vector 2 end position
     * @return then angle between the two vectors without y component.
     */
    public static float angle2D(Vector3 v1, Vector3 v2) {
        double theta = Math.atan2(v1.x - v2.x, v1.z - v2.z);
        theta += Math.PI / 2;
        return (float) Math.abs(Math.toDegrees(theta));
    }
}
