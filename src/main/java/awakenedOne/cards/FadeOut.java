package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;

public class FadeOut extends AbstractAwakenedCard {
    public final static String ID = makeID(FadeOut.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public FadeOut() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        this.baseSecondMagic = 3;
        this.secondMagic = this.baseSecondMagic;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int temp = 0;

        if (AbstractDungeon.player.hasPower(StrengthPower.POWER_ID)) {
            temp += AbstractDungeon.player.getPower(StrengthPower.POWER_ID).amount;
        }

        if (temp > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, -temp), -temp));
        }

        if (!p.hasPower(ArtifactPower.POWER_ID)) {
            if (temp > 3) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, (magicNumber * temp) / secondMagic), (magicNumber * temp) / secondMagic));
            }
        }

    }

    public void upp() {
        upgradeBaseCost(1);
    }

}