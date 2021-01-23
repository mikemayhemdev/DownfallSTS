package charbosses.bosses.Silent;

import basemod.interfaces.CloneablePowerInterface;
import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Silent.NewAge.ArchetypeAct1ShivsNewAge;
import charbosses.bosses.Silent.NewAge.ArchetypeAct2MirrorImageNewAge;
import charbosses.bosses.Silent.NewAge.ArchetypeAct3PoisonNewAge;
import charbosses.core.EnemyEnergyManager;
import charbosses.monsters.MirrorImageSilent;
import charbosses.powers.bossmechanicpowers.FakeOrRealPower;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireOverride;
import com.evacipated.cardcrawl.modthespire.lib.SpireSuper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;

public class CharBossSilent extends AbstractCharBoss {
    public static final String ID = downfallMod.makeID("Silent");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Silent").NAMES[0];

    public static boolean posStorage = false;

    public float origDX;
    public float origdY;
    public float orighX;
    public float orighY;

    public CharBossSilent() {
        super(NAME, ID, 70, -4.0f, -16.0f, 240.0f, 290.0f, null, 100.0f, -20.0f, PlayerClass.THE_SILENT);
        this.energyOrb = new EnergyOrbGreen();
        this.energy = new EnemyEnergyManager(3);
        this.loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        e.setTimeScale(0.9f);
        this.energyString = "[G]";
        type = EnemyType.BOSS;

        origDX = drawX;
        origdY = drawY;
        orighX = hb.x;
        orighY = hb.y;
    }

    @Override
    public void generateDeck() {
        AbstractBossDeckArchetype archetype;
        if (downfallMod.overrideBossDifficulty) {
            archetype = new ArchetypeAct1ShivsNewAge();
            downfallMod.overrideBossDifficulty = false;
            this.currentHealth -= 100;
        } else
            switch (AbstractDungeon.actNum) {
                case 1:
                    archetype = new ArchetypeAct1ShivsNewAge();
                    break;
                case 2:
                    archetype = new ArchetypeAct2MirrorImageNewAge();
                    break;
                case 3:
                    archetype = new ArchetypeAct3PoisonNewAge();
                    break;
                case 4:
                    //SlimeboundMod.logger.info("Silent spawned at Archetype " + NeowBoss.Rezzes);
                    {
                    switch (NeowBoss.Rezzes) {
                        case 0:
                            archetype = new ArchetypeAct1ShivsNewAge();
                            break;
                        case 1:
                            archetype = new ArchetypeAct2MirrorImageNewAge();
                            break;
                        case 2:
                            archetype = new ArchetypeAct3PoisonNewAge();
                            break;
                        default:
                            archetype = new ArchetypeAct2MirrorImageNewAge();
                            break;
                    }
                    break;
                }
                default:
                    archetype = new ArchetypeAct1ShivsNewAge();
                    break;
            }

        archetype.initialize();
        currentHealth = maxHealth;
        chosenArchetype = archetype;
        if (AbstractDungeon.ascensionLevel >= 19) {
            archetype.initializeBonusRelic();
        }
    }


    @Override
    public void onPlayAttackCardSound() {
        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_SILENT_1A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_SILENT_1B");
                break;
            default:
                break;
        }
    }

    public boolean foggy = false;

    @Override
    public void renderHealth(SpriteBatch sb) {
        if (!foggy) {
            super.renderHealth(sb);
        }
    }

    @Override
    public void renderPowerTips(SpriteBatch sb) {
        if (!foggy) {
            super.renderPowerTips(sb);
        }
    }


    @Override
    public void renderTip(SpriteBatch sb) {
        if (!foggy) {
            super.renderTip(sb);
        }
    }


    @SpireOverride
    protected void renderName(SpriteBatch sb) {
        if (!foggy) {
            SpireSuper.call(sb);
        }
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - this.currentBlock > 0) {
            AnimationState.TrackEntry e = this.state.setAnimation(0, "Hit", false);
            this.state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }

        super.damage(info);
    }

    @Override
    public void die() {
        super.die();

        switch (MathUtils.random(1)) {
            case 0:
                CardCrawlGame.sound.play("VO_SILENT_2A");
                break;
            case 1:
                CardCrawlGame.sound.play("VO_SILENT_2B");
                break;
            default:
                break;
        }

        downfallMod.saveBossFight(CharBossSilent.ID);

        if (hasPower(MinionPower.POWER_ID)){
            for (AbstractMonster m:AbstractDungeon.getCurrRoom().monsters.monsters){
                if (m instanceof MirrorImageSilent){
                    AbstractDungeon.actionManager.addToBottom(new InstantKillAction(m));
                }
            }
        }
    }

    public static void swapCreature(AbstractCreature p, AbstractCreature m) {
        float tempDX = m.drawX;
        float tempdY = m.drawY;
        float temphX = m.hb.x;
        float temphY = m.hb.y;
        m.drawX = p.drawX;
        m.drawY = p.drawY;
        m.hb.x = p.hb.x;
        m.hb.y = p.hb.y;
        p.drawX = tempDX;
        p.drawY = tempdY;
        p.hb.x = temphX;
        p.hb.y = temphY;
        posStorage = !posStorage;
    }

    public void spawnImage(boolean noSmoke) {

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        if (!noSmoke) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(orighX - 50F, orighY + 100F)));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new SmokeBombEffect(orighX - 450F, orighY + 100F)));
        }

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                foggy = true;
                AbstractDungeon.getCurrRoom().monsters.monsters.removeIf(c -> c instanceof MirrorImageSilent);
                CharBossSilent.boss.drawX = origDX;
                CharBossSilent.boss.drawY = origdY;
                CharBossSilent.boss.hb.x = orighX;
                CharBossSilent.boss.hb.y = orighY;
                //CharBossSilent.boss.state.setTimeScale(0F);
                AbstractMonster m = new MirrorImageSilent(-400, -20);
                CharBossSilent.boss.powers.add(new FakeOrRealPower(CharBossSilent.boss));
                m.currentHealth = AbstractCharBoss.boss.currentHealth;
                m.maxHealth = AbstractCharBoss.boss.maxHealth;
                m.currentBlock = AbstractCharBoss.boss.currentBlock;
                m.powers.clear();
                for (AbstractPower p : AbstractCharBoss.boss.powers) {
                    if (p instanceof CloneablePowerInterface) {
                        AbstractPower q = ((CloneablePowerInterface) p).makeCopy();
                        q.owner = m;
                        m.powers.add(q);
                    }
                }
                //m.state.setTimeScale(0F);

                AbstractDungeon.getCurrRoom().monsters.addMonster(1, m);

                m.init();
                m.applyPowers();
            }
        });

        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1F));

    }

}
