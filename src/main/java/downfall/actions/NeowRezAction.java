package downfall.actions;


import charbosses.bosses.AbstractCharBoss;
import charbosses.bosses.Defect.CharBossDefect;
import charbosses.bosses.Ironclad.CharBossIronclad;
import charbosses.bosses.Silent.CharBossSilent;
import charbosses.bosses.Watcher.CharBossWatcher;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.powers.MinionPower;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.runHistory.RunHistoryPath;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import downfall.relics.HeartBlessingBlue;
import downfall.relics.HeartBlessingGreen;
import downfall.relics.HeartBlessingRed;
import downfall.vfx.NeowBossRezEffect;
import slimebound.SlimeboundMod;

import java.util.Collections;

public class NeowRezAction extends AbstractGameAction {
    private NeowBoss owner;
    private boolean instructedMove;
    private boolean rezInit;
    public AbstractCharBoss cB;
    private NeowBossRezEffect rezVFX;

    public NeowRezAction(NeowBoss owner) {
        this.owner = owner;
        this.duration = 3F;
        this.instructedMove = false;
    }

    public void rezSpeech(){
        switch (owner.Rezzes) {
            case 0: {
                AbstractDungeon.effectList.add(new SpeechBubble(Settings.WIDTH * 0.85F, Settings.HEIGHT / 2F, 2.0F, CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("NeowBoss")).TEXT[0], false));
                CardCrawlGame.sound.play("VO_NEOW_2A");
                break;
            }

            case 1: {
                AbstractDungeon.effectList.add(new SpeechBubble(Settings.WIDTH * 0.85F, Settings.HEIGHT / 2F, 2.0F, CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("NeowBoss")).TEXT[1], false));

                CardCrawlGame.sound.play("VO_NEOW_3B");
                break;
            }
            case 2: {
                AbstractDungeon.effectList.add(new SpeechBubble(Settings.WIDTH * 0.85F, Settings.HEIGHT / 2F, 2.0F, CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("NeowBoss")).TEXT[2], false));

                CardCrawlGame.sound.play("VO_NEOW_1A");
                break;
            }
        }
    }


    @Override
    public void update() {
        if (!this.instructedMove) {
            owner.moveForRez();
            this.instructedMove = true;
            rezSpeech();
            }
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 1.5F && !rezInit) {
            this.rezInit = true;
            String name;
            if (owner.bossesToRez.size() == 0) {
                name = "downfall:Ironclad";
                SlimeboundMod.logger.info("WARNING: Neow had no bosses to rez.  Spawning an Ironclad by default.");
            } else {
                //Collections.shuffle(owner.bossesToRez);
                name = owner.bossesToRez.get(0);
                owner.bossesRezzed.add(name);
                owner.bossesToRez.remove(0);
            }
            //SlimeboundMod.logger.info("Neow rezzing: " + name);
            rezBoss(name);
            //SlimeboundMod.logger.info("Neow rezzed: " + cB.name);
            owner.minion = cB;
            cB.tint.color = new Color(.5F, .5F, 1F, 0F);
            cB.tint.changeColor(Color.WHITE.cpy(), 2F);
            AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.CYAN, true));
            AbstractDungeon.effectsQueue.add(new IntenseZoomEffect(cB.hb.cX, cB.hb.cY, false));
            rezVFX = new NeowBossRezEffect(cB.hb.cX - 120F, cB.hb.cY);
            AbstractDungeon.effectsQueue.add(rezVFX);

            AbstractDungeon.getCurrRoom().monsters.addMonster(0, cB);

            if (ModHelper.isModEnabled("Lethality")) {
                this.addToBot(new ApplyPowerAction(cB, cB, new StrengthPower(cB, 3), 3));
            }

            if (ModHelper.isModEnabled("Time Dilation")) {
                this.addToBot(new ApplyPowerAction(cB, cB, new SlowPower(cB, 0)));
            }

            this.addToTop(new TriggerPseudoStartOfCombatAction(cB));

            this.addToTop(new ApplyPowerAction(cB, cB, new MinionPower(cB)));
        }


        if (this.duration <= 0F) {
            cB.init();
            NeowBoss.Rezzes++;
            owner.isRezzing = false;
            cB.showHealthBar();

            rezVFX.end();
            cB.chosenArchetype.addedPreBattle();
           // cB.usePreBattleAction();
            this.isDone = true;
        }
    }

    public void rezBoss(String name) {
        if (name.equals(CharBossIronclad.ID)) {
            cB = new CharBossIronclad();
        } else if (name.equals(CharBossSilent.ID)) {
            cB = new CharBossSilent();
        } else if (name.equals(CharBossDefect.ID)) {
            cB = new CharBossDefect();
        } else if (name.equals(CharBossWatcher.ID)) {
            cB = new CharBossWatcher();
        } else {
            cB = new CharBossIronclad();
        }
    }
}