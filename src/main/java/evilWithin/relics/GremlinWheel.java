package evilWithin.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;

public class GremlinWheel extends CustomRelic {

    public static final String ID = EvilWithinMod.makeID("GremlinWheel");
    private static final Texture IMG = new Texture(EvilWithinMod.assetPath("images/relics/GremlinWheel.png"));
    private static final Texture OUTLINE = new Texture(EvilWithinMod.assetPath("images/relics/GremlinWheel.png"));

    public GremlinWheel() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.FLAT);
        this.counter = 1;
    }

    @Override
    public String getUpdatedDescription() {
      return this.DESCRIPTIONS[0];
    }

    public void setCounter(int setCounter) {
        this.counter = setCounter;
        if (setCounter <= 0) {
            this.usedUp();
        }

    }

}
