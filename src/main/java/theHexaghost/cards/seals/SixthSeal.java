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
        HexaMod.loadJokeCardImage(this, "SixthSeal.png");
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.canUpgrade()) {
                count_cards++;
            }
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        magicNumber = count_cards / 13;
        if( magicNumber <= 1){
            this.rawDescription = rawDescription + this.EXTENDED_DESCRIPTION[0];
        }else{
            this.rawDescription = rawDescription + this.EXTENDED_DESCRIPTION[1];
        }
        this.initializeDescription();
    }

    public void realUse(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new FutureUpgradePower(magicNumber));
    }
}