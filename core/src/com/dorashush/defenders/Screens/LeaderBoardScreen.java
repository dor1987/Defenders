package com.dorashush.defenders.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dorashush.defenders.Defenders;

import java.util.ArrayList;


/**
 * Created by Dor on 02/22/18.
 */

public class LeaderBoardScreen implements Screen {
    private Viewport viewport;
    private Stage stage,backStage;
    private Game game;
    private ArrayList<String> leaderBoardArrayList;
    private List leaderBoardList;
    private Skin skin;
    private Table table;
    private Table table2;

    private ExtendViewport backViewPort;
    private Image backgroundTexture;
    private Image tableBackground;

    private TextButton line1;
    private TextButton line2;
    private TextButton line3;
    private TextButton line4;
    private TextButton line5;
    private TextButton line6;
    private TextButton line7;


    private Image backBtn;
    private Image refreshBtn;

    public LeaderBoardScreen(final Game game){
        this.game = game;
        viewport = new FitViewport(Defenders.V_WIDTH,Defenders.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport,((Defenders)game).batch);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        backViewPort = new ExtendViewport( Defenders.V_WIDTH, Defenders.V_HEIGHT );
        backStage = new Stage(backViewPort);
        backgroundTexture = new Image(new Texture("background.png"));
        backgroundTexture.setFillParent(true);
        tableBackground= new Image(new Texture("leaderboardboard.png"));
        tableBackground.setFillParent(true);
        viewport.apply();
        backViewPort.apply();

        backBtn = new Image(new Texture("back.png"));
        refreshBtn = new Image(new Texture("refresh.png"));

        leaderBoardArrayList = Defenders.handler.getTopSeven();

        Drawable lineDrawable = new TextureRegionDrawable(new TextureRegion(new Texture("scoreboardline.png")));
        TextButton.TextButtonStyle lineStyle = new TextButton.TextButtonStyle( lineDrawable, lineDrawable, lineDrawable, new BitmapFont() );

        line1 = new TextButton("1. "+leaderBoardArrayList.get(0),lineStyle);
        line1.setDisabled( true );
        line1.getLabel().setFontScale(1.3f);
        line2 = new TextButton("2. "+leaderBoardArrayList.get(1),lineStyle);
        line2.setDisabled( true );
        line2.getLabel().setFontScale(1.3f);
        line3 = new TextButton("3. "+leaderBoardArrayList.get(2),lineStyle);
        line3.setDisabled( true );
        line3.getLabel().setFontScale(1.3f);
        line4 = new TextButton("4. "+leaderBoardArrayList.get(3),lineStyle);
        line4.setDisabled( true );
        line4.getLabel().setFontScale(1.3f);
        line5 = new TextButton("5. "+leaderBoardArrayList.get(4),lineStyle);
        line5.setDisabled( true );
        line5.getLabel().setFontScale(1.3f);
        line6 = new TextButton("6. "+leaderBoardArrayList.get(5),lineStyle);
        line6.setDisabled( true );
        line6.getLabel().setFontScale(1.3f);
        line7 = new TextButton("7. "+leaderBoardArrayList.get(6),lineStyle);
        line7.setDisabled( true );
        line7.getLabel().setFontScale(1.3f);









        table = new Table().center().padTop(30f);
        table.setFillParent(true);
       // table.add(leaderBoardList);
        table.add(line1);
        table.row();
        table.add(line2);
        table.row();
        table.add(line3);
        table.row();
        table.add(line4);
        table.row();
        table.add(line5);
        table.row();
        table.add(line6);
        table.row();
        table.add(line7).padBottom(10f);
        table.row();

        table2 = new Table().bottom();
        table2.setFillParent(true);
        table2.add(backBtn).padRight(50f).padBottom(10f).center();
        table2.add(refreshBtn).padBottom(10f);

        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                game.setScreen(new MainMenuScreen((Defenders) game));
                dispose();
            }
        });

        refreshBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                //Add leaderbaord Screen
                Defenders.handler.refresh();
                game.setScreen(new LeaderBoardScreen(game));
                dispose();
            }
        });
    }



    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        backStage.addActor(backgroundTexture);
        backStage.addActor(tableBackground);

        stage.addActor(table);
        stage.addActor(table2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        backStage.act();
        backStage.draw();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);

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
        stage.dispose();
        backStage.dispose();
    }
}
