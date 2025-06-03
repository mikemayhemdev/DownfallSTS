package awakenedOne.cards;

import automaton.actions.EasyXCostAction;
import awakenedOne.actions.ConjureAction;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;


public class ArcaneNesting extends AbstractAwakenedCard {
    public final static String ID = makeID(ArcaneNesting.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArcaneNesting() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 6;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                blck();
                atb(new ConjureAction(false));
            }
            return true;
        }));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }


    public void upp() {
        upgradeBlock(2);
    }
}