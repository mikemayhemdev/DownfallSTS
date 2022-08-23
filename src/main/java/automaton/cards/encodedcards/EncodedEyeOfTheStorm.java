//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package automaton.cards.encodedcards;

import automaton.cards.AbstractBronzeCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import downfall.util.CardIgnore;
import hermit.HermitMod;
import hermit.actions.EyeOfTheStormAction;
import hermit.characters.hermit;

import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

@CardIgnore
public class EncodedEyeOfTheStorm extends AbstractBronzeCard {
    public static final String ID = "bronze:EncodedEyeOfTheStorm";

    public EncodedEyeOfTheStorm() {
        super(ID, 1, HermitMod.makeCardPath("eye_of_the_storm.png"), CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF, hermit.Enums.COLOR_YELLOW);

        HermitMod.loadJokeCardImage(this, "eye_of_the_storm.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new EyeOfTheStormAction(p,this, getLogicalCardCost(this)));
    }

    private static int getLogicalCardCost(AbstractCard ccc) {
        if (ccc.costForTurn > 0 && !ccc.freeToPlayOnce) {
            return ccc.costForTurn;
        }
        return 0;
    }

    @Override
    public void upp() {
        upgradeName();
        upgradeBaseCost(0);
        initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new EncodedEyeOfTheStorm();
    }

}
