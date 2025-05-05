package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.util.Wiz.atb;

public class LastDitchEffort extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(LastDitchEffort.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public LastDitchEffort() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 7;
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (checkVoid()) {
            atb(new DrawCardAction(p, this.magicNumber));
        }
    }

    public static boolean checkVoid() {
        boolean hasVoid = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof VoidCard) {
                hasVoid = true;
            }
        }
        return hasVoid;
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkVoid()) {
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