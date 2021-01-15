package champ.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PetalEffect;

import java.util.ArrayList;

public class StanceDanceEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private float timer = 0.1F;
    private float timerFlip = 0F;
    private AbstractCreature owner;
    private boolean useJump;
    private boolean usePetals;
    private boolean useSpotlight;

    public StanceDanceEffect(AbstractCreature c, boolean jump, boolean petals, boolean spotlight) {
        this.duration = .65F;
        owner = c;
        useJump = jump;
        useSpotlight = spotlight;
        usePetals = petals;
    }

    public void update() {
        if (this.duration == .65F && useSpotlight) {
            AbstractDungeon.effectsQueue.add(new com.megacrit.cardcrawl.vfx.SpotlightEffect());
        }

        this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        this.timer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        this.timerFlip -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if (usePetals) {
            if (this.timer < 0.0F) {
                if (this.duration > 0.6F)
                    this.timer += 0.1F;
                AbstractDungeon.effectsQueue.add(new PetalEffect());
                //AbstractDungeon.effectsQueue.add(new PetalEffect());
            }
        }

        if (this.timerFlip < 0F){
            owner.flipHorizontal = !owner.flipHorizontal;
            this.timerFlip += 0.2F;
        }

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

