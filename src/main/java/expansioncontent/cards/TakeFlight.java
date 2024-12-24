package expansioncontent.cards;


import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.relics.Sozu;
import expansioncontent.expansionContentMod;

import static expansioncontent.expansionContentMod.loadJokeCardImage;


public class TakeFlight extends AbstractExpansionCard {
    public final static String ID = makeID("TakeFlight");

    private static final int BLOCK = 14;
    private static final int UPGRADE_BLOCK = 5;
    private static final int MAGIC = 1;
    boolean chant = false;

    public TakeFlight() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        tags.add(expansionContentMod.STUDY_AWAKENEDONE);
        tags.add(expansionContentMod.STUDY);
        //todo skill bg instead of power bg
        this.setBackgroundTexture("expansioncontentResources/images/512/bg_boss_awakenedone.png", "expansioncontentResources/images/1024/bg_boss_awakenedone.png");
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = 1;
        loadJokeCardImage(this, "TakeFlight.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            blck();
            if (chant == true) {
                applyToSelf(new BlurPower(p, magicNumber));
        }
    }

    public void triggerWhenDrawn() {
        chant = false;
    }


    @Override
    public void triggerOnCardPlayed(AbstractCard card) {
        if ((card.type == CardType.POWER) && AbstractDungeon.player.hand.contains(this)) {
        chant = true;
        }
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractRelic runicpyramid = AbstractDungeon.player.getRelic(RunicPyramid.ID);
        if (runicpyramid != null) {
            chant = false;
        } else {
        //change nothing
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}


