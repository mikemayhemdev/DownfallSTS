package champ.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.gladiatorOpen;
import static champ.ChampMod.loadJokeCardImage;

public class SigilOfWar extends AbstractChampCard {

    public final static String ID = makeID("SigilOfWar");

    public SigilOfWar() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, "SwordSigil.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        gladiatorOpen();
        atb(new DrawCardAction(magicNumber));
    }

    public void upp() {
        upgradeBaseCost(0);
        // upgradeCool(2);
    }
}