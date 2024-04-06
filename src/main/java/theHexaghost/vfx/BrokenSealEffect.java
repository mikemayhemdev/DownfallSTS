package theHexaghost.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import downfall.vfx.NeowBossRezEffect;
import theHexaghost.cards.seals.*;
import theHexaghost.relics.TheBrokenSeal;

import java.util.ArrayList;


public class BrokenSealEffect extends AbstractGameEffect {

    private int cardsShown = 0;

    private float effectHeight = 250F;
    private float effectWidth1 = 150F;
    private float effectWidth2 = 350F;

    private NeowBossRezEffect rezVFX;

    private AbstractRelic sealRelic;

    private ArrayList<AbstractCard> seals = new ArrayList<>();
    private boolean up1, up2, up3, up4, up5, up6;

    public BrokenSealEffect(boolean upp1, boolean upp2, boolean upp3, boolean upp4, boolean upp5, boolean upp6 ) {
        this.startingDuration = this.duration = 6F;
        this.up1 = upp1;
        this.up2 = upp2;
        this.up3 = upp3;
        this.up4 = upp4;
        this.up5 = upp5;
        this.up6 = upp6;
    }

    public void update() {
        //this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 5.5F && this.cardsShown == 0){
            AbstractCard c = new FirstSeal();
            if(up1) c.upgrade();
            c.target_x = Settings.WIDTH / 2F - effectWidth1 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F - effectHeight * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);
            this.cardsShown++;
        }
        if (this.duration <= 5.2F && this.cardsShown == 1){
            AbstractCard c = new SecondSeal();
            if(up2) c.upgrade();
            c.target_x = Settings.WIDTH / 2F - effectWidth2 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);

            this.cardsShown++;
        }
        if (this.duration <= 4.9F && this.cardsShown == 2){
            AbstractCard c = new ThirdSeal();
            if(up3) c.upgrade();
            c.target_x = Settings.WIDTH / 2F - effectWidth1 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F + effectHeight * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);

            this.cardsShown++;
        }
        if (this.duration <= 4.6F && this.cardsShown == 3){
            AbstractCard c = new FourthSeal();
            if(up4) c.upgrade();
            c.target_x = Settings.WIDTH / 2F + effectWidth1 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F + effectHeight * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);

            this.cardsShown++;
        }
        if (this.duration <= 4.3F && this.cardsShown == 4){
            AbstractCard c = new SixthSeal();
            if(up5) c.upgrade();
            c.target_x = Settings.WIDTH / 2F + effectWidth2 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);

            this.cardsShown++;
        }
        if (this.duration <= 4F && this.cardsShown == 5){
            AbstractCard c = new FifthSeal();
            if(up6) c.upgrade();
            c.target_x = Settings.WIDTH / 2F + effectWidth1 * Settings.scale;
            c.target_y = Settings.HEIGHT / 2F - effectHeight * Settings.scale + 75F * Settings.scale;
            c.current_x = Settings.WIDTH / 2F;
            c.current_y = 50F;
            c.superFlash();
            c.drawScale = 0.7F;
            c.targetDrawScale = c.drawScale;
            this.seals.add(c);

            this.cardsShown++;
        }
        if (this.duration <= 3F && this.cardsShown == 6){
            for (AbstractCard c : this.seals){
                AbstractDungeon.effectsQueue.add(new ExhaustCardEffect(c));
            }
            this.cardsShown++;
        }

        if (this.duration <= 2.5F && this.cardsShown == 7){

            this.rezVFX = new NeowBossRezEffect(Settings.WIDTH / 2F, Settings.HEIGHT / 2F);
            AbstractDungeon.effectsQueue.add(rezVFX);
            AbstractDungeon.effectsQueue.add(new IntenseZoomEffect(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, false));
            AbstractDungeon.effectsQueue.add(new IntenseZoomEffect(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, false));
            this.cardsShown++;

            this.seals.clear();
        }

        if (this.duration <= 1.5F && this.cardsShown == 8) {
            this.cardsShown++;
            sealRelic = new TheBrokenSeal();
            AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2F, Settings.HEIGHT / 2F, sealRelic);
            this.rezVFX.end();
        }

        for (AbstractCard c : this.seals){
            c.update();
        }
        
        this.duration -= Gdx.graphics.getDeltaTime() * 1.3F;

        if (this.duration < 0.0F) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        if (this.cardsShown < 7) {
            for (AbstractCard c : this.seals) {
                c.render(sb);
            }
        }
    }

    public void dispose() {
    }
}