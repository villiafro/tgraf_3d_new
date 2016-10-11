package com.ru.tgra.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by VilhjalmurAlex on 11/10/2016.
 */
public class Shader {
    private int renderingProgramID;
    private int vertexShaderID;
    private int fragmentShaderID;

    private int positionLoc;
    private int normalLoc;

    private int modelMatrixLoc;
    private int viewMatrixLoc;
    private int projectionMatrixLoc;

    private int eyePositionLoc;

    //private int colorLoc;
    private int lightPosition;
    private int lightDiffuse;
    private int materialDiffuse;
    private int materialShininess;

    public Shader(){
        String vertexShaderString;
        String fragmentShaderString;

        vertexShaderString = Gdx.files.internal("core/assets/shaders/simple3D.vert").readString();
        fragmentShaderString =  Gdx.files.internal("core/assets/shaders/simple3D.frag").readString();

        vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
        fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);

        Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
        Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);

        Gdx.gl.glCompileShader(vertexShaderID);
        Gdx.gl.glCompileShader(fragmentShaderID);

        renderingProgramID = Gdx.gl.glCreateProgram();

        Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
        Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);

        Gdx.gl.glLinkProgram(renderingProgramID);

        positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
        Gdx.gl.glEnableVertexAttribArray(positionLoc);

        normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
        Gdx.gl.glEnableVertexAttribArray(normalLoc);

        modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
        viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
        projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

        //colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

        lightPosition				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");
        lightDiffuse				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightDiffuse");
        materialDiffuse				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
        eyePositionLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");
        materialShininess				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialShininess");


        Gdx.gl.glUseProgram(renderingProgramID);
    }

    public void setMaterialDiffuse(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(materialDiffuse, r, g, b, a);
    }

    public void setLightDiffuse(float r, float g, float b, float a){
        Gdx.gl.glUniform4f(lightDiffuse, r, g, b, a);
    }
    public void setLightPosition(float x, float y, float z, float w){
        Gdx.gl.glUniform4f(lightPosition, x, y, z, w);
    }

    public void setEyePositionLoc(float x, float y, float z, float w){
        Gdx.gl.glUniform4f(eyePositionLoc, x, y, z, w);
    }
    public void setMaterialShininess(float shine){
        Gdx.gl.glUniform1f(materialShininess, shine);
    }


    public int getPositionLoc(){
        return positionLoc;
    }
    public int getNormalLoc(){
        return normalLoc;
    }
    public int getViewMatrixLoc(){
        return viewMatrixLoc;
    }
    public int getModelMatrixLoc(){
        return modelMatrixLoc;
    }
    public int getProjectionMatrixLoc(){
        return projectionMatrixLoc;
    }

}
