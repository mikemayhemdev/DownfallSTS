package awakenedOne.cards;

import awakenedOne.actions.ConjureAction;
import awakenedOne.actions.EasyXCostAction;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.ReinforcedBodyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.atb;
import static awakenedOne.util.Wiz.att;

public class ArcaneNesting extends AbstractAwakenedCard {
    public final static String ID = makeID(ArcaneNesting.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArcaneNesting() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        att(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                atb(new ConjureAction(false));
            }
            return true;
        }));
        atb(new ConjureAction(false));
    }

    public void upp() {
        upgradeBlock(2);
    }
}