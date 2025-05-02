package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import downfall.util.CardIgnore;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
@Deprecated
@CardIgnore
public class Willpower extends AbstractAwakenedCard {

public final static String ID = makeID(Willpower.class.getSimpleName());
    // intellij stuff skill, self, basic, , , 5, 3, ,

    public Willpower() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractPower pow : AbstractDungeon.player.powers) {
            if (pow.type == AbstractPower.PowerType.DEBUFF) {
                blck();
                atb(new DrawCardAction(p, this.magicNumber));
            }
        }
    }

    public void upp() {
        upgradeBlock(3);
    }

}
