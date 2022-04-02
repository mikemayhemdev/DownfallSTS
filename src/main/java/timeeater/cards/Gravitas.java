package timeeater.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.colorless.DramaticEntrance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.actions.SuspendAction;
import timeeater.cards.AbstractTimeEaterCard;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class Gravitas extends AbstractTimeEaterCard {
    public final static String ID = makeID("Gravitas");
    // intellij stuff attack, all_enemy, basic, 8, 4, , , , 

    public Gravitas() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ALL_ENEMY);
        baseDamage = 8;
        isMultiDamage = true;
        cardsToPreview = new DramaticEntrance();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.POISON);
        atb(new SuspendAction(new DramaticEntrance()));
    }

    public void upp() {
        upgradeDamage(4);
    }
}