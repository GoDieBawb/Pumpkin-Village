/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.Map;

/**
*
* @author Bob
*/
public class CommandParser {
    
    private AppStateManager stateManager;
    private TagParser       parser;
    
    public CommandParser(AppStateManager stateManager) {
        
        this.stateManager = stateManager;
        parser            = new TagParser();
        
    }
    
    public void parse(ArrayList commands, Entity entity) {
        
        boolean go        = true;
        boolean met       = false;
        
        for (int i = 0; i < commands.size(); i++) {
            
            if (stateManager.getState(PlayerManager.class).player.hasFailed)
                break;
            
            String[] args     = ((String) commands.get(i)).split(" ");
            String   command  = args[0];
            
            if (command.equals("if")) {
                
                if (ifCheck(entity, args)) {
                    go  = true;
                    met = true;
                }
                
                else {
                    go  = false;
                    met = false;
                }
                
            }
            
            else if (command.equals("elseif")) {
                
                if (!met) {
                    
                    if (ifCheck(entity, args)) {
                        
                        go  = true;
                        met = true;
                        
                    }
                    
                }
                
                else {
                    
                    go  = false;
                    
                }
                
            }
            
            else if (command.equals("else")) {
                
                if(met)
                    go = false;
                else
                    go = true;
                
            }
            
            else if (command.equals("end")) {
                
                go = true;
                
            }
            
            else if (command.equals("debug")) {
                
                if (!go)
                    continue;
                
                String[] a     = ((String) commands.get(i)).split(" ", 2);
                String debugMessage = a[1];
                System.out.println(debugMessage);
                
            }
            
            else if (command.equals("finish")) {
                
                if(!go)
                    continue;
                
                ((SimpleApplication) stateManager.getApplication()).getRootNode().detachAllChildren();
                stateManager.getState(GuiManager.class).delayAlert("Finish", "As you're dragged off to the gallows to be hung, you can't help but think.... Your final solace is the fact that people will always remember your entry to the Pumpkin Festival.", 4);
                stateManager.getState(PlayerManager.class).player.hasFailed = true;
                
            }
            
            else if (command.equals("fail")) {
                
                if(!go)
                    continue;
                
                stateManager.getState(PlayerManager.class).player.fail();
                
                stateManager.getState
                (SceneManager.class).initScene
                ("Scenes/PumpkinVillage.j3o");
                
                stateManager.getState
                (GuiManager.class).delayAlert
                ("Awakening", "You Wake Up, it's the night of the pumpkin festival. But the nightmare seemed so real.", 3);
                
            }
            
            else if (command.equals("look")) {
                
                if (!go)
                    continue;
                
                entity.lookAt(stateManager.getState(PlayerManager.class).player.getLocalTranslation(), new Vector3f(0,1,0));
                
            }
            
            else if (command.equals("equip")) {
                
                if (!go)
                    continue;
                
                Map<Object, Object> em  = (Map<Object, Object>) entity.behavior.getMap().get("Equip");
                
                float xLoc   = new Float((Double) em.get("xLoc"));
                float yLoc   = new Float((Double) em.get("yLoc"));
                float zLoc   = new Float((Double) em.get("zLoc"));
                float xRot   = new Float((Double) em.get("xRot"));
                float yRot   = new Float((Double) em.get("yRot"));
                float zRot   = new Float((Double) em.get("zRot"));
                float scale  = new Float((Double) em.get("scale"));
                Vector3f loc = new Vector3f(xLoc,yLoc,zLoc);
                
                Node item;
                
                try {
                    
                    item = (Node) parser.parseTag(stateManager, args[1], entity);
                    
                }
                
                catch (Exception e) {
                    
                    item = entity.model.clone(true);
                    
                }
                
                stateManager.getState(PlayerManager.class).player.equipItem
                (item, loc, xRot, yRot, zRot, scale);
                
            }
            
            else if (command.equals("drop")) {
                
                if(!go)
                    continue;
                stateManager.getState(PlayerManager.class).player.dropItem();
                
            }
            
            else if (command.equals("give")) {
                
                if (!go)
                    continue;
                stateManager.getState(PlayerManager.class).player.inventory.add(args[1]);
                
            }
            
            else if (command.equals("take")) {
                
                if (!go)
                    continue;
                stateManager.getState(PlayerManager.class).player.inventory.remove(args[1]);
                System.out.println(stateManager.getState(PlayerManager.class).player.inventory);
                
            }
            
            else if (command.equals("place_scarecrow")) {
                
                if(!go)
                    continue;
                entity.setUserData("Scarecrow", true);
                
            }
            
            else if (command.equals("remove_scarecrow")) {
                
                if(!go)
                    continue;
                entity.setUserData("Scarecrow", false);
                
            }
            
            else if (command.equals("chat")) {
                
                if (!go)
                    continue;
                
                String[] a     = ((String) commands.get(i)).split(" ", 2);
                stateManager.getState(GuiManager.class).showAlert(entity.getName(), a[1]);
                
            }
            
            else if (command.equals("delaychat")) {
                
                if (!go)
                    continue;
                
                String[] a     = ((String) commands.get(i)).split(" ", 2);
                stateManager.getState(GuiManager.class).delayAlert(entity.getName(), a[1],3);
                
            }
            
            else if (command.equals("move")) {
                
                if (!go)
                    continue;
                
                try {
                    
                    Node node     = (Node) parser.parseTag(stateManager, args[1], entity);
                    Vector3f spot = (Vector3f) parser.parseTag(stateManager, args[2], entity);
                    node.setLocalTranslation(spot);
                    
                }
                catch (Exception e) {
                    
                    Vector3f spot = (Vector3f) parser.parseTag(stateManager, args[1], entity);
                    entity.setLocalTranslation(spot);
                    
                }
                
            }
            
            else if (command.equals("hide")) {
                
                if (!go)
                    continue;
                
                try {
                    
                    Node node = (Node) parser.parseTag(stateManager, args[1], entity);
                    node.setLocalTranslation(0,-5,0);
                    
                }
                
                catch (Exception e) {
                    
                    entity.model.setLocalTranslation(0,-5,0);
                    entity.behavior.setIsHid(true);
                    
                }
                
            }
            
            else if (command.equals("show")) {
                
                if (!go)
                    continue;
                
                try {
                    
                    Node node = (Node) parser.parseTag(stateManager, args[1], entity);
                    node.setLocalTranslation(0,0,0);
                    
                }
                
                catch (Exception e) {
                    
                    entity.model.setLocalTranslation(0,0,0);
                    entity.behavior.setIsHid(false);
                    
                }
                
            }
            
            else if (command.equals("idle")) {
                
                if (!go)
                    continue;
                entity.idle();
                
            }
            
            else if (command.equals("punch")) {
                
                if (!go)
                    continue;
                entity.punch();
                
            }
            
            else if (command.equals("noanim")) {
                
                if(!go)
                    continue;
                entity.getAnimControl().clearChannels();
                
            }
            
            else {
                
                System.out.println("Unknown comand: " + command);
                
            }
            
        }
        
    }
    
