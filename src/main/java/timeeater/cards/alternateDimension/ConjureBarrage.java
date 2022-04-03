package timeeater.cards.alternateDimension;

import automaton.cards.AbstractBronzeCard;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static timeeater.TimeEaterMod.makeID;

public class ConjureBarrage extends AbstractDimensionalCard {
    public final static String ID = makeID("ConjureBarrage");
    // intellij stuff attack, enemy, basic, 6, 3, , , ,

    public ConjureBarrage() {
        super(ID, 1, CardType.SKILL, CardTarget.SELF);

        setFrame("conjurebarrageframe.png");
        exhaust = true;
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c;
        c = new MagicMissile();
        if (upgraded){
            c.upgrade();
        }
        for (int i = 0; i < BaseMod.MAX_HAND_SIZE; i++) {
            if (AbstractDungeon.player.hand.group.size() < BaseMod.MAX_HAND_SIZE){
                    addToBot(new MakeTempCardInHandAction(c.makeCopy()));
            } else {
                break;
            }
        }

    }

    public void upp() {
        uDesc();
    }
}