package automaton.cards;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PackRat extends AbstractBronzeCard {
    public final static String ID = makeID("PackRat");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public PackRat() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;

        this.setBackgroundTexture("timeResources/images/512/packratframe.png", "timeResources/images/1024/packrateframe.png");

    }


    @Override
    protected Texture getPortraitImage() {
        return null;
    }

    @SpireOverride
    protected void renderPortrait(SpriteBatch sb) {

        }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    public void upp() {
        upgradeDamage(3);
    }
}