package champ.cards.choiceCards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnterGladiator extends AbstractChampCard {

    public final static String ID = makeID("EnterGladiator");

    public EnterGladiator() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ChampMod.gladiatorOpen();
    }

    @Override
    public void onChoseThisOption() {
        ChampMod.gladiatorOpen();
    }

    public void upp() {
    }
}