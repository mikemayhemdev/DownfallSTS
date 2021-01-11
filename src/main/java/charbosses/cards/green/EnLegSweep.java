package charbosses.cards.green;

import charbosses.cards.AbstractBossCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.LegSweep;
import com.megacrit.cardcrawl.cards.green.Slice;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import java.util.ArrayList;

public class EnLegSweep extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:Leg Sweep";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(LegSweep.ID);
    }

    public EnLegSweep() {
        super(ID, EnLegSweep.cardStrings.NAME, "green/skill/leg_sweep", 2, EnLegSweep.cardStrings.DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.UNCOMMON, CardTarget.ENEMY, AbstractMonster.Intent.DEFEND_DEBUFF);
        this.baseBlock = 11;
        this.baseMagicNumber = magicNumber = 2;
        artifactConsumedIfPlayed = 1;
    }

    @Override
    public int getPriority(ArrayList<AbstractCard> hand) {
        return autoPriority() + 3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, m, new WeakPower(p, this.magicNumber + 1, false), this.magicNumber + 1));
        this.addToBot(new GainBlockAction(m, m, this.block));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBlock(3);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnLegSweep();
    }
}
