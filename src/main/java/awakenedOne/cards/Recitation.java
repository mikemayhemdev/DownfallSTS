package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import awakenedOne.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.ui.AwakenButton.awaken;
import static awakenedOne.util.Wiz.atb;

public class Recitation extends AbstractAwakenedCard {

    public final static String ID = AwakenedOneMod.makeID(Recitation.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Recitation() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 7;
        loadJokeCardImage(this, ID+".png");
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);

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
        atb(new GainEnergyAction(1));
        checkOnChant();
    }


    @Override
    public void upp() {
        upgradeDamage(4);
    }
}