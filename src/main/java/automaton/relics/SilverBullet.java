package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.FineTuning;
import automaton.cards.WhirlingStrike;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import expansioncontent.cardmods.EtherealMod;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class SilverBullet extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("SilverBullet");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("SilverBullet.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("SilverBullet.png"));

    public SilverBullet() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.MAGICAL);
        AbstractCard q = new WhirlingStrike();
        q.upgrade();
        tips.add(new CardPowerTip(q));
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        WhirlingStrike freebullet=new WhirlingStrike();
        freebullet.upgrade();
        CardModifierManager.addModifier(freebullet, new EtherealMod());
        freebullet.freeToPlayOnce=true;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(freebullet, 1));

//        addToBot(new AddToFuncAction(new WhirlingStrike(), null));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
