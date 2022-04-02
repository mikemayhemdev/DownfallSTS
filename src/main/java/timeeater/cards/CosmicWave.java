package timeeater.cards;

import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.powers.TurnBasedSlowPower;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class CosmicWave extends AbstractTimeEaterCard {
    public final static String ID = makeID("CosmicWave");
    // intellij stuff skill, all_enemy, common, , , , , 3, 1

    public CosmicWave() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 3;
        cardsToPreview = new Doubt();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(q -> {
            applyToEnemy(q, new WeakPower(q, magicNumber, false));
            applyToEnemy(q, new TurnBasedSlowPower(q, 1));
        });
        atb(new SuspendAction(new Doubt()));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}