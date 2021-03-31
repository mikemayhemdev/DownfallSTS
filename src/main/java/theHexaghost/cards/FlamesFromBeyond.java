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

    private static final int MAGIC = 15;
    private static final int UPG_MAGIC = 5;

    public FlamesFromBeyond() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        baseBurn = burn = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            burn(q, burn);
        }
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