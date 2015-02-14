
package mygame;

import com.jme3.app.state.AppStateManager;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Bob
 */
public class Behavior {
    
  private Map<Object, Object> map;
  private String              script;
  public  Entity              entity;
  private int                 proximity;
  private boolean             enteredProx;
  private AppStateManager     stateManager;
  private boolean             inProx;
  private boolean             isHid;
  
  public Behavior(AppStateManager stateManager, String script, Entity entity) {
      this.stateManager = stateManager;
      this.script       = script;
      this.entity       = entity;
      setScript();
      
  }
  
  public void setScript() {
      
      String filePath   = "Scripts/";
      stateManager.getApplication().getAssetManager().registerLoader(ScriptLoader.class, "yml");
      Object obj;
      
      try {
          
          obj               = stateManager.getApplication().getAssetManager().loadAsset(filePath + script + ".yml");
          map               = (LinkedHashMap) obj;
          
      }
      
      catch (Exception e) {
      
          System.out.println(e);
          
      }
      
      setProximity();
      startAction();
  
  }
  
  private void setProximity() {

     try {
     Map<Object, Object> pm = (Map<Object, Object>)  map.get("Proximity");
     proximity              =  (Integer) pm.get("Distance");
     }
     catch (Exception e){
     }
     
  }
  
  public void startAction() {
  
     ArrayList startScript;
      
     try {
         
        Map<Object, Object> sm = (Map<Object, Object>)  map.get("Start");
        startScript            = (ArrayList) sm.get("Script");
        entity.manager.parser.parse(startScript, entity);
     
     }
     catch (Exception e){
     }
      
  }
  
  private void proximityAction() {
      
      float distance = entity.player.getLocalTranslation().distance(entity.getLocalTranslation());
      Map<Object, Object> pm          = (Map<Object, Object>)  map.get("Proximity");
      
      if (proximity > distance) 
          inProx = true;
      
      if (proximity < distance)  
          inProx = false;
      
      
      if (inProx && !enteredProx) {
      
          enteredProx           = true;
          ArrayList enterScript = (ArrayList) pm.get("Enter");
          entity.manager.parser.parse(enterScript, entity);
      
      }
      
      if (!inProx && enteredProx) {
      
          enteredProx          = false;
          ArrayList exitScript = (ArrayList) pm.get("Exit");
          entity.manager.parser.parse(exitScript, entity);
      
      }
      
  }
  
  public Map<Object, Object> getMap() {
  
      return map;
  
  }
  
  private void checkAction() {
      
      if (entity.getParent().getChild(entity.getParent().getQuantity()-1) == entity)
      stateManager.getState(PlayerManager.class).player.hasChecked = false;
      
      if (inProx) {
      
          Map<Object, Object> im   = (Map<Object, Object>)  map.get("Interact");
          ArrayList interactScript = (ArrayList) im.get("Script");
          entity.manager.parser.parse(interactScript, entity);
      
      }
      
  }
  
  private void loopAction() {
      
      Map<Object, Object> wm          = (Map<Object, Object>)  map.get("While");
      ArrayList whileScript           = (ArrayList) wm.get("Script");
      entity.manager.parser.parse(whileScript, entity);
  
  }
  
  public boolean getInProx() {
      return inProx;    
  }
  
  public boolean getIsHid() {
      return isHid;
  }
  
  public void setIsHid(boolean status) {
      isHid = status;
  }
  
  public void actionCheck() {
      
      if (stateManager.getState(PlayerManager.class).player.hasChecked) {
      checkAction();
      }
      
      proximityAction();
      
      loopAction();
  }
    
}
