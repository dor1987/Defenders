package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;
import com.dorashush.defenders.Sprites.Defender;

import javax.naming.Context;

/**
 * Created by Dor on 01/22/18.
 */

public class MenuScreen implements Screen{
    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    protected Skin skin;
    private Game game;
    private BitmapFont font;
    TextureRegion backgroundTexture;

    public MenuScreen(Defenders game){
        this.game = game;
       // atlas = new TextureAtlas("skin.atlas");
        //skin = new Skin(Gdx.files.internal("skin.json"), atlas);
        font = new BitmapFont();
        skin = new Skin();
        skin.add("default", font);
        backgroundTexture = new TextureRegion(new Texture("background.png"),0,0,Defenders.V_WIDTH,Defenders.V_HEIGHT);

        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth()/4,(int)Gdx.graphics.getHeight()/10, Pixmap.Format.RGB888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));

        //Create a button style
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("background", Color.GRAY);
        textButtonStyle.down = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.checked = skin.newDrawable("background", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("background", Color.LIGHT_GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);



        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, camera);
        //viewport = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), camera);

        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);

    }



    @Override
    public void show() {
        //Stage should controll input:
        Gdx.input.setInputProcessor(stage);

        //Create Table
        Table mainTable = new Table();
        //Set table to fill stage
        mainTable.setFillParent(true);
        //Set alignment of contents in the table.
        mainTable.top();

        //Create buttons
        final TextButton playButton = new TextButton("Play", skin);
        TextButton optionsButton = new TextButton("Options", skin);
        final TextButton leaderBoardButton = new TextButton("Leader Board", skin);

        //Add listeners to buttons
        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {


            }
        });
        leaderBoardButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                //  Defenders.handler.sendMsgToFireBase("TEsting it out");
              //  Gdx.app.exit();
                //dispose();

            }
        });



        //Add buttons to table
        mainTable.add(playButton);
        mainTable.row();
        mainTable.add(optionsButton);
        mainTable.row();
        mainTable.add(leaderBoardButton);

        //Add table to stage
        stage.addActor(mainTable);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        resize(Defenders.V_WIDTH, Defenders.V_HEIGHT);
        //stage.act();
        batch.begin();
        batch.draw(backgroundTexture,0,0);
        batch.end();
        stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        skin.dispose();
        stage.dispose();
    }


}
