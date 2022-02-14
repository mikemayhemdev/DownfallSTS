package charbosses.cards.hermit;

import charbosses.cards.AbstractBossCard;
import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.GhostlyPresence;
import hermit.cards.ItchyTrigger;
import hermit.characters.hermit;

public class EnGhostlyPresence extends AbstractBossCard {
    public static final String ID = "downfall_Charboss:GhostlyPresence";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(GhostlyPresence.ID);

    public EnGhostlyPresence() {
        super(ID, cardStrings.DESCRIPTION, "hermitResources/images/cards/ghostly_presence.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.DEFEND_DEBUFF);
        this.baseBlock = 8;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new GainBlockAction(m, m, block));
        if (this.owner.hasPower(HermitConcentrationPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, m, new WeakPower(p, magicNumber, true), magicNumber));
        }
    }

    @Override
    public void onSpecificTrigger() {
        destroyIntent();
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
        return new EnGhostlyPresence();
    }
}
