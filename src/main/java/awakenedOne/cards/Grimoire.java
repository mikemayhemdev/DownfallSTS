package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.cards.tokens.spells.AphoticShield;
import awakenedOne.ui.OrbitingSpells;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.OrbitingSpells.spellCards;
import static awakenedOne.ui.OrbitingSpells.updateTimeOffsets;

public class Grimoire extends AbstractAwakenedCard {
    public final static String ID = makeID(Grimoire.class.getSimpleName());
    // intellij stuff attack, enemy, uncommon, 9, 1, , , 3, 1

    public Grimoire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 7;
        baseMagicNumber = magicNumber = 7;
        this.exhaust = true;
        loadJokeCardImage(this, makeBetaCardPath(Grimoire.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        AbstractCard card = this.makeStatEquivalentCopy();
        this.addToBot(new ModifyDamageAction(card.uuid, this.magicNumber));
        spellCards.add(new OrbitingSpells.CardRenderInfo(card));
        updateTimeOffsets();
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}