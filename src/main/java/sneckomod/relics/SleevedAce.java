package sneckomod.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardModifierManager;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import expansioncontent.cardmods.RetainCardMod;
import guardian.cards.GearUp;
import sneckomod.SneckoMod;
import sneckomod.cards.Cheat;
import downfall.util.TextureLoader;
import sneckomod.cards.MarkedCard;

public class SleevedAce extends CustomRelic {

    public static final String ID = SneckoMod.makeID("SleevedAce");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("SleevedAce.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("SleevedAce.png"));

    public SleevedAce() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        this.tips.add(new CardPowerTip(new MarkedCard()));
    }

    @Override
    public void atBattleStart() {
        AbstractCard q = new MarkedCard();
        addToBot(new MakeTempCardInHandAction(q));
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
