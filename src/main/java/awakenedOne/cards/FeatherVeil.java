package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class FeatherVeil extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(FeatherVeil.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public FeatherVeil() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 10;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, 0-magicNumber), 0-magicNumber));
    }


    @Override
    public void upp() {
        upgradeBlock(3);
    }
}