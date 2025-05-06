package awakenedOne.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RitualPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.util.Wiz.applyToSelf;

public class Procession extends AbstractAwakenedCard {
    public final static String ID = makeID(Procession.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Procession() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 6;
        this.exhaust = true;
        cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ShuffleAllAction());
        this.addToBot(new ShuffleAction(AbstractDungeon.player.drawPile, false));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), magicNumber, true, true));
        this.addToBot(new DrawCardAction(p, secondMagic));
    }


    public void upp() {
        upgradeSecondMagic(2);
    }
}