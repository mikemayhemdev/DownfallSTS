package evilWithin.monsters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import evilWithin.EvilWithinMod;
import evilWithin.powers.DrawReductionPowerPlus;
import evilWithin.powers.TransformDrawnCardsPower;
import evilWithin.vfx.TotemBeamEffect;

import java.util.ArrayList;

public class ChangingTotem extends AbstractTotemMonster {
    public static final String ID = EvilWithinMod.makeID("ChangingTotem");
    private static final MonsterStrings monsterStrings;
    public static final String NAME;
    public static final String[] MOVES;
    public static final String[] DIALOG;
    public Integer attackDmg;

    public Integer secondaryEffect;

    public static Color totemColor = Color.GREEN;

    public ChangingTotem() {
        super(NAME, ID, EvilWithinMod.assetPath("images/monsters/totemgreen.png"));
        this.loadAnimation(EvilWithinMod.assetPath("images/monsters/green/Totem.atlas"), EvilWithinMod.assetPath("images/monsters/green/Totem.json"), 1.0F);

        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        if (AbstractDungeon.ascensionLevel >= 19) {
            this.attackDmg = 5;
            this.secondaryEffect = 2;
        } else if (AbstractDungeon.ascensionLevel >= 4) {
            this.attackDmg = 5;
            this.secondaryEffect = 1;
        } else {
            this.attackDmg = 4;
            this.secondaryEffect = 1;
        }
        this.damage.add(new DamageInfo(this, this.attackDmg));

        this.intentType = Intent.DEBUFF;

        this.totemLivingColor = totemColor;

    }


    @Override
    public void totemAttack() {
        // AbstractDungeon.actionManager.addToBottom(new ChangeStateAction(this, "ATTACK"));
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BorderFlashEffect(Color.GREEN)));
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new TotemBeamEffect(this.hb.cX + beamOffsetX, this.hb.cY + beamOffsetY, AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Color.GOLD.cpy(), this.hb.cX + beamOffsetX2, this.hb.cY + beamOffsetY2), 0.1F));
        //AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.NONE));
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TransformDrawnCardsPower(AbstractDungeon.player, this.secondaryEffect), this.secondaryEffect));


    }

    public void getUniqueTotemMove() {this.setMove((byte)1, intentType, this.attackDmg);
    }

    static {
        monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
        NAME = monsterStrings.NAME;
        MOVES = monsterStrings.MOVES;
        DIALOG = monsterStrings.DIALOG;

    }




}