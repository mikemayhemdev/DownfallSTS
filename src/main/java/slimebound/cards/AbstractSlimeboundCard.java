package slimebound.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import slimebound.SlimeboundMod;


public abstract class AbstractSlimeboundCard extends CustomCard {
    public AbstractSlimeboundCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                                  CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);
    }

    public int baseSelfDamage;
    public int selfDamage;
    public boolean upgradeSelfDamage;
    public boolean isSelfDamageModified;

    public int poison;
    public boolean upgradePoison;
    public boolean isPoisonModified;

    public int slimed;
    public int baseSlimed;
    public boolean isSlimedModified;
    public boolean upgradeSlimed;


    public boolean goopflashVfx;


    public void upgradeSlimed(int amount) {
        this.slimed += amount;

        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.drawPile.contains(this) || AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.discardPile.contains(this) || AbstractDungeon.player.exhaustPile.contains(this)) {
                this.slimed += SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player);
            }

        }
        if (this.slimed > this.baseSlimed || amount > 0) this.isSlimedModified = true;

    }

    public void upgradeLickSlimed(int amount) {
        /*
        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.drawPile.contains(this) || AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.discardPile.contains(this) || AbstractDungeon.player.exhaustPile.contains(this))
                this.baseSlimed += amount;
            this.slimed = this.baseSlimed + SlimeboundMod.getGluttonyBonus(AbstractDungeon.player);
            if (this.slimed > this.baseSlimed || amount > 0) this.isSlimedModified = true;
        }
        */
    }

    public void upgradeSelfDamage(int originalAmount) {

        if (AbstractDungeon.player != null) {
            if (AbstractDungeon.player.drawPile.contains(this) || AbstractDungeon.player.hand.contains(this) || AbstractDungeon.player.discardPile.contains(this) || AbstractDungeon.player.exhaustPile.contains(this)) {
                this.selfDamage = originalAmount + SlimeboundMod.getTackleSelfDamageBonus(AbstractDungeon.player);
                if (this.selfDamage < 0) this.selfDamage = 0;

                if (this.selfDamage > originalAmount) {
                    this.isSelfDamageModified = true;
                } else {
                    this.isSelfDamageModified = false;
                }
            }

        }

    }


}