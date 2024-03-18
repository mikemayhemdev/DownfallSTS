package theHexaghost.cards.seals;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.FutureUpgradePower;

public class SixthSeal extends AbstractSealCard {
    public final static String ID = makeID("SixthSeal");
    private static int count_cards = 0;
    public SixthSeal() {
        super(ID, 2, CardType.POWER, CardRarity.SPECIAL, CardTarget.SELF);
        baseMagicNumber = magicNumber = 0;
        HexaMod.loadJokeCardImage(this, "SixthSeal.png");
    }

    private void count(){
        count_cards = 0;
        if(AbstractDungeon.player.masterDeck.group != null){
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (c.canUpgrade()) {
                    count_cards++;
                }
            }
        }
    }

    @Override
    public void applyPowers() {
        count();
        int real_base_magic = baseMagicNumber;
        baseMagicNumber = magicNumber = count_cards / 13;
        if( magicNumber <= 1){
            this.rawDescription = this.EXTENDED_DESCRIPTION[0] + magicNumber + this.EXTENDED_DESCRIPTION[1] + DESCRIPTION;
        }else{
            this.rawDescription = this.EXTENDED_DESCRIPTION[0] + magicNumber + this.EXTENDED_DESCRIPTION[2] + DESCRIPTION;
        }
        this.initializeDescription();
        super.applyPowers();
        baseMagicNumber = real_base_magic;
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FutureUpgradePower(magicNumber));
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