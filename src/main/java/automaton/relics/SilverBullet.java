package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.WhirlingStrike;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class SilverBullet extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("SilverBullet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilverBullet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilverBullet.png"));

    public SilverBullet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        tips.add(new CardPowerTip(new WhirlingStrike()));
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        WhirlingStrike freebullet=new WhirlingStrike();
        freebullet.freeToPlayOnce=true;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(freebullet, 1));

//        addToBot(new AddToFuncAction(new WhirlingStrike(), null));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
