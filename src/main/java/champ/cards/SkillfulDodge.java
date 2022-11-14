package champ.cards;

import champ.ChampMod;
import champ.actions.ModifyBlockAndMagicAction;
import champ.powers.CounterPower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class SkillfulDodge extends AbstractChampCard {

    public final static String ID = makeID("SkillfulDodge");

    public SkillfulDodge() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = 10;
        postInit();
        loadJokeCardImage(this, "SkillfulDodge.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new CounterPower(magicNumber));
        if (dcombo())
            applyToSelf(new CounterPower(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(4);
    }
}