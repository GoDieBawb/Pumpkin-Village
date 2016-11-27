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
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        
        super.initialize(stateManager, app);
        player  = new Player(stateManager);
        physics = new BulletAppState();
        
    }
    
    @Override
    public void update(float tpf) {
    
        if (!player.getEquippedItem().equals("None")) {
        
            Node itemModel = (Node) player.getChild(player.getEquippedItem());
            itemModel.setLocalTranslation(player.getHandPosition().add(player.getItemOffset()));
            
        }
        
    }
    
}
