package theHexaghost.cards.seals;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.PotionPostCombatPower;

@NoCompendium
public class FourthSeal extends AbstractSealCard {
    public final static String ID = makeID("FourthSeal");

    public FourthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        HexaMod.loadJokeCardImage(this, "FourthSeal.png");
        tags.add(AbstractCard.CardTags.HEALING);
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new PotionPostCombatPower(1));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
//            rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
        }
    }
}