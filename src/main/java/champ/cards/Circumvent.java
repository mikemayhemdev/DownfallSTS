package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class Circumvent extends AbstractChampCard {
    public final static String ID = makeID("Circumvent");

    public Circumvent() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 6;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBODEFENSIVE);
        tags.add(ChampMod.COMBO);
        postInit();
        loadJokeCardImage(this, "Circumvent.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new DrawCardAction(magicNumber));
        if (!dcombo()) atb(new DiscardAction(p, p, magicNumber, false));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = dcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeBlock(3);
    }
}