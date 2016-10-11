package com.ru.tgra.shapes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Random;


public class LabFirst3DGame extends ApplicationAdapter implements InputProcessor {

	Shader shader;

	float deltaTime;
	Camera cam;
	float angle;

	Random rand;
	boolean[] arr;

	private static ArrayList<Obstacle> obstacles;
    private static Cell[][] cells;

	@Override
	public void create () {

		shader = new Shader();
		
		Gdx.input.setInputProcessor(this);

		BoxGraphic.create(shader.getPositionLoc(), shader.getNormalLoc());
		SphereGraphic.create(shader.getPositionLoc(), shader.getNormalLoc());
		SincGraphic.create(shader.getPositionLoc());
		CoordFrameGraphic.create(shader.getPositionLoc());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(shader.getModelMatrixLoc());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera(shader.getViewMatrixLoc(), shader.getProjectionMatrixLoc());
		cam.perspectiveProjection(100,1,0.01f,90);
		cam.look(new Point3D(0.5f,0.5f,0.5f),new Point3D(5,1.5f,5),new Vector3D(0,5,0));

		rand = new Random();
		arr = new boolean[200];
		for(int i = 0; i < 200; i++){
			arr[i] = rand.nextBoolean();
		}

		obstacles = new ArrayList<Obstacle>();

		Maze maze = new Maze();
		cells = maze.getCells();

        MovingSphere movingSphere = new MovingSphere(0.5f,1.5f,shader);
		cam.addMovingSphere(movingSphere);

		movingSphere = new MovingSphere(5.5f,5.5f,shader);
		cam.addMovingSphere(movingSphere);

		movingSphere = new MovingSphere(9.5f,4.5f,shader);
		cam.addMovingSphere(movingSphere);

	}

	public static ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public static Cell[][] getCells(){
        return cells;
    }

	private void input()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			cam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			cam.yaw(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			cam.pitch(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			cam.pitch(-90.0f * deltaTime);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			cam.slide(-1.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			cam.slide(1.0f * deltaTime, 0, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			cam.slide(0, 0, -1.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			cam.slide(0, 0, 1.0f * deltaTime);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.R)) {
			cam.slide(0, 3.0f * deltaTime, 0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F)) {
			cam.slide(0, -3.0f * deltaTime, 0);
		}

		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			cam.roll(-90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			cam.roll(90.0f * deltaTime);
		}
	}
	
	private void update()
	{
		deltaTime = Gdx.graphics.getDeltaTime();
		angle += 180.0f * deltaTime;

		//do all updates to the game
	}
	
	private void display()
	{
        obstacles.clear();
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//float s = (float)Math.sin(angle*Math.PI/180.0);
		//float c = (float)Math.cos(angle*Math.PI/180.0);

		shader.setLightPosition(cam.eye.x,1,cam.eye.z,1);
		shader.setLightDiffuse(1,1,1,1);
		shader.setEyePositionLoc(cam.eye.x, cam.eye.y, cam.eye.z,1);

		cam.setShaderMatrix();

		ModelMatrix.main.loadIdentityMatrix();

		int maxLevel = 10;
		drawWorld();

        ModelMatrix.main.pushMatrix();
        for(int i = 0; i < maxLevel; i++)
        {
            for(int j = 0; j < maxLevel; j++)
            {
                if(cells[i][j].westWall){
                    //cells[i][j].westWall = true;
                    ModelMatrix.main.pushMatrix();
					//west wall is blue
					shader.setMaterialDiffuse(0.5f, 0.3f, 1.0f, 1.0f);
					shader.setMaterialShininess(60);

					//Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.3f, 1.0f, 1.0f);
                    ModelMatrix.main.addTranslation((float)i + 0.5f,0.5f,(float)j);
                    ModelMatrix.main.addScale(1.2f,1f,0.2f);
                    ModelMatrix.main.setShaderMatrix();
                    BoxGraphic.drawSolidCube();
                    ModelMatrix.main.popMatrix();
                }

                if(cells[i][j].southWall){
                    //cells[i][j].southWall = true;
                    ModelMatrix.main.pushMatrix();
					//south wall is red
					shader.setMaterialDiffuse(1f, 0.0f, 0.0f, 1.0f);
					shader.setMaterialShininess(60);
					//Gdx.gl.glUniform4f(colorLoc, 1f, 0.0f, 0.0f, 1.0f);
                    ModelMatrix.main.addTranslation((float)i,0.5f,(float)j + 0.5f);
                    ModelMatrix.main.addScale(0.2f,1f,1.2f);
                    ModelMatrix.main.setShaderMatrix();
                    BoxGraphic.drawSolidCube();
                    ModelMatrix.main.popMatrix();
                }
            }
        }
        ModelMatrix.main.popMatrix();

		//BoxGraphic.drawSolidCube();
		//BoxGraphic.drawOutlineCube();
		//SphereGraphic.drawSolidSphere();
		//SphereGraphic.drawOutlineSphere();

		for (MovingSphere sphere: cam.getMovingSpheres()) {
			sphere.drawSphere();
		}
	}

	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();
	}

	public void drawInitialWall(float a, float b, float c, float d, float e, float f) {
		Obstacle newOb = new Obstacle(a,b,c,d,e,f);
		obstacles.add(newOb);

		shader.setMaterialDiffuse(0.5f, 0.3f, 1.0f, 1.0f);
		shader.setMaterialShininess(160);
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(a, b, c);
		ModelMatrix.main.addScale(d, e, f);
		ModelMatrix.main.setShaderMatrix();
		BoxGraphic.drawSolidCube();
		ModelMatrix.main.popMatrix();
	}
	public void drawWorld(){
		//BOX impersonating the camera in the same place

        /*Gdx.gl.glUniform4f(colorLoc, 0.2f, 0.7f, 0.2f, 1.0f);
        ModelMatrix.main.pushMatrix();
        ModelMatrix.main.addTranslation(cam.eye.x, 0.5f, cam.eye.z);
        ModelMatrix.main.addScale(0.1f, 0.1f, 0.1f);
        ModelMatrix.main.setShaderMatrix();
        BoxGraphic.drawSolidCube();
        ModelMatrix.main.popMatrix();
        Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.3f, 1.0f, 1.0f);*/

		//BOTTOM
		drawInitialWall(5f, 0f, 5f,10, 0.2f, 10);
		//FRONT
		drawInitialWall(0f, 0.5f, 5f,0.2f, 1, 10);
		//BACK
        drawInitialWall(10f, 0.5f, 5f,0.2f, 1, 10);
		//LEFT
        drawInitialWall(5f, 0.5f, 0f,10, 1, 0.2f);
		//RIGHT
        drawInitialWall(5f, 0.5f, 10f,10, 1, 0.2f);
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}