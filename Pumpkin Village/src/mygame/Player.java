/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;

/**
 *
 * @author Bob
 */
public class Player extends Node {
    
  public  Node                   model;
  public  BetterCharacterControl phys;
  public  ArrayList<String>      inventory;
  public  boolean                hasChecked;
  private AnimControl            animControl;
  private AnimChannel            legChannel;
  private AnimChannel            armChannel;
  private String                 equippedItem;
  public  boolean                hasFailed;
  public  Long                   failTime;
  
  public Player(AppStateManager stateManager) {
  
      model        = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Truman.j3o");
      phys         = new BetterCharacterControl(.3f, 1f, 100);
      inventory    = new ArrayList();
      animControl  =  model.getChild("Body").getControl(AnimControl.class);
      legChannel   = animControl.createChannel();
      armChannel   = animControl.createChannel();
      equippedItem = "None";
      failTime     = 0L;
      armChannel.addFromRootBone("TopSPine");
      legChannel.addFromRootBone("BottomSpine");
      legChannel.setAnim("StillArms");
      armChannel.setAnim("StillLegs");
      model.scale(.2f);
      addControl(phys);
      attachChild(model);
      
  }
  
  public void fail() {
      
      System.out.println("Fail");
      System.out.println(System.currentTimeMillis()/1000 -  failTime);
      hasFailed = true;
      failTime  = System.currentTimeMillis() / 1000;
  
  }
  
  public void swing(AppStateManager stateManager) {
      
      hasChecked = true;
  
  }
  
  public String getEquippedItem() {
  
      return equippedItem;
  
  }
  
  public void equipItem(Node model, Vector3f location, float xRot, float yRot, float zRot, float scale) {

    equippedItem =  model.getName();
    model.scale(scale);
    ((Node) this.model.getChild("HandNode")).attachChild(model);
    model.setLocalTranslation(location);
    model.rotate(xRot,yRot,zRot);
  
  }
  
  public void dropItem() {
  
       equippedItem = "None";
      ((Node)model.getChild("HandNode")).detachAllChildren();
  
  }
  
  public void run() {
  
      if (!armChannel.getAnimationName().equals("UnarmedRun")) {
      armChannel.setAnim("UnarmedRun");
      }
      
      if (!legChannel.getAnimationName().equals("RunAction")) {
      legChannel.setAnim("RunAction");
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
    
}
