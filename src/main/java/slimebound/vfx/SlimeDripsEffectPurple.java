package slimebound.vfx;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class SlimeDripsEffectPurple extends com.megacrit.cardcrawl.vfx.AbstractGameEffect {
    public float xPos;
    public float yPos;
    private int count = 0;
    private float timer = 0.0F;


    public SlimeDripsEffectPurple(float x, float y, int count) {
        this.xPos = x;
        this.yPos = y;
        this.count = count;
    }

    public void dispose() {
        this.isDone = true;

    }

    public void update() {

        this.timer -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        if (this.timer < 0.0F) {

            this.timer += 0.15F;


            switch (this.count) {

                case 0:

                    CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);

                    AbstractDungeon.effectsQueue.add(new SlimeWaterDropEffectPurple(xPos, yPos + 50.0F * Settings.scale));


                    break;

                case 1:

                    AbstractDungeon.effectsQueue.add(new SlimeWaterDropEffectPurple(xPos + 50.0F * Settings.scale, yPos - 30.0F * Settings.scale));


                    break;

                case 2:

                    AbstractDungeon.effectsQueue.add(new SlimeWaterDropEffectPurple(xPos - 30.0F * Settings.scale, yPos + 50.0F * Settings.scale));


                    break;

                case 3:

                    AbstractDungeon.effectsQueue.add(new SlimeWaterDropEffectPurple(xPos + 40.0F * Settings.scale, yPos + 70.0F * Settings.scale));


                    break;

                case 4:

                    AbstractDungeon.effectsQueue.add(new SlimeWaterDropEffectPurple(xPos - 20.0F * Settings.scale, yPos - 50.0F * Settings.scale));


                    break;

            }


            this.count += 1;


            if (this.count == 6) {

                this.isDone = true;

            }

        }

    }


    public void render(SpriteBatch sb) {
    }

}


