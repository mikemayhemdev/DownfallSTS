package awakenedOne.cards;

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
        this.addToBot(new ReinforcedBodyAction(p, this.block, this.freeToPlayOnce, this.energyOnUse));

        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(card -> card instanceof AbstractSpellCard)
                .count();

        for (int i = 0; i < count; i++) {
        blck();
        }
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(card -> card instanceof AbstractSpellCard)
                .count();
        this.rawDescription = strings.DESCRIPTION;
        this.rawDescription = this.rawDescription + strings.EXTENDED_DESCRIPTION[0] + count;
        if (count == 1) {
            this.rawDescription += strings.EXTENDED_DESCRIPTION[1];
        }
        else {
            this.rawDescription += strings.EXTENDED_DESCRIPTION[2];
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = strings.DESCRIPTION;
        this.initializeDescription();
    }


    public void upp() {
        upgradeBlock(2);
    }
}