package hermit.potions;

import basemod.ReflectionHacks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.FlashPotionEffect;
import hermit.HermitMod;
import hermit.actions.EclipseAction;
import hermit.powers.Rugged;
import hermit.util.TextureLoader;

import java.util.Iterator;

public class Eclipse extends AbstractPotion {


    public static final String POTION_ID = HermitMod.makeID("Eclipse");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public Eclipse() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main DefaultMod.java
        super(NAME, POTION_ID, PotionRarity.RARE, PotionSize.FAIRY, PotionColor.FEAR);

        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_glass.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_liquid.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "hybridImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_hybrid.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "spotsImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_spots.png"));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TextureLoader.getTexture("hermitResources/images/potion/potion_eclipse_outline.png"));

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
        // Initialize the Description
        description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];

        labOutlineColor = HermitMod.HERMIT_YELLOW;
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;
    }

    @Override
    public void initializeData()
    {
        potency = getPotency();

        if (potency == 1) {
            description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[1];
        }
        else
        {
            description = DESCRIPTIONS[0] + potency + DESCRIPTIONS[2];
        }

        tips.clear();
        tips.add(new PowerTip(name, description));
    }
    // See that description? It has DESCRIPTIONS[1] instead of just hard-coding the "text " + potency + " more text" inside.
    // DO NOT HARDCODE YOUR STRINGS ANYWHERE, it's really bad practice to have "Strings" in your code:

    /*
     * 1. It's bad for if somebody likes your mod enough (or if you decide) to translate it.
     * Having only the JSON files for translation rather than 15 different instances of "Dexterity" in some random cards is A LOT easier.
     *
     * 2. You don't have a centralised file for all strings for easy proof-reading. If you ever want to change a string
     * you don't have to go through all your files individually/pray that a mass-replace doesn't screw something up.
     *
     * 3. Without hardcoded strings, editing a string doesn't require a compile, saving you time (unless you clean+package).
     *
     */

    @Override
    public void use(AbstractCreature target) {
        // If you are in combat, gain strength and the "lose strength at the end of your turn" power, equal to the potency of this potion.
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractDungeon.actionManager.addToBottom(new EclipseAction(potency));
        }
    }
    
    @Override
    public AbstractPotion makeCopy() {
        return new Eclipse();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 1;
    }

    public void upgradePotion()
    {
      potency += 1;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
