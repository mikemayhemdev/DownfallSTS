package collector.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.powers.Bruise;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.*;

public class ThornWhip extends AbstractCollectorCard {
    public final static String ID = makeID(ThornWhip.class.getSimpleName());
    // intellij stuff attack, enemy, rare, 11, 1, , , , 

    public ThornWhip() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        baseDamage = 6;
        baseMagicNumber = 3;
        isMultiDamage = true;
        cardsToPreview = new Shiv();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        forAllMonstersLiving(q -> applyToEnemy(q, new Bruise(q, magicNumber)));
        makeInHand(new Shiv());
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(1);
    }
}