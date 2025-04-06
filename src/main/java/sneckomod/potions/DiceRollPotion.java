package sneckomod.potions;

import basemod.abstracts.CustomPotion;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.LightBulbEffect;
import sneckomod.SneckoMod;
import sneckomod.util.DiceRollPotionReward;

import java.util.ArrayList;

public class DiceRollPotion extends CustomPotion {
    public static final String POTION_ID = "sneckomod:DiceRollPotion";

    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public DiceRollPotion() {
        super(NAME, POTION_ID, AbstractPotion.PotionRarity.RARE, AbstractPotion.PotionSize.SPHERE, AbstractPotion.PotionColor.FAIRY);
        this.labOutlineColor = SneckoMod.placeholderColor;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        if (this.potency == 1) {
            this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[1];
        }
        if (this.potency != 1) {
            this.description = potionStrings.DESCRIPTIONS[0] + this.potency + potionStrings.DESCRIPTIONS[2];
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom() != null) {
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new LightBulbEffect(AbstractDungeon.player.hb)));
            } else {
                this.addToBot(new VFXAction(new LightBulbEffect(AbstractDungeon.player.hb), 0.2F));
            }
            int i;
            for (i = 0; i < this.potency; i++) {
                AbstractDungeon.getCurrRoom().rewards.add(new DiceRollPotionReward());
            }
        }
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canUse() {
        return AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 48; // cannot appear in act 4
    }

    @Override
    public CustomPotion makeCopy() {
        return new DiceRollPotion();
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return 1;
    }
}
