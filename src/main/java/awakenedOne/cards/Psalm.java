package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.NextPowerAOEPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.applyToSelf;

public class Psalm extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Psalm.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Psalm() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        magicNumber = baseMagicNumber = 2;
        loadJokeCardImage(this, makeBetaCardPath(Psalm.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (!monster.isDead && !monster.isDying) {
                HexCurse(magicNumber, monster, p);
            }
        }
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}