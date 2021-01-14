package automaton.relics;

import automaton.AutomatonMod;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class DecasWashers extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("DecasWashers");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("DecasWashers.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("DecasWashers.png"));

    public DecasWashers() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.MAGICAL);
        tips.add(new CardPowerTip(new Dazed()));
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new DrawCardAction(AbstractDungeon.player, 3));
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
