package sneckomod.relics;

import basemod.abstracts.CustomBottleRelic;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConfusionPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.vfx.CustomAnimatedNPC;
import slimebound.SlimeboundMod;
import sneckomod.SneckoMod;
import sneckomod.patches.BottledD8Patch;
import theHexaghost.util.TextureLoader;

import java.util.function.Predicate;

public class BabySnecko extends CustomRelic {

    public static final String ID = SneckoMod.makeID("BabySnecko");
    private static final Texture IMG = TextureLoader.getTexture(SneckoMod.makeRelicPath("BabySnecko.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(SneckoMod.makeRelicOutlinePath("BabySnecko.png"));


    private CustomAnimatedNPC baby;

    public BabySnecko() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }


    public void atPreBattle() {
        this.flash();
        this.baby = new CustomAnimatedNPC(AbstractDungeon.player.hb.cX - 250F * Settings.scale, AbstractDungeon.player.hb.cY + 160.0F * Settings.scale, "sneckomodResources/images/monsters/BabySnecko/BabySnecko.atlas", "sneckomodResources/images/monsters/BabySnecko/BabySnecko.json", "idle", false, 0);
        this.baby.customFlipX = true;
        this.baby.setTimeScale(0.9F);
    }


    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        this.baby.state.setAnimation(0, "boop", false);
        this.baby.state.addAnimation(0, "idle", true, 0.0f);
        AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);

        if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(AbstractDungeon.player, 5, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
    }

    @Override
    public void onVictory() {
        if (this.baby != null){
        this.baby.dispose();
        SlimeboundMod.logger.info("Disposing baby snecko ");
        }
    }

    @Override
    public void update() {
        super.update();
        if (this.baby != null) this.baby.update();
    }

    @Override
    public void renderInTopPanel(SpriteBatch sb) {
        super.renderInTopPanel(sb);
        //SlimeboundMod.logger.info("Rendering");
        super.render(sb);
        if (this.baby != null){
            this.baby.render(sb);
           // SlimeboundMod.logger.info("Rendering baby snecko " + this.baby.skeleton.getX() + " " + this.baby.skeleton.getY());
        }
    }


    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
