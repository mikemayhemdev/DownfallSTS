package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.powers.ApplyBurnAtTurnStartOncePower;
import theHexaghost.powers.BurnPower;

public class FlamesFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("FlamesFromBeyond");

    //stupid intellij stuff SKILL, NONE, COMMON

    private static final int MAGIC = 12;
    private static final int UPG_MAGIC = 6;

    public FlamesFromBeyond() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseBurn = burn = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            burn(m, burn);
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            //upgradeMagicNumber(UPG_MAGIC);
            upgradeBurn(UPG_MAGIC);
        }
    }
}