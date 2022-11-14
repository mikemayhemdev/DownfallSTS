package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

import static champ.ChampMod.loadJokeCardImage;

public class Refreshment extends AbstractChampCard {

    public final static String ID = makeID("Refreshment");

    //stupid intellij stuff power, self, uncommon

    public Refreshment() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        tags.add(CardTags.HEALING);
        exhaust = true;
        loadJokeCardImage(this, "CalledShot.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new HealAction(p, p, magicNumber));
    }


    public void upp() {
        upgradeMagicNumber(2);

    }
}
