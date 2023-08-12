package collector.cards;

import collector.effects.PurpleSearingBlowEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.makeInHand;

public class GreatestHurting extends AbstractCollectorCard {
    public final static String ID = makeID(GreatestHurting.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 10, 2, , , 14, 2

    public GreatestHurting() {
        super(ID, 3, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = 30;
        isEthereal = true;
        cardsToPreview = new Ember();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new PurpleSearingBlowEffect(m.hb.cX, m.hb.cY, 9)));
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void triggerOnExhaust() {
        makeInHand(new Ember());
    }

    public void upp() {
        upgradeDamage(12);
    }
}