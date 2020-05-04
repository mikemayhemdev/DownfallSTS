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
import evilWithin.vfx.NeowBossRezEffect;
import theHexaghost.cards.seals.*;
import theHexaghost.relics.TheBrokenSeal;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class BrokenSealEffect extends AbstractGameEffect {

    private int cardsShown = 0;

    private float effectHeight = 250F;
    private float effectWidth1 = 150F;
    private float effectWidth2 = 350F;

    private NeowBossRezEffect rezVFX;

    private AbstractRelic sealRelic;

    private ArrayList<AbstractCard> seals = new ArrayList<>();

    public BrokenSealEffect() {
        this.startingDuration = this.duration = 6F;
    }

    public void update() {
        //this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 5.5F && this.cardsShown == 0){
            AbstractCard c = new FirstSeal();
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
            AbstractCard c = new FifthSeal();
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
            AbstractCard c = new SixthSeal();
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