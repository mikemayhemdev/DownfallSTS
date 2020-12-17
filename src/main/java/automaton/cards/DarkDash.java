package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DarkDash extends AbstractBronzeCard {

    public final static String ID = makeID("DarkDash");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 10;
    private static final int BLOCK = 10;

    public DarkDash() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        blck();
        if (inFunc) {
            if (upgraded) {
                atb(new MakeTempCardInDiscardAction(new VoidCard(), 1));
            }
            else {
                shuffleIn(new VoidCard());
            }
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}