package downfall.cards.curses;

import collector.cards.AbstractCollectorCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import static collector.CollectorMod.makeID;

public class Sapped extends AbstractCollectorCard {
    public final static String ID = makeID(Sapped.class.getSimpleName());
    // intellij stuff skill, none, special, , , , , 1, 1

    public Sapped() {
        super(ID, 1, CardType.CURSE, CardRarity.CURSE, CardTarget.NONE, CardColor.CURSE);
        baseMagicNumber = magicNumber = 1;
        isPyre();
        exhaust = true;
        tags.add(expansionContentMod.UNPLAYABLE);
        SoulboundField.soulbound.set(this, true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}