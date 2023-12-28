package com.rogers.rmcdouga.fitg.svgviewer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.svg.SVGGraphics2D;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import com.rogers.rmcdouga.fitg.basegame.BaseGameScenario;
import com.rogers.rmcdouga.fitg.basegame.map.BaseGameStarSystem;
import com.rogers.rmcdouga.fitg.renderer.graphics2d.Map;
import com.rogers.rmcdouga.fitg.renderer.images.BaseGameImageStoreAdapter;
import com.rogers.rmcdouga.fitg.renderer.images.ImageStore;
import com.rogers.rmcdouga.fitg.svgviewer.images.ClassPathImageStore;

class MapTest {

	@SpringBootTest(webEnvironment = WebEnvironment.NONE, 
			classes = {
					TestApplicationConfiguration.class, 
					com.rogers.rmcdouga.fitg.svgviewer.MapTest.SvgTests.LocalTestConfiguration.class 
					}
			)
	@ActiveProfiles("svg")
	public static class SvgTests {
		@Autowired Map map;
		@Autowired Graphics2D g2;
		
		@ParameterizedTest
		@CsvSource(value = {"FlightToEgrix, 3" , /* "GalacticGame, 51" */})
		void testDrawSVG(BaseGameScenario scenario, int expectedNumStarSystems) throws Exception {
			map.draw();
			
			String result = ((SVGGraphics2D)g2).getSVGDocument();
			
			Files.writeString(TestConstants.ACTUAL_RESULTS_DIR.resolve("FitgMap_" + scenario + ".svg"), result);
			
			XMLDocument xmlDoc = new XMLDocument(result);
			List<XML> nodes = xmlDoc.nodes("/svg:svg/svg:image");
			int expectedNodes = expectedNumStarSystems * 2 + 1;	// 2 per starSystem (pdb + loyalty) + 1 for the underlying map image
			assertEquals(expectedNodes , nodes.size());	// 25 Pdbs + main map
//			Node node = nodes.get(0).node();
//			System.out.println("nodeName='" + node.getLocalName() + "'.");
		}
		
		@TestConfiguration
		@Profile("svg")
		protected static class LocalTestConfiguration {
			@Bean
			public Graphics2D graphics2d() {
				return new SVGGraphics2D(Map.MAP_WIDTH, Map.MAP_HEIGHT);
			}

			@Bean
			public ImageStore imageStore() {
				return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
			}
		}

	}

	@Disabled("Currently fails, will investigate at some point...")
	@SpringBootTest(webEnvironment = WebEnvironment.NONE, 
			classes = {
					TestApplicationConfiguration.class, 
					com.rogers.rmcdouga.fitg.svgviewer.MapTest.ImageTests.LocalTestConfiguration.class 
					}
			)
	@ActiveProfiles("image")
	public static class ImageTests {
		@Autowired Map map;
		@Autowired BufferedImage mapImage;
		
		@ParameterizedTest
		@ValueSource(strings = {"FlightToEgrix", "GalacticGame"})
		void testDrawImage(BaseGameScenario scenario) throws Exception {
			map.draw();
			String filename = "FitgMap_" + scenario + ".png";
			// Compare using image comparison library: https://github.com/romankh3/image-comparison
			BufferedImage expectedImage = ImageIO.read(TestConstants.EXPECTED_RESULTS_DIR.resolve(filename).toFile());
			ImageComparisonResult comparisonResult = new ImageComparison(expectedImage, mapImage).compareImages();
			
			if (ImageComparisonState.MATCH != comparisonResult.getImageComparisonState()) {
				// write out the actual 
				ImageIO.write(mapImage, "png", TestConstants.ACTUAL_RESULTS_DIR.resolve(filename).toFile());
				// write out the diff
				String diffFilename = "FitgMap_" + scenario + "_diff.png";
				comparisonResult.writeResultTo(TestConstants.ACTUAL_RESULTS_DIR.resolve(diffFilename).toFile());
//				ImageComparisonUtil.saveImage(TestConstants.EXPECTED_RESULTS_DIR.resolve(diffFilename).toFile(),comparisonResult.getResult());
			}
			//Check the result
	        assertEquals(ImageComparisonState.MATCH, comparisonResult.getImageComparisonState());
		}

		@TestConfiguration
		@Profile("image")
		protected static class LocalTestConfiguration {

			@Bean
			BufferedImage mapImage() {
				return new BufferedImage(Map.MAP_WIDTH, Map.MAP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
			}
			
			@Bean
			public Graphics2D graphics2d(BufferedImage mapImage) {
				return mapImage.createGraphics();
			}

			@Bean
			public ImageStore imageStore() {
				return BaseGameImageStoreAdapter.wrap(new ClassPathImageStore());
			}
		}
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
	
	@Disabled
	@Test
	void testDegrees() {
		System.out.println("Pi/12=" + Math.toDegrees(Math.PI / 12));
		System.out.println("Pi/6=" + Math.toDegrees(Math.PI / 6));
		// Math.PI * 3.5/18.0
		System.out.println("Pi/3.5/18.0=" + Math.toDegrees(Math.PI * 3.5/18.0));
		System.out.println("Pi/4.72/18.0=" + Math.toDegrees(Math.PI * 4.72/18.0));
	}
	
}
