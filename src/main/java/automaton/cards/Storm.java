package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.EasyXCostAction;
import automaton.cards.encodedcards.EncodedEyeOfTheStorm;
import automaton.cards.encodedcards.EncodedPanacea;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Storm extends AbstractBronzeCard {

    public final static String ID = makeID("Storm");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = -1;

    public Storm() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        isMultiDamage = true;
        baseMagicNumber = magicNumber = MAGIC;
        // thisEncodes();
        exhaust = true;

        tags.add(AutomatonMod.ENCODES);
        cardsToPreview = new EncodedEyeOfTheStorm();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            if (effect >= magicNumber){
                addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
                exhaust = true;
            }
            for (int i = 0; i < effect; i++) {
                allDmg(AbstractGameAction.AttackEffect.LIGHTNING);
            }
            return true;
        }));

    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(UPG_MAGIC);
    }
}