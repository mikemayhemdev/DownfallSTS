package gremlin.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.ReflectionHacks;

public abstract class AbstractGremlinPower extends AbstractPower {
    private Color renderColor = null;

    static String getID(String ID){
        return "Gremlin:"+ID;
    }

    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        if (renderColor == null) {
            sb.setColor(c);
        } else {
            sb.setColor(renderColor);
        }

        if (img != null) {
            sb.draw(img, x - 12.0f, y - 12.0f, 16.0f, 16.0f, 32.0f, 32.0f, Settings.scale, Settings.scale, 0.0f, 0, 0, 32, 32, false, false);
        } else {
            sb.draw(region48, x - (float)region48.packedWidth / 2.0f, y - (float)region48.packedHeight / 2.0f, (float)region48.packedWidth / 2.0f, (float)region48.packedHeight / 2.0f, region48.packedWidth, region48.packedHeight, Settings.scale, Settings.scale, 0.0f);
        }
        @SuppressWarnings("unchecked")
        ArrayList<AbstractGameEffect> effectList = (ArrayList<AbstractGameEffect>)ReflectionHacks.getPrivate(this, AbstractPower.class, "effect");
        for (AbstractGameEffect e : effectList) {
            e.render(sb, x, y);
        }
    }

    public void onGremlinSwap(){}
}
