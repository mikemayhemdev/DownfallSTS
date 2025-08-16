package awakenedOne.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import expansioncontent.expansionContentMod;

import static awakenedOne.AwakenedOneMod.*;


public class ArcaneNesting extends AbstractAwakenedCard {
    public final static String ID = makeID(ArcaneNesting.class.getSimpleName());
    // intellij stuff attack, enemy, common, 8, 3, , , 3, 1
    private static final CardStrings strings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ArcaneNesting() {
        super(ID, -2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 4;
        tags.add(expansionContentMod.UNPLAYABLE);
        loadJokeCardImage(this, makeBetaCardPath(ArcaneNesting.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains(this)) {
            flash(Color.BLUE.cpy());
            blck();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void upp() {
        //upgradeMagicNumber(1);
        upgradeBlock(2);
    }
}