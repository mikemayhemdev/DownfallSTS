package automaton.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import sneckomod.util.ExhaustMod;

public class Repair extends AbstractBronzeCard {

    public final static String ID = makeID("Repair");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 2;

    public Repair() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        thisEncodes();
        tags.add(CardTags.HEALING);
        //Repair does not get the Negative Compile mod because then you could full heal.
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HealAction(p, p, magicNumber));
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        super.onCompile(function, forGameplay);
        if (forGameplay) {
            CardModifierManager.addModifier(function, new ExhaustMod());
        }
    }

    public void upp() {
        upgradeMagicNumber(UPG_MAGIC);
    }
}