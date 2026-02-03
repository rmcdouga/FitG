package io.github.rmcdouga.shell_viewer.view;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.junit.jupiter.api.Test;

import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;

class BaseGameCounterRendererTest {

	@Test
	void testRenderCounters_Planetary() {
		List<Counter> counters = List.of(ImperialMilitaryUnit.Line, 
										 RebelMilitaryUnit.Air_1_0, 
										 ImperialMilitaryUnit.Elite_Army, 
										 RebelMilitaryUnit.Urban_4_4_Elite, 
										 ImperialMilitaryUnit.Militia, 
										 RebelMilitaryUnit.Wild_4_3
										 );
		AttributedString result = BaseGameCounterRenderer.renderCounters(ZoomLevel.PLANETARY, counters);
		// System.out.println(result.toAnsi()); 
		assertAll(
				()->assertEquals(ColorsAndStyles.STYLE_IMPERIAL, result.styleAt(0)),
				()->assertEquals(0x24C1, result.codePointAt(0)), // Circle L
				()->assertEquals(AttributedStyle.DEFAULT, result.styleAt(1)),
				()->assertEquals(',', result.codePointAt(1)), // Comma
				()->assertEquals(AttributedStyle.DEFAULT, result.styleAt(2)),
				()->assertEquals(' ', result.codePointAt(2)), // Space
				()->assertEquals(ColorsAndStyles.STYLE_REBEL, result.styleAt(3)),
				()->assertEquals('*', result.codePointAt(3))  // *
				);
	}

	@Test
	void testRenderCounters_System() {
		List<Counter> counters = List.of(ImperialMilitaryUnit.Line, 
										 RebelMilitaryUnit.Air_1_0, 
										 ImperialMilitaryUnit.Elite_Army, 
										 RebelMilitaryUnit.Urban_4_4_Elite, 
										 ImperialMilitaryUnit.Militia, 
										 RebelMilitaryUnit.Wild_4_3
										 );
		AttributedString result = BaseGameCounterRenderer.renderCounters(ZoomLevel.SYSTEM, counters);
		 System.out.println(result.toAnsi()); 
		assertAll(
				()->assertEquals(ColorsAndStyles.STYLE_IMPERIAL, result.styleAt(0)),
				()->assertEquals(0x24BE, result.codePointAt(0)), // Circle I
				()->assertEquals(ColorsAndStyles.STYLE_REBEL, result.styleAt(1)),
				()->assertEquals(0x24C7, result.codePointAt(1)) // Circle R
				);
	}


}
