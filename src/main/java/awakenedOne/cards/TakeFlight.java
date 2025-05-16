package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.applyToSelf;

public class TakeFlight extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(TakeFlight.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public TakeFlight() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 13;
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
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
        applyToSelf((new BlurPower(AbstractDungeon.player, magicNumber)));
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = this.chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeBlock(4);
    }
}