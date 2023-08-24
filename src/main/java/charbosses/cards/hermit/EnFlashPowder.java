package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import hermit.cards.FlashPowder;
import hermit.cards.Gestalt;
import hermit.characters.hermit;
import hermit.powers.Rugged;

public class EnFlashPowder extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:FlashPowder";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(FlashPowder.ID);

    public EnFlashPowder() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/flash_powder.png", 1,
                cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW,
                CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_DEBUFF);
        baseMagicNumber = magicNumber = 1;
        baseBlock = 5;
        exhaust=true;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        addToBot(new GainBlockAction(m, m, this.block));
        this.addToBot(new ApplyPowerAction(p, m, new StrengthPower(p, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.LIGHTNING));
    }

    @Override
    public void triggerOnExhaust() {
        if (AbstractCharBoss.boss != null && AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge)
            ((ArchetypeAct2WheelOfFateNewAge) AbstractCharBoss.boss.chosenArchetype).removeCardFromDeck(uuid);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            initializeDescription();
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnFlashPowder();
    }
}
