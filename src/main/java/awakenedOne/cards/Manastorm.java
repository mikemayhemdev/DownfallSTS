package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.patches.EnumPatch;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;

public class Manastorm extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Manastorm.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Manastorm() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 14;
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(Manastorm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, EnumPatch.HERMIT_GHOSTFIRE));

        Iterator var2 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var2.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var2.next();
            if (!mo.isDeadOrEscaped()) {
                atb(new ConjureAction(false));
            }
        }

    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }
}