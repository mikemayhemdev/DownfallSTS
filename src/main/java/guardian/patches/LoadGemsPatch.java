package guardian.patches;

import basemod.BaseMod;
import basemod.abstracts.CustomSavableRaw;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves.ArrayListOfJsonElement;
import basemod.patches.com.megacrit.cardcrawl.saveAndContinue.SaveFile.ModSaves.HashMapOfJsonElement;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.google.gson.JsonElement;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import guardian.GuardianMod;
import guardian.cards.AbstractGuardianCard;

import java.util.Iterator;
import java.util.Map.Entry;

@SpirePatch(
        clz = CardCrawlGame.class,
        method = "loadPlayerSave"
)
public class LoadGemsPatch {
    public LoadGemsPatch() {
    }

    public static void Postfix(CardCrawlGame __instance, AbstractPlayer p) {
        GuardianMod.logger.info("Loading saved gems");
      for (AbstractCard c : p.masterDeck.group){
          if (c instanceof AbstractGuardianCard){
              GuardianMod.logger.info("Found guardian card to load");
              ((AbstractGuardianCard)c).loadGemMisc();
          }
      }
    }
}
