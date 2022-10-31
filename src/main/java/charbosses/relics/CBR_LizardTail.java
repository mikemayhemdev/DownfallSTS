package charbosses.relics;

import downfall.util.TextureLoader;
import charbosses.bosses.AbstractCharBoss;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.vfx.combat.HealEffect;
import downfall.downfallMod;

public class CBR_LizardTail extends AbstractCharbossRelic {

    public static String ID = downfallMod.makeID("LizardTail");
    private static RelicTier tier = RelicTier.BOSS;
    private static LandingSound sound = LandingSound.MAGICAL;


    public CBR_LizardTail() {
        super(ID,"lizardTail.png", tier, sound);
        this.largeImg = null;

    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void setCounter(final int setCounter) {
        if (setCounter == -2) {
            this.usedUp();
            this.counter = -2;
            this.grayscale = true;
        }
    }

    @Override
    public void onTrigger() {
        this.flash();
        this.addToTop(new RelicAboveCreatureAction(AbstractCharBoss.boss, this));
        int healAmt = AbstractCharBoss.boss.maxHealth;
        if (healAmt < 1) {
            healAmt = 1;
        }
        usedUp();
        AbstractCharBoss.boss.heal(healAmt, true);
        AbstractDungeon.effectsQueue.add(new HealEffect(this.hb.cX, this.hb.cY, healAmt));

        this.setCounter(-2);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_LizardTail();
    }
}
