package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.powers.SchemePower;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.util.Wiz;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class Scheme extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Scheme.class.getSimpleName());

    public Scheme() {

        super(ID, 1, CardRarity.RARE, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
        tags.add(CardTags.HEALING);


        frameString = "vault";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png", "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new SchemePower(magicNumber));

    }

    public void upp() {
        upgradeMagicNumber(1);

    }
}