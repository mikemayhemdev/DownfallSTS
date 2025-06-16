package awakenedOne.cards;

import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.makeID;

public class HeavyStrike extends AbstractAwakenedCard {
    public final static String ID = makeID(HeavyStrike.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1

    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);
    //carrionmaker
    public HeavyStrike() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 10;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = (int) AbstractDungeon.actionManager.cardsPlayedThisTurn.stream()
                .filter(card -> card instanceof AbstractSpellCard)
                .count();

        for (int i = 0; i < count+1; i++) {
            this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
        upgradeDamage(3);
    }
}