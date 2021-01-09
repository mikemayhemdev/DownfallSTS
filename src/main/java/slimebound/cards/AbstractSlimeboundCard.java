package slimebound.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.CommandAction;
import slimebound.actions.TrigggerSpecificSlimeAttackAction;
import slimebound.powers.*;
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

    public void upgradeSelfDamage(int bonus) {
        selfDamage = baseSelfDamage + bonus;
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

    protected void slimedGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();// 39

        if (AbstractDungeon.getCurrRoom().monsters != null) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(SlimedPower.POWER_ID)) {// 41
                    this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();// 42
                    break;// 43
                }
            }
        }
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        int base = baseSelfDamage;
        if (AbstractDungeon.player.hasRelic(SelfDamagePreventRelic.ID)) {
            base -= 3;
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

    public void checkMinionMaster(){
        if (AbstractDungeon.player.hasPower(BuffSecondarySlimeEffectsPower.POWER_ID)) {
            for (int i = 0; i < AbstractDungeon.player.getPower(BuffSecondarySlimeEffectsPower.POWER_ID).amount; i++) {
                addToBot(new CommandAction());
            }
        }
    }
}