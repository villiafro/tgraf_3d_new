package com.ru.tgra.shapes;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.Random;


public class LabFirst3DGame extends ApplicationAdapter implements InputProcessor {

	//private FloatBuffer matrixBuffer;

	Shader shader;

	float deltaTime;
	Camera cam;
	float angle;

	Random rand;
	boolean[] arr;

	private static ArrayList<Obstacle> obstacles;

	private MovingSphere movingSphere;

    private static Cell[][] cells;


	@Override
	public void create () {

		shader = new Shader();
		
		Gdx.input.setInputProcessor(this);

		//OrthographicProjection3D(0, Gdx.graphics.getWidth(), 0, Gdx.graphics.getHeight(), -1, 1);
/*
		float[] mm = new float[16];

		mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
		mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
		mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
		mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

		modelMatrixBuffer = BufferUtils.newFloatBuffer(16);
		modelMatrixBuffer.put(mm);
		modelMatrixBuffer.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrixBuffer);
*/
		//COLOR IS SET HERE
		//shader.setColorLoc(0.7f, 0.2f, 0, 1);

		BoxGraphic.create(shader.getPositionLoc(), shader.getNormalLoc());
		SphereGraphic.create(shader.getPositionLoc(), shader.getNormalLoc());
		SincGraphic.create(shader.getPositionLoc());
		CoordFrameGraphic.create(shader.getPositionLoc());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(shader.getModelMatrixLoc());

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		//OrthographicProjection3D(-2, 2, -2, 2, 1, 100);
		//PerspctiveProjection3D();
		//Look3D(new Point3D(1.5f, 1.2f, 2.0f), new Point3D(0,0,0), new Vector3D(0,1,0));


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

        movingSphere = new MovingSphere(0.5f,1.5f,shader);
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
		//System.out.println("delta time in update is: " + deltaTime);
		angle += 180.0f * deltaTime;

		//do all updates to the game
	}
	
	private void display()
	{
        obstacles.clear();
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		//shader.setColorLoc(0.5f, 0.3f, 1.0f, 1.0f);
		//Gdx.gl.glUniform4f(colorLoc, 0.5f, 0.3f, 1.0f, 1.0f);
		//float s = (float)Math.sin(angle*Math.PI/180.0);
		//float c = (float)Math.cos(angle*Math.PI/180.0);

		shader.setLightPosition(cam.eye.x,1,cam.eye.z,1);

		//s = Math.abs((float)Math.sin((angle/2)*Math.PI/180.0));
		//c = Math.abs((float)Math.cos((angle*2)*Math.PI/180.0));

		shader.setLightDiffuse(1,1,1,1);
		shader.setEyePositionLoc(cam.eye.x, cam.eye.y, cam.eye.z,1);

		cam.setShaderMatrix();

		ModelMatrix.main.loadIdentityMatrix();

		int maxLevel = 10;

		drawWorld();
		movingSphere.drawSphere();

		int randomizer = 0;

        ModelMatrix.main.pushMatrix();
        for(int i = 0; i < maxLevel; i++)
        {
            for(int j = 0; j < maxLevel; j++)
            {
                /*if(i > 0 && cells[i-1][j].westWall){

                }*/
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
                /*if(j > 0 && cells[i][j-1].southWall){

                }*/
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
		//BOX impersonating the camera in the right place

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

	/*private void Look3D(Point3D eye, Point3D center, Vector3D up) {
		
		Vector3D n = Vector3D.difference(eye, center);
		Vector3D u = up.cross(n);
		n.normalize();
		u.normalize();
		Vector3D v = n.cross(u);

		Vector3D minusEye = new Vector3D(-eye.x, -eye.y, -eye.z);
		
		float[] pm = new float[16];

		pm[0] = u.x; pm[4] = u.y; pm[8] = u.z; pm[12] = minusEye.dot(u);
		pm[1] = v.x; pm[5] = v.y; pm[9] = v.z; pm[13] = minusEye.dot(v);
		pm[2] = n.x; pm[6] = n.y; pm[10] = n.z; pm[14] = minusEye.dot(n);
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrixBuffer);
	}

	private void OrthographicProjection3D(float left, float right, float bottom, float top, float near, float far) {
		float[] pm = new float[16];

		pm[0] = 2.0f / (right - left); pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = -(right + left) / (right - left);
		pm[1] = 0.0f; pm[5] = 2.0f / (top - bottom); pm[9] = 0.0f; pm[13] = -(top + bottom) / (top - bottom);
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 2.0f / (near - far); pm[14] = (near + far) / (near - far);
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrixBuffer);

		pm[0] = 1.0f; pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = 0.0f;
		pm[1] = 0.0f; pm[5] = 1.0f; pm[9] = 0.0f; pm[13] = 0.0f;
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = 1.0f; pm[14] = 0.0f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = 0.0f; pm[15] = 1.0f;

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrixBuffer);
	}

	private void PerspctiveProjection3D() {
		float[] pm = new float[16];

		pm[0] = 1.0f; pm[4] = 0.0f; pm[8] = 0.0f; pm[12] = 0.0f;
		pm[1] = 0.0f; pm[5] = 1.0f; pm[9] = 0.0f; pm[13] = 0.0f;
		pm[2] = 0.0f; pm[6] = 0.0f; pm[10] = -1.02f; pm[14] = -2.02f;
		pm[3] = 0.0f; pm[7] = 0.0f; pm[11] = -1.0f; pm[15] = 0.0f;

		matrixBuffer = BufferUtils.newFloatBuffer(16);
		matrixBuffer.put(pm);
		matrixBuffer.rewind();
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrixBuffer);

	}*/

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