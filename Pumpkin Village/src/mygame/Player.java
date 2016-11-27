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
    private AppStateManager        stateManager;
    private Node                   itemModel;
    private Vector3f               itemOffset;
    
    
    public Player(AppStateManager stateManager) {
        this.stateManager = stateManager;
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
    
    public Vector3f getHandPosition() {
        return animControl.getSkeleton().getBone("Righthand").getModelSpacePosition();
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
    
    public Vector3f getItemOffset() {
        return itemOffset;
    }
    
    public void equipItem(Node model, Vector3f location, float xRot, float yRot, float zRot, float scale) {
        
        equippedItem = model.getName();
        itemModel    = model;
        itemOffset   = location;
        model.scale(scale);
        this.model.attachChild(itemModel);
        model.setLocalTranslation(0,0,0);
        model.rotate(xRot,yRot,zRot);
        
    }
    
    public void dropItem() {
        
        equippedItem = "None";
        
        if (itemModel != null)
            model.detachChild(itemModel);
        
        itemModel    = null;
        
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
