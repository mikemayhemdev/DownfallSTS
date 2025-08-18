package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.EncyclopediaAction;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoLibraryType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;

import awakenedOne.cards.AbstractAwakenedCard;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class TheEncyclopedia extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(TheEncyclopedia.class.getSimpleName());

    public TheEncyclopedia() {
        super(ID, 2, CardRarity.RARE, CardType.SKILL, CardTarget.SELF);
        exhaust = true;
        baseMagicNumber = magicNumber = 6;
        tags.add(CardTags.HEALING);

        frameString = "obelisk";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new EncyclopediaAction(magicNumber, cardStrings.EXTENDED_DESCRIPTION[0]));
    }

    public void upp() {
        upgradeMagicNumber(2);

    }

}