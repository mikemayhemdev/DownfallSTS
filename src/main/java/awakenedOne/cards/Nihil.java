package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import awakenedOne.powers.ManaburnPower;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.TriggerMarksAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.WaterDropEffect;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;


public class Nihil extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Nihil.class.getSimpleName());
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public Nihil() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 12;
        this.tags.add(AwakenedOneMod.CHANT);
        loadJokeCardImage(this, makeBetaCardPath(Nihil.class.getSimpleName() + ".png"));
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(m, p, new ManaburnPower(m, this.magicNumber), this.magicNumber));
        if (isTrig_chant()) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(m, new OfferingEnemyEffect(m), 0.2F));
            } else {
                this.addToBot(new VFXAction(m, new OfferingEnemyEffect(m), 0.5F));
            }
            this.addToBot(new TriggerMarksAction(this));
            chant();
        }
    }

    @Override
    public void chant() {
        checkOnChant();
    }

    public void triggerOnGlowCheck() {
        this.glowColor = isChantActiveGlow(this) ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    @Override
    public void upp() {
        upgradeMagicNumber(4);
    }

    private static class OfferingEnemyEffect extends AbstractGameEffect {
        private final AbstractCreature target;
        private int count = 0;
        private float timer = 0.0F;

        public OfferingEnemyEffect(AbstractCreature target) {
            this.target = target;
        }

        public void update() {
            this.timer -= Gdx.graphics.getDeltaTime();
            if (this.timer < 0.0F) {
                this.timer += 0.3F;
                switch (this.count) {
                    case 0:
                        CardCrawlGame.sound.playA("ATTACK_FIRE", -0.5F);
                        CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);
                        CardCrawlGame.sound.playA("ATTACK_FIRE", -0.5F);
                        CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);
                        CardCrawlGame.sound.playA("ATTACK_FIRE", -0.5F);
                        CardCrawlGame.sound.playA("BLOOD_SPLAT", -0.75F);
                        AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(new Color(1.0F, 0.1F, 0.1F, 1.0F)));
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX, target.hb.cY + 250.0F * Settings.scale));
                        break;
                    case 1:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX + 150.0F * Settings.scale, target.hb.cY - 80.0F * Settings.scale));
                        break;
                    case 2:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX - 200.0F * Settings.scale, target.hb.cY + 50.0F * Settings.scale));
                        break;
                    case 3:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX + 200.0F * Settings.scale, target.hb.cY + 50.0F * Settings.scale));
                        break;
                    case 4:
                        AbstractDungeon.effectsQueue.add(new WaterDropEffect(target.hb.cX - 150.0F * Settings.scale, target.hb.cY - 80.0F * Settings.scale));
                }

                ++this.count;
                if (this.count == 6) {
                    this.isDone = true;
                }
            }

        }

        public void render(SpriteBatch sb) {
        }

        public void dispose() {
        }
    }
}