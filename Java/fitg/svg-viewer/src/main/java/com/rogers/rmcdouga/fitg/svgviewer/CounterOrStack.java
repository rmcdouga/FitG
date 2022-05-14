package com.rogers.rmcdouga.fitg.svgviewer;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.ListIterator;

import com.rogers.rmcdouga.fitg.basegame.units.BaseGameCharacter;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameImperialSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.BaseGameRebelSpaceship;
import com.rogers.rmcdouga.fitg.basegame.units.Counter;
import com.rogers.rmcdouga.fitg.basegame.units.ImperialMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.RebelMilitaryUnit;
import com.rogers.rmcdouga.fitg.basegame.units.Spaceship;
import com.rogers.rmcdouga.fitg.basegame.units.StackManager.Stack;
import com.rogers.rmcdouga.fitg.svgviewer.images.ImageStore;

public abstract class CounterOrStack {
	public static record ImageSize(int width, int height) {};
	
	public abstract ImageSize getImageSize();
	public abstract void draw(Graphics2D gc);
	
	public static CounterOrStack from(Counter counter, ImageStore imageStore) {
		return counter instanceof Stack stack ? StackImpl.from(stack, imageStore) : CounterImpl.from(counter, imageStore);
	}
	
	private static Image getImage(Counter counter, ImageStore imageStore) {
		if (counter instanceof Spaceship spaceship) {
			if (spaceship instanceof BaseGameRebelSpaceship rebelSpaceship) {
				return imageStore.getImage(rebelSpaceship);
			} else if (spaceship instanceof BaseGameImperialSpaceship ImpSpaceship) {
				return imageStore.getImage(ImpSpaceship);
			}
		} else if (counter instanceof BaseGameCharacter character) {
			return imageStore.getImage(character);
		} else if (counter instanceof RebelMilitaryUnit rebelUnit) {
			return imageStore.getImage(rebelUnit);
		} else if (counter instanceof ImperialMilitaryUnit impUnit) {
			return imageStore.getImage(impUnit);
		}
		throw new IllegalStateException("Encountered unexpected counter type (" + counter.getClass().getName() + ").");
	}

	private static class CounterImpl extends CounterOrStack {
		private final Image image;

		private CounterImpl(Image image) {
			this.image = image;
		}

		@Override
		public ImageSize getImageSize() {
			return new ImageSize(image.getWidth(null), image.getHeight(null));
		}

		@Override
		public void draw(Graphics2D gc) {
			gc.drawImage(image, -image.getWidth(null)/2, -image.getHeight(null)/2, null);
		}

		public static CounterImpl from(Counter counter, ImageStore imageStore) {
			return new CounterImpl(getImage(counter, imageStore));
		}
	}
	
	private static class StackImpl extends CounterOrStack {
		private static int OFFSET = 10;  	// This is the offset from centre for counters (typically up and to the left)
		private final List<Image> images;
		
		private StackImpl(List<Image> images) {
			this.images = images;
		}

		@Override
		public ImageSize getImageSize() {
			// Make some assumptions that I know are currently true in order to simplify this routine
			// - Assume all images are the same size and square
			// - Assume and all offsets are the same size;
			int offsetSize = cumulativeOffsetSize();	// offset is inbetween images, so num of images - 1; 
			int imageSize = images.get(0).getWidth(null); 
			return new ImageSize(imageSize + offsetSize, imageSize + offsetSize);
		}

		private int cumulativeOffsetSize() {
			return (images.size() - 1) * OFFSET;
		}

		@Override
		public void draw(Graphics2D gc) {
			Graphics2D localGc = (Graphics2D) gc.create();
			int offsetSize = cumulativeOffsetSize();	// offset is inbetween images, so num of images - 1;
			localGc.translate(offsetSize, offsetSize);
			ListIterator<Image> listIterator = images.listIterator(images.size());
			while (listIterator.hasPrevious()) {
			    Image image = listIterator.previous();
			    localGc.drawImage(image, -image.getWidth(null)/2, -image.getHeight(null)/2, null);
			    localGc.translate(-OFFSET, -OFFSET);
			}
		}
		
		public static StackImpl from(Stack stack, ImageStore imageStore) {
			return new StackImpl(stack.stream().map(c->getImage(c, imageStore)).toList());
		}
	}
}
