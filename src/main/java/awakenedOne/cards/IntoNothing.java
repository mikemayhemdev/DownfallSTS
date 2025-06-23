package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.actions.ConjureAction;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.*;

public class IntoNothing extends AbstractAwakenedCard {
    public final static String ID = makeID(IntoNothing.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public IntoNothing() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 2;
        this.tags.add(AwakenedOneMod.DELVE);
        loadJokeCardImage(this, makeBetaCardPath(ID + ".png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        HexCurse(magicNumber, m, p);
        if (Wiz.isChantActive()) {
            chant();
        }

        if ((!Wiz.isChantActive()) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                chant();
                awaken(1);
            }
        }

    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void chant() {
        atb(new ConjureAction(false));
        checkOnChant();
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}