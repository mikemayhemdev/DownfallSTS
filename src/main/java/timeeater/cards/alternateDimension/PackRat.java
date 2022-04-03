package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;

public class PackRat extends AbstractDimensionalCard {
    public final static String ID = makeID("PackRat");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public PackRat() {
        super(ID, 1, CardType.SKILL, CardTarget.ENEMY);
        baseDamage = 6;

        setFrame("packratframe.png");
        exhaust = true;

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    public void upp() {
        upgradeDamage(3);
    }
}