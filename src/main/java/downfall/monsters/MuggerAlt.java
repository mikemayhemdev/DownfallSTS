package downfall.monsters;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationState.TrackEntry;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.AnimateSlowAttackAction;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.SetMoveAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.AbstractMonster.Intent;
import com.megacrit.cardcrawl.random.Random;
import downfall.downfallMod;

import java.util.ArrayList;

public class MuggerAlt extends AbstractMonster
{
    public static final String ID = downfallMod.makeID("MuggerAlt");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings("Looter");
    private static final MonsterStrings monsterStringsAlt = CardCrawlGame.languagePack.getMonsterStrings(downfallMod.makeID("MuggerAlt"));
    public static final String NAME = monsterStrings.NAME;
    public static final String[] MOVES = monsterStrings.MOVES;
    public static final String[] DIALOG = monsterStringsAlt.DIALOG;
    private int swipeDmg;
    private int bigSwipeDmg;
    private int escapeDef = 12;
    private int speechCount = 0;
    private static final String SLASH_MSG1 = DIALOG[0];
    private static final String SMOKE_BOMB_MSG = DIALOG[1];
    private int slashCount = 0;

    public MuggerAlt(float x, float y) {
        super(NAME, ID, 52, 0.0F, 0.0F, 200.0F, 220.0F, null, x, y);

        this.dialogX = (-30.0F * Settings.scale);
        this.dialogY = (50.0F * Settings.scale);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(46, 50);
        } else {
            setHp(44, 48);
        }

        if (AbstractDungeon.ascensionLevel >= 2) {
            this.swipeDmg = 11;
            this.bigSwipeDmg = 18;
        } else {
            this.swipeDmg = 10;
            this.bigSwipeDmg = 16;
        }

        this.damage.add(new DamageInfo(this, this.swipeDmg));
        this.damage.add(new DamageInfo(this, this.bigSwipeDmg));

        loadAnimation("images/monsters/theCity/looterAlt/skeleton.atlas", "images/monsters/theCity/looterAlt/skeleton.json", 1.0F);



        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public void takeTurn()
    {
        switch (this.nextMove) {
            case 1:
                if ((this.slashCount == 0 && this.speechCount == 0)) {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SLASH_MSG1, 0.3F, 2.0F));
                    speechCount = 1;
                }

                playSfx();
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                        (DamageInfo)this.damage.get(0)));

                this.slashCount += 1;
                if (this.slashCount == 2) {
                    if (AbstractDungeon.aiRng.randomBoolean(0.5F)) {
                        setMove((byte)2, AbstractMonster.Intent.DEFEND);
                    } else {
                        AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[0], (byte)4, AbstractMonster.Intent.ATTACK,
                                ((DamageInfo)this.damage.get(1)).base));
                    }
                } else {
                    AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, MOVES[1], (byte)1, AbstractMonster.Intent.ATTACK,
                            ((DamageInfo)this.damage.get(0)).base));
                }
                break;
            case 4:
                playSfx();
                this.slashCount += 1;
                AbstractDungeon.actionManager.addToBottom(new AnimateSlowAttackAction(this));
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player,
                        (DamageInfo)this.damage.get(1)));
                setMove((byte)2, AbstractMonster.Intent.DEFEND);
                break;
            case 2:
                if (this.speechCount == 1) {
                    AbstractDungeon.actionManager.addToBottom(new TalkAction(this, SMOKE_BOMB_MSG, 0.75F, 2.5F));
                    this.speechCount = 2;
                }
                AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(this, this, this.escapeDef));
                AbstractDungeon.actionManager.addToBottom(new SetMoveAction(this, (byte)1, Intent.ATTACK));
                this.slashCount = 0;
                break;
        }

    }

    private void playSfx()
    {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_MUGGER_1A"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("VO_MUGGER_1B"));
        }
    }

    private void playDeathSfx() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            CardCrawlGame.sound.play("VO_MUGGER_2A");
        } else {
            CardCrawlGame.sound.play("VO_MUGGER_2B");
        }
    }

    public void die()
    {
        playDeathSfx();
        this.state.setTimeScale(0.1F);
        useShakeAnimation(5.0F);

        super.die();
    }

    protected void getMove(int num)
    {
        setMove((byte)1, AbstractMonster.Intent.ATTACK, ((DamageInfo)this.damage.get(0)).base);
    }
}


