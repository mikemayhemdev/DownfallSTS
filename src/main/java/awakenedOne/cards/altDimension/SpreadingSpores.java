package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import hermit.util.Wiz;
import awakenedOne.cards.AbstractAwakenedCard;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class SpreadingSpores extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SpreadingSpores.class.getSimpleName());

    public SpreadingSpores() {
        super(ID, 0, CardRarity.RARE, CardType.POWER, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        tags.add(CardTags.HEALING);
        isEthereal = true;

        frameString = "train";
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png",       "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");

        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToSelf(new ThornsPower(p, magicNumber));
        Wiz.atb(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, true, true));
    }

    public void upp() {
        upgradeMagicNumber(2);
    }
}