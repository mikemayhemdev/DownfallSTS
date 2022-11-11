package champ.cards.stancecards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.ChangeGoldAction;

public class TheTrophy extends AbstractChampCard {

    public final static String ID = makeID("TheTrophy");

    public TheTrophy() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        exhaust = true;
        tags.add(ChampMod.FINISHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //TODO - Special Trophy Power - The next card you play is upgraded permanently.
    }

    public void upp() {
        upgradeMagicNumber(5);
    }
}