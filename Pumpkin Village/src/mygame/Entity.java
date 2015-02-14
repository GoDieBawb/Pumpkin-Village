/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Entity extends Node {   

    public Node          model;
    public Behavior      behavior;
    public Player        player;
    public EntityManager manager;
    public Vector3f      startSpot;
    private AnimControl  animControl;
    private AnimChannel  armChannel;
    private AnimChannel  legChannel;
    
    public Entity(Node model, AppStateManager stateManager, EntityManager manager) {
    
       this.model    = model;
       player        = stateManager.getState(PlayerManager.class).player;
       String script = model.getUserData("Script");
       behavior      = new Behavior(stateManager, script, this);
       this.manager  = manager;
       model.removeFromParent();
       attachChild(model);
       setLocalTranslation(model.getWorldTranslation());
       model.setLocalTranslation(0,0,0);
       setName(model.getName());
       
       behavior.startAction();
       setAnim();
       setMaterial(stateManager);
    
    }
    
    private void setMaterial(AppStateManager stateManager) {
        Material mat;
        
        if (model.getName().equals("Grave")) {
            
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/Corpse.j3m");
            ((Node) model.getChild("Corpse")).getChild(1).setMaterial(mat);
        
        }
        
        else if (model.getName().equals("Ghost")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/Corpse.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Graveyard")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/ScarecrowMat.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
        
        else if (model.getName().equals("House")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/ScarecrowMat.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Scarecrow")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/ScarecrowMat.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Judge")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/CitizenMat.j3m");
            model.setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Porter")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/Lumberjack.j3m");
            model.setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Smith")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/Smith.j3m");
            model.setMaterial(mat);
            
        }
        
        else if (model.getName().equals("Farmer")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/Farmer.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
        
        else if (model.getName().equals("InnKeeper")) {
        
            mat = stateManager.getApplication().getAssetManager().loadMaterial("Materials/InnKeeper.j3m");
            model.getChild(0).setMaterial(mat);
            
        }
    
    }
    
    public AnimControl getAnimControl() {
    return animControl;
    }
    
    private void setAnim() {
    
        try {
        
            animControl =  model.getChild("Body").getControl(AnimControl.class);
            legChannel  = animControl.createChannel();
            armChannel  = animControl.createChannel();
            armChannel.addFromRootBone("TopSPine");
            legChannel.addFromRootBone("BottomSpine");
            legChannel.setAnim("StillArms");
            armChannel.setAnim("StillLegs");
        
        }
        
        catch (NullPointerException e) {
        
        }
    
    }
    
  public void idle() {
  
      if (!armChannel.getAnimationName().equals("StillArms")) {
      armChannel.setAnim("StillArms");
      }
      
      if (!legChannel.getAnimationName().equals("StillLegs")) {
      legChannel.setAnim("StillLegs");
      }
    
   }
  
  public void punch() {
  
      if (!armChannel.getAnimationName().equals("Punch")) {
      armChannel.setAnim("Punch");
      }
  
  }
  
}
