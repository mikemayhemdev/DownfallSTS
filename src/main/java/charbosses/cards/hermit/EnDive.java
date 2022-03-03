package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.Dive;
import hermit.cards.GhostlyPresence;
import hermit.characters.hermit;

public class EnDive extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Dive";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Dive.ID);

    public EnDive() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/dive.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_BUFF);
        this.baseBlock = 7;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, block));
        if (this.owner.hasPower(HermitConcentrationPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(m, m, new PlatedArmorPower(m, magicNumber), magicNumber));
        }
    }

    @Override
    public void onSpecificTrigger() {
        intentActive = false;
        this.intent = AbstractMonster.Intent.DEFEND;
        createIntent();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnDive();
    }
}
