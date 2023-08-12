package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.makeInHand;

public class Hurting extends AbstractCollectorCard {
    public final static String ID = makeID(Hurting.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 2, , , 14, 2

    public Hurting() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 10;
        isEthereal = true;
        cardsToPreview = new GreaterHurting();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE); //TODO: This should use varying levels of a purple variant of searing blow vfx
    }

    @Override
    public void triggerOnExhaust() {
        AbstractCard toAdd = new GreaterHurting();
        if (upgraded) toAdd.upgrade();
        makeInHand(toAdd);
    }

    public void upp() {
        upgradeDamage(4);
        uDesc();
        cardsToPreview.upgrade();
    }
}