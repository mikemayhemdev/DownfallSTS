package champ.cards;

import champ.stances.DefensiveStance;
import champ.stances.GladiatorStance;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;

public class HoldFirm extends AbstractChampCard {

    public final static String ID = makeID("HoldFirm");

    //stupid intellij stuff skill, self, rare

    private static final int BLOCK = 16;
    private static final int UPG_BLOCK = 4;

    public HoldFirm() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (upgraded || p.stance instanceof DefensiveStance) {
            return super.canUse(p, m);
        }
        cantUseMessage = "I'm not in that Stance.";
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new BlurPower(p, 1));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
        upgradeBlock(UPG_BLOCK);
    }
}