package champ.cards;

import champ.powers.UltimateFormPower;
import champ.stances.UltimateStance;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class PerfectForm extends AbstractChampCard {

    public final static String ID = makeID("PerfectForm");

    //stupid intellij stuff power, self, rare

    public PerfectForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ultimateStance();
        applyToSelf(new UltimateFormPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}