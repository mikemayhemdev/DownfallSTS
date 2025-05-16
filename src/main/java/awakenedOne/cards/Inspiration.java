package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.CardIgnore;

import static awakenedOne.util.Wiz.makeInHand;
@Deprecated
@CardIgnore
public class Inspiration extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Inspiration.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Inspiration() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
        cardsToPreview = new Insight();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard) AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 2)).type == CardType.POWER) {
            AbstractCard q = new Insight();
            if (upgraded) q.upgrade();
            makeInHand(q);
        }
    }

    public void triggerOnGlowCheck() {
        if (!AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty() && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat.get(AbstractDungeon.actionManager.cardsPlayedThisCombat.size() - 1)).type == CardType.POWER) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}