package awakenedOne.cards.altDimension;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.AbstractAwakenedCard;
import awakenedOne.cards.tokens.PlumeJab;
import awakenedOne.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import sneckomod.SneckoMod;

@NoCompendium
@NoPools
public class Mantis extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Mantis.class.getSimpleName());

    public Mantis() {
        super(ID, 1, CardRarity.SPECIAL, CardType.SKILL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        frameString = "inscryp";
        tags.add(CardTags.HEALING);
        this.setBackgroundTexture("awakenedResources/images/512/dimension/" + frameString + "/" + getTypeName() + ".png", "awakenedResources/images/1024/dimension/" + frameString + "/" + getTypeName() + ".png");
        cardsToPreview = new PlumeJab();
        this.tags.add(SneckoMod.BANNEDFORSNECKO);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        Wiz.makeInHand(new PlumeJab(), 1);

    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}