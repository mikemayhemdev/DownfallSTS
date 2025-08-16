package champ.relics;

import basemod.abstracts.CustomRelic;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import downfall.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

import static champ.ChampMod.makeRelicOutlinePath;
import static champ.ChampMod.makeRelicPath;

public class FightingForDummies extends CustomRelic {

    public static final String ID = ChampMod.makeID("FightingForDummies");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("FightingForDummies.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("FightingForDummies.png"));

    public FightingForDummies() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
    }
    public boolean firstTurn = false;
    public boolean activated = false;
    //Dolphin's Style Guide

    @Override
    public void atPreBattle() {
        firstTurn = true;
        activated = false;
    }

    @Override
    public void atTurnStartPostDraw() {
        if (!firstTurn) {
            if (activated) {
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            }
        }
        this.firstTurn = false;
        activated = false;
    }


    @Override
    public void onPlayerEndTurn() {
        if (AbstractDungeon.player.stance instanceof NeutralStance) {
            activated = true;
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
