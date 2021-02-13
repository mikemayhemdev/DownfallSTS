package guardian.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect;
import guardian.GuardianMod;

public class PocketSentry extends CustomRelic {
    public static final String ID = "Guardian:PocketSentry";
    public static final String IMG_PATH = "relics/pocketSentry.png";
    public static final String OUTLINE_IMG_PATH = "relics/pocketSentryOutline.png";
    public static final String LARGE_IMG_PATH = "relics/pocketSentryLarge.png";
    private static final int DAMAGE = 7;

    public PocketSentry() {
        super(ID, new Texture(GuardianMod.getResourcePath(IMG_PATH)), new Texture(GuardianMod.getResourcePath(OUTLINE_IMG_PATH)),
                RelicTier.COMMON, LandingSound.FLAT);
        this.largeImg = ImageMaster.loadImage(GuardianMod.getResourcePath(LARGE_IMG_PATH));
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + DAMAGE + this.DESCRIPTIONS[1];
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        this.flash();
        if (counter == 0) {
            counter = 1;
            AbstractRelic r = this;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(null,true,AbstractDungeon.relicRng);
                    if(m != null){
                        AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
                        AbstractDungeon.actionManager.addToTop(new VFXAction(new SmallLaserEffect(r.hb.cX - (5F * Settings.scale), r.hb.cY + (10F * Settings.scale), m.hb.cX, m.hb.cY), 0.3F));
                        AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                    }
                }
            });


        } else {
            counter = 0;
            AbstractRelic r = this;
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    for (AbstractMonster m2 : AbstractDungeon.getMonsters().monsters) {
                        if (!m2.isDead && !m2.isDying) {
                            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m2, AbstractDungeon.player, new WeakPower(m2, 1, false), 1));
                        }
                    }
                    AbstractDungeon.actionManager.addToTop(new SFXAction("THUNDERCLAP"));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(AbstractDungeon.player, new ShockWaveEffect(r.hb.cX, r.hb.cY, Color.ROYAL, ShockWaveEffect.ShockWaveType.ADDITIVE), 0.1F));
                }
            });
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new PocketSentry();
    }
}