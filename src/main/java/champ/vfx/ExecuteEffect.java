package champ.vfx;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.PetalEffect;

import java.util.ArrayList;

public class ExecuteEffect extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    private float timer = 0.1F;
    private float timerFlip = 0F;
    private AbstractCreature owner;
    private boolean useJump;

    public ExecuteEffect(AbstractCreature c, boolean jump) {
        this.duration = .65F;
        owner = c;
        useJump = jump;
    }

    public void update() {
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}

