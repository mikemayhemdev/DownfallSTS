package awakenedOne.cards.tokens;

import awakenedOne.cards.AbstractAwakenedCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class Shadow extends AbstractAwakenedCard {
    public final static String ID = makeID(Shadow.class.getSimpleName());
    // intellij stuff curse, none, special, , , , , , 

    public Shadow() {
        super(ID, -2, CardType.CURSE, CardRarity.SPECIAL, CardTarget.NONE, CardColor.CURSE);
        selfRetain = true;
        SoulboundField.soulbound.set(this, true);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
    }
}