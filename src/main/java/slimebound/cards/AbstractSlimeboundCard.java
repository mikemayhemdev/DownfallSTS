package slimebound.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.powers.PreventTackleDamagePower;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;
import slimebound.powers.TackleModifyDamagePower;
import slimebound.relics.SelfDamagePreventRelic;


public abstract class AbstractSlimeboundCard extends CustomCard {
    public int baseSelfDamage;
    public int selfDamage;
    public boolean upgradeSelfDamage;
    public boolean isSelfDamageModified;
    public int slimed;
    public int baseSlimed;
    public boolean isSlimedModified;
    public boolean upgradeSlimed;

    public AbstractSlimeboundCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
                                  CardRarity rarity, CardTarget target) {
        super(id, name, img, cost, rawDescription, type,
                color, rarity, target);
    }

    public void upgradeSlimed(int amount) {
        baseSlimed += amount;
        slimed = baseSlimed;
        upgradeSlimed = true;
    }

    @Override
    public void resetAttributes() {
        super.resetAttributes();
        selfDamage = baseSelfDamage;
        isSelfDamageModified = false;
        slimed = baseSlimed;
        isSlimedModified = false;
    }

    public void upgradeSelfDamage(int originalAmount) {
        baseSelfDamage += originalAmount;
        selfDamage = baseSelfDamage;
        upgradeSelfDamage = true;
    }

    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
        if (hasTag(SlimeboundMod.TACKLE)) {
            int bonus = 0;
            if (player.hasPower(TackleBuffPower.POWER_ID)) {
                bonus = player.getPower(TackleBuffPower.POWER_ID).amount;
            }
            if (mo != null) {
                if (mo.hasPower(TackleDebuffPower.POWER_ID)) {
                    bonus = bonus + mo.getPower(TackleDebuffPower.POWER_ID).amount;
                }
            }
            return tmp + bonus;
        }
        return tmp;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int base = baseSelfDamage;
        if (AbstractDungeon.player.hasRelic(SelfDamagePreventRelic.ID)) {
            base -= 1;
        }
        if (AbstractDungeon.player.hasPower(TackleModifyDamagePower.POWER_ID)) {
            base += AbstractDungeon.player.getPower(TackleModifyDamagePower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(PreventTackleDamagePower.POWER_ID)) {
            base = 0;
        }
        selfDamage = base;
        isSelfDamageModified = (this.selfDamage != this.baseSelfDamage);

        int que = baseSlimed;
        que += SlimeboundMod.getAcidTongueBonus(AbstractDungeon.player);
        slimed = que;
        isSlimedModified = (slimed != baseSlimed);
    }
}