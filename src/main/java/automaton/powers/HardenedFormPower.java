package automaton.powers;

import automaton.cards.FunctionCard;
import automaton.vfx.FloatingBronzeOrbEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HardenedFormPower extends AbstractAutomatonPower implements OnCompilePower {
    public static final String NAME = "HardenedForm";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;
    public FloatingBronzeOrbEffect orbVFX;

    public HardenedFormPower(int amount) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, amount);
    }

    @Override
    public void receiveCompile(AbstractCard function, boolean forGameplay) {
        if (forGameplay) {
            onSpecificTrigger();
        }
    }

    @Override
    public void onSpecificTrigger() {
        flash();

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = HardenedFormPower.this.amount;
                AbstractMonster m = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (m != null) {
                    addToTop(new GainBlockAction(owner, x));
                    addToTop(new DamageAction(m, new DamageInfo(owner, x, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
                    AbstractDungeon.actionManager.addToTop(new VFXAction(new com.megacrit.cardcrawl.vfx.combat.SmallLaserEffect(orbVFX.currentX + (50F * Settings.scale), orbVFX.currentY + (85F * Settings.scale), m.hb.cX, m.hb.cY), 0.1F));
                    AbstractDungeon.actionManager.addToTop(new SFXAction("ATTACK_MAGIC_BEAM_SHORT", 0.5F));
                }
            }
        });


    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        this.orbVFX = new FloatingBronzeOrbEffect(AbstractDungeon.player);
        AbstractDungeon.actionManager.addToBottom(new VFXAction(this.orbVFX));
    }

    @Override
    public void onAfterCardPlayed(AbstractCard function) {
        if (function instanceof FunctionCard) {
            onSpecificTrigger();
        }
    }

    @Override
    public void onVictory() {
        super.onVictory();
        orbVFX.dispose();
    }

    @Override
    public void onDeath() {
        super.onDeath();
        orbVFX.dispose();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
