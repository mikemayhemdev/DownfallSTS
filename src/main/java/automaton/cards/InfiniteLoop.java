package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class InfiniteLoop extends AbstractBronzeCard {

    public final static String ID = makeID("InfiniteLoop");

    //stupid intellij stuff attack, enemy, rare

    private boolean showCard;

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 2;

    public InfiniteLoop(boolean showCard) {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 1;
        thisEncodes();
        this.showCard = showCard;
        if (showCard)
            cardsToPreview = new InfiniteLoop(false); //I had to.
    }

    public InfiniteLoop() {
        this(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        if (forGameplay) {
            AbstractCard c = new InfiniteLoop();
            c.baseDamage += this.magicNumber;
            if (upgraded) c.upgrade();
            makeInHand(c);
        }
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}