package hermit.potions;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import hermit.HermitMod;
import hermit.util.TextureLoader;
import hermit.util.Wiz;

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

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            Wiz.atb(new FetchAction(Wiz.p().exhaustPile,card -> true,this.potency, list -> {
                for (AbstractCard c : list)
                {
                    c.setCostForTurn(0);
                }
            }));
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
      this.potency += 1;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
}
