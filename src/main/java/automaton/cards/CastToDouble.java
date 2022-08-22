package automaton.cards;

import automaton.AutomatonMod;
import automaton.powers.CastToDoublePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DoubleTapPower;

public class CastToDouble extends AbstractBronzeCard {

    public final static String ID = makeID("CastToDouble");

    //stupid intellij stuff attack, enemy, basic


    public CastToDouble() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new CastToDoublePower(this.magicNumber), this.magicNumber));
    }

    public void upp() {
        upgradeName();
        upgradeMagicNumber(1);
        rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        initializeDescription();

    }
}