package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.common.BetterDiscardPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.atb;

public class Gather extends AbstractAwakenedCard {
    public final static String ID = makeID(Gather.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 1, 1

    public Gather() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 3;
        magicNumber = baseMagicNumber = 2;
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath("Victuals.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (isTrig_chant()) {
            chant();
        }
    }

    @Override
    public void chant() {
        //addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, magicNumber), magicNumber));
        atb(new BetterDiscardPileToHandAction(1));
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