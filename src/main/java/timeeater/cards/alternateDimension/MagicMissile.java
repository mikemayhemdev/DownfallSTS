package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.powers.MagicMissilePower;
import timeeater.util.Wiz;

import static timeeater.TimeEaterMod.makeID;

public class MagicMissile extends AbstractDimensionalCard {
    public final static String ID = makeID("MagicMissile");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public MagicMissile() {
        super(ID, 1, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 5;
        baseMagicNumber = magicNumber = 1;
        setFrame("magicmissileframe.png");
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.LIGHTNING);
        Wiz.applyToSelf(new MagicMissilePower(magicNumber));
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
    }

    public void upp() {
        upgradeDamage(1);
        upgradeMagicNumber(1);
    }
}