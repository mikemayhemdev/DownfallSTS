package champ.cards;

import champ.ChampMod;
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
        magicNumber = baseMagicNumber = 1;
       // tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
       // techique();
        blck();
        applyToSelf(new BlurPower(p, magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
        upgradeBlock(UPG_BLOCK);
    }
}