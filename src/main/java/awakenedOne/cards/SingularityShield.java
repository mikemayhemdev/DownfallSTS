package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.HexCurse;

public class SingularityShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SingularityShield.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SingularityShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 6;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        HexCurse(magicNumber, m, p);
    }


    @Override
    public void upp() {
        upgradeBlock(3);
        upgradeMagicNumber(1);
    }
}