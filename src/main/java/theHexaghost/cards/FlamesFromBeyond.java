package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.actions.BurnAction;
import theHexaghost.powers.ApplyBurnAtTurnStartOncePower;

public class FlamesFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("FlamesFromBeyond");

    //stupid intellij stuff SKILL, NONE, COMMON

    private static final int MAGIC = 8;
    private static final int UPG_MAGIC = 3;

    public FlamesFromBeyond() {
        super(ID, -2, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = "It's too hot to touch!";
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnExhaust() {
        applyToSelf(new ApplyBurnAtTurnStartOncePower(magicNumber));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
        }
    }
}