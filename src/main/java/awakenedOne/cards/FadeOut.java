package awakenedOne.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;

public class FadeOut extends AbstractAwakenedCard {
    public final static String ID = makeID(FadeOut.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public FadeOut() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 8;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
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
        //upgradeBlock(2);
    }
}