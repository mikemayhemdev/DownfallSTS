package charbosses.cards.hermit;

import charbosses.powers.bossmechanicpowers.HermitConcentrationPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.GhostlyPresence;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;

public class EnDesperado extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:GhostlyPresence";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(GhostlyPresence.ID);

    public EnDesperado() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/ghostly_presence.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.COMMON, CardTarget.SELF, AbstractMonster.Intent.ATTACK);
        this.baseDamage = 14;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new DamageAction(p, new DamageInfo(m, damage, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        addToBot(new ApplyPowerAction(m, m, new FrailPower(m, magicNumber, true), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(4);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnDesperado();
    }
}
