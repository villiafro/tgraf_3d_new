package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.BufferUtils;
import com.ru.tgra.shapes.LabFirst3DGame;

import java.awt.*;
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
    float offset;

    float top;
    float bottom;
    float right;
    float left;
    float near;
    float far;
    boolean orthographic;

    private int viewMatrixPointer;
    private int projectionMatrixPointer;
    private FloatBuffer matrixBuffer;

    public Camera(int matrixPointer, int projectionMatrixPointer){
        this.viewMatrixPointer = matrixPointer;
        this.projectionMatrixPointer = projectionMatrixPointer;
        matrixBuffer = BufferUtils.newFloatBuffer(16);

        eye = new Point3D();
        u = new Vector3D(1,0,0);
        v = new Vector3D(0,1,0);
        n = new Vector3D(0,0,1);

        offset = 0.1f;
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
        eye.y += delU*u.y + delV*v.y + delN*n.y;
        //eye.z = nextEye.z;
    }

    void checkWall(Obstacle ob, Point3D tempEye){

        if(eye.z < tempEye.z){
            if(!(eye.z+offset/2 >= ob.getZcord()-(ob.getZscale()/2))){
                if(checkMazeWest(true, tempEye)){
                    eye.z = tempEye.z;
                }
            }
        }
        else if(eye.z > tempEye.z){
            if(!(eye.z-offset/2 <= ob.getZcord()+(ob.getZscale()/2))){
                if(checkMazeWest(false, tempEye)){
                    eye.z = tempEye.z;
                }
            }
        }

        if(eye.x > tempEye.x){
            if(eye.x-offset/2 >= ob.getXcord()+(ob.getXscale()/2)){
                if(checkMazeSouth(false, tempEye)){
                    eye.x = tempEye.x;
                }
            }
        }
        else if(eye.x < tempEye.x){
            if(eye.x+offset/2 <= ob.getXcord()-(ob.getXscale()/2)){
                if(checkMazeSouth(true, tempEye)){
                    eye.x = tempEye.x;
                }
            }
        }
    }

    boolean checkMazeSouth(boolean up, Point3D tempEye){
        int x = (int)eye.x;
        int z = (int)eye.z;
        System.out.println("x: " + x + " z: " + z);

        Cell[][] cells = LabFirst3DGame.getCells();

        if(!up){
            if(cells[x][z].southWall){
                if(tempEye.x-offset/2 <= x + 0.1){
                    return false;
                }
            }
        }
        else if(x<9){
            if(cells[x+1][z].southWall){
                if(tempEye.x+offset/2 >= (x+1  - 0.1)){
                    //System.out.println("where we are: " + (tempEye.x+offset/2) + "where we cant be: " + (x+1  - 0.1));
                    return false;
                }
            }
        }
        return true;

    }

    boolean checkMazeWest(boolean right, Point3D tempEye){
        int x = (int)eye.x;
        int z = (int)eye.z;
        System.out.println("x: " + x + " z: " + z);

        Cell[][] cells = LabFirst3DGame.getCells();

        if(!right){
            if(cells[x][z].westWall){
                if(tempEye.z-offset/2 <= z + 0.1){
                    return false;
                }
            }
        }
        else if(z<9){
            if(cells[x][z+1].westWall){
                if(tempEye.z+offset/2 >= z + 1 - 0.1){
                    return false;
                }
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

    public void orthagrahicProjection(float left, float right, float bottom, float top, float near, float far){
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
        this.near = near;
        this.far = far;

        orthographic = true;
    }

    public void perspectiveProjection(float fov, float ratio, float near, float far){
        this.top = near * (float)Math.tan(((double) fov / 2.0) * Math.PI / 180);
        this.bottom = -top;
        this.right = ratio * top;
        this.left = -right;
        this.near = near;
        this.far = far;

        orthographic = false;
    }

    public void setShaderMatrix(){
        float[] pm = new float[16];

        if(orthographic){
            pm[0] = 2.0f /(right-left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right+left)/(right-left);
            pm[1] = 0.0f; pm[5] = 2.0f /(top-bottom); pm[9] = 0.0f; pm[13] = -(top+bottom)/(top-bottom);
            pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f /(near-far); pm[14] = -(near+far)/(near-far);
            pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;
        }
        else{
            pm[0] = (2.0f * near)/(right-left); pm[4] = 0.0f; pm[8] = (right+left)/(right-left); pm[12] = 0.0f;
            pm[1] = 0.0f; pm[5] = (2.0f * near)/(top-bottom); pm[9] = (top+bottom)/(top-bottom); pm[13] = 0.0f;
            pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -(far+near) / (far-near); pm[14] = -(2.0f * far * near)/(far-near);
            pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;
        }

        matrixBuffer = BufferUtils.newFloatBuffer(16);
        matrixBuffer.put(pm);
        matrixBuffer.rewind();
        Gdx.gl.glUniformMatrix4fv(projectionMatrixPointer, 1, false, matrixBuffer);


        Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);

        pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
        pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
        pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
        pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

        matrixBuffer.put(pm);
        matrixBuffer.rewind();
        Gdx.gl.glUniformMatrix4fv(viewMatrixPointer, 1, false, matrixBuffer);
    }

}
