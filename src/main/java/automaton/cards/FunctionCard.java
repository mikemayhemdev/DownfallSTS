package automaton.cards;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FunctionCard extends AbstractBronzeCard {
    public final static String ID = makeID("FunctionCard");

    public FunctionCard() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF_AND_ENEMY);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
    }

    @Override
    public void upp() {
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        //No Stack Overflow here.
    }

    @SpireOverride
    protected void renderBannerImage(SpriteBatch sb, float x, float y) {
        Color blah = (Color) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderColor");
        ReflectionHacks.setPrivate(this, AbstractCard.class, "renderColor", Color.GREEN.cpy());
        SpireSuper.call(sb, x, y);
        ReflectionHacks.setPrivate(this, AbstractCard.class, "renderColor", blah);
    }
}
