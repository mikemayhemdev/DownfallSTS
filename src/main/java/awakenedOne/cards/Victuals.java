package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;

public class Victuals extends AbstractAwakenedCard {
    public final static String ID = makeID(Victuals.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Victuals() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 6;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath("Victuals.png"));
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
        //addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, magicNumber), magicNumber));
        atb(new DrawCardAction(AbstractDungeon.player, this.magicNumber));
        checkOnChant();
    }


    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeBlock(3);
    }
}