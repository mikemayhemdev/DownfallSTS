package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.EasyXCostAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.att;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    public Grimoire() {
        super(ID, -1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                new ConjureAction(false);
            }
            return true;
        }));
        new ConjureAction(false);
    }


    public void upp() {
        this.exhaust = false;
    }
}