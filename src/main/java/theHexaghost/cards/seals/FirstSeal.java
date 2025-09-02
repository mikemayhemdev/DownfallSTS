package theHexaghost.cards.seals;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import com.megacrit.cardcrawl.powers.RepairPower;

@NoCompendium
public class FirstSeal extends AbstractSealCard {

    public final static String ID = makeID("FirstSeal");

    //stupid intellij stuff POWER, SELF, UNCOMMON

    public static final int MAGIC = 7;

    public FirstSeal() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(AbstractCard.CardTags.HEALING);
        HexaMod.loadJokeCardImage(this, "FirstSeal.png");
    }

    @Override
    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new RepairPower(p, magicNumber));
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
