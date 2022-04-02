package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.green.Blur;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class DarkEnergy extends AbstractTimeEaterCard {
    public final static String ID = makeID("DarkEnergy");
    // intellij stuff attack, all_enemy, common, 7, 10, , , , 

    public DarkEnergy() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = 7;
        isMultiDamage = true;
        cardsToPreview = new Blur();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        atb(new SuspendAction(new Blur()));
    }

    public void upp() {
        upgradeDamage(3);
    }
}