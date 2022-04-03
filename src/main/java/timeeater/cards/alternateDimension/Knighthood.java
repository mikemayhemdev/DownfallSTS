package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import automaton.vfx.FineTuningEffect;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.atb;

public class Knighthood extends AbstractDimensionalCard {
    public final static String ID = makeID("Knighthood");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public Knighthood() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);

        setFrame("knighthoodframe.png");
        atb(new SelectCardsInHandAction(cardStrings.EXTENDED_DESCRIPTION[0], (cards) -> {
            AbstractCard q = cards.get(0);
            q.upgrade();

            AbstractDungeon.effectList.add(new FineTuningEffect(this, true));
            AbstractCard q2 = StSLib.getMasterDeckEquivalent(q);
            if (q2 != null){
                q2.upgrade();
            }
        }));

    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
    }

    public void upp() {
        upgradeDamage(3);
    }
}