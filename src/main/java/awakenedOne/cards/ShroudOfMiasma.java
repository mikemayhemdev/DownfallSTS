package awakenedOne.cards;

import awakenedOne.powers.FeathersinksPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class ShroudOfMiasma extends AbstractAwakenedCard {
    public final static String ID = makeID(ShroudOfMiasma.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    //Shroud of Miasma

    public ShroudOfMiasma() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseBlock = 13;
        loadJokeCardImage(this, makeBetaCardPath(ShroudOfMiasma.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //applyToSelf(new FeathersinksPower(magicNumber));
        blck();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead && !monster.isDying) {
                addToBot(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.LIGHTNING));
                HexCurse(magicNumber, monster, p);
            }
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}