    private boolean ifCheck(Entity entity, String[] args) {
        
        Object comp1 = null;
        Object comp2 = null;
        String comp  = null;
        
        try {
            
            comp1      = parser.parseTag(stateManager, args[1], entity);
            comp2      = parser.parseTag(stateManager, args[3], entity);
            comp       = args[2];
            
        }
        catch (Exception e) {
        }
        
        boolean truthVal  = false;
        
        if (comp == null) {
            
            if (comp1 instanceof Boolean) {
                
                truthVal = (Boolean) comp1;
                
            }
            
            else {
                
                truthVal = false;
                
            }
            
        }
        
        else if (comp.equals("&&")) {
            
            if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                
                Boolean a = (Boolean) comp1;
                Boolean b = (Boolean) comp2;
                
                if (a&&b) {
                    truthVal = true;
                }
                else{
                    truthVal = false;
                }
                
            }
            
            else {
                
                truthVal = false;
                
            }
            
            return truthVal;
            
        }
        
        else if (comp.equals("!!")) {
            
            if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                
                Boolean a = (Boolean) comp1;
                Boolean b = (Boolean) comp2;
                
                if (!a && !b) {
                    truthVal = true;
                }
                else{
                    truthVal = false;
                }
                
            }
            
            else {
                
                truthVal = false;
                
            }
            
            return truthVal;
            
        }
        
        else if (comp.equals("!&")) {
            
            if (comp1 instanceof Boolean && comp2 instanceof Boolean) {
                
                Boolean a = (Boolean) comp1;
                Boolean b = (Boolean) comp2;
                
                if (a && !b) {
                    truthVal = false;
                }
                else{
                    truthVal = true;
                }
                
                return truthVal;
                
            }
            
            
            
            else {
                
                truthVal = true;
                
            }
            
            return truthVal;
            
        }
        
        else if (comp.equals("==")) {
            
            if (comp1.getClass().getSimpleName().equals(comp2.getClass().getSimpleName())) {
                
                if (comp1 ==  comp2) {
                    
                    truthVal = true;
                    
                }
                
                else {
                    
                    truthVal = false;
                    
                }
                
            }
            
            else {
                
                truthVal = false;
                
            }
            
        }
        
        return truthVal;
        
    }
    
}
