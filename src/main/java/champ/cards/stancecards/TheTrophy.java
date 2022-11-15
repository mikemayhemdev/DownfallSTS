package champ.cards.stancecards;

import champ.ChampMod;
import champ.cards.AbstractChampCard;
import champ.powers.TrophyPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.ChangeGoldAction;

public class TheTrophy extends AbstractChampCard {

    public final static String ID = makeID("TheTrophy");

    public TheTrophy() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        exhaust = true;
        tags.add(ChampMod.FINISHER);
        baseMagicNumber = magicNumber = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new TrophyPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);

        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}