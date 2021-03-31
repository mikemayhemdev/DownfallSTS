package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedBluePower;
import theHexaghost.HexaMod;

public class PowerFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("PowerFromBeyond");

    //stupid intellij stuff SKILL, NONE, UNCOMMON

    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    public PowerFromBeyond() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
    }

    @Override
    public void triggerOnExhaust() {
        applyToSelf(new EnergizedBluePower(AbstractDungeon.player, 1));
        applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new EnergizedBluePower(AbstractDungeon.player, 1));
        applyToSelf(new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber));
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