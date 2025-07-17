package awakenedOne.cards;

import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public Grimoire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.selfRetain = true;
        baseDamage = 6;
        baseMagicNumber = magicNumber = 6;
        loadJokeCardImage(this, makeBetaCardPath(Grimoire.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        this.addToTop(new ModifyDamageAction(this.uuid, this.magicNumber));
        spellCards.add(this);
        p.hand.removeCard(this);
        p.drawPile.removeCard(this);
        p.discardPile.removeCard(this);
        p.limbo.removeCard(this);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}