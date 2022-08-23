
package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedEntrench;
import automaton.cards.encodedcards.EncodedIronWave;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class Fortify extends AbstractBronzeCard {

    public final static String ID = makeID("Fortify");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 8;

    public Fortify() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new EncodedEntrench();
        tags.add(AutomatonMod.ENCODES);
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(AbstractDungeon.player, magicNumber));
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}

