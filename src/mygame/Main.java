package mygame;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;

public class Main extends SimpleApplication {

    /* Setup for the application */
    public Main() {
        super(new FlyCamAppState(), new DebugKeysAppState());
    }
    
    public static void main(String[] args) {
        Main app = new Main();
        app.setShowSettings(false);
        AppSettings settings = new AppSettings(true);
        settings.put("Width", 1280);
        settings.put("Height", 720);
        settings.put("Title", "Cube");
        settings.put("VSync", true);
        // AA
        settings.put("Samples", 4);
        app.setSettings(settings);
        app.start();
    }
    /* End setup for the application */

    /* Fields */
    private Geometry player;
    private boolean isRunning = true;
    /* End fields */
    
    @Override
    public void simpleInitApp() {
        Box playerBox = new Box(1, 1, 1);
        player = new Geometry("player", playerBox);
        Material playerMaterial = new Material(assetManager,
            "Common/MatDefs/Misc/ShowNormals.j3md");
        player.setMaterial(playerMaterial);
        rootNode.attachChild(player);
        initKeys(); // Custom keybindings
    }

    @Override
    public void simpleUpdate(float tpf) {
        
    }
    
    private void initKeys() {
        // Add mappings to input manager
        inputManager.addMapping("Pause", new KeyTrigger(KeyInput.KEY_P));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_J));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Rotate", new KeyTrigger(KeyInput.KEY_SPACE),
                                            new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        
        // Add names to listeners
        inputManager.addListener(actionListener, "Pause");
        inputManager.addListener(analogListener, "Left", "Right", "Rotate");
    }
    
    private final ActionListener actionListener = new ActionListener() {
        @Override
        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("Pause") && !keyPressed) {
                isRunning = !isRunning;
            }
        }
    };
    
    private final AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float value, float tpf) {
            if (isRunning) {
                if (name.equals("Rotate")) {
                    player.rotate(0, value * speed, 0);
                }
                if (name.equals("Right")) {
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x + value * speed, v.y, v.z);
                }
                if (name.equals("Left")) {
                    Vector3f v = player.getLocalTranslation();
                    player.setLocalTranslation(v.x - value * speed, v.y, v.z);
                }
            } else {
                System.out.println("Press P to unpause.");
            }
        }
    };
}