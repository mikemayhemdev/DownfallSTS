package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedDeadlyPoison;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class OilSpill extends AbstractBronzeCard {

    public final static String ID = makeID("OilSpill");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 2;


    public OilSpill() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        cardsToPreview = new EncodedDeadlyPoison();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }


    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}