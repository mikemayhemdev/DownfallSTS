package downfall.monsters.gauntletbosses;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.purple.Strike_Purple;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import downfall.downfallMod;
import downfall.powers.gauntletpowers.MonsterVigor;
import downfall.powers.gauntletpowers.OnDeathEveryoneRuggedVuln;
import hermit.cards.Defend_Hermit;
import hermit.cards.Maintenance;
import hermit.cards.Scavenge;
import hermit.cards.Strike_Hermit;
import hermit.powers.Bruise;

public class Hermit extends GauntletBoss {

    public static final String ID = downfallMod.makeID("GauntletHermit");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("hermit:hermit").NAMES[0];
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;

    int turnNum = 0;

    public Hermit(float x, float y) {
        super(NAME, ID, 72 * 2, 0.0F, -5.0F, 240.0F, 270.0F, null, x, y);
        this.loadAnimation("hermitResources/images/char/hermit/Hermit.atlas", "hermitResources/images/char/hermit/Hermit.json", 1.0f);
        this.flipHorizontal = true;
        this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
        AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(0.7F);

        type = EnemyType.ELITE;

        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OnDeathEveryoneRuggedVuln(this, 2), 2));
    }

    public void takeTurn() {
        int dex = 0;
        switch (this.nextMove) {
            case 1:
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(0), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 2:
                if (this.hasPower(DexterityPower.POWER_ID)) {
                    dex = getPower(DexterityPower.POWER_ID).amount;
                }
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(1), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new GainBlockAction(this, 5 + dex));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 3:
                if (this.hasPower(DexterityPower.POWER_ID)) {
                    dex = getPower(DexterityPower.POWER_ID).amount;
                }
                addToBot(new GainBlockAction(this, 10 + (dex * 2)));
                break;
            case 4:
                addToBot(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 4), 4));
                break;
            case 5:

                if (AbstractDungeon.ascensionLevel >= 18) {
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 4), 4));
                    addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 2), 2));
                }

                if (AbstractDungeon.ascensionLevel < 18) {
                    addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
                    addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
                }

                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void bossMove() {
        int rnd = AbstractDungeon.cardRandomRng.random(0, 3);
        switch (rnd) {
            case 0:
                isAttacking = true;
                setMove(moveName(Strike_Hermit.ID, Strike_Hermit.ID), (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
                break;
            case 1:
                isAttacking = true;
                setMove(moveName(Strike_Hermit.ID, Defend_Hermit.ID), (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                break;
            case 2:
                isAttacking = false;
                setMove(moveName(Defend_Hermit.ID, Defend_Hermit.ID), (byte) 3, Intent.DEFEND);
                break;
            case 3:
                isAttacking = false;
                setMove(moveName(Scavenge.ID), (byte) 4, Intent.BUFF);
                break;
        }
    }

    protected void getMove(int num) {
        turnNum++;
        if (turnNum == 5) {
            isAttacking = false;
            if (AbstractDungeon.ascensionLevel < 18) {
                setMove(moveName(Maintenance.ID), (byte) 5, Intent.BUFF);
            }

            if (AbstractDungeon.ascensionLevel >= 18) {
                setMove(moveName(Maintenance.ID) + "+", (byte) 5, Intent.BUFF);
            }
        } else {
            if (isThird && turnNum > 1 && ally1 != null && ally2 != null) {

                if (!ally1.isDeadOrEscaped() && !ally2.isDeadOrEscaped() && ally1.isAttacking && ally2.isAttacking) {
                    if (AbstractDungeon.cardRandomRng.randomBoolean()) {
                        setMove(moveName(Defend_Hermit.ID, Defend_Hermit.ID), (byte) 3, Intent.DEFEND);
                    } else {
                        setMove(moveName(Scavenge.ID), (byte) 4, Intent.BUFF);
                    }

                } else {
                    bossMove();
                }
            } else {
                bossMove();
            }
        }


    }
}