package champ.cards;

import champ.powers.CounterPower;
import champ.powers.ParryPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Endure extends AbstractChampCard {

    public final static String ID = makeID("Endure");

    //stupid intellij stuff skill, self, uncommon

    private static final int MAGIC = 7;

    public Endure() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = MAGIC;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)){
            block += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        if (!upgraded){
            if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)){
                block -= AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
            }
        }
    }

    public void upp() {
        upgradeBlock(3);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}