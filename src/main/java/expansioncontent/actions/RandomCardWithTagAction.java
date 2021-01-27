package expansioncontent.actions;


import automaton.AutomatonChar;
import champ.ChampChar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import expansioncontent.expansionContentMod;
import guardian.patches.GuardianEnum;
import slimebound.patches.SlimeboundEnum;
import theHexaghost.TheHexaghost;

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
        return UnlockTracker.isCharacterLocked(TheHexaghost.ID);
    }

    public static boolean champLocked() {
        return UnlockTracker.isCharacterLocked(ChampChar.ID);
    }

    public static boolean autoLocked() {
        return UnlockTracker.isCharacterLocked(AutomatonChar.ID);
    }

    public void update() {

        ArrayList<String> tmp = new ArrayList<>();

        for (Map.Entry<String, AbstractCard> stringAbstractCardEntry : CardLibrary.cards.entrySet()) {
            Map.Entry<String, AbstractCard> c = (Map.Entry) stringAbstractCardEntry;
            if (c.getValue().rarity != AbstractCard.CardRarity.SPECIAL &&
                    !c.getValue().hasTag(AbstractCard.CardTags.HEALING) &&
                    c.getValue().hasTag(tag)
                    && (!(c.getValue().hasTag(expansionContentMod.STUDY_SLIMEBOSS)
                    && AbstractDungeon.player.chosenClass == SlimeboundEnum.SLIMEBOUND))
                    && (!(c.getValue().hasTag(expansionContentMod.STUDY_GUARDIAN)
                    && (AbstractDungeon.player.chosenClass == GuardianEnum.GUARDIAN || guardianLocked())))
                    && (!(c.getValue().hasTag(expansionContentMod.STUDY_HEXAGHOST)
                    && (AbstractDungeon.player.chosenClass == TheHexaghost.Enums.THE_SPIRIT || hexaLocked())))
                    && (!(c.getValue().hasTag(expansionContentMod.STUDY_CHAMP)
                    && (AbstractDungeon.player.chosenClass == ChampChar.Enums.THE_CHAMP || champLocked())))
                    && (!(c.getValue().hasTag(expansionContentMod.STUDY_AUTOMATON)
                    && (AbstractDungeon.player.chosenClass == AutomatonChar.Enums.THE_AUTOMATON || autoLocked())))
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



