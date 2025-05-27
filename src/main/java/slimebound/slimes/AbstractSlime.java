package slimebound.slimes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;


public abstract class AbstractSlime {
    //TODO - Pretty much everything involved here. This is all just prelim copy paste.
    //TODO - Should have a hitbox which explains the next turn attack like an orb, and also include active enchantments if any are present.
    //TODO - Should have renders for both energy (maybe a smaller version of the Slime Boss energy render), and an orb-like text. Maybe more flavorful to use intent imagery?

    public String ID;
    protected int baseAttackAmount = 3;
    public int attackAmount = baseAttackAmount;
    public Hitbox hb;

    public int energy;

    private Set<SlimeEnchantmentType> activeEnchants = new HashSet<>();

    // Decide later if slimes are re-initialized every combat or just the same but using a reset function

    public AbstractSlime() {

    }

    // Core functions

    public void render(SpriteBatch sb) {
        // TODO: Render slime itself.
        // TODO: Render amount of energy. (I'd rather not use an actual energy orb...)
        // TODO: Render Strength and enchantments.
        // TODO: Render intent.
    }

    public void update() {
        this.hb.update();
        // TODO: If the slime is hovered, show tooltip with name and description.
    }

    public abstract void updateDescription();

    // Gameplay functions

    public void onEndOfTurn() {
        if (energy > 0) {
            activateEffect();
            if (!isEnchanted(SlimeEnchantmentType.NOENEERGYCOST))
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        AbstractSlime.this.SpendEnergy(1);
                    }
                });
        } else {
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    AbstractSlime.this.GainEnergy(1);
                }
            });
        }
    }

    public void GainEnergy(int amount) {
        energy += amount;
    }

    public void SpendEnergy(int amount) {
        energy -= amount;
    }

    public abstract void activateEffect();

    public boolean isEnchanted(SlimeEnchantmentType enchantToCheck){
        return activeEnchants.contains(enchantToCheck);
    }

    public enum SlimeEnchantmentType {
        NOENEERGYCOST,
        DOUBLECOMMAND,
        CULTIST,
        CHAMP,
        TORCHHEAD,
        TIMEEATER,
        GUARDOIAN,
        HEXAGHOST;
    }

    protected void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
