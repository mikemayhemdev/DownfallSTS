package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;

public class PowerFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("PowerFromBeyond");

    //stupid intellij stuff SKILL, NONE, UNCOMMON

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public PowerFromBeyond() {
        super(ID, -2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = "This power is too distant to directly channel.";
        return false;
    }

    @Override
    public void triggerOnExhaust() {
        applyToSelf(new EnergizedBluePower(AbstractDungeon.player, magicNumber));
        applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber + 1));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}