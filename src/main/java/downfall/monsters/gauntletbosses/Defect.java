package downfall.monsters.gauntletbosses;

import charbosses.cards.blue.EnBuffer;
import charbosses.core.EnemyEnergyManager;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import downfall.downfallMod;
import downfall.powers.DrawReductionPowerPlus;
import downfall.powers.gauntletpowers.MonsterVigor;
import downfall.powers.gauntletpowers.OnDeathEveryoneBuffer;
import hermit.cards.Maintenance;

public class Defect extends GauntletBoss {

    public static final String ID = downfallMod.makeID("GauntletDefect");
    public static final String NAME = CardCrawlGame.languagePack.getCharacterString("Defect").NAMES[0];
    private static final float HB_X = 0.0F;
    private static final float HB_Y = 0.0F;
    private static final float HB_W = 225.0F;
    private static final float HB_H = 250.0F;


    int turnNum = 0;

    public Defect(float x, float y) {
        super(NAME, ID, 75 * 2, 0.0F, -5.0F, 240.0F, 244.0F, null, x, y);

        this.loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        final AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        this.stateData.setMix("Hit", "Idle", 0.1f);
        this.flipHorizontal = true;
        this.skeleton.setFlip(this.flipHorizontal, this.flipVertical);
        e.setTimeScale(0.9f);
        type = EnemyType.ELITE;

        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 6));
        this.damage.add(new DamageInfo(this, 11));
    }

    @Override
    public void usePreBattleAction() {
        super.usePreBattleAction();

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new OnDeathEveryoneBuffer(this, 2), 2));
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
                if (AbstractDungeon.ascensionLevel < 18) {
                    this.addToBot(new ApplyPowerAction(this, this, new FocusPower(this, -1), -1));
                    this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 1), 1));
                    this.addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 1), 1));
                }
                if (AbstractDungeon.ascensionLevel >= 18) {
                    this.addToBot(new ApplyPowerAction(this, this, new FocusPower(this, -2), -2));
                    this.addToBot(new ApplyPowerAction(this, this, new StrengthPower(this, 2), 2));
                    this.addToBot(new ApplyPowerAction(this, this, new DexterityPower(this, 2), 2));
                }
                break;
            case 5:
                addToBot(new ApplyPowerAction(this, this, new BufferPower(this, 2), 2));
                break;
        }

        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }


    private void bossMove() {

        int rnd = AbstractDungeon.cardRandomRng.random(0, 3);
        switch (rnd) {
            case 0:
                isAttacking = true;
                setMove(moveName(Strike_Blue.ID, Strike_Blue.ID), (byte) 1, Intent.ATTACK, this.damage.get(0).base, 2, true);
                break;
            case 1:
                isAttacking = true;
                setMove(moveName(Strike_Blue.ID, Defend_Blue.ID), (byte) 2, Intent.ATTACK_DEFEND, this.damage.get(1).base);
                break;
            case 2:
                isAttacking = false;
                setMove(moveName(Defend_Blue.ID, Defend_Blue.ID), (byte) 3, Intent.DEFEND);
                break;
            case 3:
                isAttacking = false;
                if (AbstractDungeon.ascensionLevel < 18) {
                    setMove(moveName(Reprogram.ID), (byte) 4, Intent.BUFF);
                }
                if (AbstractDungeon.ascensionLevel >= 18) {
                    setMove(moveName(Reprogram.ID) + "+", (byte) 4, Intent.BUFF);
                }
                break;
        }
    }

    protected void getMove(int num) {
        turnNum++;
        if (turnNum == 5) {
            isAttacking = false;
            setMove(moveName(Buffer.ID) + "+", (byte) 5, Intent.BUFF);
        } else {
            if (isThird && turnNum > 1 && ally1 != null && ally2 != null) {

                if (!ally1.isDeadOrEscaped() && !ally2.isDeadOrEscaped() && ally1.isAttacking && ally2.isAttacking) {
                    setMove(moveName(Defend_Blue.ID, Defend_Blue.ID), (byte) 3, Intent.DEFEND);
                } else {
                    bossMove();
                }
            } else {
                bossMove();
            }


        }
    }
}


