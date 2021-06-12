package com.rogers.rmcdouga.fitg.svgviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MapTest {

	@Test
	void testDrawSVG() throws Exception {
		String result = new Map().draw();
		XMLDocument xmlDoc = new XMLDocument(result);
		List<XML> nodes = xmlDoc.nodes("/svg:svg/svg:image");
		assertEquals(2, nodes.size());
//		Node node = nodes.get(0).node();
//		System.out.println("nodeName='" + node.getLocalName() + "'.");
		Files.writeString(TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap.svg"), result);
	}

	@Test
	void testDrawImage() throws Exception {
		BufferedImage off_Image = new BufferedImage(Map.MAP_WIDTH, Map.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = off_Image.createGraphics();
		new Map().draw(g2);
		ImageIO.write(off_Image, "png", TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap.png").toFile());
	}

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
}
