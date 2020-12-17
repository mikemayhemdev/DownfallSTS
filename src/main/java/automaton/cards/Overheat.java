package automaton.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Overheat extends AbstractBronzeCard {

    public final static String ID = makeID("Overheat");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 3;

    public Overheat() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        thisEncodes();
        cardsToPreview = new Burn();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        if (inFunc) {
            if (upgraded) {
                addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
            }
            else {
                shuffleIn(new Burn());
            }
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}