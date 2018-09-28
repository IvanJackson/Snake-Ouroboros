package Game.Entities.Dynamic;


import Main.GameSetUp;
import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

import Game.GameStates.PauseState;
import Game.GameStates.State;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {
	public int score;
    public int lenght;
    public boolean justAte;
    private Handler handler;

    public int xCoord;
    public int yCoord;

    public int moveCounter;
    public double mc = 5.2; //Changes the speed of snake

    public String direction;//is your first name one?

    public Player(Handler handler){
        this.handler = handler;
        xCoord = 0;
        yCoord = 0;
        moveCounter = 0;
        direction= "Right";
        justAte = false;
        lenght= 1;

    }

    public void tick(){
        moveCounter++;
        if(moveCounter>=mc) {//original if(moveCounter>=5)
            checkCollisionAndMove();
            moveCounter=0;
        }
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) || handler.getKeyManager().up){
            if(direction != "Down"|| lenght == 1) {
        	direction="Up";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) || handler.getKeyManager().down){
        	 if(direction != "Up"|| lenght == 1) {
            direction="Down";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) || handler.getKeyManager().left){
        	 if(direction != "Right"|| lenght == 1) {
            direction="Left";}
        }if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) || handler.getKeyManager().right){
        	 if(direction != "Left"|| lenght == 1) {
            direction="Right";}
        }
        
        /*
         * Created a new KeyEvent, event that is activated when a specific key is pressed, on the letter n
         * If the N key is pressed, it activates NewTail(), which is explained below
         */
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
        	NewTail();        	
        }
        
        /*
         * This allows the 'P' key or the 'escape' key to pause the game
         */
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_P) || handler.getKeyManager().pbutt) {
        	State.setState(handler.getGame().pauseState);
        }
        /*
         * This allows the '+' and '=' key to speed up the snake
         */
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_PLUS) || handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
        	mc -= 0.5;
        }
        /*
         * This allows the '-' key to slow down the snake
         */
        if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
        	mc += 0.5;
        }
        
        	
    }	

    public void checkCollisionAndMove(){
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        int x = xCoord;
        int y = yCoord;
        switch (direction){
            case "Left":
                if(xCoord==0){
                    kill();
                }else{
                    xCoord--;
                }
                break;
            case "Right":
                if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    xCoord++;
                }
                break;
            case "Up":
                if(yCoord==0){
                    kill();
                }else{
                    yCoord--;
                }
                break;
            case "Down":
                if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
                    kill();
                }else{
                    yCoord++;
                }
                break;
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        

        if(handler.getWorld().appleLocation[xCoord][yCoord]){
            Eat();
        }
        handler.getWorld().playerLocation[xCoord][yCoord]=false;
        SnakeCollision();
        handler.getWorld().playerLocation[xCoord][yCoord]=true;
        
        if(!handler.getWorld().body.isEmpty()) {
            handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
            handler.getWorld().body.removeLast();
            handler.getWorld().body.addFirst(new Tail(x, y,handler));
        }
        

    }
    public void SnakeCollision(){
    	switch(direction) {
    	case "Up":
    		if(yCoord != 0) {
            if(handler.getWorld().playerLocation[xCoord][yCoord-1]==true){kill();}}
              break;  
        case "Down":
        	if(yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
             if(handler.getWorld().playerLocation[xCoord][yCoord+1]==true){kill();}}
               break;
        case "Left":
        	if(xCoord!=0) {
            if(handler.getWorld().playerLocation[xCoord-1][yCoord]==true){kill();}}
              break;  
        case "Right":
        	if(xCoord!=handler.getWorld().GridWidthHeightPixelCount-1) {
             if(handler.getWorld().playerLocation[xCoord+1][yCoord]==true){kill();}}
               break;
               
    	}
    }
    public void render(Graphics g,Boolean[][] playeLocation){
        Random r = new Random();
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
                g.setColor(new Color(77,204,189));

                if(playeLocation[i][j]||handler.getWorld().appleLocation[i][j]){
                    g.fillRect((i*handler.getWorld().GridPixelsize),
                            (j*handler.getWorld().GridPixelsize),
                            handler.getWorld().GridPixelsize,
                            handler.getWorld().GridPixelsize);
                }

            }
        }


    }
    //This functions calls on Eat() but doesn't allow a new apple to be created
    //NOt sure if bug in this specific code, but the creation of the new tail is kinda buggy
    public void NewTail() {
    	Eat();
    	score--;
      handler.getWorld().appleOnBoard=true;
    }
    public void Eat(){
        lenght++;
        score++;
        Tail tail= null;
        handler.getWorld().appleLocation[xCoord][yCoord]=false;
        handler.getWorld().appleOnBoard=false;
        switch (direction){
            case "Left":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail = new Tail(this.xCoord+1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail = new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail =new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

                        }
                    }

                }
                break;
            case "Right":
                if( handler.getWorld().body.isEmpty()){
                    if(this.xCoord!=0){
                        tail=new Tail(this.xCoord-1,this.yCoord,handler);
                    }else{
                        if(this.yCoord!=0){
                            tail=new Tail(this.xCoord,this.yCoord-1,handler);
                        }else{
                            tail=new Tail(this.xCoord,this.yCoord+1,handler);
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().x!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                    }else{
                        if(handler.getWorld().body.getLast().y!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                        }
                    }

                }
                break;
            case "Up":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(this.xCoord,this.yCoord+1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
            case "Down":
                if( handler.getWorld().body.isEmpty()){
                    if(this.yCoord!=0){
                        tail=(new Tail(this.xCoord,this.yCoord-1,handler));
                    }else{
                        if(this.xCoord!=0){
                            tail=(new Tail(this.xCoord-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(this.xCoord+1,this.yCoord,handler));
                        }
                    }
                }else{
                    if(handler.getWorld().body.getLast().y!=0){
                        tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
                    }else{
                        if(handler.getWorld().body.getLast().x!=0){
                            tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
                        }else{
                            tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
                        }
                    }

                }
                break;
        }
        handler.getWorld().body.addLast(tail);
        handler.getWorld().playerLocation[tail.x][tail.y] = true;
    }

    public void kill(){
        lenght = 0;
        for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
            for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

                handler.getWorld().playerLocation[i][j]=false;
                State.setState(handler.getGame().GameOverState);

            }
        }
    }

    public boolean isJustAte() {
        return justAte;
    }

    public void setJustAte(boolean justAte) {
        this.justAte = justAte;
    }
}
