package charbosses.cards.hermit;

import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Hermit.NewAge.ArchetypeAct2WheelOfFateNewAge;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import downfall.downfallMod;
import hermit.cards.Gestalt;
import hermit.powers.Rugged;

public class EnGestalt extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Gestalt";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Gestalt.ID);

    public EnGestalt() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/gestalt.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, downfallMod.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF, AbstractMonster.Intent.BUFF);
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, m, new Rugged(m, 2), 2));
        addToBot(new ApplyPowerAction(m, m, new VulnerablePower(m, magicNumber, true), magicNumber));
        if (AbstractCharBoss.boss.chosenArchetype instanceof ArchetypeAct2WheelOfFateNewAge) {
            ((ArchetypeAct2WheelOfFateNewAge) AbstractCharBoss.boss.chosenArchetype).usedGestalt = true;
        }
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-1);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnGestalt();
    }
}
