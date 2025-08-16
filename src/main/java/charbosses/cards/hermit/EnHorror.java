package charbosses.cards.hermit;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.cards.HoleUp;
import hermit.cards.Horror;
import hermit.characters.hermit;
import hermit.powers.Bruise;
import hermit.powers.HorrorPower;

public class EnHorror extends AbstractHermitBossCard {
    public static final String ID = "downfall_Charboss:Horror";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(Horror.ID);

    public EnHorror() {
        super(ID, cardStrings.NAME, "hermitResources/images/cards/horror.png", 1, cardStrings.DESCRIPTION, CardType.SKILL, hermit.Enums.COLOR_YELLOW, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, AbstractMonster.Intent.DEBUFF);

        baseMagicNumber=magicNumber=3;
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {

        int total_bruise = this.magicNumber;
        this.addToBot(new ApplyPowerAction(p, m, new Bruise(p, total_bruise), total_bruise, true, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.addToBot(new ApplyPowerAction(p, m, new HorrorPower(p, 1), 1, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(2);
        }
    }


    @Override
    public AbstractCard makeCopy() {
        return new EnHorror();
    }
}
