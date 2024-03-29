package Game.GameStates;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

import java.awt.*;

/**
 * Created by AlexVR on 7/1/2018.
 */
public class GameOverState extends State {

    private int count = 0;
    private UIManager uiManager;

    public GameOverState(Handler handler) {
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouseManager().setUimanager(uiManager);

        uiManager.addObjects(new UIImageButton(256, 523, 128, 64, Images.butstart, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(new GameState(handler));
        }));
        
        uiManager.addObjects(new UIImageButton(456, (523), 128, 64, Images.BTitle, () -> {
            handler.getMouseManager().setUimanager(null);
            State.setState(handler.getGame().menuState);
        }));
    }

    @Override
    public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Images.GameOver,0,0,800,800,null);
        uiManager.Render(g);

    }
}
