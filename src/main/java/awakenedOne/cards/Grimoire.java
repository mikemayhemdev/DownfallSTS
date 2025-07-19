package awakenedOne.cards;

import awakenedOne.actions.GrimoireAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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
        this.addToBot(new GrimoireAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.uuid));
        this.addToTop(new ModifyDamageAction(this.uuid, this.magicNumber));
        spellCards.add(this);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}