package downfall.patches;

import automaton.AutomatonChar;
import awakenedOne.AwakenedOneChar;
import champ.ChampChar;
import collector.CollectorChar;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.SadisticNature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import downfall.downfallMod;
import expansioncontent.patches.CardColorEnumPatch;
import guardian.characters.GuardianCharacter;
import slimebound.characters.SlimeboundCharacter;
import theHexaghost.TheHexaghost;

import static expansioncontent.expansionContentMod.*;

public class BanCards {
    @SpirePatch(
            clz = AbstractDungeon.class,
            method = "initializeCardPools"
    )
    public static class CardPatch {

        public static void Postfix(AbstractDungeon __instance) {
            if (EvilModeCharacterSelect.evilMode) {
                AbstractDungeon.colorlessCardPool.group.removeIf(c -> c instanceof SadisticNature);
                AbstractDungeon.srcColorlessCardPool.group.removeIf(c -> c instanceof SadisticNature);
            }

            if (!EvilModeCharacterSelect.evilMode && downfallMod.contentSharing_colorlessCards) {
                for (AbstractCard q : CardLibrary.getAllCards()) {
                    if (q.rarity != AbstractCard.CardRarity.SPECIAL && q.color == CardColorEnumPatch.CardColorPatch.BOSS) {

                        if (AbstractDungeon.player instanceof SlimeboundCharacter) {
                            if (q.hasTag(STUDY_SLIMEBOSS)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof TheHexaghost) {
                            if (q.hasTag(STUDY_HEXAGHOST)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof GuardianCharacter) {
                            if (q.hasTag(STUDY_GUARDIAN)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof ChampChar) {
                            if (q.hasTag(STUDY_CHAMP)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof AutomatonChar) {
                            if (q.hasTag(STUDY_AUTOMATON)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof CollectorChar) {
                            if (q.hasTag(STUDY_COLLECTOR)) {
                                continue;
                            }
                        }

                        if (AbstractDungeon.player instanceof AwakenedOneChar) {
                            if (q.hasTag(STUDY_AWAKENEDONE)) {
                                continue;
                            }
                        }
                        AbstractCard r = q.makeCopy();
                        AbstractDungeon.colorlessCardPool.group.add(r);
                        //AbstractDungeon.srcColorlessCardPool.group.add(r);
                    }
                }
            }
        }
    }
}