package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import java.util.Random;

/**
 * Created by kristinngudmundsson on 11/10/16.
 */


public class MovingSphere {


    private int colorLoc;
    private float radius;
    private Shader shader;
    private float deltaTime;
    private float x;
    private float height;
    private float z;
    private Direction direction;

    private float red;
    private float green;
    private float blue;


    private enum Direction{
        UP,
        DOWN
    }


    public MovingSphere(float posX, float posZ, Shader shader){
        x = posX;
        height = 0.6f;
        z = posZ;
        radius = 0.075f;

        this.shader = shader;
        direction = Direction.DOWN;
        this.randomizeSphereColor();
    }

    public void drawSphere(){
        this.updateHeight();
        ModelMatrix.main.pushMatrix();

        shader.setMaterialDiffuse(red, green, blue, 1.0f);
        ModelMatrix.main.addTranslation(this.x,this.height,this.z);
        ModelMatrix.main.addScale(this.radius*2,this.radius*2,this.radius*2);

        ModelMatrix.main.setShaderMatrix();
        SphereGraphic.drawSolidSphere();
        ModelMatrix.main.popMatrix();
    }

    private void updateHeight(){
        deltaTime = Gdx.graphics.getDeltaTime()*0.5f;
        if(direction == Direction.DOWN){
            if(height - deltaTime <= 0.2){
                direction = Direction.UP;
                height += deltaTime;
            }
            else{
                height -= deltaTime;
            }
        }
        else if(direction == Direction.UP){
            if(height + deltaTime >= 0.6){
                direction = Direction.DOWN;
                height -= deltaTime;
            }
            else{
                height += deltaTime;
            }
        }
    }

    public void randomizeSphereColor(){
        float minX = 0.0f;
        float maxX = 1.0f;
        Random rand = new Random();
        red = rand.nextFloat() * (maxX - minX) + minX;
        green= rand.nextFloat() * (maxX - minX) + minX;
        blue = rand.nextFloat() * (maxX - minX) + minX;
    }

    public float getRadius() {
        return radius;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }
}
