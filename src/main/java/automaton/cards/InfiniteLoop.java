
package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedIronWave;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InfiniteLoop extends AbstractBronzeCard {

    public final static String ID = makeID("InfiniteLoop");

    //stupid intellij stuff attack, enemy, rare
    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;
    private boolean showCard;

    public InfiniteLoop() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        tags.add(AutomatonMod.ENCODES);

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addCardToFunction(new InfiniteLoop(), upgraded);
    }


    public void upp() {
        upgradeDamage(4);
    }
}

