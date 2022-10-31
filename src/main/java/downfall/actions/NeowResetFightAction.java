package downfall.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Careless;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import downfall.monsters.NeowBoss;

public class NeowResetFightAction extends AbstractGameAction {
    private NeowBoss owner;

    public NeowResetFightAction() {

    }

    @Override
    public void update() {

        AbstractDungeon.resetPlayer();
        AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.combat.BattleStartEffect(false));

        //Pre Battle Prep

        if (!(Boolean) TipTracker.tips.get("COMBAT_TIP")) {
            AbstractDungeon.ftue = new MultiPageFtue();
            TipTracker.neverShowAgain("COMBAT_TIP");
        }

        AbstractDungeon.player.damagedThisCombat = 0;
        AbstractDungeon.player.cardsPlayedThisTurn = 0;
        AbstractDungeon.player.maxOrbs = 0;
        AbstractDungeon.player.orbs.clear();
        AbstractDungeon.player.increaseMaxOrbSlots(AbstractDungeon.player.masterMaxOrbs, false);
        AbstractDungeon.player.isBloodied = AbstractDungeon.player.currentHealth <= AbstractDungeon.player.maxHealth / 2;
        AbstractDungeon.player.poisonKillCount = 0;
        GameActionManager.playerHpLastTurn = AbstractDungeon.player.currentHealth;
        AbstractDungeon.player.endTurnQueued = false;
        AbstractDungeon.player.gameHandSize = AbstractDungeon.player.masterHandSize;
        AbstractDungeon.player.isDraggingCard = false;
        AbstractDungeon.player.isHoveringDropZone = false;
        AbstractDungeon.player.hoveredCard = null;
        AbstractDungeon.player.cardInUse = null;
        AbstractDungeon.player.drawPile.initializeDeck(AbstractDungeon.player.masterDeck);
        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        AbstractDungeon.player.hand.clear();
        AbstractDungeon.player.discardPile.clear();
        AbstractDungeon.player.exhaustPile.clear();

        if (AbstractDungeon.player.hasRelic("SlaversCollar")) {
            ((SlaversCollar)AbstractDungeon.player.getRelic("SlaversCollar")).beforeEnergyPrep();
        }

        AbstractDungeon.player.energy.prep();
        AbstractDungeon.player.powers.clear();
        AbstractDungeon.player.isEndingTurn = false;
        AbstractDungeon.player.healthBarUpdatedEvent();
        if (ModHelper.isModEnabled("Lethality")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 3), 3));
        }

        if (ModHelper.isModEnabled("Terminal")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, 5), 5));
        }

        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        AbstractDungeon.player.applyPreCombatLogic();
        
        
        ////
        
        
        
        
        
        AbstractDungeon.player.applyStartOfCombatPreDrawLogic();
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.gameHandSize));

        AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction());
        AbstractDungeon.overlayMenu.showCombatPanels();
        AbstractDungeon.player.applyStartOfCombatLogic();


        if (ModHelper.isModEnabled("Careless")) {
            Careless.modAction();
        }
        if (ModHelper.isModEnabled("ControlledChaos")) {
            com.megacrit.cardcrawl.daily.mods.ControlledChaos.modAction();
        }


        AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
        AbstractDungeon.player.applyStartOfTurnRelics();
        AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
        AbstractDungeon.player.applyStartOfTurnCards();
        AbstractDungeon.player.applyStartOfTurnPowers();
        AbstractDungeon.player.applyStartOfTurnOrbs();
        AbstractDungeon.actionManager.useNextCombatActions();

        this.isDone = true;
    }
}