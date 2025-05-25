package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;


import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.*;
import static collector.util.Wiz.applyToEnemy;

public class IntoNothing extends AbstractAwakenedCard {
    public final static String ID = makeID(IntoNothing.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public IntoNothing() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
        baseBlock = 5;
        this.tags.add(AwakenedOneMod.DELVE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if ((!monster.isDead) && (!monster.isDying)) {
                HexCurse(magicNumber, monster, p);
                applyToEnemy(monster, new WeakPower(monster, magicNumber, false));
            }
        }
        atb(new ConjureAction(false));
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}