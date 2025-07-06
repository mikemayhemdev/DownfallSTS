package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.Ceremony;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;
import static awakenedOne.ui.AwakenButton.awaken;

public class DarkPower extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(DarkPower.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public DarkPower() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
        this.cardsToPreview = new Ceremony();
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath(DarkPower.class.getSimpleName() + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();

        if (isTrig_chant()) {
        chant();
    }

        if ((!isTrig_chant()) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                chant();
                awaken(1);
            }
        }
    }

    @Override
    public void chant() {
        AbstractCard c = new Ceremony();
        if (upgraded) {
            c.upgrade();
        }
        Wiz.atb(new MakeTempCardInHandAction(c, 1));
        checkOnChant();
    }


    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        this.cardsToPreview.upgrade();
    }
}