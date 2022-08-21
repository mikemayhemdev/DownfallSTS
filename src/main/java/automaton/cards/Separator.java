package automaton.cards;

import automaton.AutomatonMod;
import automaton.FunctionHelper;
import automaton.cards.encodedcards.EncodedLeap;
import automaton.cards.encodedcards.EncodedTwinStrike;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Separator extends AbstractBronzeCard {

    public final static String ID = makeID("Separator");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;

    public Separator() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;

        tags.add(AutomatonMod.ENCODES);
        cardsToPreview = new EncodedTwinStrike();
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
        if (FunctionHelper.held.group.size() == 0){
            addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
        } else {
            addCardToFunction(new Strike(), upgraded);
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}