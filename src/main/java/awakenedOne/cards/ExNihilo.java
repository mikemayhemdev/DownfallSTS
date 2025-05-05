package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;


public class ExNihilo extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(ExNihilo.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,
    boolean chant = false;

    public ExNihilo() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber = 2;
        this.magicNumber = this.baseMagicNumber;
        this.exhaust = true;
        //cardsToPreview = new VoidCard();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.exhaust = true;
        atb(new DrawCardAction(p, this.magicNumber));

        if (this.chant) {
            chant();
            this.exhaust = false;
        }

        if ((!this.chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if (!(AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                chant();
                this.exhaust = false;
                awaken(1);
            }
        }
        //addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), 1, false, true));
    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void triggerWhenDrawn() {
        this.chant = false;
    }

    public void triggerOnCardPlayed(AbstractCard card) {
        if (card.type == CardType.POWER && AbstractDungeon.player.hand.contains((AbstractCard)this))
            this.chant = true;
    }

    @Override
    public void onMoveToDiscard() {
        this.chant = false;
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
    }
}