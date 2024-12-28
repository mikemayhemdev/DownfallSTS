package automaton.cards;

import automaton.AutomatonMod;
import collector.powers.AddCopyNextTurnPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static automaton.AutomatonMod.makeBetaCardPath;
import static collector.util.Wiz.applyToSelf;

public class InfiniteLoop extends AbstractBronzeCard {

    public final static String ID = makeID("InfiniteLoop");

    //stupid intellij stuff attack, enemy, rare
    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private boolean showCard;

    public InfiniteLoop(boolean showCard) {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 6;
        thisEncodes();
        this.showCard = showCard;
        //if (showCard)
        //    cardsToPreview = new InfiniteLoop(false); //I had to.
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("InfiniteLoop.png"));
    }

    public InfiniteLoop() {
        this(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
    }

    @Override
    public String getSpecialCompileText() {
        return masterUI.TEXT[4] + magicNumber + masterUI.TEXT[5];
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            AbstractCard copyloop = makeStatEquivalentCopy();
            copyloop.baseDamage += this.magicNumber;
            applyToSelf(new AddCopyNextTurnPower(copyloop));
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }
}