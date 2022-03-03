package downfall.actions;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import downfall.downfallMod;
import downfall.monsters.NeowBoss;
import downfall.monsters.gauntletbosses.*;
import downfall.vfx.NeowBossRezEffect;

public class NeowRezAction extends AbstractGameAction {
    private NeowBoss owner;
    private boolean instructedMove;
    private boolean rezInit;
    private NeowBossRezEffect rezVFX;

    public NeowRezAction(NeowBoss owner) {
        this.owner = owner;
        this.duration = 3F;
        this.instructedMove = false;
    }

    public void rezSpeech() {
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
            for (int i = 0; i < owner.bossesToRez.size(); i++) {
                String name;
                name = owner.bossesToRez.get(i);
                owner.bossesRezzed.add(name);
                AbstractDungeon.actionManager.addToTop(new SpawnMonsterAction(rezBoss(name, i), false));
            }
        }


        if (this.duration <= 0F) {
            owner.isRezzing = false;

            //rezVFX.end();
            // cB.usePreBattleAction();
            this.isDone = true;
        }
    }

    private static int locationSwitch(int index) {
        switch (index) {
            case 0:
                return 100;
            case 1:
                return -175;
            case 2:
                return -350;
        }
        return 100;
    }

    public AbstractMonster rezBoss(String name, int index) {
        int found = locationSwitch(index);
        if (name.equals(Ironclad.ID)) {
            return new Ironclad(found, -20);
        } else if (name.equals(Silent.ID)) {
            return new Silent(found, -20);
        } else if (name.equals(Defect.ID)) {
            return new Defect(found, -20);
        } else if (name.equals(Watcher.ID)) {
            return new Watcher(found, -20);
        } else if (name.equals(Hermit.ID)) {
            return new Hermit(found, -20);
        } else {
            return new Ironclad(found, -20);
        }
    }
}