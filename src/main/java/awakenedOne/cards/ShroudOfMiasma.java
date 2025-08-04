package awakenedOne.cards;

import awakenedOne.powers.ManaburnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import hermit.util.Wiz;

import static awakenedOne.AwakenedOneMod.*;

public class ShroudOfMiasma extends AbstractAwakenedCard {
    public final static String ID = makeID(ShroudOfMiasma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    //Shroud of Miasma

    public ShroudOfMiasma() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 9;
        //this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(ShroudOfMiasma.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(q -> {
            Wiz.applyToEnemy(q, new WeakPower(q, magicNumber, false));
            Wiz.applyToEnemy(q, new ManaburnPower(q, secondMagic));
        });
    }

    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(3);
    }
}