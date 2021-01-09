package automaton.cards;

import automaton.AutomatonMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkDash extends AbstractBronzeCard {

    public final static String ID = makeID("DarkDash");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 8;
    private static final int BLOCK = 8;

    public DarkDash() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
        cardsToPreview = new VoidCard();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        blck();
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            shuffleIn(new VoidCard());
        }
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}