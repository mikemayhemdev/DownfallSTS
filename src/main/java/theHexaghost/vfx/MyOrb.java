package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.GhostlyFireEffect;
import com.megacrit.cardcrawl.vfx.GhostlyWeakFireEffect;
import com.megacrit.cardcrawl.vfx.combat.GhostIgniteEffect;
import theHexaghost.GhostflameHelper;
import theHexaghost.ghostflames.AbstractGhostflame;

public class MyOrb {
    public static final String ID = "MyOrb";
    private BobEffect effect = new BobEffect(2.0F);
    private float activateTimer;
    public AbstractGhostflame myDaddy;
    public boolean charged = false;
    public boolean hidden = false;
    public boolean playedSfx = false;
    private Color color;
    private float x;
    private float y;
    private float particleTimer = 0.0F;

    public MyOrb(float x, float y, AbstractGhostflame pops) {
        this.x = x * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;// 32
        this.y = y * Settings.scale + MathUtils.random(-10.0F, 10.0F) * Settings.scale;// 33
        this.activateTimer = 1 * 0.3F;// 34
        this.color = Color.CHARTREUSE.cpy();// 35
        this.color.a = 0.0F;// 36
        this.hidden = false;// 37
        myDaddy = pops;
    }// 38

    public void charge() {
        this.playedSfx = false;// 44
        this.charged = true;// 45
        this.hidden = false;// 46
    }// 47

    public void update() {
        if (!this.hidden) {// 64
            if (this.charged) {// 65
                this.activateTimer -= Gdx.graphics.getDeltaTime();// 66
                if (this.activateTimer < 0.0F) {// 67
                    if (!this.playedSfx) {// 68
                        this.playedSfx = true;// 69
                        AbstractDungeon.effectsQueue.add(new GhostIgniteEffect(this.x, this.y));// 70
                        if (MathUtils.randomBoolean()) {// 71
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_1", 0.3F);// 72
                        } else {
                            CardCrawlGame.sound.play("GHOST_ORB_IGNITE_2", 0.3F);// 74
                        }
                    }

                    this.color.a = MathHelper.fadeLerpSnap(this.color.a, 1.0F);// 77
                    this.effect.update();// 78
                    this.effect.update();// 79
                    this.particleTimer -= Gdx.graphics.getDeltaTime();// 80
                    if (this.particleTimer < 0.0F) {// 81
                        AbstractDungeon.effectList.add(new GhostlyFireEffect(this.x + this.effect.y * 2.0F, this.y + this.effect.y * 2.0F));// 82
                        this.particleTimer = 0.06F;// 84
                    }
                }
            }
            if (GhostflameHelper.activeGhostFlame == myDaddy) {
                this.effect.update();// 88
                this.particleTimer -= Gdx.graphics.getDeltaTime();// 89
                if (this.particleTimer < 0.0F) {// 90
                    AbstractDungeon.effectList.add(new ActiveFireEffect(this.x + this.effect.y * 2.0F, this.y + this.effect.y * 2.0F));// 91
                    this.particleTimer = 0.06F;// 93
                }
            } else {
                this.effect.update();// 88
                this.particleTimer -= Gdx.graphics.getDeltaTime();// 89
                if (this.particleTimer < 0.0F) {// 90
                    AbstractDungeon.effectList.add(new GhostlyWeakFireEffect(this.x + this.effect.y * 2.0F, this.y + this.effect.y * 2.0F));// 91
                    this.particleTimer = 0.06F;// 93
                }
            }
        } else {
            this.color.a = MathHelper.fadeLerpSnap(this.color.a, 0.0F);// 97
        }

    }// 99
}
