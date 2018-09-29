package Worlds;

import Game.Entities.Dynamic.Player;
import Game.Entities.Dynamic.Tail;
import Game.Entities.Static.Apple;
import Main.Handler;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by AlexVR on 7/2/2018.
 */
public abstract class WorldBase {

    public int GridWidthHeightPixelCount;
    public int GridPixelsize;
    public Player player;
    protected Handler handler;
    public Boolean appleOnBoard;
    protected Apple apple;
    public Boolean[][] appleLocation;
    public Boolean[][] playerLocation;

    public LinkedList<Tail> body = new LinkedList<>();

    public WorldBase(Handler handler){
        this.handler = handler;
        appleOnBoard = false;
    }
    
    public void tick(){}

    public void render(Graphics g){
        for (int i = 0; i <= 780; i = i + GridPixelsize) {

            g.setColor(new Color(28,37,65));
            g.drawLine(0, i, handler.getHeight() , i);
            g.drawLine(i,0,i,handler.getWidth());
            g.setColor(new Color(255,255,255));
            g.setFont(new Font("Roboto", Font.BOLD, 20));
            g.drawString("Score:"+player.score, handler.getWidth()-100,handler.getHeight()-10);

        }
    }
}
