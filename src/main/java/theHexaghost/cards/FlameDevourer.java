package theHexaghost.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.GhostflameHelper;
import theHexaghost.actions.RetractAction;

public class FlameDevourer extends AbstractHexaCard{
    //Devour Flame
    public final static String ID = makeID("FlameDevourer");
    public FlameDevourer() {
        super(ID, 1, CardType.SKILL, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        baseBlock = 10;
        baseMagicNumber = magicNumber = 6;
//        HexaMod.loadJokeCardImage(this, "BacktrackSmack.png");
    }

    @Override
    public void applyPowers() {
        if((GhostflameHelper.getPreviousGhostFlame()).charged){
            int real_base_block = this.baseBlock;
            this.baseBlock += this.magicNumber;
            super.applyPowers();
            this.baseBlock = real_base_block;
            this.isBlockModified = this.block != this.baseBlock;
        }else{
            super.applyPowers();
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if((GhostflameHelper.getPreviousGhostFlame()).charged){
            int real_base_block = this.baseBlock;
            this.baseBlock += this.magicNumber;
            super.calculateCardDamage(mo);
            this.baseBlock = real_base_block;
            this.isBlockModified = this.block != this.baseBlock;
        }else{
            super.calculateCardDamage(mo);
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
        }
    }
}
