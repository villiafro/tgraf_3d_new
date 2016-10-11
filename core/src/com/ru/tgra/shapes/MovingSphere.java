package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;

/**
 * Created by kristinngudmundsson on 11/10/16.
 */


public class MovingSphere {

    private Shader shader;
    private float height;
    private float deltaTime;
    private Direction direction;


    private enum Direction{
        UP,
        DOWN;
    }

    public MovingSphere(Shader shader){
        height = 0.6f;
        this.shader = shader;
        direction = Direction.DOWN;

    }

    public void drawSphere(){
        this.updateHeight();
        ModelMatrix.main.pushMatrix();
        //Gdx.gl.glUniform4f(colorLoc, 0.2f, 0.7f, 0.2f, 1.0f);
        shader.setMaterialDiffuse(0.2f, 0.7f, 0.2f, 1.0f);
        ModelMatrix.main.addTranslation(0.5f,height,1.5f);
        ModelMatrix.main.addScale(0.15f,0.15f,0.15f);
        ModelMatrix.main.setShaderMatrix();
        SphereGraphic.drawSolidSphere();
        ModelMatrix.main.popMatrix();
    }

    private void updateHeight(){
        deltaTime = Gdx.graphics.getDeltaTime()*0.5f;
        //System.out.println("height is: " + height + " and deltatime is: " + deltaTime);
        if(direction == Direction.DOWN){
            if(height - deltaTime <= 0.2){
                direction = Direction.UP;
                //System.out.println("height is: " + height);
                height += deltaTime;
            }
            else{
                height -= deltaTime;
            }
        }
        else if(direction == Direction.UP){
            if(height + deltaTime >= 0.6){
                direction = Direction.DOWN;
                //System.out.println("height is: " + height);
                height -= deltaTime;
            }
            else{
                height += deltaTime;
            }
        }
    }

}
