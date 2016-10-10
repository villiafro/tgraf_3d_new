package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.shapes.LabFirst3DGame;

import java.nio.FloatBuffer;
import java.util.ArrayList;


/**
 * Created by VilhjalmurAlex on 06/10/2016.
 */
public class Camera {
    Point3D eye;
    Vector3D u;
    Vector3D v;
    Vector3D n;

    private int viewMatrixPointer;
    private FloatBuffer matrixBuffer;

    public Camera(int matrixPointer){
        this.viewMatrixPointer = matrixPointer;
        matrixBuffer = BufferUtils.newFloatBuffer(16);

        eye = new Point3D();
        u = new Vector3D(1,0,0);
        v = new Vector3D(0,1,0);
        n = new Vector3D(0,0,1);
    }

    public void look(Point3D eye, Point3D center, Vector3D up){
        this.eye.set(eye.x, eye.y, eye.z);
        n = Vector3D.difference(eye,center);
        u = up.cross(n);
        n.normalize();
        u.normalize();
        v = n.cross(u);
    }

    public void slide(float delU, float delV, float delN){

        ArrayList<Obstacle> obstacles = LabFirst3DGame.getObstacles();

        Point3D nextEye = new Point3D();
        nextEye.set(eye.x+delU*u.x + delV*v.x + delN*n.x, eye.y, eye.z+delU*u.z + delV*v.z + delN*n.z);

        for(int i = 0; i < obstacles.size(); i++){
            checkWall(obstacles.get(i), nextEye);
        }
        //eye.x = nextEye.x;
        //eye.y += delU*u.y + delV*v.y + delN*n.y;
        //eye.z = nextEye.z;
    }

    void checkWall(Obstacle ob, Point3D tempEye){

        if(eye.z < tempEye.z){
            if(!(eye.z >= ob.getZcord()+ob.getZscale()/2)){
                //if(checkMazeSouth(true)){
                    eye.z = tempEye.z;
                //}
            }
        }
        else if(eye.z > tempEye.z){
            if(!(eye.z <= ob.getZcord()+ob.getZscale()/2)){
                //if(checkMazeSouth(false)){
                    eye.z = tempEye.z;
                //}
            }
        }

        if(eye.x > tempEye.x){
            if(eye.x >= ob.getXcord()+ob.getXscale()/2){
                //if(checkMazeWest(true)){
                    eye.x = tempEye.x;
                //}
            }
        }
        else if(eye.x < tempEye.x){
            if(eye.x <= ob.getXcord()+ob.getXscale()/2){
                //if(checkMazeWest(false)){
                    eye.x = tempEye.x;
                //}
            }
        }
    }

    boolean checkMazeSouth(boolean up){
        int x = (int)eye.x;
        int z = (int)eye.z;
        System.out.println("x: " + x + " z: " + z);

        Cell[][] cells = LabFirst3DGame.getCells();

        if(!up){
            if(cells[x][z].southWall){
                return false;
            }
        }
        else if(z<10){
            if(cells[x][z+1].southWall){
                return false;
            }
        }
        return true;

    }

    boolean checkMazeWest(boolean right){
        int x = (int)eye.x;
        int z = (int)eye.z;

        Cell[][] cells = LabFirst3DGame.getCells();

        if(!right){
            if(cells[x][z].westWall){
                return false;
            }
        }
        else if(x<10){
            if(cells[x+1][z].westWall){
                return false;
            }
        }
        return true;
    }

    public void roll(float angle)
    {
        float radians = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);
        Vector3D t = new Vector3D(u.x, u.y, u.z);

        u.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
        v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
    }

    public void yaw(float angle)
    {
        float radians = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);
        Vector3D t = new Vector3D(u.x, u.y, u.z);

        u.set(t.x * c - n.x * s, t.y * c - n.y * s, t.z * c - n.z * s);
        n.set(t.x * s + n.x * c, t.y * s + n.y * c, t.z * s + n.z * c);
    }

    public void pitch(float angle)
    {
        float radians = angle * (float)Math.PI / 180.0f;
        float c = (float)Math.cos(radians);
        float s = (float)Math.sin(radians);
        Vector3D t = new Vector3D(n.x, n.y, n.z);

        n.set(t.x * c - v.x * s, t.y * c - v.y * s, t.z * c - v.z * s);
        v.set(t.x * s + v.x * c, t.y * s + v.y * c, t.z * s + v.z * c);
    }

    public void setShaderMatrix(){
        Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);

        float[] pm = new float[16];

        pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
        pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
        pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        matrixBuffer.put(pm);
        matrixBuffer.rewind();
        Gdx.gl.glUniformMatrix4fv(viewMatrixPointer, 1, false, matrixBuffer);
    }

}
