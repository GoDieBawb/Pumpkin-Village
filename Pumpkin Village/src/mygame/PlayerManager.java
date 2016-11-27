/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
*
* @author Bob
*/
public class PlayerManager extends AbstractAppState {
    
    public Player         player;
    public BulletAppState physics;
    private AppStateManager  sm;
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        super.initialize(stateManager, app);
        player  = new Player(stateManager);
        physics = new BulletAppState();
        sm      = stateManager;
        
    }
    
    @Override
    public void update(float tpf) {
    
        if (!player.getEquippedItem().equals("None")) {
        
            Node itemModel = (Node) player.getChild(player.getEquippedItem());
            itemModel.setLocalTranslation(player.getHandPosition().add(player.getItemOffset()));
            
        }
        
        if (player.getLocalTranslation().y < -10) {
            player.phys.warp(sm.getState(SceneManager.class).scene.getChild("StartSpot").getLocalTranslation());
            sm.getState(GuiManager.class).showAlert
            ("No Escape", "As you fall into the darkness awaiting the sweet release of death, "
            + "you simply find yourself back where you started.");
        }
        
    }
    
}
