package com.ru.tgra.shapes;

/**
 * Created by VilhjalmurAlex on 09/10/2016.
 */
public class Obstacle {
    private float xcord;
    private float ycord;
    private float zcord;

    private float xscale;
    private float yscale;
    private float zscale;

    public Obstacle(float xc, float yc, float zc, float xs, float ys, float zs) {
        xcord = xc;
        ycord = yc;
        zcord = zc;

        xscale = xs;
        yscale = ys;
        zscale = zs;
    }

    public float getXcord() {
        return xcord;
    }

    public float getYcord() {
        return ycord;
    }

    public float getZcord() {
        return zcord;
    }

    public float getXscale() {
        return xscale;
    }

    public float getYscale() {
        return yscale;
    }

    public float getZscale() {
        return zscale;
    }
}

