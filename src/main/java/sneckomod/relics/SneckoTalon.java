package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleRandomCardAction;


import java.util.ArrayList;

import static hermit.util.Wiz.atb;

public class SneckoTalon extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SneckoTalon");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SneckoTalon.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SneckoTalon.png"));

    public SneckoTalon() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void atTurnStartPostDraw() {
       atb(new MuddleRandomCardAction(1, true));
        flash();
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
