package fr.vergne.gameGenetic.gui;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.HttpZipLocator;
import com.jme3.asset.plugins.UrlLocator;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class Game extends SimpleApplication {

	@Override
	public void simpleInitApp() {
		Spatial scene = createScene();

		Spatial player = createPlayer();
		player.setLocalTranslation(new Vector3f(-2, 0, 0));

		Spatial npc1 = createNPC();
		npc1.setLocalTranslation(new Vector3f(2, 0, 0));

		Node world = new Node("world");
		world.attachChild(scene);
		world.attachChild(player);
		world.attachChild(npc1);

		// TODO replace world node+translation by camera placement
		world.setLocalTranslation(0, -2.6f, 0);
		rootNode.attachChild(world);

		DirectionalLight sun1 = new DirectionalLight();
		sun1.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
		rootNode.addLight(sun1);
		DirectionalLight sun2 = new DirectionalLight();
		sun2.setDirection(new Vector3f(0.1f, -0.7f, 1.0f));
		rootNode.addLight(sun2);
	}

	private Spatial createScene() {
		assetManager.registerLocator(
				"http://jmonkeyengine.googlecode.com/files/wildhouse.zip",
				HttpZipLocator.class);
		Spatial content = assetManager.loadModel("main.scene");
		content.setLocalTranslation(15.0f, 16.9f, 4.5f);

		Node scene = new Node("scene");
		scene.attachChild(content);
		return scene;
	}

	private Spatial createNPC() {
		Mesh mesh = new Box(1, 1, 1);

		Material material = new Material(assetManager,
				"Common/MatDefs/Misc/Unshaded.j3md");
		material.setColor("Color", ColorRGBA.Red);

		Geometry body = new Geometry("body", mesh);
		body.setMaterial(material);
		body.setLocalTranslation(0.0f, 1.0f, 0.0f);

		Node npc = new Node("NPC1");
		npc.attachChild(body);

		return npc;
	}

	private Spatial createPlayer() {
		assetManager.registerLocator(
				"https://raw.githubusercontent.com/jMonkeyEngine/jmonkeyengine/e2d8fe829359dd6028d6810efddbf671d9980231/jme3-testdata/src/main/resources/Models/Ninja/",
				UrlLocator.class);
		Spatial body = assetManager.loadModel("Ninja.mesh.xml");
		body.scale(0.02f);
		body.rotate(0.0f, -FastMath.HALF_PI, 0.0f);

		Node player = new Node("player");
		player.attachChild(body);

		return player;
	}
}