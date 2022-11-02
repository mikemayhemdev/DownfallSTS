package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.cardmods.RetainMod;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;
import sneckomod.SneckoMod;
import sneckomod.cards.Cheat;

public class SleevedAce extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SleevedAce");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SleevedAce.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SleevedAce.png"));

    public SleevedAce() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }

    @Override
    public void atBattleStart() {
        AbstractCard q = new Cheat();
        CardModifierManager.addModifier(q, new RetainMod());
        addToBot(new MakeTempCardInHandAction(q));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
