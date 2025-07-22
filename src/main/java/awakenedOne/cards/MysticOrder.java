package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.powers.RageExhaustPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.util.Wiz.atb;

public class MysticOrder extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(MysticOrder.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public MysticOrder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(MysticOrder.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        atb(new ConjureAction(false));
        //this.addToBot(new ApplyPowerAction(p, p, new RageExhaustPower(p, this.magicNumber), this.magicNumber));
    }


    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}