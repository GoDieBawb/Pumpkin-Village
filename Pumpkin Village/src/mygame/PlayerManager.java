/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;

/**
*
* @author Bob
*/
public class PlayerManager extends AbstractAppState {
    
    public Player         player;
    public BulletAppState physics;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        super.initialize(stateManager, app);
        player  = new Player(stateManager);
        physics = new BulletAppState();
        
    }
    
}
