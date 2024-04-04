package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class MyOrb {
    public static final String ID = "MyOrb";
    public AbstractGhostflame myDaddy;
    public Hitbox mom;
    public boolean charged = false; // controls whether to set fire particle effect to the flame (myDaddy here)
    public boolean hidden = false;
    public boolean playedSfx = false;
    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;

    public MyOrb(float x, float y, AbstractGhostflame pops, Hitbox mommy) {
        this.x = x;
        this.y = y;
        this.activateTimer = 1 * 0.3F;
        this.color = Color.CHARTREUSE.cpy();
        this.color.a = 0.0F;
        this.hidden = false;
        myDaddy = pops;
        mom = mommy;
    }

    public void charge() {
        this.playedSfx = false;
        this.charged = true;
        this.hidden = false;
    }

    public void update() {
        x = mom.cX;
        y = mom.cY;
        if (!this.hidden) {
            if (this.charged) {
                this.activateTimer -= Gdx.graphics.getDeltaTime();
                if (this.activateTimer < 0.0F) {
                    if (!this.playedSfx) {
                        this.playedSfx = true;
                        AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(this.x, this.y));
                        if (MathUtils.randomBoolean()) {
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);
                        } else {
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);
                        }
                    }

                    this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);
                   // this.effect.update();  78
                   // this.effect.update();  79
                    this.particleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.particleTimer < 0.0F) {
                        AbstractDungeon.effectList.add(new GhostlyFireEffect(this.x, this.y));
                        this.particleTimer = 0.06F;
                    }
                }
            } else if (GhostflameHelper.activeGhostFlame == myDaddy) {
                //this.effect.update();
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    AbstractDungeon.effectList.add(new ActiveFireEffect(this.x, this.y, myDaddy.getActiveColor()));
                    this.particleTimer = 0.06F;
                }
            } else {
               // this.effect.update();
                this.particleTimer -= Gdx.graphics.getDeltaTime();
                if (this.particleTimer < 0.0F) {
                    AbstractDungeon.effectList.add(new GhostlyWeakColorableFireEffect(this.x, this.y, myDaddy.getFlameColor()));
                    this.particleTimer = 0.06F;
                }
            }
        } else {
            this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.0F);
        }

    }
}
