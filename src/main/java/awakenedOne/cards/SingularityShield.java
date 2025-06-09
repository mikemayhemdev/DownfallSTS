package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.relics.KTRibbon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.HexCurse;
import static awakenedOne.ui.AwakenButton.awaken;

public class SingularityShield extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(SingularityShield.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public SingularityShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        baseBlock = 8;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        this.exhaust = false;
        if (chant) {
            HexCurse(magicNumber, m, p);
            checkOnChant();
        }

        if ((!chant) && AbstractDungeon.player.hasRelic(KTRibbon.ID)) {
            if ((AbstractDungeon.player.getRelic(KTRibbon.ID).counter == -1)) {
                HexCurse(magicNumber, m, p);
                checkOnChant();
                awaken(1);
            }
        }

    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = chant ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}