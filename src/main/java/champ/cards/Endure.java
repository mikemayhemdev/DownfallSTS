package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static champ.ChampMod.loadJokeCardImage;

public class Endure extends AbstractChampCard {
    public final static String ID = makeID("Endure");

    public Endure() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        tags.add(ChampMod.OPENER);

        this.tags.add(ChampMod.OPENERDEFENSIVE);
        baseBlock = block = 6;
        postInit();

        loadJokeCardImage(this, "Endure.png");

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        defenseOpen();
        blck();
    }

    public void applyPowersToBlock() {
        int realBaseBlock = this.baseBlock;
        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            baseBlock += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }
        if (!upgraded && AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            baseBlock -= AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        super.applyPowersToBlock();
        baseBlock = realBaseBlock;
        isBlockModified = block != baseBlock;
    }

    public void upp() {
        upgradeBlock(2);
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}