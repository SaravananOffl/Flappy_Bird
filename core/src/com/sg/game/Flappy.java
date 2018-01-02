package com.sg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;

import java.util.Random;

import static com.badlogic.gdx.Gdx.*;

public class Flappy extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
    Texture[] birds;
	int flapstate = 0;
    float birdY = 0;
    float velocity = 0;
    int gamestate =0;
    float gravity =2;
    Texture toptube;
    Texture bottomtube;
    float gap = 400;
    float maxTubeset;
    Random random;
    float tubevelocity = 4;
    int numberTubes =4;
    float[] tubeX= new float[numberTubes];
    Circle birdcircle = new Circle();
    ShapeRenderer shaperenderer = new ShapeRenderer();

    float distancetubes;

    float[] tubeoffset= new float[numberTubes];
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
        birds = new Texture[2];
        birds[0] = new Texture("bird.png");
        birds[1] = new Texture("bird2.png");
        birdY=Gdx.graphics.getHeight()/2 - birds[0].getHeight()/2;
        toptube = new Texture("toptube.png");
        bottomtube = new Texture("bottomtube.png");
        distancetubes = Gdx.graphics.getWidth()/2.5f;
        maxTubeset = Gdx.graphics.getHeight()/2 - gap/2 -100;
        random  = new Random();
        for (int i =0;i<numberTubes ; i++){
            tubeoffset[i] = (random.nextFloat()- 0.6f)*(Gdx.graphics.getHeight()-gap);
            if (tubeoffset[i] < -500){
                tubeoffset[i] = 150;
            }
            if (tubeoffset[i] > 500){
                tubeoffset[i] = 150;
            }
            tubeX[i] = Gdx.graphics.getWidth()/2 - toptube.getWidth()/2 + i*distancetubes;

        }
	}

	@Override
	public void render () {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if(gamestate!= 0) {
            if(Gdx.input.justTouched()) {
                velocity = -30;
            }


            for (int i =0;i<numberTubes ; i++) {
                if (tubeX[i]< -toptube.getWidth()){
                    tubeX[i] = tubeX[i]+ numberTubes*distancetubes;
                    tubeoffset[i] = (random.nextFloat()- 0.6f)*(Gdx.graphics.getHeight()-gap);
                    if (tubeoffset[i] < -500){
                        tubeoffset[i] = 150;
                    }
                    if (tubeoffset[i] > 500){
                        tubeoffset[i] = 150;
                    }

                }
                else {
                    tubeX[i] = tubeX[i] - tubevelocity;
                }
                batch.draw(toptube, tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeoffset[i]);
                batch.draw(bottomtube, tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomtube.getHeight() + tubeoffset[i]);

            }
            if (birdY>0 || velocity<0){
            velocity = velocity + gravity;
            birdY -= velocity;
            }
        }
        else{
                if(Gdx.input.justTouched()){
                    gamestate=1;
                }
            }
        if (flapstate == 0)
            flapstate = 1;
        else
            flapstate = 0;

        batch.draw(birds[flapstate], Gdx.graphics.getWidth() / 2 - birds[flapstate].getWidth() / 2, birdY);
        batch.end();
        shaperenderer.begin(ShapeRenderer.ShapeType.Filled);
        shaperenderer.setColor(Color.BLUE);
        birdcircle.set(birdY+birds[flapstate].getHeight()/2,Gdx.graphics.getWidth() / 2,Gdx.graphics.getWidth() / 2,);
    }

	
}
