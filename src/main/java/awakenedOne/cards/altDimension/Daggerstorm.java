package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.DaggerstormPower;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import awakenedOne.cards.AbstractAwakenedCard;
import static awakenedOne.util.Wiz.applyToSelf;


@NoCompendium
@NoPools
public class Daggerstorm extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Daggerstorm.class.getSimpleName());

    public Daggerstorm() {
        super(ID, 2, CardRarity.RARE, CardType.POWER, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;

        frameString = "roguebook";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DaggerstormPower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}