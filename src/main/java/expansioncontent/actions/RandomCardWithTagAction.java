package expansioncontent.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import downfall.downfallMod;

import java.util.ArrayList;
import java.util.Map;


public class RandomCardWithTagAction extends AbstractGameAction {
    public boolean upgradeCard;
    public AbstractCard.CardTags tag;
    public boolean free;
    public boolean oneless;

    public RandomCardWithTagAction(boolean upgraded, AbstractCard.CardTags tagToSearch) {
        this.upgradeCard = upgraded;
        this.tag = tagToSearch;
        this.free = true;
        this.oneless = false;
    }

    public RandomCardWithTagAction(boolean upgraded, AbstractCard.CardTags tagToSearch, boolean free) {
        this.upgradeCard = upgraded;
        this.tag = tagToSearch;
        this.free = free;
        this.oneless = false;
    }

    public RandomCardWithTagAction(boolean upgraded, AbstractCard.CardTags tagToSearch, boolean free, boolean oneless) {
        this.upgradeCard = upgraded;
        this.tag = tagToSearch;
        this.free = free;
        this.oneless = oneless;
    }

    public static boolean guardianLocked() {
        return UnlockTracker.isCharacterLocked("Guardian");
    }

    public static boolean hexaLocked() {
        return UnlockTracker.isCharacterLocked("hexamod:theHexaghost");
    }

    public static boolean champLocked() {
        return UnlockTracker.isCharacterLocked("champ:theChamp");
    }

    public static boolean autoLocked() {
        return UnlockTracker.isCharacterLocked("bronze:theAutomaton");
    }

    public void update() {

        ArrayList<String> tmp = new ArrayList<>();

        for (Map.Entry<String, AbstractCard> stringAbstractCardEntry : CardLibrary.cards.entrySet()) {
            Map.Entry<String, AbstractCard> c = stringAbstractCardEntry;
            if (c.getValue().rarity != AbstractCard.CardRarity.SPECIAL &&
                    !c.getValue().hasTag(AbstractCard.CardTags.HEALING) &&
                    c.getValue().hasTag(tag)
                    && (!(c.getValue().hasTag(downfallMod.STUDY_SLIMEBOSS)
                    && AbstractDungeon.player.chosenClass == downfallMod.Enums.SLIMEBOUND))
                    && (!(c.getValue().hasTag(downfallMod.STUDY_GUARDIAN)
                    && (AbstractDungeon.player.chosenClass == downfallMod.Enums.GUARDIAN || guardianLocked())))
                    && (!(c.getValue().hasTag(downfallMod.STUDY_HEXAGHOST)
                    && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_SPIRIT || hexaLocked())))
                    && (!(c.getValue().hasTag(downfallMod.STUDY_CHAMP)
                    && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_CHAMP || champLocked())))
                    && (!(c.getValue().hasTag(downfallMod.STUDY_AUTOMATON)
                    && (AbstractDungeon.player.chosenClass == downfallMod.Enums.THE_AUTOMATON || autoLocked())))
            ) {
                tmp.add(c.getKey());
            }
        }


        AbstractCard cStudy = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1))).makeStatEquivalentCopy();
        if (this.upgradeCard) {
            cStudy.upgrade();
        }
        if (this.free) {
            cStudy.freeToPlayOnce = true;
        }
        if (this.oneless) {
            cStudy.modifyCostForCombat(-1);
        }

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cStudy));

        this.isDone = true;
    }

}



