package charbosses.cards.red;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FlameBarrierPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;

import java.util.ArrayList;

public class EnFlameBarrier extends AbstractBossCard {
    public static final String ID = "EvilWithin_Charboss:Flame Barrier";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings("Flame Barrier");
    }

    public EnFlameBarrier() {
        super("Flame Barrier", EnFlameBarrier.cardStrings.NAME, "red/skill/flame_barrier", 2, EnFlameBarrier.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = 12;
        this.baseMagicNumber = 4;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.1f));
        } else {
            this.addToBot(new VFXAction(m, new FlameBarrierEffect(m.hb.cX, m.hb.cY), 0.5f));
        }
        this.addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(m, m, new FlameBarrierPower(m, this.magicNumber), this.magicNumber));
    }


    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 4;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(4);
            this.upgradeMagicNumber(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnFlameBarrier();
    }
}
