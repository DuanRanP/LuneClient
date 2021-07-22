
package librarys.org.newdawn.slick.font.effects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import librarys.org.newdawn.slick.UnicodeFont;
import librarys.org.newdawn.slick.font.Glyph;

/**
 * Makes glyphs a solid color.
 * 
 * @author Nathan Sweet <misc@n4te.com>
 */
public class ColorEffect implements ConfigurableEffect {
	/** The colour that will be applied across the text */
	private Color color = Color.white;

	/**
	 * Default constructor for injection
	 */
	public ColorEffect() {
	}

	/**
	 * Create a new effect to colour the text
	 * 
	 * @param color The colour to apply across the text
	 */
	public ColorEffect(Color color) {
		this.color = color;
	}

	/**
	 * @see Effect#draw(BufferedImage, Graphics2D, UnicodeFont, Glyph)
	 */
	public void draw(BufferedImage image, Graphics2D g, UnicodeFont unicodeFont, Glyph glyph) {
		g.setColor(color);
		g.fill(glyph.getShape());
	}

	/**
	 * Get the colour being applied by this effect
	 * 
	 * @return The colour being applied by this effect
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Set the colour being applied by this effect
	 * 
	 * @param color The colour being applied by this effect
	 */
	public void setColor(Color color) {
		if (color == null) throw new IllegalArgumentException("color cannot be null.");
		this.color = color;
	}

	/**
	 * @see Object#toString()
	 */
	public String toString () {
		return "Color";
	}

	/**
	 * @see ConfigurableEffect#getValues()
	 */
	public List getValues() {
		List values = new ArrayList();
		values.add(EffectUtil.colorValue("Color", color));
		return values;
	}

	/**
	 * @see ConfigurableEffect#setValues(List)
	 */
	public void setValues(List values) {
		for (Iterator iter = values.iterator(); iter.hasNext();) {
			Value value = (Value)iter.next();
			if (value.getName().equals("Color")) {
				setColor((Color)value.getObject());
			}
		}
	}
}
