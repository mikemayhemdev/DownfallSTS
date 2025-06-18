package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.EyeOfTheOccult;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ReboundPower;
import hermit.actions.ReduceCostActionFixed;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.ui.AwakenButton.awaken;

public class SingularityShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SingularityShield.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SingularityShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 7;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        HexCurse(magicNumber, m, p);
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}