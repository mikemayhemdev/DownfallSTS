package hermit.util;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import hermit.actions.TimedVFXAction;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Wiz {
    //The wonderful Wizard of Oz allows access to most easy compilations of data, or functions.

    public static AbstractPlayer adp() {
        return AbstractDungeon.player;
    }

    public static AbstractPlayer p() {
        return AbstractDungeon.player;
    }

    public static CardGroup hand() {
        return AbstractDungeon.player.hand;
    }

    public static CardGroup deck() {
        return AbstractDungeon.player.masterDeck;
    }

    public static void forAllCardsInList(Consumer<AbstractCard> consumer, ArrayList<AbstractCard> cardsList) {
        for (AbstractCard c : cardsList) {
            consumer.accept(c);
        }
    }

    public static ArrayList<AbstractCard> getAllCardsInCardGroups(boolean includeHand, boolean includeExhaust) {
        ArrayList<AbstractCard> masterCardsList = new ArrayList<>();
        masterCardsList.addAll(AbstractDungeon.player.drawPile.group);
        masterCardsList.addAll(AbstractDungeon.player.discardPile.group);
        if (includeHand) {
            masterCardsList.addAll(AbstractDungeon.player.hand.group);
        }
        if (includeExhaust) {
            masterCardsList.addAll(AbstractDungeon.player.exhaustPile.group);
        }
        return masterCardsList;
    }

    public static void forAllMonstersLiving(Consumer<AbstractMonster> consumer) {
        for (AbstractMonster m : getEnemies()) {
            consumer.accept(m);
        }
    }

    public static ArrayList<AbstractMonster> getEnemies() {
        ArrayList<AbstractMonster> monsters = new ArrayList<>(AbstractDungeon.getMonsters().monsters);
        monsters.removeIf(AbstractCreature::isDeadOrEscaped);
        return monsters;
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred) {
        return getCardsMatchingPredicate(pred, false);
    }

    public static ArrayList<AbstractCard> getCardsMatchingPredicate(Predicate<AbstractCard> pred, boolean allcards) {
        if (allcards) {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            for (AbstractCard c : CardLibrary.getAllCards()) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            return cardsList;
        } else {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
                if (pred.test(c)) cardsList.add(c.makeStatEquivalentCopy());
            }
            return cardsList;
        }
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred, boolean allCards) {
        return getRandomItem(getCardsMatchingPredicate(pred, allCards));
    }

    public static AbstractCard returnTrulyRandomPrediCardInCombat(Predicate<AbstractCard> pred) {
        return returnTrulyRandomPrediCardInCombat(pred, false);
    }

    public static <T> T getRandomItem(ArrayList<T> list, Random rng) {
        return list.isEmpty() ? null : list.get(rng.random(list.size() - 1));
    }

    public static <T> T getRandomItem(ArrayList<T> list) {
        return getRandomItem(list, AbstractDungeon.cardRandomRng);
    }

    private static boolean actuallyHovered(Hitbox hb) {
        return InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height;
    }

    public static boolean isInCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    public static void atb(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    public static void att(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public static void vfx(AbstractGameEffect gameEffect) {
        atb(new VFXAction(gameEffect));
    }

    public static void vfx(AbstractGameEffect gameEffect, float duration) {
        atb(new VFXAction(gameEffect, duration));
    }

    public static void tfx(AbstractGameEffect gameEffect) {
        atb(new TimedVFXAction(gameEffect));
    }

    public static void makeInHand(AbstractCard c, int i) {
        atb(new MakeTempCardInHandAction(c, i));
    }

    public static void makeInHand(AbstractCard c) {
        makeInHand(c, 1);
    }

    public static void shuffleIn(AbstractCard c, int i) {
        atb(new MakeTempCardInDrawPileAction(c, i, true, true));
    }

    public static void shuffleIn(AbstractCard c) {
        shuffleIn(c, 1);
    }

    public static void topDeck(AbstractCard c, int i) {
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(c, i, false, true));
    }

    public static void topDeck(AbstractCard c) {
        topDeck(c, 1);
    }

    public static void applyToEnemy(AbstractMonster m, AbstractPower po) {
        atb(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToEnemyTop(AbstractMonster m, AbstractPower po) {
        att(new ApplyPowerAction(m, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelf(AbstractPower po) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void applyToSelfTop(AbstractPower po) {
        att(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, po, po.amount));
    }

    public static void reducePower(AbstractPower p, int amount) {
        atb(new ReducePowerAction(p.owner, p.owner, p, amount));
    }

    public static void reducePower(AbstractPower p) {
        reducePower(p, 1);
    }

    public static void removePower(AbstractPower p, boolean top) {
        if (top) {
            att(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        } else {
            atb(new RemoveSpecificPowerAction(p.owner, p.owner, p));
        }
    }

    public static void removePower(AbstractPower p) {
        removePower(p, false);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c) {
        doDmg(target, c.damage, c.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c, AbstractGameAction.AttackEffect ae) {
        doDmg(target, c.damage, c.damageTypeForTurn, ae);
    }

    public static void doDmg(AbstractCreature target, AbstractCard c, AbstractGameAction.AttackEffect ae, boolean top) {
        doDmg(target, c.damage, c.damageTypeForTurn, ae, false, top);
    }

    public static void doDmg(AbstractCreature target, int amount) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt) {
        doDmg(target, amount, dt, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doDmg(AbstractCreature target, int amount, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, DamageInfo.DamageType.NORMAL, ae);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae) {
        doDmg(target, amount, dt, ae, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast) {
        doDmg(target, amount, dt, ae, fast, false);
    }

    public static void doDmg(AbstractCreature target, int amount, DamageInfo.DamageType dt, AbstractGameAction.AttackEffect ae, boolean fast, boolean top) {
        if (target == null) {
            target = AbstractDungeon.getRandomMonster();
        }
        if (top) {
            att(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        } else {
            atb(new DamageAction(target, new DamageInfo(p(), amount, dt), ae, fast));
        }
    }

    public static void thornDmg(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmg(AbstractCreature m, int amount) {
        thornDmg(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void thornDmgAll(int amount, AbstractGameAction.AttackEffect effect) {
        forAllMonstersLiving(mon -> thornDmg(mon, amount, effect));
    }

    public static void thornDmgTop(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX));
    }

    public static void thornDmgTop(AbstractCreature m, int amount, AbstractGameAction.AttackEffect AtkFX, boolean superfast) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS), AtkFX, superfast));
    }

    public static void thornDmgTop(AbstractCreature m, int amount) {
        thornDmgTop(m, amount, AbstractGameAction.AttackEffect.NONE);
    }

    public static void doAllDmg(int amount, AbstractGameAction.AttackEffect ae, DamageInfo.DamageType dt, boolean top) {
        if (top) {
            att(new DamageAllEnemiesAction(p(), amount, dt, ae));
        } else {
            atb(new DamageAllEnemiesAction(p(), amount, dt, ae));
        }
    }

    public static void doAllDmg(AbstractCard c, AbstractGameAction.AttackEffect ae, boolean top) {
        if (top) {
            att(new DamageAllEnemiesAction(p(), c.multiDamage, c.damageTypeForTurn, ae));
        } else {
            atb(new DamageAllEnemiesAction(p(), c.multiDamage, c.damageTypeForTurn, ae));
        }
    }

    public static void doBlk(AbstractCard c) {
        doBlk(c.block, false);
    }

    public static void doBlk(int amount) {
        doBlk(amount, false);
    }

    public static void doBlk(int amount, boolean top) {
        if (top) {
            att(new GainBlockAction(p(), p(), amount));
        } else {
            atb(new GainBlockAction(p(), p(), amount));
        }
    }

    public static void doBlk(int amount, AbstractCreature source, boolean top) {
        if (top) {
            att(new GainBlockAction(p(), source, amount));
        } else {
            atb(new GainBlockAction(p(), source, amount));
        }
    }

    public static boolean isAttacking(AbstractCreature m) {
        if (m instanceof AbstractMonster) {
            return ((AbstractMonster) m).getIntentBaseDmg() >= 0;
        }
        return false;
    }

    public static AbstractGameAction.AttackEffect getRandomSlash() {
        int x = AbstractDungeon.miscRng.random(0, 2);
        if (x == 0)
            return AbstractGameAction.AttackEffect.SLASH_DIAGONAL;
        if (x == 1)
            return AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
        return AbstractGameAction.AttackEffect.SLASH_VERTICAL;
    }

    public static AbstractMonster getRandomEnemy() {
        return AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }

    public static AbstractMonster getLowestHealthEnemy() {
        AbstractMonster weakest = null;
        for (AbstractMonster m : getEnemies()) {
            if (weakest == null)
                weakest = m;
            else if (weakest.currentHealth > m.currentHealth)
                weakest = m;
        }
        return weakest;
    }

    public static AbstractMonster getHighestHealthEnemy() {
        AbstractMonster strongest = null;
        for (AbstractMonster m : getEnemies()) {
            if (strongest == null)
                strongest = m;
            else if (strongest.currentHealth < m.currentHealth)
                strongest = m;
        }
        return strongest;
    }

    public static void discard(int amount, boolean isRandom) {
        atb(new DiscardAction(adp(), adp(), amount, isRandom));
    }

    public static void discard(int amount) {
        discard(amount, false);
    }

    public static int pwrAmt(AbstractCreature check, String ID) {
        AbstractPower found = check.getPower(ID);
        if (found != null) {
            return found.amount;
        }
        return 0;
    }

    public static int getLogicalCardCost(AbstractCard c) {
        if (!c.freeToPlay()) {
            if(c.cost <= -2) {
                return 0;
            } else if(c.cost == -1)
                return EnergyPanel.totalCount;
            return c.costForTurn;
        }
        return 0;
    }
}
