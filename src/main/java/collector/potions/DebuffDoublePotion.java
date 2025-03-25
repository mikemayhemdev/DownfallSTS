package collector.potions;


import basemod.abstracts.CustomPotion;
import collector.CollectorMod;
import collector.actions.GainReservesAction;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.atb;
import static collector.util.Wiz.att;


public class DebuffDoublePotion extends CustomPotion {
    public static final String POTION_ID = makeID(DebuffDoublePotion.class.getSimpleName());
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DebuffDoublePotion() {
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.H, PotionColor.ELIXIR);
        this.isThrown = true;
        this.targetRequired = true;
        this.labOutlineColor= CollectorMod.potionLabColor;
    }

    public void initializeData() {
        this.potency = getPotency();
        if (AbstractDungeon.player != null && potency > 1) {
            this.description = potionStrings.DESCRIPTIONS[1] + (potency+1) + potionStrings.DESCRIPTIONS[2];
        } else {
            this.description = potionStrings.DESCRIPTIONS[0];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature targetFoe) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                this.addToBot(new VFXAction(targetFoe, new VerticalAuraEffect(Color.BLACK, targetFoe.hb.cX, targetFoe.hb.cY), 0.1F));
                this.addToBot(new SFXAction("ATTACK_FIRE"));
                this.addToBot(new VFXAction(targetFoe, new VerticalAuraEffect(Color.PURPLE, targetFoe.hb.cX, targetFoe.hb.cY), 0.1F));
                this.addToBot(new VFXAction(targetFoe, new VerticalAuraEffect(Color.CYAN, targetFoe.hb.cX, targetFoe.hb.cY), 0.0F));
                isDone = true;
                for (AbstractPower p : targetFoe.powers) {
                    if (p.type == AbstractPower.PowerType.DEBUFF) {

                        p.amount *= potency + 1;
                    }
                }
            }
        });
    }

    public CustomPotion makeCopy() {
        return new DebuffDoublePotion();
    }

    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
