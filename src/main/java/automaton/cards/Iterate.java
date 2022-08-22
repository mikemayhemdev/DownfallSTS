package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedInsight;
import automaton.cards.encodedcards.EncodedPummel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Iterate extends AbstractBronzeCard {

    public final static String ID = makeID("Iterate");

    //stupid intellij stuff attack, all_enemy, common

    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    public Iterate() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        cardsToPreview = new EncodedPummel();

        tags.add(AutomatonMod.ENCODES);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        }
        addCardToFunction(cardsToPreview);

    }

    public void upp() {
        upgradeDamage(1);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}