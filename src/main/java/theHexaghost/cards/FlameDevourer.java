package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.actions.RetractAction;

public class FlameDevourer extends AbstractHexaCard{
    public final static String ID = makeID("FlameDevourer");
    public FlameDevourer() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        baseBlock = 12;
        baseMagicNumber = magicNumber = 4;
//        HexaMod.loadJokeCardImage(this, "BacktrackSmack.png");
    }

    @Override
    public void applyPowers() {
        if(GhostflameHelper.getPreviousGhostFlame().charged){
            int real_base_block = baseBlock;
            baseBlock += magicNumber;
            super.applyPowers();
            baseBlock = real_base_block;
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new RetractAction());
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(2);
            upgradeMagicNumber(2);
//            upgradeDamage(2);
        }
    }
}
