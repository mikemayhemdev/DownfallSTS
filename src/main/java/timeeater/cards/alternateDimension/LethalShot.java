package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import timeeater.util.Wiz;

import static timeeater.TimeEaterMod.makeID;

public class LethalShot extends AbstractDimensionalCard {
    public final static String ID = makeID("LethalShot");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public LethalShot() {
        super(ID, 2, CardType.ATTACK, CardTarget.ENEMY);
        baseDamage = 21;
        baseMagicNumber = magicNumber = 11;
        exhaust = true;


        setFrame("lethalshotframe.png");
    }



    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        Wiz.applyToEnemy(m, new PoisonPower(m, p, magicNumber));
        Wiz.applyToEnemy(m, new VulnerablePower(m, 1, false));
    }

    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(3);
    }
}