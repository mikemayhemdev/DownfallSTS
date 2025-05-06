package awakenedOne.cards;

import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;

import static awakenedOne.AwakenedOneMod.makeID;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.applyToSelf;

public class Victuals extends AbstractAwakenedCard {
    public final static String ID = makeID(Victuals.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1
    boolean chant = false;

    public Victuals() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 5;
        baseSecondMagic = secondMagic = 2;
        //this.tags.add(CardTags.HEALING);
        this.exhaust = true;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!this.chant) {

            if (!AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
                this.addToTop(new LoseHPAction(p, p, magicNumber));
            }

            if (AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
                if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == 1)) {
                    this.addToTop(new LoseHPAction(p, p, magicNumber));
                }
            }
        }

                if (!upgraded)
                    this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 1), 1));
                if (upgraded)
                    this.addToBot(new ApplyPowerAction(p, p, new EnergizedBluePower(p, 2), 2));
                this.addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, secondMagic), secondMagic));
                if (this.chant) {
                    chant();
                }

                if ((!this.chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
                    if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                        chant();
                        awaken(1);
                    }
                }
            }

    @Override
    public void chant() {
        //this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new RepairPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
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



    public void upp() {
    }
}