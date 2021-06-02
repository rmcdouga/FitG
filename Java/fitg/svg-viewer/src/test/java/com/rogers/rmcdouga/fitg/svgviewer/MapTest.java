package com.rogers.rmcdouga.fitg.svgviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class MapTest {

	@Test
	void testDraw() throws Exception {
		String result = new Map().draw();
		XMLDocument xmlDoc = new XMLDocument(result);
		List<XML> nodes = xmlDoc.nodes("/svg:svg/svg:image");
		assertEquals(2, nodes.size());
//		Node node = nodes.get(0).node();
//		System.out.println("nodeName='" + node.getLocalName() + "'.");
		Files.writeString(TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap.svg"), result);
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
}
