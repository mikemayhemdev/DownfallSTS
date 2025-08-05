package awakenedOne.powers;

import awakenedOne.cards.ExNihilo;
import awakenedOne.cards.ManaburnCard;
import awakenedOne.relics.StrengthBooster;
import champ.relics.RageAmulet;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import hermit.patches.EnumPatch;

public class ManaburnPower extends AbstractAwakenedPower implements OnLoseEnergyPower {
    // intellij stuff buff

    public static final String NAME = ManaburnPower.class.getSimpleName();
    public static final String POWER_ID = makeID(NAME);


    public ManaburnPower(final AbstractCreature owner, int amount) {
        super(NAME, PowerType.DEBUFF, false, owner, null, amount);
        updateDescription();
    }


    @Override
    public void LoseEnergyAction(int gained) {
        this.flash();

        boolean hascrow = false;

        if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            hascrow = true;
        }

        if (!hascrow) {
            this.addToBot(new DamageAction(owner, new DamageInfo(owner, amount * gained, DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));
        }

        if (hascrow) {
            this.addToBot(new DamageAction(owner, new DamageInfo(owner, (amount * gained)+((amount * gained)/2), DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                if (r instanceof StrengthBooster) {
                    ((StrengthBooster) r).onSpecificTrigger((amount * gained)/2);
                }
            }
        }
    }

    @Override
    public void triggerMarks(AbstractCard card) {
        if (card.cardID.equals(ExNihilo.ID)) {


            boolean hascrow = false;

            if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
                hascrow = true;
            }

            if (!hascrow) {
                this.addToBot(new DamageAction(owner, new DamageInfo(owner, amount, DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));
            }

            if (hascrow) {
                this.addToBot(new DamageAction(owner, new DamageInfo(owner, (amount)+((amount)/2), DamageInfo.DamageType.HP_LOSS), EnumPatch.HERMIT_GHOSTFIRE));

                for (AbstractRelic r : AbstractDungeon.player.relics) {
                    if (r instanceof StrengthBooster) {
                        ((StrengthBooster) r).onSpecificTrigger((amount)/2);
                    }
                }
            }

        }
    }

    @Override
    public void updateDescription() {
        boolean hascrow = false;

        if (AbstractDungeon.player.hasRelic(StrengthBooster.ID)) {
            hascrow = true;
        }

        if (!hascrow) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }

        if (hascrow) {
            description = DESCRIPTIONS[0] + (amount)+(amount/2) + DESCRIPTIONS[1];
        }

    }
}