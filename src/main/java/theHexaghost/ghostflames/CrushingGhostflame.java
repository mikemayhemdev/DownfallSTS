package theHexaghost.ghostflames;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.AnimatedSlashEffect;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;
import downfall.util.TextureLoader;
import gremlin.actions.PseudoDamageRandomEnemyAction;
import theHexaghost.GhostflameHelper;
import theHexaghost.HexaMod;
import theHexaghost.powers.EnhancePower;
import theHexaghost.powers.FlameAffectAllEnemiesPower;
import theHexaghost.relics.JarOfFuel;

public class CrushingGhostflame extends AbstractGhostflame {

    public static Texture bruh = TextureLoader.getTexture(HexaMod.makeUIPath("crushing.png"));
    public static Texture bruhB = TextureLoader.getTexture(HexaMod.makeUIPath("crushingBright.png"));
    public static Texture bruh2 = TextureLoader.getTexture(HexaMod.makeUIPath("damage.png"));
    public int skillsPlayedThisTurn = 0;

    private String ID = "hexamod:CrushingGhostflame";
    private String NAME = CardCrawlGame.languagePack.getOrbString(ID).NAME;
    private String[] DESCRIPTIONS = CardCrawlGame.languagePack.getOrbString(ID).DESCRIPTION;

    private Color flameColor = new Color(249F/255F, 185F/255F, 164F/255F, 1F);
    private Color activeColor = new Color(249F/255F * 0.5F, 185F/255F * 0.5F, 164F/255F * 0.5F, 1F);

    public CrushingGhostflame(float x, float y) {
        super(x, y);
        damage = 6;
        //this.textColor = new Color(1F,.75F,.75F,1F);
        this.triggersRequired = 2;

        this.effectIconXOffset = 80F;
        this.effectIconYOffset = -20F;
        this.advanceOnCardUse = true;
    }

    @Override
    public void advanceTrigger(AbstractCard c) {
        if (!charged && ( c.type == AbstractCard.CardType.SKILL || (AbstractDungeon.player.hasRelic(JarOfFuel.ID) && c.type == AbstractCard.CardType.POWER ) ) ){
            if (skillsPlayedThisTurn < 2) {
                if(AbstractDungeon.player.hasRelic(JarOfFuel.ID) && c.type == AbstractCard.CardType.POWER){
                    AbstractRelic r =  AbstractDungeon.player.getRelic(JarOfFuel.ID);
                    if(r != null){ r.flash(); }
                }
                advanceTriggerAnim();
                skillsPlayedThisTurn++;
                if (skillsPlayedThisTurn == 2){
                    charge();
                }
            }
        }
    }

    @Override
    public int getActiveFlamesTriggerCount() {
        return skillsPlayedThisTurn;
    }

        @Override
    public void onCharge() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                int x = getEffectCount();
                isDone = true;

                if(AbstractDungeon.player.hasPower(FlameAffectAllEnemiesPower.POWER_ID)){
                    for(int i = 0; i < AbstractDungeon.player.getPower(FlameAffectAllEnemiesPower.POWER_ID).amount; i++){

                            addToTop(new VFXAction(
                                    new AbstractGameEffect() {

                                        public void update() {
                                            CardCrawlGame.sound.playA("ATTACK_IRON_2", -0.4F);
                                            CardCrawlGame.sound.playA("ATTACK_HEAVY", -0.4F);
                                            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                                                if (m != null && !m.isDead && !m.isDying && !m.halfDead) {
                                                    AbstractDungeon.effectsQueue.add(new AnimatedSlashEffect(m.hb.cX, m.hb.cY - 30.0F * Settings.scale, 0.0F, -500.0F, 180.0F, 5.0F, Color.GOLD, Color.GOLD));
                                                }
                                            }
                                            this.isDone = true;
                                        }

                                        @Override
                                        public void render(SpriteBatch spriteBatch) {}

                                        @Override
                                        public void dispose() {}
                                    }
                            ));

                        att(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(x, true), DamageInfo.DamageType.THORNS, AttackEffect.NONE));
//                        att(new DamageAllEnemiesAction(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS, AttackEffect.NONE));
                    }
                } else {
                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                    if (m != null && !m.isDead && !m.isDying && !m.halfDead) {
                        AbstractDungeon.actionManager.addToTop(new PseudoDamageRandomEnemyAction(m, new DamageInfo(AbstractDungeon.player, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                        addToTop(new VFXAction(new GoldenSlashEffect(m.hb.cX, m.hb.cY, true)));
                    }
                }
            }
        });
    }

    @Override
    public String returnHoverHelperText() {
        int x = getEffectCount();
        return x+"";
    }

    public int getEffectCount(){
        int x = damage;
        if (AbstractDungeon.player.hasPower(EnhancePower.POWER_ID)) {
            x += AbstractDungeon.player.getPower(EnhancePower.POWER_ID).amount;
        }
        return x;
    }

    @Override
    public Texture getHelperTexture() {
        return bruh;
    }

    @Override
    public Texture getHelperTextureBright() {
        return bruhB;
    }

    @Override
    public Texture getHelperEffectTexture() {
        return bruh2;
    }

    @Override
    public void resetVariable() {
        skillsPlayedThisTurn = 0;
    }

    @Override
    public String getName(){ return NAME;}

    @Override
    public String getDescription() {
        String s = "";
        if (charged) {
            s = DESCRIPTIONS[0];
        }
        if (GhostflameHelper.activeGhostFlame == this) {
            int x = (2 - skillsPlayedThisTurn);
            if (x == 1) {
                s = s + DESCRIPTIONS[1] + x + DESCRIPTIONS[2];
            } else {
                s = s + DESCRIPTIONS[1] + x + DESCRIPTIONS[3];
            }
        } else {
            s = s + DESCRIPTIONS[4];
        }
        int x = getEffectCount();
        s = s + DESCRIPTIONS[5] + x + DESCRIPTIONS[6];
        if (GhostflameHelper.activeGhostFlame == this) {
            s = s + DESCRIPTIONS[7];
        }
        return s;
    }

    public Color getFlameColor() {
        return activeColor.cpy();
        //return Color.SKY.cpy();
    }

    public Color getActiveColor() {
        //return activeColor.cpy();
        return Color.PURPLE.cpy();
    }
}
