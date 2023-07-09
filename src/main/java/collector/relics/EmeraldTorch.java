package collector.relics;

import basemod.abstracts.CustomRelic;
import basemod.helpers.CardPowerTip;
import collector.CollectorMod;
import collector.actions.GainReservesAction;
import collector.cards.Ember;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import downfall.util.TextureLoader;

import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class EmeraldTorch extends CustomRelic {
    public static final String ID = CollectorMod.makeID("EmeraldTorch");
    private static final String IMG_PATH = "EmeraldTorch.png";
    private static final String OUTLINE_IMG_PATH = "EmeraldTorch.png";

    public EmeraldTorch() {
        super(ID, TextureLoader.getTexture(CollectorMod.makeRelicPath(IMG_PATH)), TextureLoader.getTexture(CollectorMod.makeRelicOutlinePath(OUTLINE_IMG_PATH)), RelicTier.STARTER, LandingSound.MAGICAL);
        this.tips.add(new CardPowerTip(new Ember()));
    }

    @Override
    public void atBattleStart() {
        flash();
        makeInHand(new Ember());
        this.grayscale = false;
        this.usedUp = false;
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card instanceof Ember){
            if (!usedUp){
                atb(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                this.grayscale = true;
                this.usedUp = true;
                atb(new GainReservesAction(1));
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}

