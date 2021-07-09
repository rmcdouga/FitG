package com.rogers.rmcdouga.fitg.svgviewer;

import static com.rogers.rmcdouga.fitg.basegame.BaseGameScenario.FlightToEgrix;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.Game;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixImperialStrategy;
import com.rogers.rmcdouga.fitg.basegame.strategies.hardcoded.FlightToEgrixRebelStrategy;

@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = TestApplicationConfiguration.class)
class MapTest {

	@ParameterizedTest
	@ValueSource(strings = {"FlightToEgrix" /* , "GalacticGame" */})
	void testDrawSVG(BaseGameScenario scenario) throws Exception {
		var g2 = new SVGGraphics2D(Map.MAP_WIDTH, Map.MAP_HEIGHT);
		new Map(g2, createFlightToEgrxxGame()).draw();
		
		String result = g2.getSVGDocument();
		
		Files.writeString(TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap_" + scenario + ".svg"), result);
		
		XMLDocument xmlDoc = new XMLDocument(result);
		List<XML> nodes = xmlDoc.nodes("/svg:svg/svg:image");
		assertEquals(52, nodes.size());	// 25 Pdbs + main map
//		Node node = nodes.get(0).node();
//		System.out.println("nodeName='" + node.getLocalName() + "'.");
	}

	@Test
	void testDrawImage() throws Exception {
		BufferedImage off_Image = new BufferedImage(Map.MAP_WIDTH, Map.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = off_Image.createGraphics();
		new Map(g2, createFlightToEgrxxGame()).draw();
		ImageIO.write(off_Image, "png", TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap.png").toFile());
	}

	@Disabled("This test is used to extract information from the inkscape file.")
	@Test
	void testInkscape() throws Exception {
		XMLDocument xmlDoc = new XMLDocument(TestConstants.SAMPLE_FILES_DIR.resolve("FitgMap.svg"));
		List<XML> nodes = xmlDoc.nodes("/svg:svg/svg:g[*]/svg:circle");
		System.out.println("Found " + nodes.size() + " circles");
//		System.out.println(nodes.get(0).xpath("./@cx").get(0));
		nodes.stream().forEach(n->System.out.println(getInteger(n, "./@cx") + "," + getInteger(n, "./@cy")));
		System.out.println("There are " + BaseGameStarSystem.values().length + " Star Systems.");
	}

	private static int getInteger(XML node, String xpath) {
		return Math.round(Float.parseFloat(node.xpath(xpath).get(0)));
	}
	
	@Test
	void testDegrees() {
		System.out.println("Pi/12=" + Math.toDegrees(Math.PI / 12));
		System.out.println("Pi/6=" + Math.toDegrees(Math.PI / 6));
		// Math.PI * 3.5/18.0
		System.out.println("Pi/3.5/18.0=" + Math.toDegrees(Math.PI * 3.5/18.0));
		System.out.println("Pi/4.72/18.0=" + Math.toDegrees(Math.PI * 4.72/18.0));
	}
	
	private static Game createFlightToEgrxxGame() {
		FlightToEgrixRebelStrategy rebelDecisions = new FlightToEgrixRebelStrategy();
		FlightToEgrixImperialStrategy imperialDecisions = new FlightToEgrixImperialStrategy();
		return Game.createGame(FlightToEgrix, rebelDecisions, imperialDecisions);
	}
}
