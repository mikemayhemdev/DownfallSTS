package hermit.cards;

import charbosses.powers.BossIntangiblePower;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePower;
import hermit.HermitMod;
import hermit.characters.hermit;
import hermit.patches.EnumPatch;
import hermit.powers.SnipePower;


import static hermit.HermitMod.loadJokeCardImage;
import static hermit.HermitMod.makeCardPath;

public class Headshot extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = HermitMod.makeID(Headshot.class.getSimpleName());
    public static final String IMG = makeCardPath("headshot.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = hermit.Enums.COLOR_YELLOW;

    private static final int COST = 1;

    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/

    public Headshot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.tags.add(Enums.DEADON);
        loadJokeCardImage(this, "headshot.png");
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //damage calculation happens here for dead on stuff
        int dam = this.damage;
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, dam, damageTypeForTurn), EnumPatch.HERMIT_GUN2));
        if (isDeadOn()) {
            TriggerDeadOnEffect(p,m);
        }
    }

    public void triggerOnGlowCheck() {
        this.glowColor = AbstractDynamicCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (isDeadOnPos()) {
            this.glowColor = AbstractDynamicCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }


    @Override
    public void calculateCardDamage(AbstractMonster mo) {
            if (isDeadOnPos() || trig_deadon) {
                int DeadOnTimes = DeadOnAmount();

                //The dead on effect is +100% damage, not *2 damage!!!

                //So the code here is so that it won't repeat for each dead on time * 2 since it's not doubling-doubling, it's increasing by 100% for each trigger
                //This means each trigger is an additional stack of the original damage, and not an additional stack of the new damage!

                //tldr; If this triggers dead on 3 times it deals 400% damage by adding 100% 3 times, not 800% damage.

                this.damage *= DeadOnTimes;
            }
            super.calculateCardDamage(mo);
            //don't uncomment this, bad idea
            //this.baseDamage = base_dam;
            isDamageModified = damage != baseDamage;
    }

    public void applyPowers() {
        //just read the calculate card damage one this is identical but has no comments
        int base_dam = this.damage;
        if (isDeadOnPos() || trig_deadon) {
            int DeadOnTimes = DeadOnAmount();

            for (int a = 0; a < DeadOnTimes; a++) {
                this.damage += base_dam;
            }
        }
        super.applyPowers();
        //this.baseDamage = base_dam;
        isDamageModified = damage != baseDamage;
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}