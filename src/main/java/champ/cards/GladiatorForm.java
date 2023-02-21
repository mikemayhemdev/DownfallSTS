package champ.cards;

import basemod.helpers.BaseModCardTags;
import champ.powers.GladiatorFormPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class GladiatorForm extends AbstractChampCard {

    public final static String ID = makeID("GladiatorForm");

    //stupid intellij stuff power, self, rare

    public GladiatorForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
        baseMagicNumber = magicNumber = 1;
        postInit();
        loadJokeCardImage(this, "GladiatorForm.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new GladiatorFormPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}