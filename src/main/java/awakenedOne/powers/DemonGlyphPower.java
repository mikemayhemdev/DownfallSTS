package awakenedOne.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.InflameEffect;

public class DemonGlyphPower extends AbstractAwakenedPower implements OnAwakenPower {
    // intellij stuff buff
    public static final String NAME = DemonGlyphPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);
    public boolean activated = false;

    public DemonGlyphPower(int amount) {
        super(NAME, PowerType.BUFF, false, AbstractDungeon.player, null, amount);
        updateDescription();
        activated = false;
    }

    @Override
    public void onAwaken(int vibe) {
        if (vibe == 10 && !activated) {
            activated = true;
            flash();
            this.addToBot(new VFXAction(AbstractDungeon.player, new InflameEffect(AbstractDungeon.player), 1.0F));
            applyToSelf(new StrengthPower(AbstractDungeon.player, amount));
            applyToSelf(new DexterityPower(AbstractDungeon.player, amount));
        }
    }


    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}