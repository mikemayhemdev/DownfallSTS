package downfall.monsters.gauntletbosses;

import charbosses.core.EnemyEnergyManager;
import charbosses.powers.cardpowers.EnemyWraithFormPower;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Defend_Blue;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import downfall.downfallMod;
import downfall.powers.DrawReductionPowerPlus;
import downfall.powers.EnemyDemonFormPower;
import downfall.powers.gauntletpowers.MonsterVigor;
import downfall.powers.gauntletpowers.OnDeathEveryoneBuffer;
import downfall.powers.gauntletpowers.OnDeathEveryoneStr;

public class Ironclad extends GauntletBoss {

    public static final String ID = downfallMod.makeID("GauntletIronclad");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Ironclad").NAMES[0];
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;

    int turnNum = 0;

    public Ironclad(float x, float y) {
        super(NAME, ID, 80 * 2, -4.0f, -16.0f, 220.0f, 290.0f, null, x, y);

        this.loadAnimation("images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0f);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
        e.setTimeScale(0.6f);
        type = EnemyType.ELITE;

        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 8));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OnDeathEveryoneStr(this, 3), 3));
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
                addToBot(new DamageAction(AbstractDungeon.player, this.damage.get(2), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                addToBot(new ApplyPowerAction(AbstractDungeon.player, this, new VulnerablePower(AbstractDungeon.player, 2, true), 2));
                if (hasPower(MonsterVigor.POWER_ID)) {
                    addToBot(new RemoveSpecificPowerAction(this, this, MonsterVigor.POWER_ID));
                }
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new EnemyDemonFormPower(this, 2), 2));
                break;
        }


        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    private void bossMove() {
        int rnd = AbstractDungeon.cardRandomRng.random(0, 3);
        if (hasPower(EnemyDemonFormPower.POWER_ID)) {
            if (rnd == 2) {
                while (rnd == 2) {
                    rnd = AbstractDungeon.cardRandomRng.random(0, 3);
                }
            }
        }
        switch (rnd) {
            case 0:
                isAttacking = true;
                setMove(moveName(Strike_Red.ID, Strike_Red.ID), (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
                break;
            case 1:
                isAttacking = true;
                setMove(moveName(Strike_Red.ID, Defend_Red.ID), (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                break;
            case 2:
                isAttacking = false;
                setMove(moveName(Defend_Red.ID, Defend_Red.ID), (byte) 3, Intent.DEFEND);
                break;
            case 3:
                isAttacking = true;
                setMove(moveName(Bash.ID), (byte) 4, Intent.ATTACK_DEBUFF, this.damage.get(2).base);
                break;
    }
    }

    protected void getMove(int num) {
        turnNum++;
        if (turnNum == 5) {
            isAttacking = false;
            setMove(moveName(DemonForm.ID), (byte) 5, Intent.BUFF);
        } else {
            if (isThird && turnNum > 1 && ally1 != null && ally2 != null) {

                if (!ally1.isDeadOrEscaped() && !ally2.isDeadOrEscaped() && ally1.isAttacking && ally2.isAttacking) {
                    setMove(moveName(Defend_Red.ID, Defend_Red.ID), (byte) 3, Intent.DEFEND);
                } else {
                    bossMove();
                }

            } else {
                bossMove();
            }
        }


    }
}
